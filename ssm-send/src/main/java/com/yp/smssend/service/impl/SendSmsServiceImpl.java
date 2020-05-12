package com.yp.smssend.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yp.smssend.enums.StatusCode;
import com.yp.smssend.service.SendSmsService;
import com.yp.smssend.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 * Created By YinP
 * 2020/5/12
 */
@Slf4j
@Service
public class SendSmsServiceImpl implements SendSmsService {

    private static final String SIGN_NAME = "狂神说Java";
    private static final String TEMPLATE_CODE = "SMS_189826041";
    private static final int RANDOM = 6;

    private final StringRedisTemplate redisTemplate;

    public SendSmsServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String sendSms(String phoneNum) {
        String code = redisTemplate.opsForValue().get(phoneNum);
        if (!StringUtils.isEmpty(code)) {
            return "短信验证码还存在，没有过期";
        }

        code = RandomUtil.getRandNumber(RANDOM);
        Map<String, String> templateParam = new HashMap<>();
        templateParam.put(phoneNum, code);
        boolean isSend = send(phoneNum, templateParam);
        if (isSend) {
            redisTemplate.opsForValue().set(phoneNum, code, 5, TimeUnit.MINUTES);
            return StatusCode.SEND_SUCCESS.getMsg();
        } else {
            return StatusCode.SEND_FAILURE.getMsg();
        }
    }

    private boolean send(String phoneNumbers, Map<String, String> templateParam) {

        DefaultProfile profile = DefaultProfile.getProfile("ch-hangzhou", "LTAI4G8YgfwQZK7tb4gyb3R8", "WMi3FTmxbe9xU6nwOiKXufo7ou6Foi");
        DefaultAcsClient client = new DefaultAcsClient(profile);

        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysMethod(MethodType.POST);
        commonRequest.setSysDomain("dysmsapi.aliyuncs.com");
        commonRequest.setSysVersion("2017-05-25");
        commonRequest.setSysAction("SendSms");

        commonRequest.putQueryParameter("PhoneNumbers", phoneNumbers);
        commonRequest.putQueryParameter("SignName", SIGN_NAME);
        commonRequest.putQueryParameter("TemplateCode", TEMPLATE_CODE);

        try {
            commonRequest.putQueryParameter("TemplateParam", Jackson2ObjectMapperBuilder.json().build().writeValueAsString(templateParam));
            CommonResponse commonResponse = client.getCommonResponse(commonRequest);
            log.debug(commonResponse.getData());
            return commonResponse.getHttpResponse().isSuccess();
        } catch (Exception e) {
            log.error("短信发送失败", e);
        }

        return false;
    }
}
