package com.cp.bootmongo.service;

import com.cp.bootmongo.dto.CatalogDTO;
import com.cp.bootmongo.dto.QueryDTO;
import com.cp.bootmongo.exception.DocumentException;
import com.cp.bootmongo.model.CatalogModel;
import com.cp.bootmongo.repository.CatalogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public List<CatalogDTO> findAllCatalogsByQuery(QueryDTO queryDTO) throws DocumentException {
        try {
            Long skuId = queryDTO.getSkuid();
            if (null != skuId) {
                return queryBySkuId(skuId);
            }
            if (!StringUtils.isEmpty(queryDTO.getName())) {
                return queryBySkuName(queryDTO);
            }
            if (!StringUtils.isEmpty(queryDTO.getPrice())) {
                return queryByPrice(queryDTO);
            }
            if (!StringUtils.isEmpty(queryDTO.getCreatedDate())) {
                return queryByCreatedDate(queryDTO);
            }
        } catch (Exception ex) {
            throw new DocumentException("Error querying :", ex);
        }

        return Collections.emptyList();
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

    private Date convertStringToDate(String date, String format) throws java.text.ParseException {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(format);
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        formatter.setTimeZone(gmtTime);
        return formatter.parse(date);
    }

    private Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }

    private List<CatalogDTO> queryBySkuId(Long skuId) {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        CatalogModel catalogModel = catalogRepository.findBy_id(skuId);
        CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
        catalogDTOs.add(catalogDTO2);
        return catalogDTOs;
    }

    private List<CatalogDTO> queryBySkuName(QueryDTO queryDTO) {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        String name = queryDTO.getName();
        Pageable paging = PageRequest.of(queryDTO.getPageNo(), queryDTO.getPageSize());
        List<CatalogModel> catalogModels = catalogRepository.findByNameContains(name, paging);
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }

    private List<CatalogDTO> queryByPrice(QueryDTO queryDTO) {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        String price = queryDTO.getPrice();
        String[] pRanges = price.split(",");
        Pageable paging = PageRequest.of(queryDTO.getPageNo(), queryDTO.getPageSize());
        List<CatalogModel> catalogModels;
        if (pRanges.length == 1) {
            catalogModels = catalogRepository.findByPrice_Amount(new BigDecimal(pRanges[0]), paging);
        } else if (pRanges.length == 2) {
            catalogModels = catalogRepository.findCatalogModelsByPriceBetween(new BigDecimal(pRanges[0]), new BigDecimal(pRanges[1]), paging);
        } else {
            catalogModels = new ArrayList<>();
        }
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }

    private List<CatalogDTO> queryByCreatedDate(QueryDTO queryDTO) throws java.text.ParseException {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        String dates = queryDTO.getCreatedDate();
        String[] dateRanges = dates.split(",");
        Pageable paging = PageRequest.of(queryDTO.getPageNo(), queryDTO.getPageSize());
        List<CatalogModel> catalogModels;
        if (dateRanges.length == 1) {
            Date date = convertStringToDate(dateRanges[0], "yyyy-MM-dd");
            Date toDate = add(date, 5, 1);
            catalogModels = catalogRepository.findByCreatedDateIsBetween(date, toDate, paging);
        } else if (dateRanges.length == 2) {
            Date date1 = convertStringToDate(dateRanges[0], "yyyy-MM-dd");
            Date date2 = convertStringToDate(dateRanges[1], "yyyy-MM-dd");
            catalogModels = catalogRepository.findByCreatedDateBetween(date1, date2, paging);
        } else {
            catalogModels = new ArrayList<>();
        }
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }
}
