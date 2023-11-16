package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.utils.JsonUtil;

import javax.swing.JComponent;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;


public class GeneralOptionTextFieldEditPane extends JComponent {

    protected static final int TEXT_FIELD_HEIGHT = 30;
    /**
     *
     */
    private static final long serialVersionUID = 5524631221141266508L;
    protected int textFieldWidth = 120;

    protected int spacing = 10;

    public GeneralOptionTextFieldEditPane() {
        // TODO Auto-generated constructor stub
        setLayout(new thisPaneLayout());
    }

    public GeneralOptionTextFieldEditPane(int textFieldWidth) {
        this();
        this.textFieldWidth = textFieldWidth;
    }

    public JTextField addOptionTextField() {
        JTextField textField = new JTextField();
        add(textField);
        return textField;
    }

    public void delOptionTextField() {
        int num = getComponentCount();
        if (num > 0) {
            remove(num - 1);
        }
    }

    public void delAndKeepFirstOptionTextField() {
        int num = getComponentCount();
        if (num > 1) {
            for (int i = num - 1; i > 0; i--) {
                if (i != 0) {
                    remove(i);
                }
            }
        }
    }

    /**
     * 返回输入内容的数组
     *
     * @return
     */
    public ArrayList<String> getTextList() {
        ArrayList<String> list = new ArrayList<>();
        JTextField temp;
        for (int i = 0; i < getComponentCount(); i++) {
            temp = (JTextField) getComponent(i);
            list.add(temp.getText());
        }
        return list;
    }

    /**
     * 返回输入内容的json格式
     *
     * @return
     */
    public String getTextJson() {
        ArrayList<String> textList = getTextList();
        String str = JsonUtil.getJsonStr(textList);
        return str;
    }

    /**
     * 还原
     *
     * @param textList
     */
    public void restore(ArrayList<String> textList) {
        JTextField textField;
        for (int i = 0; i < textList.size(); i++) {
            textField = new JTextField();
            textField.setText(textList.get(i));
            add(textField);
        }
    }

    /**
     * 获取当前已经添加的输入框的数量
     *
     * @return
     */
    public int getCurrentTextFieldCount() {
        return getComponentCount();
    }

    public Dimension getRequiredDimension() {
        int w = textFieldWidth;
        int h = 10 + (TEXT_FIELD_HEIGHT + spacing) * getComponentCount();
        return new Dimension(w, h);
    }

    // 该布局类的布局管理器
    class thisPaneLayout implements LayoutManager {
        // private static final int INTER_TAB_PADDING = 15;

        public thisPaneLayout() {
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
            int w = 5, h = 5;
            h = (TEXT_FIELD_HEIGHT + spacing) * getComponentCount() + 10;
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
            int x = 5, y = 5;
            Component component;
            for (int i = 0; i < getComponentCount(); i++) {
                component = getComponent(i);
                component.setBounds(x, y, textFieldWidth, TEXT_FIELD_HEIGHT);
                component.doLayout();
                y += TEXT_FIELD_HEIGHT + spacing;
            }
        }
    }

}
