package com.lazycoder.uiutils.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JTextPane;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * 摘自 https://blog.csdn.net/weixin_30501857/article/details/95972808
 */
public class EditPanel extends JTextPane {
    /**
     * 是否实现行号，默认不显示
     */
    private boolean showLineNumber = false;
    public int fontSize = 16;//默认为16号字体

    public EditPanel() {
        super();
    }
    public void setShowLineNumber(boolean isShow) {
        this.showLineNumber = isShow;
    }
    public boolean getShowLineNumber() {
        return this.showLineNumber;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        StyleConstants.setFontSize(getInputAttributes(), getFontSize());
        if (getShowLineNumber()) {
            drawLineNumber(g);
        }
    }
    protected void drawLineNumber(Graphics g) {
        setMargin(new Insets(0, 35, 0, 0));
        // 绘制行号的背景色
        g.setColor(new Color(180, 180, 180));
        g.fillRect(0, 0, 30, getHeight());
        // 获得有多少行
        StyledDocument docu = getStyledDocument();
        Element element = docu.getDefaultRootElement();
        int rows = element.getElementCount();
        // 绘制行号的颜色
        g.setColor(new Color(90, 90, 90));
        g.setFont(new Font(getFont().getName(), getFont().getStyle(), 16));
        for (int row = 0; row < rows; row++) {
            g.drawString((row + 1)+"",2, getPositionY(row + 1));
        }
    }
    public void setFontSize(int fontSize) {
        if(fontSize!=12 &&
                fontSize!=14 &&
                fontSize!=16 &&
                fontSize!=18 &&
                fontSize!=20 &&
                fontSize!=22 &&
                fontSize!=24 ){
            throw new RuntimeException("该行号不能识别");
        }
        this.fontSize = fontSize;
    }
    public int getFontSize() {
        return fontSize;
    }
    /**
     * 获得行号中y坐标的位置<br/>
     * 在计算的过程中，有一个比率值，该比率值是根据getY()的返回值之差决定的。
     * @param row 第几行
     * @return 该行的y坐标位置
     */
    private int getPositionY(int row) {
        int y = 0;
        switch (getFontSize()) {
            case 12:
                y = (row * 18) - 4;
                break;
            case 14:
                y = (row * 20) - 5;
                break;
            case 16:
                y = (row * 23) - 6;
                break;
            case 18:
                y = (row * 26) - 8;
                break;
            case 20:
                y = (row * 29) - 10;
                break;
            case 22:
                y = (row * 31) - 11;
                break;
            case 24:
                y = (row * 34) - 12;
                break;
            default:
                break;
        }
        return y;
    }
}
