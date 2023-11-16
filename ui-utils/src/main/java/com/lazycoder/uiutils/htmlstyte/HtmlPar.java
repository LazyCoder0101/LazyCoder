package com.lazycoder.uiutils.htmlstyte;

import java.util.ArrayList;
import java.util.Collections;

/**
 * html段落结果的简单封装
 */
public class HtmlPar extends ArrayList<String> {

    private static final String PAR_START = "<p>";

    private static final String PAR_END = "</p>";

    /**
     * 蓝色
     */
    public static final String BLUE = "blue";

    /**
     * 黑色
     */
    public static final String BLACK = "black";

    /**
     * 灰色
     */
    public static final String GRAY = "gray";

    /**
     * 褐红色
     */
    public static final String MAROON = "maroon";

    /**
     * 绿色
     */
    public static final String GREEN = "green";

    /**
     * 红色
     */
    public static final String RED = "red";

    /**
     * 白色
     */
    public static final String WHITE = "white";

    /**
     * 黄色
     */
    public static final String YELLOW = "yellow";

    public static final String PURPLE = "rgb(160,32,240)";

    public static final String CRYSTAL_BLUE = "#5CB3FF";

    public static final String Munsell = "#97f205";

    public static final String Orange = "rgb(255,165,0)";

    public static final String SBLUE = "rgb(24,144,255)";

    /**
     * 写入一段文字内容
     *
     * @param text
     * @param boldOrNot 是否加粗
     */
    public void addText(String text, boolean boldOrNot) {
        if (boldOrNot == true) {
            text = "<b>" + text + "</b>";
        }
        add(text);
    }

    /**
     * 写入一段文字内容
     *
     * @param text
     * @param size      字体尺寸
     * @param boldOrNot 是否加粗
     */
    public void addText(String text, int size, boolean boldOrNot) {
        text = "<font size=" + size + ">" + text + "</font>";
        if (boldOrNot == true) {
            text = "<b>" + text + "</b>";
        }
        add(text);
    }

    /**
     * 添加一段带颜色的文字
     *
     * @param text
     * @param colorType
     * @param boldOrNot 是否加粗
     */
    public void addColorText(String text, String colorType, boolean boldOrNot) {
        String temp = "<font color=\"" + colorType + "\">" + text + "</font>";
        if (boldOrNot == true) {
            temp = "<b>" + temp + "</b>";
        }
        add(temp);
    }

    /**
     * 添加一段带颜色的文字
     *
     * @param text
     * @param colorType 填颜色变量或者颜色代码
     * @param size      字体尺寸
     * @param boldOrNot 是否加粗
     */
    public void addColorText(String text, String colorType, int size, boolean boldOrNot) {
        String temp = "<font size=" + size + " color=\"" + colorType + "\">" + text + "</font>";
        if (boldOrNot == true) {
            temp = "<b>" + temp + "</b>";
        }
        add(temp);
    }

    /**
     * 跳到下一行
     */
    public void nextLine() {
        add("<br>");
    }

    /**
     * 获取下一行的符号
     * @return
     */
    public String getNextLine() {
        return "<br>";
    }

    /**
     * 添加空格
     *
     * @param n 空格个数
     */
    public void blankSpace(int n) {
        add(String.join("", Collections.nCopies(n, "&nbsp;")));
    }

    /**
     * 获取多个空格符号
     * @param n
     * @return
     */
    public static String getBlankSpaceSymbol(int n) {
        return String.join("", Collections.nCopies(n, "&nbsp;"));
    }

//    /**
//     * 输入制表符
//     */
//    public void tab() {
//        add("<PRE>&#9</PRE>");
//    }

    /**
     * 获取最终的段落内容
     *
     * @return
     */
    public String getFinalParContent() {
        StringBuilder str = new StringBuilder();
        str.append(PAR_START);
        for (String temp : this) {
            str.append(temp);
        }
        str.append(PAR_END);
        return str.toString();
    }


}
