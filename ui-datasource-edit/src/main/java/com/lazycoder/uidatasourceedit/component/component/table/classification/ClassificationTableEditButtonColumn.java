package com.lazycoder.uidatasourceedit.component.component.table.classification;

import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.modulemanagement.editframe.ClassEditFrame;
import com.lazycoder.uiutils.component.ButtonColumn;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;


public class ClassificationTableEditButtonColumn extends ButtonColumn {

    /**
     *
     */
    private static final long serialVersionUID = -8541413299935854944L;

    public ClassificationTableEditButtonColumn(JTable table, int column) {
        super(table, column);
        // TODO Auto-generated constructor stub
        getEditButton().addActionListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (DataSourceEditHolder.currentModule == null) {
                fireEditingStopped();
                String className = (String) getTable().getValueAt(getTable().getSelectedRow(),
                        ClassificationColumn.CLASS_NAME);
                TheClassification classification = SysService.CLASSIFICATION_SERVICE.getTheClassification(className);
                if (classification != null) {
                    new ClassEditFrame(classification);
                }
            } else {
                fireEditingStopped();
                String className = (String) getTable().getValueAt(getTable().getSelectedRow(),
                        ClassificationColumn.CLASS_NAME);
                if (className.equals(DataSourceEditHolder.currentModule.getClassName())) {
                    LazyCoderOptionPane.showMessageDialog(null,
                            "(*^▽^*) 亲，你在\"模块设置\"里正在编辑的模块就是\"" + className + "\"分类的，建议先保存那里需要的数据，再切换到其他分类，以避免数据错乱", "系统信息", JOptionPane.PLAIN_MESSAGE);
                } else {
                    TheClassification classification = SysService.CLASSIFICATION_SERVICE.getTheClassification(className);
                    if (classification != null) {
                        new ClassEditFrame(classification);
                    }
                }
            }
        }
    };

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component cc = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (NotNamed.nonModule.getClassName().equals(getTable().getValueAt(row, ClassificationColumn.CLASS_NAME))) {
            cc.setEnabled(false);
        } else {
            cc.setEnabled(true);
        }
        return cc;
    }

}
