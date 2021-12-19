package com.cp.bootmongo.dto.common;

import lombok.Data;

import java.io.Serializable;


@Data
public class APIResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status;
    private String message;
    private int statusCode;
    private T body;

    @Override
    public String toString() {
        return "APIResponse{" +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", body=" + body +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}
