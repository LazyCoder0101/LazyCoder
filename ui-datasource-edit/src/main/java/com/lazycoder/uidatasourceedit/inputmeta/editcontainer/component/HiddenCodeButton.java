package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import com.lazycoder.service.fileStructure.GeneralImageManager;
import lombok.NoArgsConstructor;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class HiddenCodeButton extends FoldButton {

    /**
     *
     */
    private static final long serialVersionUID = -6172551520606313035L;

    public HiddenCodeButton() {
        this(true);
    }

    /**
     * @param expanded 标题
     * @expanded 目前是否展开
     */
    public HiddenCodeButton(boolean expanded) {
        // this.text=text;
        super(expanded);
        setUI(new HiddenCodeButtonUI());
        setText("源码编辑");
    }

}

/**
 * 这个类时CaptionButton组建的UI类。 该UI类是个有状态UI类。
 *
 * @author William Chen
 * @mail rehte@hotmail.com
 */
@NoArgsConstructor
class HiddenCodeButtonUI extends FoldButtonUI {
    // 缺省前景色和缺省字体
    // private static final Color DEFAULT_FOREGROUND=new Color(33,93,198);
    private static final Color DEFAULT_FOREGROUND = Color.BLACK;
    // private static final Font DEFAULT_FONT=new Font("Dialog", Font.PLAIN,
    // 12);
    // 定义一些缺省属性的值，比如颜色、字体、尺寸、间隙、缺省图标、虚线框的stroke等等。
    private static final Color DARKER = new Color(198, 211, 247);
    private static final Color LIGHTER = new Color(255, 255, 255);
    private static final int TEXT_LEADING_GAP = 14, IMAGE_TAILING_GAP = 12;
    // private static final Color HOVERED_COLOR=new Color(66,142,255);
    private static final Color HOVERED_COLOR = Color.BLACK;
    private static Icon iconExpanded, iconFoldered, hoveredExpanded, hoveredFoldered;

    static {
        // 初始化
        iconExpanded = new ImageIcon(GeneralImageManager.ICON_EXPANDED);
        iconFoldered = new ImageIcon(GeneralImageManager.ICON_FOLDERED);
        hoveredExpanded = new ImageIcon(GeneralImageManager.HOVERED_EXPANDED);
        hoveredFoldered = new ImageIcon(GeneralImageManager.HOVERED_FOLDERED);
    }


    // 文字左边间隙
    private int textLeadingGap = TEXT_LEADING_GAP;
    // 图像邮编间隙
    private int imageTailingGap = IMAGE_TAILING_GAP;
    // 渐变色起始色
    private Color lightColor = LIGHTER;
    // 渐变色结束色
    private Color darkColor = DARKER;
    // 当前鼠标是否浮动在上面
    private boolean hovered;
    // 鼠标浮动在标题上方时标题的颜色
    private Color hoveredColor = HOVERED_COLOR;

    public static ComponentUI createUI(JComponent c) {
        return new HiddenCodeButtonUI();
    }


    // 画CaptionButton的背景。继承CaptionButtonUI的子类可以覆盖该方法自定义背景
    protected void paintBackground(Graphics g) {
        int w = button.getWidth();
        int h = button.getHeight();
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(1, 1, lightColor, w - 2, 1, darkColor);
        g2d.setPaint(gp);
        g2d.fillRect(1, 1, w - 2, h - 1);
        gp = new GradientPaint(2, 0, lightColor, w - 4, 0, darkColor);
        g2d.setPaint(gp);
        g2d.fillRect(2, 0, w - 4, 1);
        g2d.setColor(lightColor);
        g2d.drawLine(0, 2, 0, h - 1);
        g2d.setColor(darkColor);
        g2d.drawLine(w - 1, 2, w - 1, h - 1);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        paintBackground(g);
        paintCaptionText(g);
        paintIcon(g);
    }

    // 画标题栏展开、关闭图标，继承CaptionButtonUI的子类可以覆盖该方法自定义展开、关闭图标
    protected void paintIcon(Graphics g) {
        Icon icon = null;
        if (hovered) {
            icon = button.isExpanded() ? hoveredExpanded : hoveredFoldered;
        } else {
            icon = button.isExpanded() ? iconExpanded : iconFoldered;
        }
        int x = button.getWidth() - imageTailingGap - icon.getIconWidth();
        int y = (button.getHeight() - icon.getIconHeight()) / 2;
        icon.paintIcon(button, g, x, y);
    }

    // 画标题栏文字，继承CaptionButtonUI的子类可以覆盖该方法自定义文字的外观
    protected void paintCaptionText(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        if (button.getText() != null) {
            Color foreground = button.getForeground();
            Color color = hovered ? hoveredColor : foreground;
            g.setColor(color);
            int y = (button.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(button.getText(), textLeadingGap, y);
        }
    }


}
