package com.lazycoder.uiutils.utils;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Panit3D {

	/**
	 * 图片绘制3d效果
	 *
	 * @param srcImage
	 * @param radius
	 * @param border
	 * @param padding
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage draw3D(BufferedImage srcImage, int radius, int border, int padding, Color bgColor)
			throws IOException {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();
		int size = (width + height) / 2;
		Shape shape = new RoundRectangle2D.Float(0, 0, width, height, radius, radius);

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();

		// 绘制渐变背景
		if (bgColor == null) {
			bgColor = Color.LIGHT_GRAY;
		}
		GradientPaint paint = new GradientPaint(0, 0, bgColor.darker(), 0, height, bgColor.brighter().brighter());
		g2d.setPaint(paint);
		g2d.setComposite(AlphaComposite.DstIn);
		g2d.fillRect(0, 0, width, height);

		// 绘图
		g2d.setComposite(AlphaComposite.Src);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fill(shape);
		g2d.setComposite(AlphaComposite.SrcAtop);
		g2d.drawImage(setClip(srcImage, radius), 0, 0, null);

		// 设置高亮效果
		g2d.setColor(new Color(255, 255, 255, 150));
		Shape highlightArea = createHighlightShape(0, 0, size, shape);
		g2d.fill(highlightArea);

		// 绘制边框
		if (border != 0) {
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.setStroke(new BasicStroke(border));
			g2d.drawRoundRect(border / 2, border / 2, width - border, height - border, radius, radius);
		}
		g2d.dispose();

		// 加旁白
		if (padding > 0) {
			int canvasWidth = width + padding * 2;
			int canvasHeight = height + padding * 2;
			BufferedImage newImage = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gd = newImage.createGraphics();
			float[] fractions = {0.0f, 0.6f};
			Color[] colors = {Color.LIGHT_GRAY, Color.WHITE};
			Point2D center = new Point2D.Float(canvasWidth / 2, canvasHeight / 2);
			RadialGradientPaint paint2 = new RadialGradientPaint(center, size, fractions, colors);
			gd.setPaint(paint2);
			gd.setComposite(AlphaComposite.Src);
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			gd.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
			gd.setComposite(AlphaComposite.SrcAtop);
			gd.drawImage(image, padding, padding, null);
			return newImage;
		}
		return image;
	}

	/**
	 * 图片设置圆角
	 *
	 * @param srcImage
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage draw3D(BufferedImage srcImage) throws IOException {
		int radius = (srcImage.getWidth() + srcImage.getHeight()) / 6;
		return draw3D(srcImage, radius, 2, 5, null);
	}

	/**
	 * 图片切圆角
	 *
	 * @param srcImage
	 * @param radius
	 * @return
	 */
	public static BufferedImage setClip(BufferedImage srcImage, int radius) {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
		g2d.drawImage(srcImage, 0, 0, null);
		g2d.dispose();
		return image;
	}

	/**
	 * 描边
	 */
	public static BufferedImage setBorder(BufferedImage srcImage) {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();
		int border = 1;

		int nWidth = width + border * 2;
		int nHeight = height + border * 2;

		BufferedImage image = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		int colorIncrement = 4;
		for (int i = border; i > 0; i--) {
			int nw = width + 2 * i;
			int nh = height + 2 * i;
			BufferedImage tImage = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_ARGB);
			Graphics2D tgs = tImage.createGraphics();
			tgs.drawImage(srcImage, 0, 0, nw, nh, null);

			int red = 255 - colorIncrement * (border - i);
			int green = 255 - colorIncrement * (border - i);
			int blue = 255 - colorIncrement * (border - i);
			int rgb = new Color(red, green, blue).getRGB();
			for (int x = 0; x < nw; x++) {
				for (int y = 0; y < nh; y++) {
					if (tImage.getRGB(x, y) != 0) {
						tImage.setRGB(x, y, rgb);
					}
				}
			}

			tgs.dispose();
			g2d.drawImage(tImage, border - i, border - i, null);
		}
		g2d.drawImage(srcImage, border, border, null);
		g2d.dispose();
		return image;
	}

	/**
	 * 高亮层
	 *
	 * @param centerX
	 * @param centerY
	 * @param size
	 * @param body
	 * @return
	 */
	private static Shape createHighlightShape(int centerX, int centerY, int size, Shape body) {
		double myRadius = size * 4;
		double x = centerX - size * 2.3;
		double y = centerY - size * 3.2;
		Ellipse2D.Double circle = new Ellipse2D.Double(x, y, myRadius, myRadius);
		Area area = new Area(circle);
		area.intersect(new Area(body));
		return area;
	}
}
