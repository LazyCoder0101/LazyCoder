package com.lazycoder.uiutils.component;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;


import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.AbstractBorder;

/**
 * 摘自https://blog.csdn.net/cml_blog/article/details/24630459
 */
public class LineNumberBorder extends AbstractBorder {

    public LineNumberBorder() {
    }


    /*
     * Insets 对象是容器边界的表示形式。 它指定容器必须在其各个边缘留出的空间。
     */
    // 此方法在实例化时自动调用
    // 此方法关系到边框是否占用组件的空间
    @Override
    public Insets getBorderInsets(Component c) {

        return getBorderInsets(c, new Insets(0, 0, 0, 0));
    }


    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        if (c instanceof JTextPane) {
            //这里设置行号左边边距
            insets.left = 20;
        }
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }


    // 边框的绘制方法
    // 此方法必须实现
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
                            int height) {
        // 获得当前剪贴区域的边界矩形。
        java.awt.Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics();
        int fontHeight = fm.getHeight();

        // starting location at the "top" of the page...
        // y is the starting baseline for the font...
        int ybaseline = y + fm.getAscent();

        // now determine if it is the "top" of the page...or somewhere
        // else
        int startingLineNumber = (clip.y / fontHeight) + 1;

        if (startingLineNumber != 1) {
            ybaseline = y + startingLineNumber * fontHeight
                    - (fontHeight - fm.getAscent());
        }

        int yend = ybaseline + height;
        if (yend > (y + height)) {

            yend = y + height;
        }
        g.setColor(Color.blue);
        // 绘制行号
        while (ybaseline < yend) {

            String label = padLabel(startingLineNumber, 0, true);


            g.drawString(label, 0, ybaseline);
            ybaseline += fontHeight;
            startingLineNumber++;
        }
    }


    // 寻找适合的数字宽度
    private int lineNumberWidth(JTextArea jta) {
        int lineCount = Math.max(jta.getRows(), jta.getLineCount());
        return jta.getFontMetrics(jta.getFont()).stringWidth(lineCount + " ");
    }


    private String padLabel(int lineNumber, int length, boolean addSpace) {

        StringBuffer buffer = new StringBuffer();
        buffer.append(lineNumber);
        for (int count = (length - buffer.length()); count > 0; count--) {
            buffer.insert(0, ' ');
        }
        if (addSpace) {

            buffer.append(' ');
        }
        return buffer.toString();
    }


}
