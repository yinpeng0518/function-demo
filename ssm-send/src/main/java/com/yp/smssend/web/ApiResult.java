package com.yp.smssend.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ApiResult {

    private int code = 2000;        //返回的代码，2000表示成功，其他表示失败
    private String msg;             //成功或失败时返回的错误信息
    private Object data;            //成功时返回的数据信息
    private long beginTime;         //请求开始时间戳
    private long elapsedTime;       //运行时间
    private boolean isException;    //是否有异常
    private String errCause;        //异常信息

    public ApiResult() {
        this.beginTime = System.currentTimeMillis();
    }

    public void ready() {
        this.elapsedTime = System.currentTimeMillis() - this.beginTime;
    }
}