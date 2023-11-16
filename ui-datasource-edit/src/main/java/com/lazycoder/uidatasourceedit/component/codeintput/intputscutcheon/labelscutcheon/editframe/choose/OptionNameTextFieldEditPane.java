package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;

import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import lombok.Getter;

public class OptionNameTextFieldEditPane extends AbstractOptionTextFieldEditPane {

    /**
     *
     */
    private static final long serialVersionUID = 8406202599719187553L;

    public static Color lineColor = new Color(220, 220, 220);

    public static final int THIS_PANE_HIGHT = 65;

    public OptionNameTextFieldEditPane() {
        // TODO Auto-generated constructor stub
        textFieldHeight = THIS_PANE_HIGHT;
    }

    @Override
    protected void addOptionTextField() {
        int num = getComponentCount();
        OptionNameBox textField = new OptionNameBox(num + 1);
        // textField.setPreferredSize(new Dimension(textFieldWidth,
        // OptionNameBox.OptionNameBoxHeight));
        add(textField);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(lineColor);
        g.drawLine(0, textFieldHeight - 1, getWidth(), textFieldHeight - 1);
    }

    /**
     * 还原
     *
     * @param nameList
     */
    public void recover(ArrayList<String> nameList) {
        int temp = 0;
        OptionNameBox textField;
        for (int i = 0; i < nameList.size(); i++) {
            temp++;
            textField = new OptionNameBox(temp);
            textField.getTextField().setText(nameList.get(i));
            add(textField);
        }
    }

    /**
     * 获取内容列表
     *
     * @return
     */
    public ArrayList<String> getContentList() {
        ArrayList<String> list = new ArrayList<>();
        OptionNameBox temp;
        for (int i = 0; i < getComponentCount(); i++) {
            temp = (OptionNameBox) getComponent(i);
            list.add(temp.getTextField().getText());
        }
        return list;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        OptionNameBox temp1, temp2;
        String str1, str2;
        for (int i = 0; i < getComponentCount(); i++) {
            temp1 = (OptionNameBox) getComponent(i);
            str1 = temp1.getTextField().getText().trim();
            if ("".equals(str1)) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "亲，第" + (i + 1) + "的选项名称还没填哦。  (*^▽^*)");
                break;
            }
            for (int a = i + 1; a < getComponentCount(); a++) {
                temp2 = (OptionNameBox) getComponent(a);
                str2 = temp2.getTextField().getText().trim();
                if (str1.equals(str2)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null, "亲，第" + (i + 1) + "的选项名和第" + (a + 1) + "选项名一样，改一下吧");
                    break;
                }
            }
            if (flag == false) {
                break;
            }
        }
        return flag;
    }

    /**
     * 添加选项名
     */
    public void addOptionName() {
        addOptionTextField();
    }

    class OptionNameBox extends JComponent {

        /**
         *
         */
        private static final long serialVersionUID = 6117928717775225331L;

        private JLabel label;

        @Getter
        private JTextField textField;

        public OptionNameBox(int index) {
            // TODO Auto-generated constructor stub
            setLayout(null);

            label = new JLabel("选项" + index);
            label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            label.setBounds(0, 0, TEXT_FIELD_WIDTH, 14);
            add(label);

            textField = new JTextField();
            textField.setBounds(0, 20, TEXT_FIELD_WIDTH, 35);
            add(textField);
        }

    }

}
