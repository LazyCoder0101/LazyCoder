package com.lazycoder.uiutils.popup;


/* Swing高级，16.6
 * 摘自 https://edu.csdn.net/course/detail/20810
 */

import java.awt.Component;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class AfPopupPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 3135734955011636L;

    protected AfPopupMouseGrabber mouseGrabber;

    public void showPopup(Component owner, int x, int y) {
        showPopup(owner, x, y, new AfPopup.ClosingPolicy()); // 使用默认关闭策略
    }

    // 显示Popup
    public void showPopup(Component owner, int x, int y, AfPopup.ClosingPolicy policy) {
        Point pt = new Point(x, y);
        SwingUtilities.convertPointToScreen(pt, owner);

        // 创建弹出式窗口
        JWindow popup = new JWindow(SwingUtilities.windowForComponent(owner));
        popup.getRootPane().setContentPane(this);
        popup.setSize(this.getPreferredSize());
        popup.setLocation(pt.x, pt.y);

        // 添加监控 （grabber内部已经实现“当点击在popup窗口之外时自动关闭popup”的逻辑)
        this.mouseGrabber = getMouseGrabber(owner, popup);
        mouseGrabber.installListeners(policy);
        // 显示弹出式窗口
        popup.setVisible(true);
    }


    protected AfPopupMouseGrabber getMouseGrabber(Component owner, JWindow popup) {
        return new AfPopupMouseGrabber(owner, popup);
    }

    // 隐藏 Popup
    public void hidePopup() {
        if (mouseGrabber != null) {
            mouseGrabber.cancelPopup();
            mouseGrabber = null;
        }
    }

}
