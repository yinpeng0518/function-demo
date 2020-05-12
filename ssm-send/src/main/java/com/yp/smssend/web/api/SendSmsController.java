package com.yp.smssend.web.api;

import com.yp.smssend.service.SendSmsService;
import org.springframework.web.bind.annotation.*;

/*
 * Created By YinP
 * 2020/5/12
 */
@CrossOrigin  //支持跨域请求
@RestController
@RequestMapping("/api")
public class SendSmsController {

    private final SendSmsService sendSmsService;

    public SendSmsController(SendSmsService sendSmsService) {
        this.sendSmsService = sendSmsService;
    }

    @GetMapping("/sendSms/{phoneNum}")
    public String sendSms(@PathVariable String phoneNum) {
        return sendSmsService.sendSms(phoneNum);
    }
}
