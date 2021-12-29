package com.cp.bootmongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "catalog")
@Data
public class CatalogModel {
    @Id
    @Field("skuId")
    private Long _id;

    @Field("skuId")
    @Indexed(name="idx_catalog_sku_id" , unique = true , direction = IndexDirection.ASCENDING)
    private Long skuId;

    @Field("productId")
    @Indexed(name="idx_catalog_product_id" ,direction = IndexDirection.ASCENDING)
    private Long productId;

    @Field("name")
    @Indexed(name="idx_catalog_name" ,direction = IndexDirection.ASCENDING)
    private String name;

    @Field("description")
    private String description;

    @Field("link")
    private String link;

    @Field("price")
    private MoneyModelInfo price;

    @Field("availability")
    private String availability;

    @Field("imageLink")
    private String imageLink;

    @Field("active")
    private boolean active;

    @Field("category")
    @Indexed(name="idx_catalog_category")
    private List<String> category;

    @Field("brand")
    @Indexed(name="idx_catalog_brand")
    private String brand;

    @Field("gender")
    private String gender;

    @Field("color")
    private String color;

    @Field("size")
    private String size;

    @Field("freeShipping")
    private boolean freeShipping;

    @Field("attributes")
    private Object attributes;

    @Field("productOptions")
    private Object options;

    @Field("createdDate")
    @Indexed(name="idx_catalog_created_date_v1")
    private Date createdDate;

    @Field("salePrice")
    private MoneyModelInfo salePrice;

    public CatalogModel(){

    }

}
