package com.it.utils.constant;

import lombok.Getter;

@Getter
public enum StatusConstant {
    ACTIVE("Kích hoạt"),
    LOCKED("Khóa");

    private final String value;
    StatusConstant(String value) {
        this.value = value;
    }
}
