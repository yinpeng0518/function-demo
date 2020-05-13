package com.yp.captchaimage.service;

import javax.servlet.http.HttpServletResponse;

/*
 * Created By YinP
 * 2020/5/12
 */
public interface CaptchaService {

    void generateCaptcha (HttpServletResponse response);

    boolean checkCaptcha(String code);
}
