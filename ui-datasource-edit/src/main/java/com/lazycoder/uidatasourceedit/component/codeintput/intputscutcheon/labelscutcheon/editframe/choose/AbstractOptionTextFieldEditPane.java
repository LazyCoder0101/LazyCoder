package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;

import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public abstract class AbstractOptionTextFieldEditPane extends JComponent {

    protected static final int TEXT_FIELD_WIDTH = 120;
    /**
     *
     */
    private static final long serialVersionUID = -7721035500453646489L;

    protected int textFieldHeight = 30;

    protected int spacing = 10;

    public AbstractOptionTextFieldEditPane() {
        // TODO Auto-generated constructor stub
        setLayout(new OptionTextFieldEditPaneLayout());

    }

    protected abstract void addOptionTextField();

    public void delOptionTextField() {
        int num = getComponentCount();
        if (num > 1) {
            remove(num - 1);
        }
    }

    /**
     * 检查
     *
     * @return
     */
    public abstract boolean check();

    Dimension getRequiredDimension() {
        int w = 10 + (TEXT_FIELD_WIDTH + spacing) * getComponentCount();
        int h = textFieldHeight;
        return new Dimension(w, h);
    }

    // 该布局类的布局管理器
    class OptionTextFieldEditPaneLayout implements LayoutManager {
        // private static final int INTER_TAB_PADDING = 15;

        public OptionTextFieldEditPaneLayout() {
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            // 补充实现preferredSize以便在JScrollPane调整时使用
            Insets insets = parent.getInsets();
            int w = 0, h = textFieldHeight;
            w = (TEXT_FIELD_WIDTH + spacing) * getComponentCount() + 10;
            w += insets.left + insets.right;
            h += insets.top + insets.bottom;
            return new Dimension(w, h);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        @Override
        public void layoutContainer(Container parent) {
            // Insets insets = parent.getInsets();
            int x = 10, y = 0;
            Component component;
            for (int i = 0; i < getComponentCount(); i++) {
                component = getComponent(i);
                component.setBounds(x, y, TEXT_FIELD_WIDTH, textFieldHeight);
                component.doLayout();
                x += TEXT_FIELD_WIDTH + spacing;
            }
        }
    }

}

