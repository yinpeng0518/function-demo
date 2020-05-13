package com.yp.captchaimage.utils;

import com.yp.captchaimage.exceptions.BusinessException;
import com.yp.captchaimage.exceptions.NotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.SecureRandom;

@Slf4j
public final class CaptchaCodeUtils {

    private static final int CAPTCHA_WIDTH = 100;                       //宽度
    private static final int CAPTCHA_HEIGHT = 35;                       //高度
    private static final int NUMBER_CNT = 4;                            //数字的长度
    private static final String IMAGE_TYPE = "JPEG";                    //图片类型
    private final String[] fontNames = {"宋体", "华文楷体", "黑体",
            "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"};               //字体
    private final Color bgColor = new Color(255, 255, 255);     // 背景色,白色
    private String text;                                                // 验证码上的文本
    private static final SecureRandom r = new SecureRandom();
    private static CaptchaCodeUtils instance = null;

    private CaptchaCodeUtils() {
    }

    //双重校验获取单例对象
    public static synchronized CaptchaCodeUtils getInstance() {
        if (instance == null) {
            synchronized (CaptchaCodeUtils.class) {
                if (instance == null) {
                    instance = new CaptchaCodeUtils();
                }
            }
        }
        return instance;
    }


    //从指定路径读取验证码文本
    public String getCode(String path) {
        BufferedImage bi = instance.getImage();
        try {
            output(bi, new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException(e.getMessage());
        }
        return this.text;
    }


    //生成验证码
    public CaptchaCode getCode() {
        BufferedImage img = instance.getImage();
        CaptchaCode code = new CaptchaCode();      //返回验证码对象
        code.setText(this.text);
        code.setData(this.copyImage2Byte(img));
        return code;
    }


    //将图片转化为 二进制数据
    public byte[] copyImage2Byte(BufferedImage img) {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            ImageIO.write(img, IMAGE_TYPE, bout);
            return bout.toByteArray();
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }


    //将二进制数据转化为文件
    public boolean copyByte2File(byte[] data, String file) {
        try (ByteArrayInputStream in = new ByteArrayInputStream(data);
             FileOutputStream out = new FileOutputStream(file)) {
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            out.flush();
            return true;
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }


    //生成随机的颜色
    private Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }


    //生成随机的字体
    private Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];                 //生成随机的字体名称
        int style = r.nextInt(4);                    //生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
        int size = r.nextInt(5) + 24;                //生成随机字号[24,28]
        return new Font(fontName, style, size);
    }

    //画干扰线
    private void drawLine(BufferedImage image) {
        int num = 5;                                        //一共画5条
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {                     //生成两个点的坐标，即4个值
            int x1 = r.nextInt(CAPTCHA_WIDTH);
            int y1 = r.nextInt(CAPTCHA_HEIGHT);
            int x2 = r.nextInt(CAPTCHA_WIDTH);
            int y2 = r.nextInt(CAPTCHA_HEIGHT);
            g2.setStroke(new BasicStroke(1.5F));      //设置Graphics2D上下文的笔画(使用指定的线宽以及cap和join样式的默认值构造一个实线基笔画)
            g2.setColor(randomColor());                     //随机生成干扰线颜色
            g2.drawLine(x1, y1, x2, y2);                    //画干扰线
        }
    }

    //生成可变随机字符
    private char randomChar() {
        String codes = "0123456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    // 创建BufferedImage
    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        return image;
    }

    //创建验证码图片
    public BufferedImage getImage() {
        BufferedImage image = createImage();                        //创建图片缓冲区
        Graphics2D g2 = (Graphics2D) image.getGraphics();           //得到绘制环境
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NUMBER_CNT; i++) {
            String s = randomChar() + "";
            float x = i * 1.0F * CAPTCHA_WIDTH / NUMBER_CNT;        //设置当前字符的x轴坐标
            g2.setFont(randomFont());                               //设置随机字体
            g2.setColor(randomColor());                             //设置随机颜色
            g2.drawString(s, x, CAPTCHA_HEIGHT - 5F);            //画图
            sb.append(s);
        }
        this.text = sb.toString();                                  //把生成的字符串赋给了this.text
        drawLine(image);                                            //添加干扰线
        return image;
    }

    //保存图片到指定的输出流
    public static void output(BufferedImage image, OutputStream out) {
        try {
            ImageIO.write(image, IMAGE_TYPE, out);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }


    //图片验证码对象
    @Data
    public static class CaptchaCode {
        private String text;    //验证码文字信息
        private byte[] data;    //验证码二进制数据
    }
}