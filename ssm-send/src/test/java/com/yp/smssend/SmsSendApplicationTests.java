package com.yp.smssend;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class SmsSendApplicationTests {

    @Test
    void contextLoads() throws ClientException, JsonProcessingException {
        //连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("ch-hangzhou", "LTAI4G8YgfwQZK7tb4gyb3R8", "WMi3FTmxbe9xU6nwOiKXufo7ou6Foi");
        DefaultAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysMethod(MethodType.POST);
        commonRequest.setSysDomain("dysmsapi.aliyuncs.com");  //固定值
        commonRequest.setSysVersion("2017-05-25");  //固定值
        commonRequest.setSysAction("SendSms");

        //自定义参数
        commonRequest.putQueryParameter("PhoneNumbers", "18627883927");
        commonRequest.putQueryParameter("SignName", "狂神说Java");             //签名
        commonRequest.putQueryParameter("TemplateCode", "SMS_189826041");     //模版code
        //构建一个短信验证码
        Map<String, Integer> map = new HashMap<>();
        map.put("code", 2233); //测试用
        commonRequest.putQueryParameter("TemplateParam", Jackson2ObjectMapperBuilder.json().build().writeValueAsString(map));
        CommonResponse commonResponse = client.getCommonResponse(commonRequest);
        System.out.println(commonResponse.getData());
    }
}
