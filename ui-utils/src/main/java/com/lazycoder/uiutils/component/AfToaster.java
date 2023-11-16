package com.lazycoder.uiutils.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/* 本类将被收录为 af.swing.AfToaster 作为公共类
 * 摘自 https://edu.csdn.net/course/detail/20810
 */
public class AfToaster extends JPanel {
    // 警告级别
    public enum Level {INFO, WARN, ERROR}

    // 背景色
    public static Color[] bgColors = {
            new Color(60, 60, 60, 200),
            Color.ORANGE,
            Color.RED
    };

    // 文字颜色
    public static Color[] textColors = {
            new Color(0xF4F4F4),
            new Color(0x333333),
            new Color(0xF4F4F4)
    };

    // 使用 AfLabel 进行文本测算
    private AfLabel content = new AfLabel();
    private Timer timer; // Swing Timer
    private int delay = 1500; // N毫秒之后自动关闭

    // 消息窗口 ( 使用 JWindow 实现 )
    JWindow popup;

    public AfToaster() {
        // 设置 AfLabel
        content.setWrappingWidth(240);
        content.setOpaque(true);
        content.setBackground(new Color(60, 60, 60, 200));
        content.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        content.setForeground(new Color(0xF4F4F4));

        setLevel(Level.INFO);
        this.setLayout(new BorderLayout());
        this.add(content, BorderLayout.CENTER);
    }

    // 设置消息
    public void setMessage(String message) {
        content.setText(message);
    }

    // 设置消息级别
    public void setLevel(Level level) {
        if (level == Level.INFO) {
            content.setBackground(bgColors[0]);
            content.setForeground(textColors[0]);
        } else if (level == Level.WARN) {
            content.setBackground(bgColors[1]);
            content.setForeground(textColors[1]);
        } else if (level == Level.ERROR) {
            content.setBackground(bgColors[2]);
            content.setForeground(textColors[2]);
        }
    }

    // 设置延时
    public void setDelay(int delay) {
        this.delay = delay;
    }

    // 计算窗口所需的大小
    @Override
    public Dimension getPreferredSize() {
        Dimension size = content.getPreferredSize();
        return size;
    }

    // 显示消息提示
    public void showPopup(Window ownerWindow) {
        /* 根据主窗口的位置计算，将消息显示在主窗口的中心位置 */
        Rectangle rect = ownerWindow.getBounds();
        Dimension size = this.getPreferredSize();
        int x = (int) rect.getCenterX() - size.width / 2;
        int y = (int) rect.getCenterY() - size.height / 2;
        ;

        /* 创建并显示消息窗口 ( 用 JWindow 实现 ) */

        // 创建 JWindow , 参数为 owner 窗口
        popup = new JWindow(ownerWindow);
        // 设置 ContentPane ( 固定用法 )
        popup.getRootPane().setContentPane(this);
        // 设置窗口大小
        popup.setSize(this.getPreferredSize());
        // 设置窗口显示位置
        popup.setLocation(x, y);
        // 显示窗口
        popup.setVisible(true);

        // 启动定时,自动关闭  (javax.swing.Timer , 10.2讲 )
        timer = new Timer(100, new TimerHandler());
        timer.start();
    }

    // 关闭消息提示
    public void hidePopup() {
        if (popup != null) {
            popup.setVisible(false); // 隐藏窗口
            popup.dispose(); // 销毁窗口
            popup = null;
        }
    }

    private class TimerHandler implements ActionListener {
        // 开始的时间
        long startTime = System.currentTimeMillis();

        @Override
        public void actionPerformed(ActionEvent e) {
            long pass = System.currentTimeMillis() - startTime;
            int remain = delay - (int) pass;

            // 已经结束
            if (remain <= 0) {
                timer.stop();
                hidePopup();
                return;
            }

            // 倒计时300毫秒
            if (remain <= 300) {
                float percent = remain / 300.0f; // 0.0~1.0之间
                if (popup != null) {
                    popup.setOpacity(percent);
                }
            }
        }
    }

    //////////////// 工具方法 ///////////////

    public static void show(Window ownerWindow, String text) {
        show(ownerWindow, Level.INFO, 1500, text);
    }

    public static void show(Window ownerWindow, Level level, String text) {
        show(ownerWindow, level, 1500, text);
    }

    public static void show(Window ownerWindow, Level level, int delay, String text) {
        AfToaster toaster = new AfToaster();
        toaster.setMessage(text);
        toaster.setLevel(level); // 消息级别
        toaster.setDelay(delay); // 延时
        toaster.showPopup(ownerWindow);
    }


    // 第一个参数为 控件 Component
    public static void show(Component owner, String text) {
        show(owner, Level.INFO, 1500, text);
    }

    public static void show(Component owner, Level level, String text) {
        show(owner, level, 1500, text);
    }

    public static void show(Component owner, Level level, int delay, String text) {
        Window ownerWindow = SwingUtilities.getWindowAncestor(owner);
        show(ownerWindow, level, delay, text);
    }

    public static AfToaster show(JFrame frame,String text, Level level, int delay){
        AfToaster toaster = new AfToaster();
        toaster.setMessage(text);
        toaster.setLevel(level); // 消息级别
        toaster.setDelay(delay); // 延时
        toaster.showPopup(frame);
        return toaster;
    }
}
