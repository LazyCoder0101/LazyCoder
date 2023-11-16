package com.lazycoder.uiutils.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ShineEffect {

	public static Color defaultComponentColor = new Color(253, 236, 219);

	//
	public static int FRAME_NONE = 0;
	public static int FRAME_RIGHT = 1;
	public static int FRAME_AROUND = 4;

	protected static float alpha = 1f;//不透明
	protected static int borderType = FRAME_NONE;
	protected static Color frameColor_up = new Color(255, 255, 222);
	protected static Color c1_up = new Color(253, 236, 219);
	protected static Color c2_up = new Color(253, 223, 187);
	protected static Color c3_up = new Color(255, 206, 105);
	protected static Color c4_up = new Color(255, 255, 222);
	protected static Color frameColor_down = new Color(255, 183, 0);
	private static AlphaComposite half_composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

	public static Graphics2D createShineEffect(Graphics g, Component component, Color color) {
		Graphics2D g2 = (Graphics2D) g;

		int c1r = color.getRed(), c1g = color.getGreen(), c1b = color.getBlue(), c2r = 0, c2g = 0, c2b = 0, c3r = 0, c3g = 0, c3b = 0, c4r = 0, c4g = 0, c4b = 0;
		c2r = c1r;
		c2g = c1g - 13 > 0 ? c1g - 13 : 0;
		c2b = c1b - 32 > 0 ? c1b - 32 : 0;
		c3r = c1r + 2 < 255 ? c1r + 2 : 255;
		c3g = c1g - 20 > 0 ? c1g - 20 : 0;
		c3b = c1b - 114 > 0 ? c1b - 114 : 0;
		c4r = c1r + 2 < 255 ? c1r + 2 : 255;
		c4g = c1g + 19 < 255 ? c1g + 19 : 255;
		c4b = c1b + 3 < 255 ? c1b + 3 : 255;
		Color color2 = new Color(c2r, c2g, c2b);
		Color color3 = new Color(c3r, c3g, c3b);
		Color color4 = new Color(c4r, c4g, c4b);

		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2.setComposite(composite);
		//渐变背景
		g2.setPaint(new GradientPaint(2, 2, color, 1, component.getHeight() / 3, color2));
		g2.fillRect(2, 2, component.getWidth() - 5, component.getHeight() / 3);
		//渐变二段
		g2.setPaint(new GradientPaint(1, component.getHeight() / 3, color3, 1, component.getHeight(), color4));
		g2.fillRect(2, component.getHeight() / 3, component.getWidth() - 5, component.getHeight() / 3 * 2 - 1);

		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2.setComposite(composite);
		return g2;
	}


	public static Graphics2D createShineEffect(Graphics g, Component component) {
		Graphics2D g2 = (Graphics2D) g;
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2.setComposite(composite);

		//绘制边框
//        g2.setColor(frameColor_up);
//        g2.drawRoundRect(1, 1, component.getWidth() - 4, component.getHeight() - 2, 3, 3);
		//渐变背景
		g2.setPaint(new GradientPaint(2, 2, c1_up, 1, component.getHeight() / 3, c2_up));
		g2.fillRect(2, 2, component.getWidth() - 5, component.getHeight() / 3);
		//渐变二段
		g2.setPaint(new GradientPaint(1, component.getHeight() / 3, c3_up, 1, component.getHeight(), c4_up));
		g2.fillRect(2, component.getHeight() / 3, component.getWidth() - 5, component.getHeight() / 3 * 2 - 1);

		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2.setComposite(composite);
		return g2;
	}

}
