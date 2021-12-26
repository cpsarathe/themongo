package com.cp.bootmongo.repository;

import com.cp.bootmongo.model.CatalogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CatalogRepository extends MongoRepository<CatalogModel, Long> {
     CatalogModel findBy_id(Long skuId);
     List<CatalogModel> findByNameContains(String name , Pageable pageable);
     Page<CatalogModel> findByPrice_Amount(BigDecimal amount , Pageable pageable);
     //by default between results are excluding given criteria i.e  gt>amount1 , lt<amount2
     @Query("{'price.amount':{$gte:?0,$lte:?1}}")
     Page<CatalogModel> findByPrice_AmountBetween(BigDecimal amount1 , BigDecimal amount2 , Pageable pageable);
     Page<CatalogModel> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate , Pageable pageable);
}

