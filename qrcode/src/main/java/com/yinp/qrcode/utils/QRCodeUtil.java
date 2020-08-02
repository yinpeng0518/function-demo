package com.yinp.qrcode.utils;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created By YinP
 * 2020/8/2
 */
@Slf4j
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";

    // 二维码尺寸
    private static final int QR_CODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    private QRCodeUtil() {
    }

    private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        if(StringUtils.isEmpty(imgPath)){
            return image;
        }

        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws IOException {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + " 该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QR_CODE_SIZE - width) / 2;
        int y = (QR_CODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws IOException, WriterException {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        mkdir(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    public static BufferedImage encode(String content, String imgPath, boolean needCompress) throws IOException, WriterException {
        return QRCodeUtil.createImage(content, imgPath, needCompress);
    }

    public static void mkdir(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdir会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            boolean mkdir = file.mkdirs();
            log.debug("文件路径{},是否创建成功:{}", destPath, mkdir);
        }
    }

    public static void encode(String content, String imgPath, String destPath) throws IOException, WriterException {
        QRCodeUtil.encode(content, imgPath, destPath, false);
    }

    public static void encode(String content, String destPath) throws IOException, WriterException {
        QRCodeUtil.encode(content, null, destPath, false);
    }

    public static void encode(String content,
                              String imgPath,
                              OutputStream output,
                              boolean needCompress) throws IOException, WriterException {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public static void encode(String content, OutputStream output) throws IOException, WriterException {
        QRCodeUtil.encode(content, null, output, false);
    }

    public static String decode(File file) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(file);
        if (Objects.isNull(image)) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        EnumMap<DecodeHintType, String> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    public static String decode(String path) throws IOException, NotFoundException {
        return QRCodeUtil.decode(new File(path));
    }

}
