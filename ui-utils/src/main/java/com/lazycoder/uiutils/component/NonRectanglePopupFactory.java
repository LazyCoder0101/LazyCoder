/*
 * ShadowPopupFactory.java
 *
 * Created on 2007-6-15, 22:17:20
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lazycoder.uiutils.component;

import com.lazycoder.utils.MathUtil;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.image.BufferedImage;


/**
 * https://download.csdn.net/download/susuifeng/2200531?utm_medium=distribute.
 * pc_relevant_download.none-task-download-baidujs-2.nonecase&depth_1-utm_source
 * =distribute.pc_relevant_download.none-task-download-baidujs-2.nonecase
 *
 * @author William Chen
 */
public class NonRectanglePopupFactory extends PopupFactory {
    private static final int BORDER_PAD = 20;
    private static final int ARROW_PAD = 10;
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (Exception e) {
        }
    }

    public NonRectanglePopupFactory() {
    }

    @Override
    public Popup getPopup(Component owner, Component contents, int x, int y) throws IllegalArgumentException {
        if (contents instanceof JToolTip) {
            ((JToolTip) contents).setBorder(null);
            Dimension dim = contents.getPreferredSize();
            Rectangle bound = new Rectangle(x, y, dim.width + 2 * BORDER_PAD, dim.height + 2 * BORDER_PAD);
            BufferedImage backgroundImage = robot.createScreenCapture(bound);
            NonRectangleFrame frame = new NonRectangleFrame(owner, contents, backgroundImage);
            return super.getPopup(owner, frame, x, y);
        } else {
            return super.getPopup(owner, contents, x, y);
        }
    }

    class NonRectangleFrame extends JComponent {
        public NonRectangleFrame(Component owner, Component content, BufferedImage backgroundImage) {
            setLayout(new BorderLayout());
            add(content, BorderLayout.CENTER);
            NonRectangleBorder border = new NonRectangleBorder(owner, content, backgroundImage);
            setBorder(border);
        }
    }

    class NonRectangleBorder implements Border {
        private BufferedImage leftImage;
        private BufferedImage rightImage;
        private BufferedImage topImage;
        private BufferedImage bottomImage;
        private Component owner;
        private Component content;

        NonRectangleBorder(Component owner, Component content, BufferedImage backgroundImage) {
            this.owner = owner;
            this.content = content;
            generateLeftImage(backgroundImage);
            generateTopImage(backgroundImage);
            generateRightImage(backgroundImage);
            generateBottomImage(backgroundImage);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawImage(leftImage, x, y, c);
            g.drawImage(rightImage, x + width - BORDER_PAD, y, c);
            g.drawImage(topImage, x + BORDER_PAD, y, c);
            g.drawImage(bottomImage, x + BORDER_PAD, y + height - BORDER_PAD, c);
            Point op = owner.getLocationOnScreen();
            Point cp = c.getLocationOnScreen();
            Point ccp = new Point(cp.x + c.getWidth() / 2, cp.y + c.getHeight() / 2);
            op.x += owner.getWidth() / 2;
            op.y += owner.getHeight() / 2;
            Rectangle bounds = new Rectangle(cp.x + x, cp.y + y, width, height);
            Rectangle innerBounds = new Rectangle(bounds.x + BORDER_PAD, bounds.y + BORDER_PAD, width - 2 * BORDER_PAD,
                    height - 2 * BORDER_PAD);
            Polygon polygon = calculateArrowPolygon(ccp, op, bounds, innerBounds, cp);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(content.getBackground());
            g.fillPolygon(polygon);
            g.setColor(Color.black);
            g.drawPolygon(polygon);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(BORDER_PAD, BORDER_PAD, BORDER_PAD, BORDER_PAD);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        private void generateLeftImage(BufferedImage backgroundImage) {
            leftImage = backgroundImage.getSubimage(0, 0, BORDER_PAD, backgroundImage.getHeight());
        }

        private void generateTopImage(BufferedImage backgroundImage) {
            topImage = backgroundImage.getSubimage(BORDER_PAD, 0, backgroundImage.getWidth() - 2 * BORDER_PAD,
                    BORDER_PAD);
        }

        private void generateRightImage(BufferedImage backgroundImage) {
            rightImage = backgroundImage.getSubimage(backgroundImage.getWidth() - BORDER_PAD, 0, BORDER_PAD,
                    backgroundImage.getHeight());
        }

        private void generateBottomImage(BufferedImage backgroundImage) {
            bottomImage = backgroundImage.getSubimage(BORDER_PAD, backgroundImage.getHeight() - BORDER_PAD,
                    backgroundImage.getWidth() - 2 * BORDER_PAD, BORDER_PAD);
        }

        private Polygon calculateArrowPolygon(Point cp, Point op, Rectangle outer, Rectangle inner, Point zero) {
            Polygon polygon = new Polygon();
            Point p2 = MathUtil.intersectPoint(op, cp, outer);
            if (p2 != null) {
                polygon.addPoint(p2.x, p2.y);
            }
            Point p1 = MathUtil.intersectWithHorizontalLine(cp, op, inner.x, inner.x + inner.width, inner.y);
            if (p1 == null) {
                p1 = MathUtil.intersectWithHorizontalLine(cp, op, inner.x, inner.x + inner.width,
                        inner.y + inner.height);
                if (p1 == null) {
                    p1 = MathUtil.intersectWithVerticalLine(cp, op, inner.x, inner.y, inner.y + inner.height);
                    if (p1 == null) {
                        p1 = MathUtil.intersectWithVerticalLine(cp, op, inner.x + inner.width, inner.y,
                                inner.y + inner.height);
                        if (p1 != null) {
                            addRightBorderPoint(inner, polygon, p1);
                        }
                    } else {
                        addLeftBorderPoint(inner, polygon, p1);
                    }
                } else {
                    addBottomBorderPoint(inner, polygon, p1);
                }
            } else {
                addTopBorderPoint(inner, polygon, p1);
            }
            if (polygon.npoints < 4) {
                polygon = new Polygon();
                polygon.addPoint(outer.x, outer.y);
                polygon.addPoint(outer.x, outer.y + outer.height - 1);
                polygon.addPoint(outer.x + outer.width - 1, outer.y + outer.height - 1);
                polygon.addPoint(outer.x + outer.width - 1, outer.y);
            }
            for (int i = 0; i < polygon.npoints; i++) {
                polygon.xpoints[i] -= zero.x;
                polygon.ypoints[i] -= zero.y;
            }
            return polygon;
        }

        private void addTopBorderPoint(Rectangle inner, Polygon polygon, Point p) {
            if (p.x + ARROW_PAD < inner.x + inner.width + 1) {
                polygon.addPoint(p.x + ARROW_PAD, inner.y - 1);
                polygon.addPoint(inner.x + inner.width + 1, inner.y - 1);
                polygon.addPoint(inner.x + inner.width + 1, inner.y + inner.height + 1);
                polygon.addPoint(inner.x - 1, inner.y + inner.height + 1);
                polygon.addPoint(inner.x - 1, inner.y - 1);
            }
            if (p.x - ARROW_PAD > inner.x - 1) {
                polygon.addPoint(p.x - ARROW_PAD, inner.y - 1);
            }
        }

        private void addBottomBorderPoint(Rectangle inner, Polygon polygon, Point p) {
            if (p.x + ARROW_PAD < inner.x + inner.width + 1) {
                polygon.addPoint(p.x + ARROW_PAD, inner.y + inner.height + 1);
                polygon.addPoint(inner.x + inner.width + 1, inner.y + inner.height + 1);
                polygon.addPoint(inner.x + inner.width + 1, inner.y - 1);
                polygon.addPoint(inner.x - 1, inner.y - 1);
                polygon.addPoint(inner.x - 1, inner.y + inner.height + 1);
            }
            if (p.x - ARROW_PAD > inner.x - 1) {
                polygon.addPoint(p.x - ARROW_PAD, inner.y + inner.height + 1);
            }
        }

        private void addLeftBorderPoint(Rectangle inner, Polygon polygon, Point p) {
            if (p.y + ARROW_PAD < inner.y + inner.height + 1) {
                polygon.addPoint(inner.x - 1, p.y + ARROW_PAD);
                polygon.addPoint(inner.x - 1, inner.y + inner.height + 1);
                polygon.addPoint(inner.x + inner.width + 1, inner.y + inner.height + 1);
                polygon.addPoint(inner.x + inner.width + 1, inner.y - 1);
                polygon.addPoint(inner.x - 1, inner.y - 1);
            }
            if (p.y - ARROW_PAD > inner.y - 1) {
                polygon.addPoint(inner.x - 1, p.y - ARROW_PAD);
            }
        }

        private void addRightBorderPoint(Rectangle inner, Polygon polygon, Point p) {
            if (p.y + ARROW_PAD < inner.y + inner.height + 1) {
                polygon.addPoint(inner.x + inner.width + 1, p.y + ARROW_PAD);
                polygon.addPoint(inner.x + inner.width + 1, inner.y + inner.height + 1);
                polygon.addPoint(inner.x - 1, inner.y + inner.height + 1);
                polygon.addPoint(inner.x - 1, inner.y - 1);
                polygon.addPoint(inner.x + inner.width + 1, inner.y - 1);
            }
            if (p.y - ARROW_PAD > inner.y - 1) {
                polygon.addPoint(inner.x + inner.width + 1, p.y - ARROW_PAD);
            }
        }
    }
}
