package com.yp.smssend.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yp.smssend.enums.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.yp.smssend.web.api"}) // 注意哦，这里要加上需要扫描的包
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ApiHandler apiHandler;

    public ResponseAdvice(ApiHandler apiHandler) {
        this.apiHandler = apiHandler;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ApiResult那就没有必要进行额外的操作，返回false
        return !returnType.getGenericParameterType().equals(ApiResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ApiResult里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(apiHandler.apiResult(data, StatusCode.SUCCESS));
            } catch (JsonProcessingException e) {
                log.error("包装json失败：{}", e.getMessage(), e);
            }
        }
        // 将原本的数据包装在ApiResult里
        return apiHandler.apiResult(data, StatusCode.SUCCESS);
    }
}