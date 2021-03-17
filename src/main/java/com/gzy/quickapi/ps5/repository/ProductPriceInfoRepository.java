package com.gzy.quickapi.ps5.repository;

import com.gzy.quickapi.ps5.dataobject.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPriceInfoRepository extends JpaRepository<ProductPrice, String> {

    List<ProductPrice> findByProductId(String productId);
}
