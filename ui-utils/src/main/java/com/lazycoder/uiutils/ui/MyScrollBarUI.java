package com.lazycoder.uiutils.ui;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

/**
 * 源码摘自 https://www.jiweichengzhu.com/article/7bf6460398fd4dc390674dea5b91ceb9
 * Created by SongFei on 2017/11/8.
 */
public class MyScrollBarUI extends BasicScrollBarUI {

	// 设置滚动区域宽度，高度默认即可
	@Override
	public Dimension getPreferredSize(JComponent c) {
		Dimension preferredSize = super.getPreferredSize(c);
		return new Dimension(10, preferredSize.height);
	}

	// 重绘滑动把手，主要是颜色和形状
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		Graphics2D g2 = (Graphics2D) g;
		// 把绘制区的x，y点坐标定义为坐标系的原点
		// 这句一定要加上，不然拖动就失效了
		g2.translate(thumbBounds.x, thumbBounds.y);
		// 设置把手颜色
		g2.setColor(Color.GRAY);
		// 消除锯齿
		g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		// 半透明
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		// 填充圆角矩形
		g2.fillRoundRect(0, 0, thumbBounds.width, thumbBounds.height - 1, 0, 0);
	}

	// 自定义向上的箭头按钮
	@Override
	protected JButton createDecreaseButton(int orientation) {
		return nullButton();
	}

	// 自定义向下的箭头按钮
	@Override
	protected JButton createIncreaseButton(int orientation) {
		return nullButton();
	}

	// 重绘当鼠标点击滑动到向上或向左按钮之间的区域
	@Override
	protected void paintDecreaseHighlight(Graphics g) {
		g.setColor(trackColor);
		int x = getTrackBounds().x;
		int y = getTrackBounds().y;
		int w = 0, h = 0;
		if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
			w = getThumbBounds().width;
			h = getThumbBounds().y - y;
		}
		if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
			w = getThumbBounds().x - x;
			h = getThumbBounds().height;
		}
		g.fillRect(x, y, w, h);
	}

	// 重绘当鼠标点击滑块至向下或向右按钮之间的区域
	@Override
	protected void paintIncreaseHighlight(Graphics g) {
		g.setColor(trackColor);
		int x = getThumbBounds().x;
		int y = getThumbBounds().y;
		int w = getTrackBounds().width;
		int h = getTrackBounds().height;
		g.fillRect(x, y, w, h);
	}

	/**
	 * 返回一个没有任何内容的Jbutton，连边框都没有
	 *
	 * @return Jbutton
	 */
	private JButton nullButton() {
		JButton button = new JButton();
		button.setBorder(null);
		return button;
	}

}
