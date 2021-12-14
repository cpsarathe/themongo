package com.cp.bootmongo.controller;

import com.cp.bootmongo.dto.CatalogDTO;
import com.cp.bootmongo.dto.CatalogDocumentDTO;
import com.cp.bootmongo.dto.common.APIResponse;
import com.cp.bootmongo.service.CatalogService;
import com.cp.bootmongo.wrapper.ApiResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ApiResponseWrapper apiResponseWrapper;

    @GetMapping("/documents")
    public APIResponse<CatalogDocumentDTO> getAllDocuments() {
        List<CatalogDTO> catalogDTOS = catalogService.findAllCatalogs();
        CatalogDocumentDTO catalogDocumentDTO = new CatalogDocumentDTO();
        catalogDocumentDTO.setCatalogs(catalogDTOS);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), Boolean.TRUE,
                "success", catalogDocumentDTO);
    }

    @GetMapping("/documents/{id}")
    public APIResponse<CatalogDTO> getDocumentById(@PathVariable("id") Long skuId) {
        CatalogDTO catalogDTO = catalogService.findDocumentById(skuId);
        if(catalogDTO==null) {
            return apiResponseWrapper.wrapResponse(HttpStatus.NOT_FOUND.value(), Boolean.FALSE,
                    "Not Found", catalogDTO);
        }
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), Boolean.TRUE,
                "fetched successfully", catalogDTO);
    }

    @PostMapping("/documents")
    public APIResponse<String> saveDocument(@RequestBody CatalogDocumentDTO catalogDocumentDTO) {
        catalogService.saveCatalogs(catalogDocumentDTO.getCatalogs());
        return apiResponseWrapper.wrapResponse(HttpStatus.CREATED.value(), Boolean.TRUE,
                "saved successfully", null);
    }

    @DeleteMapping("/documents/{id}")
    public APIResponse<String> deleteDocumentById(@PathVariable("id") Long skuId) {
        catalogService.deleteDocumentBySkuId(skuId);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), Boolean.TRUE,
                "deleted successfully", String.valueOf(skuId));
    }
}
