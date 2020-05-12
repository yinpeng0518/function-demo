package com.yp.ssmsend;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmsSendApplicationTests {

    @Test
    void contextLoads() throws ClientException {
        DefaultProfile profile =DefaultProfile.getProfile("ch-hangzhou", "","" );
        DefaultAcsClient client = new DefaultAcsClient(profile);
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysMethod(MethodType.POST);
        commonRequest.setSysDomain("dysmsapi.aliyuncs.com");
        commonRequest.setSysVersion("2017-05-25");
        commonRequest.setSysAction("SendSms");
        commonRequest.putQueryParameter("RegionId","cn-hangzhou");
        CommonResponse commonResponse = client.getCommonResponse(commonRequest);
        System.out.println(commonResponse.getData());
    }
}
