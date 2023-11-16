package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NoteRowHeaderView extends JComponent {

    public final int nHEIGHT = Integer.MAX_VALUE - 1000000;

    private int valueRow = 0, preferredWidth = 100;

    private JLabel nameLabel = new JLabel("选项名"),
            optionLabel = new JLabel("选项值"),
            noteLabel = new JLabel("注释");


    public NoteRowHeaderView() {
        setLayout(new NoteRowHeaderViewLayout());
//        nameLabel.setToolTipText("这个选项组件有哪些选项可以选的，把选项名称列出来");
        add(nameLabel);
//        optionLabel.setToolTipText("选中某个选项后，需要输入哪些值，请列出来");
        add(optionLabel);
//        noteLabel.setToolTipText("每一行的选项是做什么用的，可以备注一下，让自己添加的时候便于寻找");
        add(noteLabel);
//        addValueNote();
        setPreferredSize(new Dimension(preferredWidth, nHEIGHT));
    }

    public void addValueNote(String note) {
        valueRow++;
        JTextField textField = new JTextField(note);
        add(textField);
        setPreferredSize(new Dimension(preferredWidth, nHEIGHT));
    }

    public void delValueNote() {
        Component component = getComponent(getComponentCount() - 1);
        if (component instanceof JTextField) {
            valueRow--;
            remove(component);
            setPreferredSize(new Dimension(preferredWidth, nHEIGHT));
        }
    }

    /**
     * 获取选项的各行注释
     *
     * @return
     */
    public ArrayList<String> getRowNoteList() {
        ArrayList<String> noteList = new ArrayList<>();
        Component component;
        JTextField textField;
        String temp;
        int theNum = getComponentCount();
        for (int i = 0; i < theNum; i++) {
            component = getComponent(i);
            if (component instanceof JTextField) {
                textField = (JTextField) component;
                temp = textField.getText() == null ? "" : textField.getText().trim();
                noteList.add(temp);
            }
        }
        return noteList;
    }

    // 该布局类的布局管理器
    class NoteRowHeaderViewLayout implements LayoutManager {
        // private static final int INTER_TAB_PADDING = 15;

        public NoteRowHeaderViewLayout() {
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
            return new Dimension(40, OptionNameTextFieldEditPane.THIS_PANE_HIGHT + valueRow * OptionValueTextFieldEditPane.THIS_PANE_HIGHT);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        @Override
        public void layoutContainer(Container parent) {
            // Insets insets = parent.getInsets();
            int x = 4, y = OptionNameTextFieldEditPane.THIS_PANE_HIGHT;
            nameLabel.setBounds(58, 0, 40, 30);
            optionLabel.setBounds(50, 38, 40, 30);
            noteLabel.setBounds(5, 45, 40, 30);

            Component component;
            for (int i = 0; i < getComponentCount(); i++) {
                component = getComponent(i);
                if (component instanceof JTextField) {
                    component.setBounds(x, y + 20, 90, 30);
                    component.doLayout();
                    y = y + OptionValueTextFieldEditPane.THIS_PANE_HIGHT + 10;
                }
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int h = OptionNameTextFieldEditPane.THIS_PANE_HIGHT - 1 + 10;
        g.setColor(OptionNameTextFieldEditPane.lineColor);
        g.drawLine(0, h, getWidth(), h);

//        g.drawLine(0, 0, getWidth(), 3 + (2 * h) / 5);
//        g.drawLine(0, 0, ((2 * getWidth()) / 3) - 6, h);
//        g.drawLine(getWidth() - 1, 0, getWidth() - 1, 9999);//画右边的那条竖线
        g.drawLine(0, 0, getWidth(), (3 * h) / 5);
        g.drawLine(0, 0, ((getWidth()) / 2), h);
        g.drawLine(getWidth() - 1, 0, getWidth() - 1, 9999);//画右边的那条竖线
    }


}
