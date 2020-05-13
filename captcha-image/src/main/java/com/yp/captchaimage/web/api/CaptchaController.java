package com.yp.captchaimage.web.api;

import com.yp.captchaimage.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api")
@Slf4j
@RestController
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping(value = "/generateCaptcha", produces = "image/png")
    public void generateCaptcha(HttpServletResponse response) {
        captchaService.generateCaptcha(response);
    }


    @PostMapping(value = "/checkCaptcha")
    public boolean checkCaptcha(String code) {
        return captchaService.checkCaptcha(code);
    }
}