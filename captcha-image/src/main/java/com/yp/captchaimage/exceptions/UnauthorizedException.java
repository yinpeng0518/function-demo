package com.yp.captchaimage.exceptions;


import com.yp.captchaimage.enums.StatusCode;

public class UnauthorizedException extends BizException {

    public UnauthorizedException(StatusCode statusCode) {
        super(statusCode);
    }

    public UnauthorizedException(StatusCode statusCode, String field) {
        super(statusCode, field);
    }

    public UnauthorizedException(int code, String message) {
        super(code, message);
    }
}
