/*
 * Drawer.java
 *
 * Created on June 8, 2007, 11:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.lazycoder.uiutils.folder;

import javax.swing.JComponent;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * 该类是个辅助类，目的是实现动画状态时透明图像及组件的移动效果
 * 像是一个抽屉，放应用程序的组件
 *
 * @author William Chen
 * @mail rehte@hotmail.com
 */
public class Drawer extends JComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = -2838713127223081919L;

	//目前抽屉抽开比例，同时是透明度值
	private double ratio = 1.0;
	//目前是否处于动画状态
	private boolean animating;
	//应用程序的组件
	private JComponent content;
	//保存有应用程序组件的虚屏图像
	private Image offImage;

	/**
	 * Creates a new instance of Drawer
	 */
	public Drawer(double r, JComponent comp) {
		this.ratio = r;
		this.content = comp;
		add(comp);
		setLayout(null);
	}

	public int getContentHeight() {
		return content.getHeight();
	}

	public int getContentWidth() {
		return content.getWidth();
	}

	public double getRatio() {
		return this.ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
		repaint();
	}

	//覆盖父类的paintChildren
	@Override
	protected void paintChildren(Graphics g) {
		if (animating) {
			//动画状态画出当前透明的应用程序组件以及抽拉状态
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) ratio));
			g2d.drawImage(getOffImage(), 0, getHeight() - content.getHeight(), this);
		} else {
			//普通状态简单使用容器缺省的渲染
			super.paintChildren(g);
		}
	}

	public void setAnimating(boolean animating) {
		this.animating = animating;
	}

	//创建应用程序组件的虚屏图像
	private Image getOffImage() {
		if (offImage == null) {
			int contentWidth = content.getWidth();
			int contentHeight = content.getHeight();
			if (offImage == null) {
				offImage = createImage(contentWidth, contentHeight);
				Graphics g = offImage.getGraphics();
				//使用Renderer机制
				content.paint(g);
			}
		}
		return offImage;
	}

}
