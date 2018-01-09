package com.example.myapplication.util.voice;

import java.text.DecimalFormat;

/**
 * Created by xieH on 2017/11/8 0008.
 */
public class MoneyFormat {

    private static final String[] pattern = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] cPattern = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};
    private static final String[] cfPattern = {"", "角", "分"};

    private static DecimalFormat mFormat = new DecimalFormat("#0.00");

    /**
     * 金额转换成中文大写
     *
     * @param money 需要转换的money
     * @return 换成大写的money
     */
    public static String format(double money) {
        if (money < 0) {
            return "";
        }

        if (money == 0) {
            return "零元整";
        }

        String moneyStr = mFormat.format(money);

        if (moneyStr.equals("0.00")) {
            return "零元整";
        }

        // 判断是否有小数
        int dotPoint = moneyStr.indexOf(".");

        String integerStr = "";
        if (dotPoint != -1) {
            integerStr = moneyStr.substring(0, moneyStr.indexOf("."));
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < integerStr.length(); i++) {
            // 按数组的编号加入对应大写汉字
            stringBuffer.append(pattern[integerStr.charAt(i) - 48]);
        }

        int cpCursor = 1;
        for (int j = integerStr.length() - 1; j > 0; j--) {
            // 在j之后加字符,不影响j对原字符串的相对位置
            stringBuffer.insert(j, cPattern[cpCursor]);
            // 亿位之后重新循环
            cpCursor = cpCursor == 8 ? 1 : cpCursor + 1;
        }

        // 当十位为零时用一个"零"代替"零拾"
        while (stringBuffer.indexOf("零拾") != -1) {
            stringBuffer.replace(stringBuffer.indexOf("零拾"), stringBuffer.indexOf("零拾") + 2, "零");
        }

        // 当百位为零时，同理
        while (stringBuffer.indexOf("零佰") != -1) {
            stringBuffer.replace(stringBuffer.indexOf("零佰"), stringBuffer.indexOf("零佰") + 2, "零");
        }

        // 同理
        while (stringBuffer.indexOf("零仟") != -1) {
            stringBuffer.replace(stringBuffer.indexOf("零仟"), stringBuffer.indexOf("零仟") + 2, "零");
        }

        // 万需保留，中文习惯
        while (stringBuffer.indexOf("零万") != -1) {
            stringBuffer.replace(stringBuffer.indexOf("零万"), stringBuffer.indexOf("零万") + 2, "万");
        }

        // 同上
        while (stringBuffer.indexOf("零亿") != -1) {
            stringBuffer.replace(stringBuffer.indexOf("零亿"), stringBuffer.indexOf("零亿") + 2, "亿");
        }

        // 有连续数位出现零，即有以下情况，此时根据习惯保留一个零即可
        while (stringBuffer.indexOf("零零") != -1) {
            stringBuffer.replace(stringBuffer.indexOf("零零"), stringBuffer.indexOf("零零") + 2, "零");
        }

        // 特殊情况，如:100000000，根据习惯保留高位
        while (stringBuffer.indexOf("亿万") != -1) {
            stringBuffer.replace(stringBuffer.indexOf("亿万"), stringBuffer.indexOf("亿万") + 2, "亿");
        }

        // 当结尾为零，不必显示，经过处理也只可能出现一个零
        while (stringBuffer.length() > 0 && stringBuffer.lastIndexOf("零") == stringBuffer.length() - 1) {
            stringBuffer.delete(stringBuffer.lastIndexOf("零"), stringBuffer.lastIndexOf("零") + 1);
        }

        // 中文习惯，"壹拾"开头简称"拾"
        if (stringBuffer.indexOf("壹拾") == 0) {
            stringBuffer.replace(0, "壹拾".length(), "拾");
        }

        // 小数部分的处理，以及最后的元
        StringBuffer fractionBuffer = null;
        // 是小数的进入
        if (dotPoint != -1) {
            String fs = moneyStr.substring(dotPoint + 1, moneyStr.length());
            // 若前两位小数全为零，则跳过操作
            if (fs.indexOf("00") == -1 || fs.indexOf("00") >= 2) {
                // 仅保留两位小数
                int end = fs.length() > 2 ? 2 : fs.length();
                fractionBuffer = new StringBuffer(fs.substring(0, end));

                for (int j = 0; j < fractionBuffer.length(); j++) {
                    // 替换大写汉字
                    fractionBuffer.replace(j, j + 1, pattern[fractionBuffer.charAt(j) - 48]);
                }

                // 插入中文标识
                for (int i = fractionBuffer.length(); i > 0; i--) {
                    fractionBuffer.insert(i, cfPattern[i]);
                }

                // 有整数时
                if (stringBuffer.length() > 0) {
                    // 为整数部分添加标识
                    fractionBuffer.insert(0, "元");
                }
            } else {
                fractionBuffer = new StringBuffer("元整");
            }
        } else {
            fractionBuffer = new StringBuffer("元整");
        }

        // 加入小数部分
        stringBuffer.append(fractionBuffer);

        return stringBuffer.toString();
    }

}
