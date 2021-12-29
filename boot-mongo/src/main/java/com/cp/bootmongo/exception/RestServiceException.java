package com.cp.bootmongo.exception;

public class RestServiceException extends  RuntimeException {
    private static final long serialVersionUID = 1L;
    public RestServiceException(String message) {
        super(message);
    }
    public RestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
