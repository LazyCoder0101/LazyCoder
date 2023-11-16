package com.lazycoder.uiutils.component;

/*
 * Arjick@163.com
 *
 */

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JToolTip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A JTabbedPane which has a close ('X') icon on each tab.
 * To add a tab, use the method addTab(String, Component)
 * To have an extra icon on each tab (e.g. like in JBuilder,
 * showing the file type) use the method
 * addTab(String, Component, Icon).
 * Only clicking the 'X' closes the tab.
 */

/**
 * 源码摘自http://blog.csdn.net/ycb1689/article/details/7971954
 *
 * @author Administrator
 */
public class JClosableTabbedPane extends JTabbedPane implements MouseListener {

    /**
     *
     */
    private static final long serialVersionUID = 5318684511407056177L;
    /**
     *
     */
    String delTabName = "";// 临时变量，用来记下被删的面板的文件名
    // 有没有按下关闭按钮，如果有，设为true
    private boolean pressCloseOrNot = false;
    private double scaleRatio = 0.3;
    private HashMap<String, Component> maps = new HashMap<String, Component>();

    public JClosableTabbedPane() {
        super();
        addMouseListener(this);
    }

    public String getDelTabName() {
        return delTabName;
    }

    public void setDelTabName(String delTabName) {
        this.delTabName = delTabName;
    }

    public boolean isPressCloseOrNot() {
        return pressCloseOrNot;
    }

    public void setPressCloseOrNot(boolean pressCloseOrNot) {
        this.pressCloseOrNot = pressCloseOrNot;
    }

    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, new CloseTabIcon(null), component);
    }

    public void addTab(String title, Component component, Icon extraIcon) {
        super.addTab(title, new CloseTabIcon(extraIcon), component);
    }

    public void addTab(String title, Component component, String tip) {
        super.addTab(title, new CloseTabIcon(null), component, tip);
    }

    public void superAddTab(String title, Component component, String tip) {
        super.addTab(title, null, component, tip);
    }

    public void superAddTab(String title, Component component) {
        super.addTab(title, component);
    }

    public void insertCloseTab(String title, Component component, int index) {
        super.insertTab(title, new CloseTabIcon(null), component, null, index);
    }

    public void insertTab(String title, Component component, int index) {
        super.insertTab(title, null, component, null, index);
    }

    @Override
    public void insertTab(String title, Icon icon, Component component, String tip, int index) {
        tip = "tab" + component.hashCode();
        maps.put(tip, component);
        super.insertTab(title, icon, component, tip, index);
    }

    @Override
    public void removeTabAt(int index) {
        Component component = getComponentAt(index);
        maps.remove("tab" + component.hashCode());
        super.removeTabAt(index);
    }

    @Override
    public JToolTip createToolTip() {
        ImageToolTip tooltip = new ImageToolTip();
        tooltip.setComponent(this);
        return tooltip;
    }

    public double getScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(double scaleRatio) {
        this.scaleRatio = scaleRatio;
    }

    /**
     * 查看是否按下了关闭按钮，给继承类的鼠标事件调用
     *
     * @param e
     * @return
     */
    public boolean pressTheCloseButton(MouseEvent e) {
        boolean flag = false;
        int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber >= 0) {
            if (getIconAt(tabNumber) != null) {
                Rectangle rect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();
                if (rect.contains(e.getX(), e.getY())) {// 如果按下关闭按钮
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber < 0) {
            return;
        }
        if (getIconAt(tabNumber) != null) {
            Rectangle rect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();
            if (rect.contains(e.getX(), e.getY())) {// 如果按下关闭按钮
                this.setDelTabName(this.getTitleAt(tabNumber));// 把删除的文件名记下
                this.setPressCloseOrNot(true);// 把标记设为true
                // the tab is being closed
                this.removeTabAt(tabNumber);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    class ImageToolTip extends JToolTip {
        /**
         *
         */
        private static final long serialVersionUID = 6773397012042576822L;

        @Override
        public Dimension getPreferredSize() {
            String tip = getTipText();
            Component component = maps.get(tip);
            if (component != null) {
                return new Dimension((int) (getScaleRatio() * component.getWidth()),
                        (int) (getScaleRatio() * component.getHeight()));
            } else {
                return super.getPreferredSize();
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            String tip = getTipText();
            Component component = maps.get(tip);
            if (component instanceof JComponent) {
                JComponent jcomponent = (JComponent) component;
                Graphics2D g2d = (Graphics2D) g;
                AffineTransform at = g2d.getTransform();
                g2d.transform(AffineTransform.getScaleInstance(getScaleRatio(), getScaleRatio()));
                ArrayList<JComponent> dbcomponents = new ArrayList<JComponent>();
                updateDoubleBuffered(jcomponent, dbcomponents);
                jcomponent.paint(g);
                resetDoubleBuffered(dbcomponents);
                g2d.setTransform(at);
            }
        }

        private void updateDoubleBuffered(JComponent component, ArrayList<JComponent> dbcomponents) {
            if (component.isDoubleBuffered()) {
                dbcomponents.add(component);
                component.setDoubleBuffered(false);
            }
            for (int i = 0; i < component.getComponentCount(); i++) {
                Component c = component.getComponent(i);
                if (c instanceof JComponent) {
                    updateDoubleBuffered((JComponent) c, dbcomponents);
                }
            }
        }

        private void resetDoubleBuffered(ArrayList<JComponent> dbcomponents) {
            for (JComponent component : dbcomponents) {
                component.setDoubleBuffered(true);
            }
        }
    }
}

/**
 * The class which generates the 'X' icon for the tabs. The constructor accepts
 * an icon which is extra to the 'X' icon, so you can have tabs like in
 * JBuilder. This value is null if no extra icon is required.
 */
class CloseTabIcon implements Icon, ImageObserver {

    private static Color closePaintColor = new Color(165,165,165);

    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private Icon fileIcon;

    public CloseTabIcon(Icon fileIcon) {
        this.fileIcon = fileIcon;
        width = 16;
        height = 16;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.xPos = x;
        this.yPos = y;
        Color col = g.getColor();
        g.setColor(closePaintColor);
        int y_p = y + 2;
//        g.drawLine(x + 1, y_p, x + 12, y_p);
//        g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
//        g.drawLine(x, y_p + 1, x, y_p + 12);
//        g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
        g.setColor(col);
        if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x + width, y_p);
        }

    }

    @Override
    public int getIconWidth() {
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(xPos, yPos, width, height);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        // TODO Auto-generated method stub
        return false;
    }

}