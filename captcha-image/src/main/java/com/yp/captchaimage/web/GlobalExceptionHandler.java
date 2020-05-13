package com.yp.captchaimage.web;


import com.yp.captchaimage.enums.StatusCode;
import com.yp.captchaimage.exceptions.BizException;
import com.yp.captchaimage.exceptions.LoginException;
import com.yp.captchaimage.exceptions.UnauthorizedException;
import com.yp.captchaimage.exceptions.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private final ApiHandler apiHandler;

    public GlobalExceptionHandler(ApiHandler apiHandler) {
        this.apiHandler = apiHandler;
    }

    @ExceptionHandler(value = Exception.class)
    public ApiResult exceptionHandler(Exception e){

        log.error("系统异常",e);
        if (e instanceof BizException){
            BizException bizException = (BizException)e;
            return apiHandler.apiResponseError(bizException.getCode(),bizException.getMessage(),null);
        }

        if (e instanceof ValidateException){
            ValidateException validateException = (ValidateException)e;
            return apiHandler.apiResponseError(StatusCode.VALIDATE_FAILURE.getCode(),validateException.getMessage(), e);
        }

        return apiHandler.apiResponseError(StatusCode.FAILURE.getCode(),StatusCode.FAILURE.getMsg(), e);
    }

    @ExceptionHandler(value = LoginException.class)
    public ApiResult loginException(LoginException ex, HttpServletResponse response) {
        log.warn(ex.getMessage());
        return apiHandler.apiResponseError(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ApiResult unauthorizedException(UnauthorizedException ex, HttpServletResponse response) {
        log.warn(ex.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return apiHandler.apiResponseError(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ApiResult requestMissingServletRequest(MissingServletRequestParameterException ex, HttpServletResponse response) {
        log.warn(ex.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return apiHandler.apiResponseError(StatusCode.CHECK_NULL_FRIENDSHIP.getCode(), "缺少必要参数:" + ex.getParameterName());
    }

    @ExceptionHandler(value = TypeMismatchException.class)
    public ApiResult requestTypeMismatch(TypeMismatchException ex, HttpServletResponse response) {
        log.warn(ex.getMessage());
        String name = ex.getPropertyName();
        if (name == null || ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException mex = (MethodArgumentTypeMismatchException)ex;
            name = mex.getName();
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return apiHandler.apiResponseError(StatusCode.BAD_REQUEST.getCode(), "参数类型不匹配,参数"
                + name + "类型应该为" + ex.getRequiredType());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult requestMethodNotSupported (HttpRequestMethodNotSupportedException ex, HttpServletResponse response) {
        log.warn(ex.getMessage());
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return apiHandler.apiResponseError(StatusCode.METHOD_NOT_ALLOWED.getCode(), ex.getMethod() + StatusCode.METHOD_NOT_ALLOWED.getMsg());
    }

}
