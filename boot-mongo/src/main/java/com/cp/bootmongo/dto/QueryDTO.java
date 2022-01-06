package com.cp.bootmongo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long skuid;
    private Long productid;
    private String name;
    private String price;
    private String createdDate;
    private String brand;
    private String category;
    private String color;
    private String sortBy;
    @JsonIgnore
    private int pageNo;
    @JsonIgnore
    private int pageSize;
}
