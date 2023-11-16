package com.lazycoder.uiutils.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.Border;
import lombok.Data;

/**
 * https://blog.csdn.net/e_real/article/details/40651587
 * Swing
 * 设置圆角边框（可以自定义边框的颜色）
 * 可以为button，文本框等人以组件添加边框
 * 使用方法：
 * JButton close = new JButton(" 关 闭 ");
 * close.setOpaque(false);// 设置原来按钮背景透明
 * close.setBorder(new RoundBorder());黑色的圆角边框
 * close.setBorder(new RoundBorder(Color.RED)); 红色的圆角边框
 *
 * @author Monsoons
 */

@Data
public class ChangeableRoundBorder implements Border {

    private boolean TOP_LEFT = true;

    private boolean BOTTOM_LEFT = true;

    private boolean TOP_RIGHT = true;

    private boolean BOTTOM_RIGHT = true;

    private int arcWidth = 15;//圆弧的x轴半径

    private int arcHeight = arcWidth;//圆弧的y轴半径

    private Color borderColor = Color.BLACK;

    private int thickness = 3;

    public ChangeableRoundBorder(Color color) {// 有参数的构造方法
        this.borderColor = color;
    }

    public ChangeableRoundBorder() {// 无参构造方法
        this.borderColor = Color.BLACK;
        // 如果实例化时，没有传值
        // 默认是黑色边框
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    // 实现Border（父类）方法
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
                            int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(thickness));

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//消除锯齿
        g2.drawLine(arcWidth, 0, c.getWidth() - arcWidth, 0);//顶部那条线
        g2.drawLine(0, arcHeight, 0, c.getHeight() - arcHeight);//左边那条线
        g2.drawLine(arcWidth, c.getHeight(), c.getWidth() - arcWidth, c.getHeight());//底部那条线

        if (TOP_LEFT) {
            g2.setStroke(new BasicStroke(thickness - 1));
            g2.drawArc(0, 0, arcWidth * 2, 2 * arcHeight, 90, 90);//左上角的圆弧
            g2.setStroke(new BasicStroke(thickness));
        } else {
            g2.drawLine(0, 0, arcWidth, 0);//左上方横线
            g2.drawLine(0, 0, 0, arcHeight);//左上方竖线
        }
        if (BOTTOM_LEFT) {
            g2.setStroke(new BasicStroke(thickness - 1));
            g2.drawArc(0, c.getHeight() - 2 * arcHeight, arcWidth * 2, 2 * arcHeight, -90, -90);//左下角的圆弧
            g2.setStroke(new BasicStroke(thickness));
        } else {
            g2.drawLine(0, c.getHeight(), arcWidth, c.getHeight());//左下方横线
            g2.drawLine(0, c.getHeight() - arcHeight, 0, c.getHeight());//左下方竖线
        }
//        g2.setStroke(new BasicStroke(thickness - 1));
        g2.drawLine(c.getWidth(), arcHeight, c.getWidth(), c.getHeight() - arcHeight);//右边那条线

        if (TOP_RIGHT) {
            g2.setStroke(new BasicStroke(thickness - 1));
            g2.drawArc(c.getWidth() - arcWidth * 2, 0, arcWidth * 2, 2 * arcHeight, 0, 90);//右上角的圆弧
            g2.setStroke(new BasicStroke(thickness));

//            g2.drawArc(50,50,30,30,0,90);//右上角的圆弧：
        } else {
            g2.drawLine(c.getWidth() - arcWidth, 0, c.getWidth(), 0);//右上角横线
            g2.drawLine(c.getWidth(), 0, c.getWidth(), arcHeight);//右下角竖线
        }
        if (BOTTOM_RIGHT) {
            g2.setStroke(new BasicStroke(thickness - 1));
            g2.drawArc(c.getWidth() - 2 * arcWidth, c.getHeight() - 2 * arcHeight, arcWidth * 2, 2 * arcHeight, 0, -90);//右上角的圆弧
            g2.setStroke(new BasicStroke(thickness));
            //g2.drawArc(50,50,30,30,0,-90);//右下角的圆弧：

        } else {
            g.drawLine(c.getWidth(), c.getHeight() - arcWidth, c.getWidth(), c.getHeight());//右下角竖线
            g.drawLine(c.getWidth() - arcWidth, c.getHeight(), c.getWidth(), c.getHeight());
        }
    }

}