package com.cp.bootmongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "catalog-feed")
@Data
public class CatalogModel {
    @Id
    @Field("skuId")
    private Long _id;
    @Field("skuId")
    private Long skuId;
    @Field("productId")
    private Long productId;
    @Field("name")
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
    private List<String> category;
    @Field("brand")
    private String brand;
    @Field("gender")
    private String gender;
    @Field("color")
    private String color;
    @Field("size")
    private String size;
    @Field("freeShipping")
    private boolean freeShipping;

    public CatalogModel(){

    }


}
