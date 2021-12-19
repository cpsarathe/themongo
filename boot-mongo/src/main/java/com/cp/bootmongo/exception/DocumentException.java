package com.cp.bootmongo.exception;

public class DocumentException extends  RuntimeException {
    private static final long serialVersionUID = 1L;
    public DocumentException(String message) {
        super(message);
    }
    public DocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
