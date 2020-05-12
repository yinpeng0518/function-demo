package com.yp.smssend.exceptions;

import com.yp.smssend.enums.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateException extends RuntimeException {

    private int errCode;

    public ValidateException() {
        super();
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.errCode = statusCode.getCode();
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(StatusCode statusCode, Throwable cause) {
        super(statusCode.getMsg(), cause);
        this.errCode = statusCode.getCode();
    }

    public ValidateException(String message, Throwable cause, boolean enableSuppression, boolean
            writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
