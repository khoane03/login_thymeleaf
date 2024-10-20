package com.it.exception;


import lombok.Getter;

@Getter
public enum ErrorMessage {
    USER_EXISTS("Tài khoản đã tồn tại !"),
    USER_NOT_FOUND("Tài khoản không tồn tại !"),
    PASSWORD_NOT_MATCH("Mật khẩu không khớp !"),
    USER_NOT_ACTIVE("Tài khoản bị khoá !"),
    USERNAME_PASSWORD_INCORRECT("Tài khoản hoặc mật khẩu không đúng !");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
