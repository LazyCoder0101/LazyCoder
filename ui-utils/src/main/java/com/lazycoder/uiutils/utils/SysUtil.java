package com.lazycoder.uiutils.utils;

import com.tech.callback.swingc.toast.DefaultToast;
import com.tech.callback.swingc.toast.Toast;
import com.tech.callback.swingc.toast.ToastMoveListener;
import com.tech.callback.swingc.toast.ToastStatus;

import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;


/**
 * 系统工具和参数
 *
 * @author Administrator
 */
public class SysUtil {

    /**
     * 获取屏幕尺寸
     */
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * 获取剪切板
     */
    public static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    //任务栏的尺寸参数 摘自 https://www.iteye.com/blog/krs-2042006
    //getScreenInsets是指获得屏幕的 insets
    public static Insets taskbarInsets = Toolkit.getDefaultToolkit().getScreenInsets(new JFrame().getGraphicsConfiguration());

    /**
     * 刷新窗口界面
     *
     * @param component
     */
    public static void updateFrameUI(JComponent component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        if (window != null) {
            window.validate();
        }
    }

    /***
     * 摘自 https://blog.csdn.net/hw1287789687/article/details/84822840
     * 增加参数后,使滚动条自动定位到底部
     *
     * @param panel_7JS2
     */
    public static void scrollToBottom(JScrollPane panel_7JS2) {
        if (panel_7JS2 != null) {
            int maxHeight = panel_7JS2.getVerticalScrollBar().getMaximum();
            panel_7JS2.getViewport().setViewPosition(new Point(0, maxHeight));
            panel_7JS2.updateUI();
        }
    }

    /**
     * 把滑动条自动定位到对应比例的位置
     *
     * @param proportion 该值需要传一个0~1的小数，使滑动条自动定位到对应比例
     */
    public static void scrollToProportion(float proportion, JScrollPane scrollPane) {
        if ((proportion > 0 && proportion < 1) && scrollPane != null) {
            int h = (int) (proportion * scrollPane.getVerticalScrollBar().getMaximum());
            scrollPane.getViewport().setViewPosition(new Point(0, h));
            scrollPane.updateUI();
        }
    }

    /**
     * 使滚动条定位到顶部
     *
     * @param scrollPane
     */
    public static void scrollToTop(JScrollPane scrollPane) {
        if (scrollPane != null) {
            scrollPane.getViewport().setViewPosition(new Point(0, 0));
            scrollPane.updateUI();
        }
    }

    /**
     * 展示一个Toast
     *
     * @param owner  Toast出现的父窗口（DefaultToast必须依赖于JFrame而存在）
     * @param status Toast的状态，INFO，ERROR， SUCCESS
     * @param text   展示的文本
     * @return 创建的Toast
     */
    public static DefaultToast showToast(JFrame owner, ToastStatus status, String text) {
        DefaultToast toast = new DefaultToast(owner, text, status);
        Window frame = toast.getOwner();
        ComponentListener[] listeners = frame.getComponentListeners();

        /* 清除过期的listener */
        Optional.ofNullable(listeners).ifPresent((arrays) -> {
            for (ComponentListener listener : arrays) {
                boolean removePredict = (listener instanceof ToastMoveListener)
                        && !((ToastMoveListener) listener).isToastShowing();
                if (removePredict) {
                    frame.removeComponentListener(listener);
                }
            }
        });

        frame.addComponentListener(new ToastMoveListener(toast, Toast.CENTER)); /* 添加一个监听器，使得Toast跟随主窗口移动。 */
        Point toastLocation = Toast.calculateToastLocation(frame.getBounds(), toast.getBounds(), Toast.CENTER);
        toast.setLocation(toastLocation);
        toast.setVisible(true);
        return toast;
    }

    public static void setWindowClosingDispose(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
    }

}
