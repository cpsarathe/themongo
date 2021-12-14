package com.cp.bootmongo.repository;

import com.cp.bootmongo.model.CatalogModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatalogRepository extends MongoRepository<CatalogModel, Long> {
     CatalogModel findBy_id(Long skuId);

}

