package com.cp.bootmongo.dto.common;

import lombok.Data;

import java.io.Serializable;


@Data
public class APIResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean success = Boolean.TRUE;
    private int status = 200;
    private String message;
    private T body;
    private String errorCode;

    @Override
    public String toString() {
        return "APIResponse{" +
                "success=" + success +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", body=" + body +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
