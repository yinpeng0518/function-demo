/*
 * Copyright(C) 2019 FUYUN DATA SERVICES CO.,LTD. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 该源代码版权归属福韵数据服务有限公司所有
 * 未经授权，任何人不得复制、泄露、转载、使用，否则将视为侵权
 */
package com.yp.smssend.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created By YinP
 * 2020/6/28
 * Luhn校验算法
 */
@Slf4j
public abstract class LuhnUtil {

    String number = "6214832707610965";

    /**
     * 银行卡号码的校验规则
     * 银行卡号码的校验采用Luhn算法，校验过程大致如下：
     * <p>
     * 1. 从右到左给卡号字符串编号，最右边第一位是1，最右边第二位是2，最右边第三位是3….
     * 2. 从右向左遍历，对每一位字符t执行第三个步骤，并将每一位的计算结果相加得到一个数s。
     * 3. 对每一位的计算规则：如果这一位是奇数位，则返回t本身，如果是偶数位，则先将t乘以2得到一个数n，如果n是一位数（小于10），直接返回n，否则将n的个位数和十位数相加返回。
     * 4. 如果s能够整除10，则此号码有效，否则号码无效。
     * <p>
     * 因为最终的结果会对10取余来判断是否能够整除10，所以又叫做模10算法。
     */
    public static boolean checkBankCardNumber(String number) {
        int sumOdd = 0;
        int sumEven = 0;
        int length = number.length();
        int[] wei = new int[length];
        for (int i = 0; i < number.length(); i++) {
            wei[i] = Integer.parseInt(number.substring(length - i - 1, length - i));
            // System.out.print(wei[i]);
        }
        for (int i = 1; i < length; i++) {
            int j = i - 1;
            if (i % 2 == 1) {
                sumOdd += wei[j];
            } else {
                if ((wei[j] * 2) > 9) {
                    wei[j] = wei[j] * 2 - 9;
                } else {
                    wei[j] *= 2;
                }
                sumEven += wei[j];
            }
        }
        //log.debug("sumOdd = {}", sumOdd);
        // log.debug("sumEven = {}", sumEven);
        return (sumOdd + sumEven) % 10 == 0;
    }

    public static void main(String[] args) {
        System.out.println(LuhnUtil.checkBankCardNumber("6214832707610965"));
        System.out.println(LuhnUtil.checkBankCardNumber("6217002640009454701"));
        System.out.println(LuhnUtil.checkBankCardNumber("3602028401037579236"));
        System.out.println(LuhnUtil.checkBankCardNumber("6217002640009454703"));
        System.out.println(LuhnUtil.checkBankCardNumber("60530120000003337"));
        System.out.println(LuhnUtil.checkBankCardNumber("6222002640009454699"));
        System.out.println(LuhnUtil.checkBankCardNumber("6216260000017261364"));
        System.out.println(LuhnUtil.checkBankCardNumber("6228480519599769576"));
        System.out.println(LuhnUtil.checkBankCardNumber("6222082307000584742"));
    }

}
