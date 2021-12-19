package com.cp.bootmongo.validator;

import com.cp.bootmongo.dto.CatalogDTO;
import com.cp.bootmongo.dto.CatalogDocumentDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CatalogValidator implements DocumentValidator {

    private static final BigDecimal ZERO = new BigDecimal("0");
    @Override
    public List<DocumentError> validate(Object obj) {
        CatalogDocumentDTO catalogDocumentDTO = (CatalogDocumentDTO) obj;
        List<DocumentError> documentErrors = new ArrayList<>();
        List<CatalogDTO> catalogDTOS = catalogDocumentDTO.getCatalogs();
        for (CatalogDTO catalogDTO : catalogDTOS) {
            validateDocument(catalogDTO, documentErrors);
        }
        return documentErrors;
    }

    public void validateDocument(CatalogDTO catalogDTO, List<DocumentError> documentErrors) {
        if(catalogDTO.getSkuId()==null || catalogDTO.getSkuId()<=0 ) {
           DocumentError documentError =  DocumentError.builder()
                    .fieldName("skuId")
                    .value(String.valueOf(catalogDTO.getSkuId()))
                    .message("Values can not be null or <= 0").build();
            documentErrors.add(documentError);
        }
        if(catalogDTO.getProductId()==null || catalogDTO.getProductId()<=0 ) {
            DocumentError documentError =  DocumentError.builder()
                    .fieldName("productId")
                    .value(String.valueOf(catalogDTO.getSkuId()))
                    .message("Values can not be null or <= 0").build();
            documentErrors.add(documentError);
        }

        if(catalogDTO.getPrice()==null) {
            DocumentError documentError =  DocumentError.builder()
                    .fieldName("price")
                    .value(String.valueOf(catalogDTO.getPrice()))
                    .message("Values can not be null or empty").build();
            documentErrors.add(documentError);
        }

        if(catalogDTO.getPrice()!=null
                &&
           (   catalogDTO.getPrice().getAmount()==null
            || catalogDTO.getPrice().getAmount().compareTo(ZERO) < 0
           )
           ) {
            DocumentError documentError =  DocumentError.builder()
                    .fieldName("price.amount")
                    .value(String.valueOf(catalogDTO.getPrice().getAmount()))
                    .message("Price amount can not be null or < 0").build();
            documentErrors.add(documentError);
        }
    }
}
