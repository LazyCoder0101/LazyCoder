package com.lazycoder.uiutils.folder;

import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;


public class FoldButtonUI extends BEButtonUI implements MouseMotionListener, MouseListener, FocusListener {

	protected FoldButton button;

	/**
	 * 当前鼠标是否浮动在上面
	 */
	private boolean hovered;

	public static ComponentUI createUI(JComponent c) {
		return new FoldButtonUI();
	}

	@Override
	public void installUI(JComponent c) {
		// 设置缺省属性
		button = (FoldButton) c;
		button.setFocusPainted(false);
		button.setFocusable(true);
		// 添加事件处理器
		button.addMouseListener(this);
		button.addMouseMotionListener(this);
		button.addFocusListener(this);
	}

	/**
	 * 卸载UI
	 */
	@Override
	public void uninstallUI(JComponent c) {
		// 卸载事件处理器
		button.removeMouseListener(this);
		button.removeMouseMotionListener(this);
		button.removeFocusListener(this);
	}

	public void setArmed(boolean b) {
		// armed=b;
		button.repaint();
	}

	public void setHovered(boolean b) {
		hovered = b;
		button.repaint();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		setArmed(false);
	}

	/**
	 * 鼠标退出事件
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// 浮动消失
		setHovered(false);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// 浮动
		setHovered(true);
		// 改鼠标外观
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	/**
	 * 处理按下鼠标事件
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (button.isEnabled() == true) {
			// 改换外观
			setArmed(true);
			// 折叠或者展开
			button.setExpanded(!button.isExpanded());
			button.doSomethingWhenMousePressed(button.isExpanded());
			// 触发选择事件
			ItemEvent evt = new ItemEvent(button, button.isExpanded() ? 0 : 1, button.getText(),
					button.isExpanded() ? ItemEvent.SELECTED : ItemEvent.DESELECTED);
			button.fireItemStateChanged(evt);
			// 获得焦点
			button.requestFocus();
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

}
