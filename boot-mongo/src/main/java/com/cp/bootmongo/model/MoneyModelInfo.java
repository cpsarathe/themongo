package com.cp.bootmongo.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MoneyModelInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;
    private String currency;

    public MoneyModelInfo(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public MoneyModelInfo(){
    }
}
