package com.gzy.quickapi.price.service;

import com.gzy.quickapi.price.crawler.WebCrawler;
import com.gzy.quickapi.price.dataobject.ProductPrice;
import com.gzy.quickapi.price.dto.ProductPriceInfo;
import com.gzy.quickapi.price.dto.ProductPriceInfos;
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
    public static ProductPriceInfos opticalDriveProductPriceInfos;

    // 数字版
    public static ProductPriceInfos digitalEditionProductPriceInfos;

    public ProductPriceInfos getProductPriceInfos(ProductIdEnum productIdEnum) {
        String url;
        if (productIdEnum == ProductIdEnum.PS5_OPTICAL_DRIVE) {
            url = "https://search.smzdm.com/?c=home&s=ps5%E5%85%89%E9%A9%B1%E7%89%88&brand_id=249&min_price=3500&max_price=5500&v=b&p=1";
        } else {
            url = "https://search.smzdm.com/?c=home&s=ps5%E6%95%B0%E5%AD%97%E7%89%88&brand_id=249&min_price=3000&max_price=5000&v=b&p=1";
        }

        List<ProductPriceInfo> productPriceInfoList = webCrawler.startWebCrawler(url);

        ProductPriceInfos productPriceInfos = new ProductPriceInfos();
        productPriceInfos.setUpdateDate(new Date());
        productPriceInfos.setProductDataList(productPriceInfoList);
        double averagePrice = calAveragePrice(productPriceInfoList);
        double minAveragePrice = calAveragePrice(productPriceInfoList.subList(0, productPriceInfoList.size() / 5));
        double minPrice = calMinPrice(productPriceInfoList);
        productPriceInfos.setAveragePrice(averagePrice);
        productPriceInfos.setMinAveragePrice(minAveragePrice);
        productPriceInfos.setMinPrice(minPrice);

        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(KeyUtil.genUniqueKey());
        productPrice.setAveragePrice(productPriceInfos.getAveragePrice());
        productPrice.setMinAveragePrice(productPriceInfos.getMinAveragePrice());
        productPrice.setMinPrice(productPriceInfos.getMinPrice());
        productPrice.setProductId(String.valueOf(productIdEnum.getProductId()));
        productPrice.setCreateTime(new Date());
        productPriceInfoRepository.save(productPrice);

        return productPriceInfos;
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
            query.where(criteriaBuilder.equal(productId, String.valueOf(productIdEnum.getProductId())))
                    .orderBy(criteriaBuilder.asc(root.get("createTime")));
            return query.getRestriction();
        });
    }
}
