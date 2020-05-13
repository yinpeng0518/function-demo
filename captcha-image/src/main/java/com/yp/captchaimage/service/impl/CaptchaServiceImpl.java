package com.yp.captchaimage.service.impl;

import com.yp.captchaimage.exceptions.BusinessException;
import com.yp.captchaimage.service.CaptchaService;
import com.yp.captchaimage.utils.CaptchaCodeUtils;
import com.yp.captchaimage.web.ApiHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/*
 * Created By YinP
 * 2020/5/12
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    public static final String STORE_CAPTCHA_CODE = "store_captcha_code";

    private final ApiHandler apiHandler;

    public CaptchaServiceImpl(ApiHandler apiHandler) {
        this.apiHandler = apiHandler;
    }


    @Override
    public void generateCaptcha(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            CaptchaCodeUtils.CaptchaCode captchaCode = CaptchaCodeUtils.getInstance().getCode();
            apiHandler.setSessionAttr(STORE_CAPTCHA_CODE, captchaCode.getText());
            byte[] bytes = captchaCode.getData();
            out.write(bytes, 0, bytes.length);
            out.flush();
        } catch (IOException e) {
            log.error("生成验证码失败");
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public boolean checkCaptcha(String code) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        String storeCode = apiHandler.getSessionAttr(STORE_CAPTCHA_CODE);
        apiHandler.removeSession(STORE_CAPTCHA_CODE);
        return code.trim().equalsIgnoreCase(storeCode);
    }
}
