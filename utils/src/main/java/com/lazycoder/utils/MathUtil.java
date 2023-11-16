/*
 * MathUtil.java
 *
 * Created on 2006��11��19��, ����6:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.lazycoder.utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.text.DecimalFormat;

/**
 * https://download.csdn.net/download/susuifeng/2200531?utm_medium=distribute.
 * pc_relevant_download.none-task-download-baidujs-2.nonecase&depth_1-utm_source
 * =distribute.pc_relevant_download.none-task-download-baidujs-2.nonecase
 *
 * @author William Chen
 */
public class MathUtil {
    private static final int STICK_DISTANCE = 10;

    /**
     * 摘自 https://blog.csdn.net/qq_35573326/article/details/79974366
     * TODO 除法运算，保留小数
     *
     * @param a 被除数
     * @param b 除数
     * @return 商
     * @author 袁忠明
     * @date 2018-4-17下午2:24:48
     */
    public static float txfloat(int a, int b) {
        // TODO 自动生成的方法存根
        DecimalFormat df = new DecimalFormat("0.00");//设置保留位数
//		return df.format((float)a/b);
        return (float) a / b;
    }

    public static Point intersectPoint(Rectangle rect1, Rectangle rect2) {
        return intersectPoint(rect1.x, rect1.y, rect1.x + rect1.width, rect1.y + rect1.height, rect2);
    }

    public static double calculateAngle(Point p) {
        return calculateAngle(p.x, p.y);
    }

    public static Point spin(Point p, double angle) {
        double r = Math.sqrt(p.x * p.x + p.y * p.y);
        double angle0 = MathUtil.calculateAngle(p) + angle;
        return new Point((int) (r * Math.cos(angle0)), (int) (r * Math.sin(angle0)));
    }

    public static double calculateAngle(int x, int y) {
        if (x == 0) {
            if (y == 0) {
                return 0;
            } else if (y > 0) {
                return Math.PI / 2;
            } else {
                return 3 * Math.PI / 2;
            }
        } else if (x > 0) {
            if (y == 0) {
                return 0;
            } else {
                return Math.atan((double) y / (double) x);
            }
        } else {
            if (y == 0) {
                return Math.PI;
            } else {
                return Math.PI + Math.atan((double) y / (double) x);
            }
        }
    }

    public static boolean stickToLine(Point p, Point start, Point end) {
        if (start.x == end.x) {
            if (start.y == end.y) {
                return false;
            } else {
                if (Math.abs(p.x - start.x) > STICK_DISTANCE) {
                    return false;
                }
                if (p.y < Math.min(start.y, end.y)) {
                    return false;
                }
                if (p.y > Math.max(start.y, end.y)) {
                    return false;
                }
                return true;
            }
        } else if (start.y == end.y) {
            if (Math.abs(p.y - start.y) > STICK_DISTANCE) {
                return false;
            }
            if (p.x < Math.min(start.x, end.x)) {
                return false;
            }
            if (p.x > Math.max(start.x, end.x)) {
                return false;
            }
            return true;
        } else {
            Point xp = intersectLine(p, start, end);
            if (xp.x < Math.min(start.x, end.x) || xp.x > Math.max(start.x, end.x)) {
                return false;
            }
            if (p.distance(xp) > STICK_DISTANCE) {
                return false;
            }
            return true;
        }
    }

    public static Point intersectLine(Point p, Point s, Point e) {
        double k = (double) (e.y - s.y) / (double) (e.x - s.x);
        double d = p.y - s.y + k * s.x - p.x / k;
        double r = k - 1 / k;
        double x = d / r;
        double y = (x - p.x) / k + p.y;
        return new Point((int) x, (int) y);
    }

    public static Point intersectPoint(Point p1, Point p2, Rectangle rect) {
        return intersectPoint(p1.x, p1.y, p2.x, p2.y, rect);
    }

    public static Point intersectPoint(int x1, int y1, int x2, int y2, Rectangle rect) {
        Point p = intersectWithHorizontalLine(x1, y1, x2, y2, rect.x, rect.x + rect.width, rect.y);
        if (p != null) {
            return p;
        }
        p = intersectWithHorizontalLine(x1, y1, x2, y2, rect.x, rect.x + rect.width, rect.y + rect.height);
        if (p != null) {
            return p;
        }
        p = intersectWithVerticalLine(x1, y1, x2, y2, rect.x, rect.y, rect.y + rect.height);
        if (p != null) {
            return p;
        }
        p = intersectWithVerticalLine(x1, y1, x2, y2, rect.x + rect.width, rect.y, rect.y + rect.height);
        if (p != null) {
            return p;
        }
        return null;
    }

    public static Point intersectWithHorizontalLine(Point p1, Point p2, int startx, int endx, int hy) {
        return intersectWithHorizontalLine(p1.x, p1.y, p2.x, p2.y, startx, endx, hy);
    }

    public static Point intersectWithHorizontalLine(int x1, int y1, int x2, int y2, int startx, int endx, int hy) {
        if (y1 == y2) {
            return null;
        }
        if (x1 == x2) {
            return intersectVH(x1, y1, y2, startx, endx, hy);
        }
        int dy = hy - y1;
        int ix = (int) ((double) dy / tangent(x1, y1, x2, y2) + x1);
        if (ix < Math.min(startx, endx) || ix > Math.max(startx, endx)) {
            return null;
        }
        if (ix < Math.min(x1, x2) || ix > Math.max(x1, x2)) {
            return null;
        }
        if (hy < Math.min(y1, y2) || hy > Math.max(y1, y2)) {
            return null;
        }
        return new Point(ix, hy);
    }

    public static double tangent(int x1, int y1, int x2, int y2) {
        int dy = y1 - y2;
        int dx = x1 - x2;
        return (double) dy / (double) dx;
    }

    public static Point intersectVH(int vx, int vy1, int vy2, int startx, int endx, int hy) {
        if (vx < Math.min(startx, endx) || vx > Math.max(startx, endx)) {
            return null;
        }
        int miny = Math.min(vy1, vy2);
        int maxy = Math.max(vy1, vy2);
        if (miny > hy || maxy < hy) {
            return null;
        }
        return new Point(vx, hy);
    }

    public static Point intersectWithVerticalLine(Point p1, Point p2, int vx, int starty, int endy) {
        return intersectWithVerticalLine(p1.x, p1.y, p2.x, p2.y, vx, starty, endy);
    }

    public static Point intersectWithVerticalLine(int x1, int y1, int x2, int y2, int vx, int starty, int endy) {
        if (x1 == x2) {
            return null;
        }
        if (y1 == y2) {
            return intersectVH(vx, starty, endy, x1, x2, y1);
        }
        int dx = vx - x1;
        int iy = (int) ((double) dx * tangent(x1, y1, x2, y2) + y1);
        if (iy < Math.min(y1, y2) || iy > Math.max(y1, y2)) {
            return null;
        }
        if (iy < Math.min(starty, endy) || iy > Math.max(starty, endy)) {
            return null;
        }
        if (vx < Math.min(x1, x2) || vx > Math.max(x1, x2)) {
            return null;
        }
        return new Point(vx, iy);
    }

}
