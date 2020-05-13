package com.yp.captchaimage.web;

import com.yp.captchaimage.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
@Getter
public class ApiHandler {

    public static final String API_RESULT_REQUEST_KEY = "API_RESULT_REQUEST_KEY";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ApiResult getApiResult() {
        return getRequestAtt(API_RESULT_REQUEST_KEY);
    }

    public void setApiResult(ApiResult result) {
        setRequestAtt(API_RESULT_REQUEST_KEY, result);
    }

    @SuppressWarnings("unchecked")
    public <T> T getRequestAtt(String key) {
        return (T) request.getAttribute(key);
    }

    public void setRequestAtt(String key, Object obj) {
        request.setAttribute(key, obj);
    }

    public void setSessionAttr(String key, Object obj) {
        request.getSession(true).setAttribute(key, obj);
    }

    public void removeSession(String key) {
        request.getSession(true).removeAttribute(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getSessionAttr(String key) {
        if (request.getSession() != null) {
            return (T) request.getSession().getAttribute(key);
        }
        return null;
    }

    public ApiResult apiResponseError(int status, String errMsg) {
        return apiResponseError(status, errMsg, null);
    }

    public ApiResult apiResponseError(int status, String errMsg, Exception exception) {
        ApiResult result = checkRequestResult();
        result.setMsg(errMsg);
        result.setCode(status);
        result.setException(true);
        result.setErrCause(exception == null ? errMsg : exception.getMessage());
        result.ready();
        return result;
    }

    public ApiResult apiResult(Object entityResults, StatusCode statusCode) {
        ApiResult result = checkRequestResult();
        result.setData(entityResults);
        result.setCode(statusCode.getCode());
        result.setMsg(statusCode.getMsg());
        result.setException(statusCode.getCode() >= StatusCode.CHECK_NULL_FRIENDSHIP.getCode());
        result.ready();
        return result;
    }

    //解决ApiResult为空的问题
    private ApiResult checkRequestResult() {
        ApiResult apiResult = getApiResult();
        if (apiResult == null) {
            apiResult = new ApiResult();
            setApiResult(apiResult);
        }
        return apiResult;
    }
}