package com.yp.smssend.utils;

import java.util.Random;

/*
 * Created By YinP
 * 2020/5/13
 */
public class RandomUtil {

    private RandomUtil() {
    }

    /**
     * 生成随机位数的字符串(数字)
     */
    public static String getRandNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 生成随机位数的字符串(字母)
     */
    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 获取Ascii码中的字符 数字48-57 小写65-90 大写97-122
            int range = new Random().nextInt(75) + 48; //[48-123)
            if (range < 97) {
                if (range >= 65) {
                    range = range > 90 ? 180 - range : range;
                } else {
                    range = range > 57 ? 114 - range : range;
                }
            }
            sb.append((char) range);
        }
        return sb.toString();
    }

    /**
     * 首字母转小写
     */
    public static String lowerFirst(String word) {
        if (Character.isLowerCase(word.charAt(0))) {
            return word;
        } else {
            return (Character.toLowerCase(word.charAt(0)) + word.substring(1));
        }
    }

    /**
     * 首字母转大写
     */
    public static String upperFirst(String word) {
        if (Character.isUpperCase(word.charAt(0))) {
            return word;
        } else {
            return Character.toUpperCase(word.charAt(0)) + word.substring(1);
        }
    }
}
