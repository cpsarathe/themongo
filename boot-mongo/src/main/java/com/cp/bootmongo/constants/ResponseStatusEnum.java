package com.cp.bootmongo.constants;

public enum ResponseStatusEnum {
    SUCCESS("SUCCESS"),
    BAD_REQUEST_ERROR("BAD_REQUEST_ERROR"),
    SYSTEM_ERROR("SYSTEM_ERROR");
    String name;
    ResponseStatusEnum(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
