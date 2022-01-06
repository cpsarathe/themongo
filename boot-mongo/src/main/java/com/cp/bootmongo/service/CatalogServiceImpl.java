package com.cp.bootmongo.service;

import com.cp.bootmongo.dto.CatalogDTO;
import com.cp.bootmongo.dto.QueryDTO;
import com.cp.bootmongo.dto.feeds.Product;
import com.cp.bootmongo.exception.DocumentException;
import com.cp.bootmongo.model.CatalogModel;
import com.cp.bootmongo.repository.CatalogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQueries;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("cpCatalogService")
@CommonsLog
public class CatalogServiceImpl implements CatalogService {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
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

            if (!StringUtils.isEmpty(queryDTO.getBrand()) && !StringUtils.isEmpty(queryDTO.getCategory())) {
                return queryByCategoryAndBrand(queryDTO);
            }

            if (!StringUtils.isEmpty(queryDTO.getBrand())) {
                return queryByBrand(queryDTO);
            }
            if (!StringUtils.isEmpty(queryDTO.getCategory())) {
                return queryByCategory(queryDTO);
            }
            if (!StringUtils.isEmpty(queryDTO.getColor())) {
                return queryByColor(queryDTO);
            }
        } catch (Exception ex) {
            throw new DocumentException("Error querying :", ex);
        }

        return Collections.emptyList();
    }

    @Override
    public void saveCatalogProducts(List<Product> products) {
        log.info("Begin saveCatalogProducts with size :" + products.size());
        List<CatalogModel> catalogModels = new ArrayList<>();
        for (Product product : products) {
            CatalogModel catalogModel = convertProductToCatalogModel(product);
            catalogModels.add(catalogModel);
        }
        log.info("converted Product to catalogModel");
        catalogRepository.saveAll(catalogModels);
        log.info("End saveCatalogProducts with size :" + products.size());
    }


    @Override
    public void deleteDocumentBySkuId(Long skuId) {
        catalogRepository.deleteById(skuId);
    }


    private CatalogModel convertProductToCatalogModel(Product product) {
        CatalogModel catalogModel = objectMapper.convertValue(product, CatalogModel.class);
        catalogModel.setSkuId(RandomUtils.nextLong());
        catalogModel.setProductId(RandomUtils.nextLong());
        catalogModel.set_id(catalogModel.getSkuId());
        return catalogModel;
    }

    private CatalogModel convertCatalogDTOToCatalogModel(CatalogDTO catalogDTO) {
        CatalogModel catalogModel = objectMapper.convertValue(catalogDTO, CatalogModel.class);
        catalogModel.set_id(catalogDTO.getSkuId());
        return catalogModel;
    }

    private CatalogDTO convertCatalogModelToCatalogDTO(CatalogModel catalogModel) {
        return objectMapper.convertValue(catalogModel, CatalogDTO.class);
    }

    private LocalDateTime convertStringToDateWithGMTTimeZone(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = formatter.parse(date, t -> {
            LocalDate date1 = t.query(TemporalQueries.localDate());
            LocalTime time1 = t.query(TemporalQueries.localTime());
            return LocalDateTime.of(date1, time1 != null ? time1 : LocalTime.MIDNIGHT);
        });
        //default date coming from query params is considered to be in GMT time zone
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("GMT"));
        return zonedDateTime.toLocalDateTime();
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
        Pageable paging = buildPagingAndSorting(queryDTO);
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
        Pageable paging = buildPagingAndSorting(queryDTO);
        Page<CatalogModel> catalogModels;
        if (pRanges.length == 1) {
            catalogModels = catalogRepository.findByPrice_Amount(new BigDecimal(pRanges[0]), paging);
        } else if (pRanges.length == 2) {
            catalogModels = catalogRepository.findByPrice_AmountBetween(new BigDecimal(pRanges[0]), new BigDecimal(pRanges[1]), paging);
        } else {
            catalogModels = Page.empty();
        }
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }

    private List<CatalogDTO> queryByCreatedDate(QueryDTO queryDTO) {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        String dates = queryDTO.getCreatedDate();
        String[] dateRanges = dates.split(",");
        Pageable paging = buildPagingAndSorting(queryDTO);
        Page<CatalogModel> catalogModels;
        if (dateRanges.length == 1) {
            LocalDateTime date = convertStringToDateWithGMTTimeZone(dateRanges[0], YYYY_MM_DD);
            //add +4 hrs to GMT Time Zone so that when mongodb converts back to UTC it our query criteria remains as is
            date = date.plusHours(4);
            LocalDateTime toDate = date.plusDays(1);
            catalogModels = catalogRepository.findByCreatedDateBetween(date, toDate, paging);
        } else if (dateRanges.length == 2) {
            LocalDateTime date1 = convertStringToDateWithGMTTimeZone(dateRanges[0], YYYY_MM_DD);
            //add +4 hrs to GMT Time Zone so that when mongodb converts back to UTC it our query criteria remains as is
            date1 = date1.plusHours(4);
            LocalDateTime date2 = convertStringToDateWithGMTTimeZone(dateRanges[1], YYYY_MM_DD);
            date2 = date2.plusHours(4);
            catalogModels = catalogRepository.findByCreatedDateBetween(date1, date2, paging);
        } else {
            catalogModels = Page.empty();
        }
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }

    private List<CatalogDTO> queryByBrand(QueryDTO queryDTO) {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        String dates = queryDTO.getBrand();
        String[] brands = dates.split(",");
        Pageable paging = buildPagingAndSorting(queryDTO);
        List<String> brandList = Arrays.asList(brands);
        Page<CatalogModel> catalogModels = catalogRepository.findByBrandIn(brandList, paging);
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }

    private List<CatalogDTO> queryByCategory(QueryDTO queryDTO) {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        String category = queryDTO.getCategory();
        String[] categories = category.split(",");
        Pageable paging = buildPagingAndSorting(queryDTO);
        List<String> categoriesList = Arrays.asList(categories);
        Page<CatalogModel> catalogModels = catalogRepository.findByCategoryIn(categoriesList, paging);
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }

    private List<CatalogDTO> queryByColor(QueryDTO queryDTO) {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        String color = queryDTO.getColor();
        String[] colors = color.split(",");
        Pageable paging = buildPagingAndSorting(queryDTO);
        List<String> colorList = Arrays.asList(colors);
        Page<CatalogModel> catalogModels = catalogRepository.findByColorIn(colorList, paging);
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }

    private List<CatalogDTO> queryByCategoryAndBrand(QueryDTO queryDTO) {
        List<CatalogDTO> catalogDTOs = new ArrayList<>();
        String category = queryDTO.getCategory();
        String[] categories = category.split(",");
        List<String> categoriesList = Arrays.asList(categories);
        String brand = queryDTO.getBrand();
        String[] brands = brand.split(",");
        List<String> brandList = Arrays.asList(brands);
        Pageable paging = buildPagingAndSorting(queryDTO);
        Page<CatalogModel> catalogModels = catalogRepository.findByCategoryInAndBrandIn(categoriesList, brandList, paging);
        for (CatalogModel catalogModel : catalogModels) {
            CatalogDTO catalogDTO2 = convertCatalogModelToCatalogDTO(catalogModel);
            catalogDTOs.add(catalogDTO2);
        }
        return catalogDTOs;
    }

    private Pageable buildPagingAndSorting(QueryDTO queryDTO) {
        String sortBy = queryDTO.getSortBy();
        Sort sortOrder = null;
        if(!StringUtils.isEmpty(sortBy)) {
            String[] sorts = !StringUtils.isEmpty(sortBy) ? sortBy.split(" ") : null;
            if (sorts[1].equalsIgnoreCase(Sort.Direction.ASC.name())) {
                sortOrder = Sort.by(Sort.Direction.ASC, sorts[0]);
            }
            else if (sorts[1].equalsIgnoreCase(Sort.Direction.DESC.name())) {
                sortOrder = Sort.by(Sort.Direction.DESC, sorts[0]);
            }
        }
        Pageable paging = null;
        if (sortOrder != null) {
            paging = PageRequest.of(queryDTO.getPageNo(), queryDTO.getPageSize(), sortOrder);
        } else {
            paging = PageRequest.of(queryDTO.getPageNo(), queryDTO.getPageSize());
        }
        return paging;
    }
}
