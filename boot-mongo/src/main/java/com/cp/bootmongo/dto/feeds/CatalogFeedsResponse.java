package com.cp.bootmongo.dto.feeds;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogFeedsResponse implements Serializable  {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String status;
    private String message;
    private CatalogFeedsResponseBody body;
}
