package com.yp.captchaimage.exceptions;


import com.yp.captchaimage.enums.StatusCode;

public class LoginException extends BizException {

    public LoginException(StatusCode statusCode) {
        super(statusCode);
    }

    public LoginException(StatusCode statusCode, String field) {
        super(statusCode, field);
    }

    public LoginException(int code, String message) {
        super(code, message);
    }
}
