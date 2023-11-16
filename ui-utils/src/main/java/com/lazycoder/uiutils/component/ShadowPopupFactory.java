/*
 * ShadowPopupFactory.java
 *
 * Created on 2007-6-15, 22:17:20
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lazycoder.uiutils.component;

import javax.swing.JComponent;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 * 带有阴影效果的Popup工厂类 摘自
 * https://download.csdn.net/download/susuifeng/2200531?utm_medium=distribute.
 * pc_relevant_download.none-task-download-baidujs-2.nonecase&depth_1-utm_source
 * =distribute.pc_relevant_download.none-task-download-baidujs-2.nonecase
 *
 * @author William Chen
 */
// public class ShadowPopupFactory extends PopupFactory {
//
// public ShadowPopupFactory(boolean state) {
// // TODO Auto-generated constructor stub
// super();
// }
//
// }
public class ShadowPopupFactory extends PopupFactory {
    // 阴影灰度系数
    // 边灰度级别
    static double[] gray_grade = {144.0 / 255.0, 172.0 / 255.0, 212.0 / 255.0, 241.0 / 255.0};
    // 角灰度级别
    static double[][] gray_matrix = {{155.0 / 255.0, 180.0 / 255.0, 217.0 / 255.0, 242.0 / 255.0},
            {180.0 / 255.0, 199.0 / 255.0, 227.0 / 255.0, 245.0 / 255.0},
            {217.0 / 255.0, 227.0 / 255.0, 241.0 / 255.0, 250.0 / 255.0},
            {242.0 / 255.0, 245.0 / 255.0, 250.0 / 255.0, 253.0 / 255.0},};
    // 抓取屏幕图像的机器人
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (Exception e) {
        }
    }

    // 是否有阴影
    private boolean shadowed;

    /**
     * @shadowed 是否有阴影
     */
    public ShadowPopupFactory(boolean shadowed) {
        this.shadowed = shadowed;
    }

    @Override
    public Popup getPopup(Component owner, Component contents, int x, int y) throws IllegalArgumentException {
        if (!shadowed) {// 没有阴影直接返回缺省的Popup
            return super.getPopup(owner, contents, x, y);
        }
        // 抓取弹出窗口背景图片
        Dimension dim = contents.getPreferredSize();
        Rectangle bound = new Rectangle(x, y, dim.width + 4, dim.height + 4);
        BufferedImage backgroundImage = robot.createScreenCapture(bound);
        // 封装一下边框
        ShadowFrame frame = new ShadowFrame(contents, backgroundImage);
        // 获取Popup对象
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                contents.repaint();
            }
        });
        return super.getPopup(owner, frame, x, y);
    }

    // 灰度转换接口
    interface Filter {
        double filter(int x, int y, int rgb);
    }

    // 为弹出窗口的提供阴影边框的封装容器
    class ShadowFrame extends JComponent {
        /**
         * @param content         弹出窗口的内容组件
         * @param backgroundImage 弹出窗口的背景图片
         */
        public ShadowFrame(Component content, BufferedImage backgroundImage) {
            // 使用BorderLayout以便内容组件充满空间
            setLayout(new BorderLayout());
            add(content, BorderLayout.CENTER);
            // 设置阴影边框
            ShadowBorder border = new ShadowBorder(backgroundImage);
            setBorder(border);
        }
    }

    // 自定义阴影边框实现
    class ShadowBorder implements Border {
        // 右边框阴影
        private BufferedImage rightImage;
        // 下边框阴影
        private BufferedImage bottomImage;
        // 右下边框阴影
        private BufferedImage rightBottomImage;

        /**
         * @param backgroundImage 背景图片
         */
        ShadowBorder(BufferedImage backgroundImage) {
            // 初始化边框阴影
            generateRightImage(backgroundImage);
            generateBottomImage(backgroundImage);
            generateRightBottomImage(backgroundImage);
        }

        // 画边框
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawImage(rightImage, x + width - 4, y, c);
            g.drawImage(bottomImage, x, y + height - 4, c);
            g.drawImage(rightBottomImage, x + width - 4, y + height - 4, c);
        }

        // 左上都为0，右下为4
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 4, 4);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        // 产生下边框阴影
        private void generateBottomImage(BufferedImage backgroundImage) {
            int w = backgroundImage.getWidth();
            int h = backgroundImage.getHeight();
            // 剪切下边框图片
            bottomImage = backgroundImage.getSubimage(0, h - 4, w - 4, 4);
            // 生成下边框阴影
            shadowBottom();
        }

        // 产生右下边框阴影
        private void generateRightBottomImage(BufferedImage backgroundImage) {
            int w = backgroundImage.getWidth();
            int h = backgroundImage.getHeight();
            // 剪切右下边框图片
            rightBottomImage = backgroundImage.getSubimage(w - 4, h - 4, 4, 4);
            // 生成右下边框阴影
            shadowRightBottom();
        }

        // 产生右边框阴影
        private void generateRightImage(BufferedImage backgroundImage) {
            int w = backgroundImage.getWidth();
            int h = backgroundImage.getHeight();
            // 剪切右边框图片
            rightImage = backgroundImage.getSubimage(w - 4, 0, 4, h - 4);
            // 产生右边框阴影
            shadowRight();
        }

        // 过滤底边图像
        private void shadowBottom() {
            filterImage(bottomImage, new Filter() {
                @Override
                public double filter(int x, int y, int rgb) {
                    if (x < 4) {
                        return 1;
                    }
                    if (x < 8) {
                        return gray_matrix[7 - x][y];
                    }
                    return gray_grade[y];
                }
            });
        }

        // 过滤右边图像
        private void shadowRight() {
            filterImage(rightImage, new Filter() {
                @Override
                public double filter(int x, int y, int rgb) {
                    if (y < 4) {
                        return 1;
                    }
                    if (y < 8) {
                        return gray_matrix[x][7 - y];
                    }
                    return gray_grade[x];
                }
            });
        }

        // 过滤右下边图像
        private void shadowRightBottom() {
            filterImage(rightBottomImage, new Filter() {
                @Override
                public double filter(int x, int y, int rgb) {
                    return gray_matrix[x][y];
                }
            });
        }

        // 返回颜色rgb灰度为grade的颜色
        private int gradeRGB(int rgb, double grade) {
            int a = rgb & 0xff000000;
            int r = (rgb & 0xff0000) >> 16;
            int g = (rgb & 0xff00) >> 8;
            int b = rgb & 0xff;
            r = ((int) (grade * r)) << 16;
            g = ((int) (grade * g)) << 8;
            b = (int) (grade * b);
            return a | r | g | b;
        }

        // 使用灰度过滤器filter过滤图像image
        private void filterImage(BufferedImage image, Filter filter) {
            int w = image.getWidth();
            int h = image.getHeight();
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int rgb = image.getRGB(x, y);
                    double grade = filter.filter(x, y, rgb);
                    rgb = gradeRGB(rgb, grade);
                    image.setRGB(x, y, rgb);
                }
            }
        }
    }

}
