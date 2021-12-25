package com.cp.bootmongo.service;

import com.cp.bootmongo.dto.CatalogDTO;
import com.cp.bootmongo.dto.QueryDTO;

import java.util.List;

public interface CatalogService {

    public void saveCatalogs(List<CatalogDTO> catalogDTOS);

    public List<CatalogDTO> findAllCatalogs();

    public void deleteDocumentBySkuId(Long skuId);

    public CatalogDTO findDocumentById(Long skuId);

    public List<CatalogDTO> findAllCatalogsByQuery(QueryDTO queryDTO);
}
