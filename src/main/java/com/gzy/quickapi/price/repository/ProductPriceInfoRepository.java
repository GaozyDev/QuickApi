package com.gzy.quickapi.price.repository;

import com.gzy.quickapi.price.dataobject.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductPriceInfoRepository extends JpaRepository<ProductPrice, String>, JpaSpecificationExecutor<ProductPrice> {

}
