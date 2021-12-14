package com.cp.bootmongo.service;

import com.cp.bootmongo.dto.CatalogDTO;
import com.cp.bootmongo.model.CatalogModel;
import com.cp.bootmongo.repository.CatalogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cpCatalogService")
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void saveCatalogs(List<CatalogDTO> catalogDTOS) {
        List<CatalogModel> catalogModels = new ArrayList<>();
        for (CatalogDTO catalogDTO : catalogDTOS) {
            CatalogModel catalogModel = convertCatalogDTOToCatalogModel(catalogDTO);
            catalogModels.add(catalogModel);
        }
        catalogRepository.saveAll(catalogModels);
    }

    @Override
    public List<CatalogDTO> findAllCatalogs() {
        List<CatalogModel> catalogs = catalogRepository.findAll();
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        for (CatalogModel catalogModel : catalogs) {
            CatalogDTO catalogDTO = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO);
        }

        return catalogDTOs;
    }

    @Override
    public CatalogDTO findDocumentById(Long skuId) {
        CatalogModel catalogModel = catalogRepository.findBy_id(skuId);
        return convertCatalogModelToCatalogDTO(catalogModel);
    }


    @Override
    public void deleteDocumentBySkuId(Long skuId) {
        catalogRepository.deleteById(skuId);
    }


    private CatalogModel convertCatalogDTOToCatalogModel(CatalogDTO catalogDTO) {
        CatalogModel catalogModel = objectMapper.convertValue(catalogDTO, CatalogModel.class);
        catalogModel.set_id(catalogDTO.getSkuId());
        return catalogModel;
    }

    private CatalogDTO convertCatalogModelToCatalogDTO(CatalogModel catalogModel) {
        CatalogDTO catalogDTO = objectMapper.convertValue(catalogModel, CatalogDTO.class);
        return catalogDTO;
    }
}
