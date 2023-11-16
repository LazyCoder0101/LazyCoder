package com.lazycoder.uiutils.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.border.AbstractBorder;

/**
 * 摘自 https://blog.csdn.net/gjs935219/article/details/97646382
 */
public class MyColoursBorder extends AbstractBorder {

    private int thickness;

    public MyColoursBorder() {
        this.thickness = 1;
    }

    public MyColoursBorder(int thickness) {
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if ((this.thickness > 0) && (g instanceof Graphics2D)) {
            Graphics2D g2d = (Graphics2D) g;

//            Color oldColor = g2d.getColor();

            //设置线型
            Stroke stroke = new BasicStroke(thickness);
            g2d.setStroke(stroke);

            Color c1 = Color.RED,
                    c2 = new Color(186, 186, 186),
                    c3 = Color.GRAY,
                    c4 = new Color(252, 132, 132);

            drawLine(g2d, 0, 0, width, 0, c4, c2);//上
            //drawLine(g2d, 0, 0, 0, height, c1, c2);//左
            drawLine(g2d, 0, 0, 0, height, c4, c1);//左

            stroke = new BasicStroke(thickness + 1);
            g2d.setStroke(stroke);
            //drawLine(g2d, width, height, width, 0, c1, c2);//右
            drawLine(g2d, width, height, width, 0, c3, c2);//右

            //drawLine(g2d, width, height, 0, height, c1, c2);//下
            drawLine(g2d, width, height, 0, height, c3, c1);//下
        }
    }

    //绘制
    private void drawLine(Graphics2D g2d,
                          int x1, int y1, int x2, int y2,
                          Color c1, Color c2) {
        //Point2D
        Point2D start = new Point2D.Double(x1, y1);  //起点
        Point2D end = new Point2D.Double(x2, y2);  //终点(渐变的方向）

        float[] dist = {0.0f, 1.0f};  //插入关键点
        Color[] colors = {c1, c2};  //关键点的颜色值
        Paint paint = new LinearGradientPaint(start, end, dist, colors);

        //设置Paint
        g2d.setPaint(paint);

        //构造一个shape
        Shape shape = new Line2D.Double(start, end);
        g2d.draw(shape);
    }


}
