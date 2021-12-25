package com.cp.bootmongo.controller;

import com.cp.bootmongo.constants.ResponseStatusEnum;
import com.cp.bootmongo.dto.CatalogDTO;
import com.cp.bootmongo.dto.CatalogDocumentDTO;
import com.cp.bootmongo.dto.QueryDTO;
import com.cp.bootmongo.dto.common.APIResponse;
import com.cp.bootmongo.helper.Beautifier;
import com.cp.bootmongo.service.CatalogService;
import com.cp.bootmongo.validator.DocumentError;
import com.cp.bootmongo.validator.DocumentValidator;
import com.cp.bootmongo.wrapper.ApiResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private DocumentValidator documentValidator;

    @Autowired
    private Beautifier beautifier;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ApiResponseWrapper apiResponseWrapper;

    @GetMapping("/documents")
    public APIResponse<CatalogDocumentDTO> getAllDocuments() {
        List<CatalogDTO> catalogDTOS = catalogService.findAllCatalogs();
        CatalogDocumentDTO catalogDocumentDTO = new CatalogDocumentDTO();
        catalogDocumentDTO.setCatalogs(catalogDTOS);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS,
                "success", catalogDocumentDTO);
    }

    @GetMapping("/documents/{id}")
    public APIResponse<CatalogDTO> getDocumentById(@PathVariable("id") Long skuId) {
        CatalogDTO catalogDTO = catalogService.findDocumentById(skuId);
        if (catalogDTO == null) {
            return apiResponseWrapper.wrapResponse(HttpStatus.NOT_FOUND.value(), ResponseStatusEnum.BAD_REQUEST_ERROR,
                    "Not Found", catalogDTO);
        }
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS,
                "fetched successfully", catalogDTO);
    }

    @GetMapping("/documents/search")
    public APIResponse<List<CatalogDTO>> findDocumentsBy(@ModelAttribute QueryDTO queryDTO, BindingResult bindingResult) {
        List<CatalogDTO> catalogDTOS = catalogService.findAllCatalogsByQuery(queryDTO);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS,
                "fetched successfully", catalogDTOS);
    }

    @PostMapping("/documents")
    public APIResponse<String> saveDocument(@RequestBody CatalogDocumentDTO catalogDocumentDTO, BindingResult bindingResult) {
        List<DocumentError> documentErrors = documentValidator.validate(catalogDocumentDTO);
        if (!CollectionUtils.isEmpty(documentErrors)) {
            return apiResponseWrapper.wrapResponse(HttpStatus.BAD_REQUEST.value(), ResponseStatusEnum.BAD_REQUEST_ERROR,
                    "Bad request", beautifier.beautify(documentErrors));
        }
        catalogService.saveCatalogs(catalogDocumentDTO.getCatalogs());
        return apiResponseWrapper.wrapResponse(HttpStatus.CREATED.value(), ResponseStatusEnum.SUCCESS,
                "saved successfully", null);
    }

    @DeleteMapping("/documents/{id}")
    public APIResponse<String> deleteDocumentById(@PathVariable("id") Long skuId) {
        catalogService.deleteDocumentBySkuId(skuId);
        return apiResponseWrapper.wrapResponse(HttpStatus.OK.value(), ResponseStatusEnum.SUCCESS,
                "deleted successfully", String.valueOf(skuId));
    }

}
