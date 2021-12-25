package com.cp.bootmongo.repository;

import com.cp.bootmongo.model.CatalogModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CatalogRepository extends MongoRepository<CatalogModel, Long> {
     CatalogModel findBy_id(Long skuId);
     List<CatalogModel> findByNameContains(String name , Pageable pageable);
     List<CatalogModel> findByPrice_Amount(BigDecimal amount , Pageable pageable);
     @Query("{ 'price.amount' : { $gte: ?0, $lte: ?1 } }")
     List<CatalogModel> findCatalogModelsByPriceBetween(BigDecimal amount1 , BigDecimal amount2 , Pageable pageable);
     @Query("{ 'createdDate' : { $gte: ?0, $lte: ?1 } }")
     List<CatalogModel> findByCreatedDateBetween(Date fromDate, Date toDate , Pageable pageable);
     @Query("{ 'createdDate' : { $gte: ?0, $lte: ?1 } }")
     List<CatalogModel> findByCreatedDateIsBetween(Date fromDate , Date toDate , Pageable pageable);
}

