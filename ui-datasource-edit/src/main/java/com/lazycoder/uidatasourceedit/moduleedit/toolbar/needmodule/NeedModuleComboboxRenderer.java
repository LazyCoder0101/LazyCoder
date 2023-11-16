package com.lazycoder.uidatasourceedit.moduleedit.toolbar.needmodule;

import com.lazycoder.database.model.Module;
import com.lazycoder.uiutils.component.MultiSelectComboBox;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;

public class NeedModuleComboboxRenderer extends MultiSelectComboBox.MultiSelectComboBoxRenderer {

    /**
     *
     */
    private static final long serialVersionUID = -342406570894227449L;

    public NeedModuleComboboxRenderer() {
        super();
    }

    public static DefaultComboBoxModel<Module> getTestModel() {
        DefaultComboBoxModel<Module> model = new DefaultComboBoxModel<>();
        Module m1 = new Module();
        m1.setClassName("分类1");
        m1.setModuleName("模块1");
        model.addElement(m1);
        Module m2 = new Module();
        m2.setClassName("分类2");
        m2.setModuleName("模块2");
        model.addElement(m2);
        Module m3 = new Module();
        m3.setClassName("分类3");
        m3.setModuleName("模块3");
        model.addElement(m3);
        return model;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        // setComponentOrientation(list.getComponentOrientation());

        String theText = ""; // 显示内容
        String toolTipText = ""; // 提示文本
        if (value instanceof Module) {
            Module module = (Module) value; // 把值转换成数组

            if (value != null) {
                theText = module.getModuleName(); // 获取显示内容

                toolTipText = "分类：" + module.getClassName(); // 获取提示文本
                setToolTipText(toolTipText);
            }

            if (isSelected) {
                this.setBackground(list.getSelectionBackground());
                this.setForeground(list.getSelectionForeground());
            } else {
                this.setBackground(list.getBackground());
                this.setForeground(list.getForeground());
            }
        }
        setEnabled(list.isEnabled());
        setSelected(isSelected);
        setFont(list.getFont());
        this.setText(theText);
        return this;
    }

}