package com.lazycoder.uidatasourceedit.moduleedit.toolbar.nousemodule;

import com.lazycoder.database.model.Module;
import com.lazycoder.uiutils.component.MultiSelectComboBox;
import java.awt.Component;
import javax.swing.JList;

public class NoUseModuleComboboxRenderer extends MultiSelectComboBox.MultiSelectComboBoxRenderer {

    /**
     *
     */
    private static final long serialVersionUID = 4309701654066480705L;

    public NoUseModuleComboboxRenderer() {
        super();
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