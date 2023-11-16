package com.lazycoder.uicodegeneration.generalframe.operation;

import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * 当前添加方法的面板显示的效果
 */
public class CurrentAddOperaitonEffect {

    //    private transient static Polygon polygon1, polygon2, polygon3;
    private transient static Graphics2D g2;
    private transient static GradientPaint paint1, paint2, paint3;
    private transient static Color color = new Color(254, 210, 68);

    public static Graphics2D createCurrentAddPaneEffect(Graphics g, Component component) {
        if (g != null) {
            g2 = (Graphics2D) g;
            int width = component.getWidth();// 获取组件大小
            int height = component.getHeight();

            // 创建填充模式对象，参数分别是起点坐标下x，y,起点颜色，终点坐标下x，y，终点颜色。
            paint1 = new GradientPaint(0, 0, new Color(253, 235, 218), width / 2, height / 2, color);
            g2.setPaint(paint1);// 设置绘图对象的填充模式
//            polygon1=new Polygon();
//            polygon1.addPoint(0,0);
//            polygon1.addPoint(width,0);
//            polygon1.addPoint(0,height);
//            g2.fillPolygon(polygon1);

            paint2 = new GradientPaint(width / 2, height / 2, color, width, height / 2, CodeGenerationFrameHolder.CURRENT_ADDITIVE_COLOR);
            g2.setPaint(paint2);
//            polygon2=new Polygon();
//            polygon2.addPoint(0,height);
//            polygon2.addPoint(width,0);
//            polygon2.addPoint(width,height);
//            g2.fillPolygon(polygon2);

            paint3 = new GradientPaint(20, (float) (0.45 * height), color, 0, height, CodeGenerationFrameHolder.CURRENT_ADDITIVE_COLOR);
            g2.setPaint(paint3);
//            polygon3=new Polygon();
//            polygon3.addPoint(0,height/2);
//            polygon3.addPoint((int) (0.85*width),height);
//            polygon3.addPoint(0,height);
//            g2.fillPolygon(polygon3);

            //           g2.fillRect(0, 0, width, height);// 绘制矩形填充控件界面
            return g2;
        }
        return null;
    }


}
