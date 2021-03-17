package com.gzy.quickapi.ps5.repository;

import com.gzy.quickapi.ps5.dataobject.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductPriceInfoRepository extends JpaRepository<ProductPrice, String>, JpaSpecificationExecutor<ProductPrice> {

}
