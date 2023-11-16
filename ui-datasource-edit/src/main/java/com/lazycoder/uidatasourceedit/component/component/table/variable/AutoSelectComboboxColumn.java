package com.lazycoder.uidatasourceedit.component.component.table.variable;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.uiutils.component.ComboboxColumn;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class AutoSelectComboboxColumn extends ComboboxColumn implements BaseModel {

    public static String[] optionShowValue = new String[]{"是", "否"};

    private AbstractVariableTable variableTable = null;

    public AutoSelectComboboxColumn(AbstractVariableTable table, JComboBox comboBox, int column) {
        super(table, comboBox, column);
        this.variableTable = table;
    }

    public static AutoSelectComboboxColumn createAutoSelectComboboxColumn(AbstractVariableTable table, int column) {
        return new AutoSelectComboboxColumn(table, new AutoSelectCombobox(table), column);
    }

    /**
     * 获取对应是否自动选择需要显示的值
     *
     * @param noUserSelectionIsRequired
     * @return
     */
    public static String getShowAutoSelectText(boolean noUserSelectionIsRequired) {
        return noUserSelectionIsRequired == true ? optionShowValue[0] : optionShowValue[1];
    }

    /**
     * 获取对应是否自动选择需要显示的值
     *
     * @param dontNeedTheUserToSelect
     * @return
     */
    public static String getShowAutoSelectText(int dontNeedTheUserToSelect) {
        return dontNeedTheUserToSelect == TRUE_ ? optionShowValue[0] : optionShowValue[1];
    }

    /**
     * 根据text判断是否需要
     *
     * @param text
     * @return
     */
    public static int getDontNeedTheUserToSelect(String text) {
        return "是".equals(text) ? TRUE_ : FALSE_;
    }

    public static boolean getNoUserSelectionIsRequired(String text) {
        return "是".equals(text) ? true : false;
    }

    static class AutoSelectCombobox extends JComboBox {

        private AbstractVariableTable variableTable = null;

        public AutoSelectCombobox(AbstractVariableTable variableTable) {
            this.variableTable = variableTable;
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (String str : optionShowValue) {
                model.addElement(str);
            }
            setModel(model);
            addPopupMenuListener(popupMenuListener);
        }

        PopupMenuListener popupMenuListener = new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (variableTable != null) {
                    int dontNeedTheUserToSelect = optionShowValue[0].equals(getSelectedItem()) ? TRUE_ : FALSE_,
                            row = variableTable.getSelectedRow();
                    if (row > -1) {
                        variableTable.updateWetherAutoSelectParam(row, dontNeedTheUserToSelect);
                    }
                }
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        };

    }

}
