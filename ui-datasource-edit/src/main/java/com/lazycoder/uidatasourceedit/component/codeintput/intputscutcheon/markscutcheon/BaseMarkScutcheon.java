package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon;

import com.formdev.flatlaf.ui.FlatUIUtils;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.HarryingMark;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.MyColoursBorder;
import com.lazycoder.uiutils.utils.ShineEffect;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public abstract class BaseMarkScutcheon extends MyButton implements MarkComponentInterface, HarryingMark {

    /**
     *
     */
    private static final long serialVersionUID = 8126836442837361012L;

    public final static int BLANK_SPACE_NUM = 2;

    protected final static String FEATURE_COLOR = HtmlPar.CRYSTAL_BLUE,
                        CODE_LABEL_COLOR = HtmlPar.PURPLE;

    /**
     * 是否可以编辑属性
     */
    protected boolean editOrNot = true;


    /**
     * 一般颜色
     */
    protected static final Color GENERAL_COLOR = ShineEffect.defaultComponentColor;

    private Border defaultborder;


    public void init() {
        // TODO Auto-generated constructor stub
        setUI(new MarkScutheonUI());
//        setDocument(new HTMLDocument());
//        setEditable(false);
        setOpaque(false);
//        if (editOrNot) {
        defaultborder = getBorder();
//        }
        addMouseListener(mouseAdapter);
        //		setBackground(generalColor);
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            super.mouseEntered(mouseEvent);
//            if (editOrNot) {
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
//            }
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            super.mouseExited(mouseEvent);
//            if (editOrNot) {
            setBorder(defaultborder);
//            }
        }
    };

    /**
     * 设置是否选中
     *
     * @param navigateOrNot
     */
    public void setNavigate(boolean navigateOrNot) {
        if (navigateOrNot == true) {
            setBorder(new MyColoursBorder(3));
//            if (editOrNot==false){
//                setBackground(generalColor);
//            }
        } else {
            setBorder(defaultborder);
        }
    }

    protected void setTextSize(int textLenth) {
        Dimension dd = new Dimension(textLenth * 12 + 30, 30);
        this.setMaximumSize(dd);
        this.setPreferredSize(dd);
        this.setMinimumSize(dd);
    }


    @Override
    public abstract void deleteFromPanel();

    @Override
    public abstract BaseMarkElement property();

    @Override
    public abstract BaseMarkElement getMarkElement();

//    @Override
//    public void paint(Graphics graphics) {
//        super.paint(graphics);
//        paintComponent(graphics);
//    }

//    @Override
//    protected void paintComponent(Graphics graphics) {
//        super.paintComponent(graphics);
//        if (editOrNot) {
//            ShineEffect.createShineEffect(graphics, this, ShineEffect.defaultComponentColor);
//
//            graphics.setColor(Color.black);
//            graphics.setFont(getFont());
//
//            graphics.drawString(getText(), getInsets().left, 20);
//
//        } else {
//            setBackground(GENERAL_COLOR);
//        }
//    }

    static class MarkScutheonUI extends BasicButtonUI {

        public MarkScutheonUI() {
            super();
        }

        public static ComponentUI createUI(JComponent c) {
            return FlatUIUtils.createSharedUI(MarkScutheonUI.class, MarkScutheonUI::new);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            ShineEffect.createShineEffect(g, c, ShineEffect.defaultComponentColor);
            super.paint(g, c);
        }
    }

}
