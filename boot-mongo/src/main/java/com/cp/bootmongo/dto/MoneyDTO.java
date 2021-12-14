package com.cp.bootmongo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MoneyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;
    private String currency;

    public MoneyDTO(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public MoneyDTO(){}

}
