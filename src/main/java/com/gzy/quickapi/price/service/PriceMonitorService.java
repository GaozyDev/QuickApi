package com.gzy.quickapi.price.service;

import com.gzy.quickapi.price.crawler.WebCrawler;
import com.gzy.quickapi.price.dataobject.ProductPrice;
import com.gzy.quickapi.price.dto.ProductPriceInfo;
import com.gzy.quickapi.price.dto.AveragePriceInfo;
import com.gzy.quickapi.price.enums.ProductIdEnum;
import com.gzy.quickapi.price.repository.ProductPriceInfoRepository;
import com.gzy.quickapi.price.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class PriceMonitorService {

    @Autowired
    private WebCrawler webCrawler;

    @Autowired
    private ProductPriceInfoRepository productPriceInfoRepository;

    // 光驱版
    public static AveragePriceInfo opticalDriveAveragePriceInfo;

    // 数字版
    public static AveragePriceInfo digitalEditionAveragePriceInfo;

    public AveragePriceInfo getProductPriceInfos(ProductIdEnum productIdEnum) {
        String url = productIdEnum.getUrl();

        List<ProductPriceInfo> productPriceInfoList = webCrawler.startWebCrawler(url);

        AveragePriceInfo averagePriceInfo = new AveragePriceInfo();
        averagePriceInfo.setUpdateDate(new Date());
        averagePriceInfo.setProductDataList(productPriceInfoList);
        double averagePrice = calAveragePrice(productPriceInfoList);
        double minAveragePrice = calAveragePrice(productPriceInfoList.subList(0, productPriceInfoList.size() / 5));
        double minPrice = calMinPrice(productPriceInfoList);
        averagePriceInfo.setAveragePrice(averagePrice);
        averagePriceInfo.setMinAveragePrice(minAveragePrice);
        averagePriceInfo.setMinPrice(minPrice);

        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(KeyUtil.genUniqueKey());
        productPrice.setAveragePrice(averagePriceInfo.getAveragePrice());
        productPrice.setMinAveragePrice(averagePriceInfo.getMinAveragePrice());
        productPrice.setMinPrice(averagePriceInfo.getMinPrice());
        productPrice.setProductId(productIdEnum.getProductId());
        productPrice.setCreateTime(new Date());
        productPriceInfoRepository.save(productPrice);

        return averagePriceInfo;
    }

    private double calAveragePrice(List<ProductPriceInfo> productPriceInfoList) {
        double averagePrice = Double.MAX_VALUE;
        OptionalDouble average = productPriceInfoList.stream().mapToDouble(ProductPriceInfo::getPrice).average();
        if (average.isPresent()) {
            averagePrice = average.getAsDouble();
        }
        return averagePrice;
    }

    private double calMinPrice(List<ProductPriceInfo> productPriceInfoList) {
        double minPrice = Double.MAX_VALUE;
        OptionalDouble min = productPriceInfoList.stream().mapToDouble(ProductPriceInfo::getPrice).min();
        if (min.isPresent()) {
            minPrice = min.getAsDouble();
        }
        return minPrice;
    }

    public List<ProductPrice> getProductPriceList(ProductIdEnum productIdEnum) {
        return productPriceInfoRepository.findAll((Specification<ProductPrice>) (root, query, criteriaBuilder) -> {
            Path<String> productId = root.get("productId");
            query.where(criteriaBuilder.equal(productId, productIdEnum.getProductId()))
                    .orderBy(criteriaBuilder.asc(root.get("createTime")));
            return query.getRestriction();
        });
    }
}
