package com.yinp.qrcode;

import com.yinp.qrcode.utils.QRCodeUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QrCodeApplication {

    public static void main(String[] args) throws Exception {
        //SpringApplication.run(QrCodeApplication.class, args);

        // 存放在二维码中的内容,二维码中的内容可以是文字，可以是链接等
        String text = "http://www.baidu.com";

        // 生成的二维码的路径及名称
        String destPath = "/Users/huizuofandechengxuyuan/Documents/images/qrcode/" + System.currentTimeMillis() + ".jpg";
        //生成二维码
        QRCodeUtil.encode(text, null, destPath, true);
        // 解析二维码
        String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);
    }

}
