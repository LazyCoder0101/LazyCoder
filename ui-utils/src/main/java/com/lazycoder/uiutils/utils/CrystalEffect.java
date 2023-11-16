package com.lazycoder.uiutils.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * 水晶效果 摘自 http://www.blogjava.net/Swing/archive/2012/08/15/255676.html
 *
 * @author admin
 */
public class CrystalEffect {

	/**
	 * 绘制出水晶效果（在组件的paint方法里面调用即可）
	 *
	 * @param g
	 * @param color
	 * @return
	 */
	public static Graphics2D createCrystalEffect(Graphics g, Color color, Component component) {
		Graphics2D g2d = (Graphics2D) g;

//		g2d.setColor(Color.black);
//		g.drawOval(0, 0, 50, 50);
		int x = 0, y = 0, width = component.getWidth(), heigh = component.getHeight();
		Rectangle2D body = new Rectangle(x, y, width, heigh);
//		// draw body
//		g2d.setColor(color);
//		GradientPaint paint = new GradientPaint(x, y, color.darker(), x + width, y + heigh,color.brighter().brighter());
//		g2d.setPaint(paint);
//		g2d.fill(body);
		ShineEffect.createShineEffect(g2d, component, color);
//        g2d.setClip(body);
//        g2d.setClip(null);
		// draw highlight.
		Shape highlightArea = createHighlightShape(width, heigh);

		g2d.setColor(new Color(255, 255, 255, 150));
		g2d.fill(highlightArea);
//		//渐变
//		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
//		g2.setComposite(composite);
//		//渐变背景
//		g2.setPaint(new GradientPaint(2, 2, color, 1, component.getHeight() / 3, color2));
//		g2.fillRect(2, 2, component.getWidth() - 5, component.getHeight() / 3);
//		//渐变二段
//		g2.setPaint(new GradientPaint(1, component.getHeight() / 3, color3, 1, component.getHeight(), color4));
//		g2.fillRect(2, component.getHeight() / 3, component.getWidth() - 5, component.getHeight() / 3 * 2 - 1);
//
//		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
//		g2.setComposite(composite);

		return g2d;

		// 其中，对高亮区域的计算，可以用一个圆心在左上方的大圆形和矩形进行剪切：
	}
//	public static Graphics2D createCrystalEffect(Graphics g, Color color, Component component) {
//		Graphics2D g2d = (Graphics2D) g;
//
//		ShineEffect.createShineEffect(g2d, component);
//		g2d.setColor(Color.black);
//		g.drawOval(0, 0, 50, 50);
//
//		int x = 0, y = 0, width = component.getWidth(), heigh = component.getHeight();
//
//		Rectangle2D body = new Rectangle(x, y, width, heigh);
//
//		// draw body
//		g2d.setColor(color);
//		GradientPaint paint = new GradientPaint(x, y, color.darker(), x + width, y + heigh,
//				color.brighter().brighter());
//		g2d.setPaint(paint);
//		g2d.fill(body);
//
//		g2d.setClip(body);
//		g2d.setClip(null);
//
//		// draw highlight.
//		Shape highlightArea = createHighlightShape(width, heigh, body);
//
//		g2d.setColor(new Color(255, 255, 255, 150));
//		g2d.fill(highlightArea);
//
//		return g2d;
//
//		// 其中，对高亮区域的计算，可以用一个圆心在左上方的大圆形和矩形进行剪切：
//	}

	private static Shape createHighlightShape(int width, int heigh) {
		Rectangle2D.Double rectangular = new Rectangle2D.Double(0, 0, width, heigh);

		// double x1 = 0 - 0.79 * width, y1 = 0 - 2.36 * heigh, r1 = 1.89 * width;
		double x1 = 0 - 0.79 * width, y1 = 0 - 1.96 * heigh, r1 = 1.89 * width;
		Ellipse2D.Double circle1 = new Ellipse2D.Double(x1, y1, r1, r1);

		Area area = new Area(rectangular);
		area.intersect(new Area(circle1));

		return area;
	}

	private static Shape createHighlightShape(int centerX, int centerY, int radius) {
		double myRadius = radius * 0.8;
		double x = centerX - myRadius;
		double y1 = centerY - myRadius - myRadius / 5;
		double y2 = centerY - myRadius - myRadius / 5 * 2;

		Ellipse2D.Double circle1 = new Ellipse2D.Double(x, y1, myRadius * 2, myRadius * 2);
		Ellipse2D.Double circle2 = new Ellipse2D.Double(x, y2, myRadius * 2, myRadius * 2);

		Area area = new Area(circle1);
		area.intersect(new Area(circle2));
		return area;
	}

}
