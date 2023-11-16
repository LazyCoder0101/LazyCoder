package com.lazycoder.uiutils.component;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * 源码摘自 https://blog.csdn.net/iteye_3947/article/details/81687600
 *
 * @author admin
 */
public class CircleLayout implements LayoutManager {

	int maxCompWidth = 0; // 最大组件宽度
	int maxCompHeight = 0; // 最大组件高度

	int lc = 0; // 组件数(边的个数，即多边形边的个数)

	double v = 1.5D; // 缩放因子
	double cd = 0; // 组件和组件之间的距离(中心点到中心点)

	int l; // 外截正方形边长/2

	int lw = 0; // 调整过的边长
	int lh = 0;

	public CircleLayout() {
		this(1.5D);
	}

	public CircleLayout(double factor) {
		this.v = factor;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {

	}

	@Override
	public void layoutContainer(Container parent) {

		synchronized (parent.getTreeLock()) {

			int cs = parent.getComponentCount();

			if (cs == 1) {
				layoutContainer1(parent);
				return;
			}

			if (cs == 2) {
				layoutContainer2(parent);
				return;
			}

			Dimension d = parent.getPreferredSize();

			Point cp = new Point(d.width / 2, d.height / 2);

			double dx = 0, dy = 0;
			int cpx = 0, cpy = 0;
			for (int i = 0; i < cs; i++) {
				dx = sin(i * (2 * PI / lc)) * l;
				dy = -cos(i * (2 * PI / lc)) * l;

				cpx = (int) (cp.x + dx);
				cpy = (int) (cp.y + dy);
				Point cpp = new Point(cpx, cpy);
				Component comp = parent.getComponent(i);
				Dimension compSize = comp.getPreferredSize();

				comp.setBounds(calcR(cpp, compSize));
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(lw, lh);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {

		lc = parent.getComponentCount();

		Component[] components = parent.getComponents();
		for (Component component : components) {
			Dimension d = component.getPreferredSize();
			this.maxCompWidth = max(d.width, this.maxCompWidth);
			this.maxCompHeight = max(d.height, this.maxCompHeight);
		}

		calc();

		return new Dimension(lw, lh);

	}

	@Override
	public void removeLayoutComponent(Component comp) {

	}

	// -------------------------------

	private void layoutContainer1(Container parent) {
		Component c = parent.getComponent(0);
		c.setBounds(0, 0, lw, lh);
	}

	private void layoutContainer2(Container parent) {

		Component c = parent.getComponent(0);
		Dimension d = c.getPreferredSize();
		c.setBounds(0, 0, d.width, d.height);

		c = parent.getComponent(1);
		d = c.getPreferredSize();

		c.setBounds(lw - d.width, lh - d.height, d.width, d.height);

	}

	private void calc() {

		if (lc == 1) {
			lw = maxCompWidth;
			lh = maxCompHeight;
			return;
		}

		cd = max(maxCompWidth, maxCompHeight) * v;

		if (lc == 2) {
			double yy = sqrt(cd * cd * 2) / 2;
			l = (int) yy + 1;
			lh = l + maxCompHeight;
			lw = l + maxCompWidth;
			return;
		}

		// 根据正多边形的一条边长，计算出这个多边形外切圆形的外切矩形的边长（近似值）
		// 使用正弦定理，
		double x = cd / sin(2 * PI / lc);
		l = (int) x + 1;
		lh = 2 * l + maxCompHeight;
		lw = 2 * l + maxCompWidth;
	}

	private Rectangle calcR(Point p, Dimension d) {
		int y = p.y - d.height / 2;
		int x = p.x - d.width / 2;
		return new Rectangle(new Point(x, y), d);
	}
}
