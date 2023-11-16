package com.lazycoder.uiutils.component;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Vector;

/**
 * 摘自： https://blog.csdn.net/u012643122/article/details/39081017
 * 自定义单选下拉框
 * <p>
 *
 * @author tang
 */
@SuppressWarnings({"rawtypes", "unchecked", "serial"})
public class CustomComboBox<E> extends JComboBox<E> {


    protected Icon arrowIcon;

    protected Color selectionBackground;
    protected Color selectionForeground;
    protected Color popupBackground;
    protected Color popupForeground;
    protected Border popupBorder;

    public CustomComboBox() {
        init();
    }

    public CustomComboBox(ComboBoxModel<E> aModel) {
        super(aModel);
        init();
    }

    public CustomComboBox(E[] items) {
        super(items);
        init();

    }

    public CustomComboBox(Vector<E> items) {
        super(items);
        init();
    }

    public CustomComboBox(Icon arrowIcon) {
        this.arrowIcon = arrowIcon;
        init();
    }

    public static boolean isMenuShortcutKeyDown(InputEvent event) {
        return (event.getModifiersEx() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()) != 0;
    }

    protected void init() {

        setUI(new CustomComboBoxUI());
        setOpaque(true);
        setSelectionBackground(new Color(236, 245, 255));
        Color theForeground = new Color(96, 98, 102);
        setSelectionForeground(theForeground);
        setForeground(theForeground);
//		setBackgroundAndToPopup(new Color(236,245,255));
//		setSelectionForeground(new Color());
        // setForeground(Color.decode("#9a9a9a"));
        // setBackground(Color.WHITE);
//		Border lineBorder = BorderFactory.createLineBorder(Color.decode("#c5c7c8"));
//		setBorder(lineBorder);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
        // setPopupForeground(getForeground());
        // setPopupBackground(getBackground());
        // getPopup().setOpaque(true);
        // getPopup().setBackground(popupBackground);
//		setPopupBorder(lineBorder);
        setRenderer(new CustomComboBoxRenderer());
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);
        if (getArrowButton() != null) {
            getArrowButton().addMouseListener(l);
        }
    }

    public void setSelectionBackground(Color selectionBackground) {
        this.selectionBackground = selectionBackground;
        getPopup().getList().setSelectionBackground(selectionBackground);
    }

    public void setSelectionForeground(Color selectionForeground) {
        this.selectionForeground = selectionForeground;
        getPopup().getList().setSelectionForeground(selectionForeground);
    }

    public void setPopupBackground(Color popupBackground) {
        this.popupBackground = popupBackground;
        getPopup().getList().setBackground(popupBackground);
    }

    public void setPopupForeground(Color popupForeground) {
        this.popupForeground = popupForeground;
        getPopup().getList().setForeground(popupForeground);
    }

    public void setPopupBorder(Border popupBorder) {
        this.popupBorder = popupBorder;
        getPopup().setBorder(popupBorder);
    }

    @Override
    public void setUI(ComboBoxUI ui) {
        if (ui instanceof CustomComboBoxUI) {
            super.setUI(ui);
        } else {
            super.setUI(new CustomComboBoxUI());
        }
    }

    @Override
    public void updateUI() {

        setUI(new CustomComboBoxUI());

        setSelectionBackground(selectionBackground);
        setSelectionForeground(selectionForeground);
        setPopupBackground(popupBackground);
        setPopupForeground(popupForeground);
        setPopupBorder(popupBorder);

        ListCellRenderer<?> renderer = getRenderer();
        if (renderer instanceof Component) {
            SwingUtilities.updateComponentTreeUI((Component) renderer);
        }
    }

    /**
     * 如果想将ComboBox的Background和Foreground与弹出的Popup的Background和Foreground保持一致则可以调用此方法
     */
    public void synchGroundToPopup() {
        setPopupBackground(getBackground());
        setPopupForeground(getForeground());
    }

    /**
     * 如果想将ComboBox的Background和Foreground和Border与弹出的Popup的Background和Foreground和Border保持一致则可以调用此方法
     */
    public void synchAllToPopup() {
        setPopupBackground(getBackground());
        setPopupForeground(getForeground());
        setPopupBorder(getBorder());
    }

    /**
     * 设置ComboBox的Background和Popup的Background
     *
     * @param color
     */
    public void setBackgroundAndToPopup(Color color) {
        setBackground(color);
        setPopupBackground(color);
    }

    /**
     * 设置ComboBox的Foreground和Popup的Foreground
     *
     * @param color
     */
    public void setForegroundAndToPopup(Color color) {
        setForeground(color);
        setPopupForeground(color);
    }

    /**
     * 设置ComboBox的Border和Popup的Border
     *
     * @param border
     */
    public void setBorderAndToPopup(Border border) {
        setBorder(border);
        setPopupBorder(border);
    }

    public Icon getArrowIcon() {
        if (arrowIcon == null) {
            arrowIcon = new ArrowIcon(16, 10, 10, 10, new Color(96, 98, 102), SwingConstants.BOTTOM);
        }
        return arrowIcon;
    }

    public BasicComboPopup getPopup() {
        return ((CustomComboBoxUI) getUI()).getPopup();
    }

    public JButton getArrowButton() {
        return ((CustomComboBoxUI) getUI()).getArrowButton();
    }

    public static class CustomList<E> extends JList<E> {

        public CustomList() {
            super();
        }

        public CustomList(E[] listData) {
            super(listData);
        }

        public CustomList(ListModel<E> dataModel) {
            super(dataModel);
        }

        public CustomList(Vector<? extends E> listData) {
            super(listData);
        }

        @Override
        public void processMouseEvent(MouseEvent e) {
            if (isMenuShortcutKeyDown(e)) {
                // Fix for 4234053. Filter out the Control Key from the list.
                // ie., don't allow CTRL key deselection.
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                e = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(),
                        e.getModifiersEx() ^ toolkit.getMenuShortcutKeyMaskEx(), e.getX(), e.getY(), e.getXOnScreen(),
                        e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), MouseEvent.NOBUTTON);
            }
            super.processMouseEvent(e);
        }
    }

    public static class CustomComboBoxUI extends MetalComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            final CustomComboBox box = (CustomComboBox) comboBox;
            Icon arrowIcon = box.getArrowIcon();
            JButton button = new JButton(arrowIcon);
            button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.CENTER);
            button.setRolloverEnabled(true);
            button.setFocusPainted(false);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
            if (arrowIcon != null) {
                button.setPressedIcon(new MoveIcon(arrowIcon, 0, 1));
            }
            button.setName("ComboBox.arrowButton");
            return button;
        }

        /**
         * Paints the currently selected item.
         */
        @Override
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            Component c = comboBox.getRenderer().getListCellRendererComponent(listBox, comboBox.getSelectedItem(), -1,
                    hasFocus && !isPopupVisible(comboBox), false);
            c.setBackground(comboBox.getBackground());// 清除渲染器原来设置的背景色,将渲染器的背景设置成ComboBox的背景色
            c.setForeground(comboBox.getForeground());// 清除渲染器原来设置的前景色,将渲染器的前景设置成ComboBox的前景色
            if (c instanceof JComponent) {
                JComponent jc = (JComponent) c;
                jc.setOpaque(comboBox.isOpaque());
            }

            int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
            if (padding != null) {
                x = bounds.x + padding.left;
                y = bounds.y + padding.top;
                w = bounds.width - (padding.left + padding.right);
                h = bounds.height - (padding.top + padding.bottom);
            }

            currentValuePane.paintComponent(g, c, comboBox, x, y, w, h, c instanceof JPanel);
        }

        /**
         * Paints the background of the currently selected item.
         */
        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            Color t = g.getColor();
            g.setColor(comboBox.getBackground());
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g.setColor(t);
        }

        public BasicComboPopup getPopup() {
            return (BasicComboPopup) popup;
        }

        public JButton getArrowButton() {
            return arrowButton;
        }
    }

    public static class CustomComboBoxRenderer extends DefaultListCellRenderer
            implements ListCellRenderer<Object>, Serializable {

        private Border rendererBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);

        public CustomComboBoxRenderer() {
            setBorder(rendererBorder);
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBorder(rendererBorder);
            return this;
        }

        public Border getRendererBorder() {
            return rendererBorder;
        }

        public void setRendererBorder(Border rendererBorder) {
            this.rendererBorder = rendererBorder;
        }
    }

    public static class CustomComboBoxTextSizeRenderer extends CustomComboBoxRenderer {

        private int textSize;

        public CustomComboBoxTextSizeRenderer(int textSize) {
            this.textSize = textSize;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            JLabel component = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                String toolTipText = (String) value;
                if (toolTipText.length() > textSize) {
                    component.setToolTipText(toolTipText);
                }
            }
            return component;
        }
    }

    /**
     * 箭头Icon
     *
     * @author tang
     */
    public static class ArrowIcon implements Icon {

        protected int iconWidth;
        protected int iconHeight;
        protected int triangleWidth;
        protected int triangleHeight;
        protected Color triangleColor;
        protected int direction;
        protected Polygon triangle = new Polygon();

        public ArrowIcon(int width, int height, Color arrowColor, int direction) {
            this(width, height, width, height, arrowColor, direction);
        }

        public ArrowIcon(int iconWidth, int iconHeight, int triangleWidth, int triangleHeight, Color triangleColor,
                         int direction) {
            this.iconWidth = iconWidth;
            this.iconHeight = iconHeight;
            this.triangleWidth = triangleWidth;
            this.triangleHeight = triangleHeight;
            this.triangleColor = triangleColor;
            this.direction = direction;

            createTriangle();
        }

        protected void createTriangle() {
            int x = (iconWidth - triangleWidth) / 2;
            int y = (iconHeight - triangleHeight) / 2;

            if (direction == SwingConstants.TOP) {// 箭头向上
                triangle.addPoint(triangleWidth / 2 + x, y);
                triangle.addPoint(triangleWidth + x, triangleHeight + y);
                triangle.addPoint(x, triangleHeight + y);
            } else if (direction == SwingConstants.BOTTOM) {// 箭头向下
                triangle.addPoint(x, y);
                triangle.addPoint(triangleWidth + x, y);
                triangle.addPoint(triangleWidth / 2 + x, triangleHeight + y);
            } else if (direction == SwingConstants.LEFT) {
                triangle.addPoint(x, triangleHeight / 2 + y);
                triangle.addPoint(triangleWidth + x, y);
                triangle.addPoint(triangleWidth + x, triangleHeight + y);
            } else if (direction == SwingConstants.RIGHT) {
                triangle.addPoint(x, y);
                triangle.addPoint(triangleWidth + x, triangleHeight / 2 + y);
                triangle.addPoint(x, triangleHeight + y);
            }
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(triangleColor);
            AffineTransform af = new AffineTransform();
            af.translate(x, y);
            Shape shape = af.createTransformedShape(triangle);
            g2.fill(shape);
        }

        @Override
        public int getIconWidth() {
            return iconWidth;
        }

        @Override
        public int getIconHeight() {
            return iconHeight;
        }
    }

    /**
     * 将原Icon位置移动的Icon
     *
     * @author PC
     */
    public static class MoveIcon implements Icon {
        Icon icon;
        int moveX;
        int moveY;

        public MoveIcon(Icon icon, int moveX, int moveY) {
            this.icon = icon;
            this.moveX = moveX;
            this.moveY = moveY;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            icon.paintIcon(c, g, x + moveX, y + moveY);
        }

        @Override
        public int getIconWidth() {
            return icon.getIconWidth();
        }

        @Override
        public int getIconHeight() {
            return icon.getIconHeight();
        }
    }
}