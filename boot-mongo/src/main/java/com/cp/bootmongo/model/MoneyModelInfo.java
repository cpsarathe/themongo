package com.cp.bootmongo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Document(collection = "catalog")
public class MoneyModelInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field("amount")
    @Indexed(direction = IndexDirection.ASCENDING)
    private BigDecimal amount;

    private String currency;

    public MoneyModelInfo(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public MoneyModelInfo(){
    }
}
