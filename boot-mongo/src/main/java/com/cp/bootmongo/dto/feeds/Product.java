package com.cp.bootmongo.dto.feeds;

import com.cp.bootmongo.dto.MoneyDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private Long _id;
    private Long skuId;
    private Long productId;
    private String name;
    private String description;
    private String link;
    private MoneyDTO price;
    private MoneyDTO salePrice;
    private String availability;
    private String imageLink;
    private boolean active;
    private List<String> category;
    private String brand;
    private String gender;
    private String color;
    private String size;
    private boolean freeShipping;
    private Object attributes;
    private Object options;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
}
