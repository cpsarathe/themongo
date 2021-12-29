package com.cp.bootmongo.dto.feeds;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CatalogFeedsResponseBody implements Serializable  {
    private static final long serialVersionUID = 1L;
    private List<Product> products;
}
