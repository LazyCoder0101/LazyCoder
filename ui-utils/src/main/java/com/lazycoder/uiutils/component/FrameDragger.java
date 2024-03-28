package com.lazycoder.uiutils.component;

import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

//代码摘自：https://github.com/moomoohk/FrameDragger
public class FrameDragger extends MouseAdapter implements MouseMotionListener
{
	private Component c;
	private Point mouseDownScreenCoords = null;
	private Point mouseDownCompCoords = null;
	private Runnable onPress = null, onRelease = null, onDrag = null;

//	@Override
//	public void mouseDragged(MouseEvent e)
//	{
//		if (onDrag != null) {
//			onDrag.run();
//		}
//		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
//		Rectangle bounds = gc.getBounds();
//		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
//		Rectangle screen = new Rectangle();
//		screen.x = bounds.x + screenInsets.left;
//		screen.y = bounds.y + screenInsets.top;
//		screen.height = bounds.height - screenInsets.top - screenInsets.bottom;
//		screen.width = bounds.width - screenInsets.left - screenInsets.right;
//		Point currCoords = e.getLocationOnScreen();
//		int newX = mouseDownScreenCoords.x + (currCoords.x - mouseDownScreenCoords.x) - mouseDownCompCoords.x;
//		int newY = mouseDownScreenCoords.y + (currCoords.y - mouseDownScreenCoords.y) - mouseDownCompCoords.y;
//		if (newX < 0) {
//			newX = 0;
//		}
//		if (newX + c.getWidth() > screen.width) {
//			newX = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - c.getWidth();
//		}
//		if (newY < screen.y) {
//			newY = screen.y;
//		}
//		if (newY + c.getHeight() > Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
//			newY = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - c.getHeight();
//		}
//		c.setLocation(newX, newY);
//	}

	/**
	 * 这里做了改动，改为获取组件所在的JWindow组件，对组件进行拖动操作直接拖动JWindow组件
	 * @param e
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (onDrag != null) {
			onDrag.run();
		}
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Rectangle bounds = gc.getBounds();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		Rectangle screen = new Rectangle();
		screen.x = bounds.x + screenInsets.left;
		screen.y = bounds.y + screenInsets.top;
		screen.height = bounds.height - screenInsets.top - screenInsets.bottom;
		screen.width = bounds.width - screenInsets.left - screenInsets.right;
		Point currCoords = e.getLocationOnScreen();
		int newX = mouseDownScreenCoords.x + (currCoords.x - mouseDownScreenCoords.x) - mouseDownCompCoords.x;
		int newY = mouseDownScreenCoords.y + (currCoords.y - mouseDownScreenCoords.y) - mouseDownCompCoords.y;
		if (newX < 0) {
			newX = 0;
		}

		Window ancestor = SwingUtilities.getWindowAncestor(c);

		if (newX + ancestor.getWidth() > screen.width) {
			newX = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - ancestor.getWidth();
		}
		if (newY < screen.y) {
			newY = screen.y;
		}
		if (newY + ancestor.getHeight() > Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
			newY = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - ancestor.getHeight();
		}
		ancestor.setLocation(newX, newY);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (onRelease != null) {
			onRelease.run();
		}
		this.mouseDownScreenCoords = null;
		this.mouseDownCompCoords = null;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (onPress != null) {
			onPress.run();
		}
		this.mouseDownScreenCoords = e.getLocationOnScreen();
		this.mouseDownCompCoords = e.getPoint();
	}

	public void applyTo(Component c)
	{
		applyTo(c, null, null, null);
	}

	public void applyTo(Component c, Runnable onPress, Runnable onRelease, Runnable onDrag)
	{
		this.c = c;
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		this.onPress = onPress;
		this.onRelease = onRelease;
		this.onDrag = onDrag;
	}
}
