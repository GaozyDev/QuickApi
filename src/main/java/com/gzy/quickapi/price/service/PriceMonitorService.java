package com.gzy.quickapi.price.service;

import com.gzy.quickapi.price.crawler.WebCrawler;
import com.gzy.quickapi.price.dataobject.ProductPrice;
import com.gzy.quickapi.price.dto.HistoryPriceInfo;
import com.gzy.quickapi.price.dto.ProductPriceInfo;
import com.gzy.quickapi.price.dto.AveragePriceInfo;
import com.gzy.quickapi.price.enums.ProductIdEnum;
import com.gzy.quickapi.price.repository.ProductPriceInfoRepository;
import com.gzy.quickapi.price.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PriceMonitorService {

    @Autowired
    private WebCrawler webCrawler;

    @Autowired
    private ProductPriceInfoRepository productPriceInfoRepository;

    // 光驱版
    public static AveragePriceInfo opticalAveragePriceInfo;

    // 数字版
    public static AveragePriceInfo digitalAveragePriceInfo;

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

    public HistoryPriceInfo getHistoryPriceInfo(ProductIdEnum productIdEnum) {
        List<ProductPrice> productPriceList = productPriceInfoRepository.findAll((Specification<ProductPrice>) (root, query, criteriaBuilder) -> {
            Path<String> productId = root.get("productId");
            query.where(criteriaBuilder.equal(productId, productIdEnum.getProductId()))
                    .orderBy(criteriaBuilder.asc(root.get("createTime")));
            return query.getRestriction();
        });
        return processData(productPriceList);
    }

    private HistoryPriceInfo processData(List<ProductPrice> productPriceList) {

        List<Double> averagePriceList = new ArrayList<>();
        List<Double> minAveragePriceList = new ArrayList<>();
        List<Double> minPriceList = new ArrayList<>();
        List<String> labelList = new ArrayList<>();

        Date currentDate = new Date();
        Calendar todayCalendar = Calendar.getInstance(Locale.CHINA);
        todayCalendar.setTime(currentDate);
        int historyDataIndex = 0;
        for (int i = productPriceList.size() - 1; i >= 0; i--) {
            Date date = productPriceList.get(i).getCreateTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (!(todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                    todayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    todayCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
            ) {
                historyDataIndex = i;
                break;
            }
        }
        List<ProductPrice> lastData = productPriceList.subList(0, historyDataIndex + 1);

        int dayStep = lastData.size() / 10 + 1;

        double averagePrice = 0;
        double minAveragePrice = 0;
        double minPrice = 0;
        Date date;
        for (int i = 0; i < lastData.size(); i++) {
            ProductPrice productPrice = lastData.get(i);
            averagePrice += productPrice.getAveragePrice();
            minAveragePrice += productPrice.getMinAveragePrice();
            minPrice += productPrice.getMinPrice();
            date = lastData.get(i).getCreateTime();

            if ((i + 1) % dayStep == 0) {
                averagePriceList.add(averagePrice / dayStep);
                minAveragePriceList.add(minAveragePrice / dayStep);
                minPriceList.add(minPrice / dayStep);
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                String dateString = sdf.format(date);
                labelList.add(dateString);

                averagePrice = 0;
                minAveragePrice = 0;
                minPrice = 0;
            }
        }

        if (!productPriceList.isEmpty()) {
            ProductPrice newData = productPriceList.get(productPriceList.size() - 1);
            averagePriceList.add(newData.getAveragePrice());
            minAveragePriceList.add(newData.getMinAveragePrice());
            minPriceList.add(newData.getMinPrice());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String dateString = sdf.format(PriceMonitorService.opticalAveragePriceInfo.getUpdateDate());
            labelList.add(dateString);
        }

        double priceChange = 0;
        if (minAveragePriceList.size() >= 2) {
            double lastPrice = minAveragePriceList.get(minAveragePriceList.size() - 2);
            double nowPrice = minAveragePriceList.get(minAveragePriceList.size() - 1);
            priceChange = nowPrice - lastPrice;
        }

        HistoryPriceInfo historyPriceInfo = new HistoryPriceInfo();
        historyPriceInfo.setAveragePriceList(averagePriceList);
        historyPriceInfo.setMinAveragePriceList(minAveragePriceList);
        historyPriceInfo.setMinPriceList(minPriceList);
        historyPriceInfo.setLabelList(labelList);
        historyPriceInfo.setPriceChange(priceChange);
        return historyPriceInfo;
    }
}
