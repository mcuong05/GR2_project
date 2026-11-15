package com.shopmanager.backend.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends AppException {

    public BusinessException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}