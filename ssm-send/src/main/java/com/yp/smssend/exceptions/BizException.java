package com.yp.smssend.exceptions;

import com.yp.smssend.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BizException extends RuntimeException {

    private int code;           //异常状态码
    private String message;     //异常信息

    protected BizException() {
    }

    public BizException(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMsg();
    }

    public BizException(StatusCode statusCode, String field) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMsg() + field;
    }
}
