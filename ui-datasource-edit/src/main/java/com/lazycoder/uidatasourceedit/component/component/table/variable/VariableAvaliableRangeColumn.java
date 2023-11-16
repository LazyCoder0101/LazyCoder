package com.lazycoder.uidatasourceedit.component.component.table.variable;

import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uiutils.component.ButtonColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VariableAvaliableRangeColumn extends ButtonColumn {

    /**
     *
     */
    private static final long serialVersionUID = 832585761508679297L;

    private AbstractVariableTable variableTable;

    public VariableAvaliableRangeColumn(AbstractVariableTable variableTable, int column) {
        super(variableTable, column);
        this.variableTable = variableTable;
        // TODO Auto-generated constructor stub
        getEditButton().addActionListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String usingRangeTextString = (String) variableTable.getValueAt(variableTable.getSelectedRow(), AbstractVariableTable.usingRange);
            AttributeUsageRange temp = AttributeUsageRange.convertShowTextIntoVariableUsageRange(usingRangeTextString);

            VariableUsageRangeCombobox comboBox = variableTable.getVariableUsageRangeCombobox();
            comboBox.setSelectedItem(temp);

            new VariableUsingRangeFrame(comboBox) {

                /**
                 *
                 */
                private static final long serialVersionUID = -7269273141835482201L;

                @Override
                public void ok() {
                    variableTable.updateUsingRange(variableTable.getSelectedRow(),
                            (AttributeUsageRange) comboBox.getModel().getSelectedItem());

                    super.ok();
                }
            };
            fireEditingStopped();
        }
    };

//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//                                                   int row, int column) {
//
//        if (hasFocus) {
//            renderButton.setForeground(table.getForeground());
//            renderButton.setBackground(UIManager.getColor("Button.background"));
//            renderButton.setForeground(UIManager.getColor("Button.foreground"));
//            renderButton.setBackground(UIManager.getColor("Button.background"));
//        }
//        renderButton.setText((String) this.table.getTableModel().getValueAt(row, column));
//
//        return renderButton;
//    }

}
