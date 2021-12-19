package com.cp.bootmongo.validator;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DocumentError implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fieldName;
    private String value;
    private String message;

}
