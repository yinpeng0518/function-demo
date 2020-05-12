package com.yp.smssend.exceptions;

import com.yp.smssend.enums.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private int errCode;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.errCode = statusCode.getCode();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(StatusCode statusCode, Throwable cause) {
        super(statusCode.getMsg(), cause);
        this.errCode = statusCode.getCode();
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean
            writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
