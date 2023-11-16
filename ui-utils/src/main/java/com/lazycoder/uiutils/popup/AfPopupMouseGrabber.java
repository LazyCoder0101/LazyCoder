package com.lazycoder.uiutils.popup;


/* Swing高级，16.5
 * 摘自 https://edu.csdn.net/course/detail/20810
 *
 */


import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class AfPopupMouseGrabber implements AWTEventListener, WindowListener, ComponentListener {
	private Component owner;
	private Window ownerWindow; // owner所在的窗口
	private JWindow popup; // 弹出式窗口

	// owner: 触发者控件 ( 例如，点击按钮时弹出窗口，则按钮为owner)
	// popup: 弹出的窗口
	public AfPopupMouseGrabber(Component owner, JWindow popup) {
		this.owner = owner;
		this.popup = popup;
	}

	public void installListeners(AfPopup.ClosingPolicy p) {
		// 监测主窗口内发生的鼠标事件 (Swing高级, 11.2讲 )
		if (p.autoClose) {
			Toolkit tk = Toolkit.getDefaultToolkit();
			tk.addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK
					| AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);
		}

		// 获取owner的顶层窗口
		ownerWindow = SwingUtilities.getWindowAncestor(owner);
//		if (ownerWindow == null)
			// 当主窗口最小化、最大化、关闭时，应关闭 Popup 窗口
			if (p.autoClose) {
				ownerWindow.addWindowListener(this);
			}

		// 当主窗口移动、改变大小时，应关闭 Popup 窗口
		if (p.autoClose) {
			owner.addComponentListener(this);
		}
	}

	public void uninstallListeners() {
		owner.removeComponentListener(this);
		ownerWindow.removeWindowListener(this);
		Toolkit.getDefaultToolkit().removeAWTEventListener(this);
	}

	public void cancelPopup() {
		this.uninstallListeners();
		popup.setVisible(false);
	}

	///////////////// AWTEventListener /////////
	@Override
	public void eventDispatched(AWTEvent event) {
		if (event instanceof MouseEvent) {
			int eventID = event.getID();
			if (eventID == MouseEvent.MOUSE_PRESSED) {
				MouseEvent e = (MouseEvent) event;

				// comp: 鼠标点中的控件
				Component comp = e.getComponent();

				// 判断鼠标点中的控件，是否在 Popup窗口里面
				Window targetWindow = SwingUtilities.getWindowAncestor(comp);
				if (targetWindow != popup) {
					cancelPopup();
					afterCancelPopup();
				}
			}
		}
	}

	///////////// WindowListener :主窗口最小化、最大化、关闭 ///////////////
	@Override
	public void windowClosing(WindowEvent e) {
		cancelPopup();
		afterCancelPopup();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		cancelPopup();
		afterCancelPopup();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		cancelPopup();
		afterCancelPopup();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		cancelPopup();
		afterCancelPopup();
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	///////// ComponentListener : 主窗口移动、改变大小 ///////
	@Override
	public void componentResized(ComponentEvent e) {
		cancelPopup();
		afterCancelPopup();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		cancelPopup();
		afterCancelPopup();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		cancelPopup();
		afterCancelPopup();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		cancelPopup();
		afterCancelPopup();
	}

	public void afterCancelPopup() {

	}

}
