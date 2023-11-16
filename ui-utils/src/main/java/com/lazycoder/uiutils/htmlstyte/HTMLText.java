package com.lazycoder.uiutils.htmlstyte;

import java.util.ArrayList;
import lombok.NoArgsConstructor;

/**
 * 一个html文本的简陋封装，供给可以根据html文本个性化显示的组件使用
 */
@NoArgsConstructor
public class HTMLText {

    private static final String START_STRING = "<html>\n<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "</head>\n<body>\n";

    private static String HEAD_STRING = "";

    private static final String END_STRING = "\n</body>\n</html>";

    private ArrayList<HtmlPar> bodyList = new ArrayList<>();

    /**
     * 设置标题
     *
     * @param tittle 标题名称
     * @param size   标题大小，取值 1 —— 6
     */
    public void setHead(String tittle, int size) {
        HEAD_STRING = "<h" + size + ">" + tittle + "</h" + size + ">";
    }

    /**
     * 添加段落
     *
     * @param par
     */
    public void addPar(HtmlPar par) {
        bodyList.add(par);
    }

    /**
     * 获取最终生成的HTML文本内容
     *
     * @return
     */
    public String getHtmlContent() {
        StringBuilder str = new StringBuilder();
        str.append(START_STRING);
        str.append(HEAD_STRING);
        for (HtmlPar par : bodyList) {
            str.append(par.getFinalParContent());
        }
        str.append(END_STRING);
        return str.toString();
    }

    /**
     * 生成指定内容的html格式文本
     *
     * @param text
     * @param boldOrNot
     * @return
     */
    public static String createHtmlContent(String text, boolean boldOrNot) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par = new HtmlPar();
        par.addText(text, boldOrNot);
        htmlText.addPar(par);
        return htmlText.getHtmlContent();
    }

    /**
     * 生成指定内容的html格式文本
     *
     * @param text
     * @param boldOrNot
     * @return
     */
    public static String createHtmlContent(String text, int size, boolean boldOrNot) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par = new HtmlPar();
        par.addText(text, size, boldOrNot);
        htmlText.addPar(par);
        return htmlText.getHtmlContent();
    }

    /**
     * 生成指定内容的html格式文本
     *
     * @param text
     * @param colorType
     * @param boldOrNot
     * @return
     */
    public static String createHtmlContent(String text, String colorType, boolean boldOrNot) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par = new HtmlPar();
        par.addColorText(text, colorType, boldOrNot);
        htmlText.addPar(par);
        return htmlText.getHtmlContent();
    }

    /**
     * 生成指定内容的html格式文本
     * @param text
     * @param colorType
     * @param size
     * @param boldOrNot
     * @return
     */
    public static String createHtmlContent(String text, String colorType, int size, boolean boldOrNot) {
        HTMLText htmlText = new HTMLText();
        HtmlPar par = new HtmlPar();
        par.addColorText(text, colorType, size, boldOrNot);
        htmlText.addPar(par);
        return htmlText.getHtmlContent();
    }

}
