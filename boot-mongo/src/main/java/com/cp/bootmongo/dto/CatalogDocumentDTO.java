package com.cp.bootmongo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CatalogDocumentDTO implements Serializable  {
    private static final long serialVersionUID = 1L;
    List<CatalogDTO> catalogs;
}
