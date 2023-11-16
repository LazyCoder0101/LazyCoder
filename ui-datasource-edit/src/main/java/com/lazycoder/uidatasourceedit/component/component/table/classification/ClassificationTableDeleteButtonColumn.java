package com.lazycoder.uidatasourceedit.component.component.table.classification;

import com.lazycoder.database.common.NotNamed;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.ModuleManagementPaneHolder;
import com.lazycoder.uiutils.component.ButtonColumn;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ClassificationTableDeleteButtonColumn extends ButtonColumn {

    /**
     *
     */
    private static final long serialVersionUID = -1185001240371362355L;

    public ClassificationTableDeleteButtonColumn(JTable table, int column) {
        super(table, column);
        // TODO Auto-generated constructor stub
        getEditButton().addActionListener(actionListener);
    }

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

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

            String className = (String) getTable().getValueAt(getTable().getSelectedRow(),
                    ClassificationColumn.CLASS_NAME);
            if (DataSourceEditHolder.currentModule == null) {
                if (SysService.MODULE_INFO_SERVICE.selectHaveAddModuleFor(className)) {//如果该分类有添加过模块，不给删
                    LazyCoderOptionPane.showMessageDialog(null,
                            "o(´^｀)o 这个分类添加过模块了，真要删掉的话，把里面的模块改成其他分类吧", "系统信息", JOptionPane.PLAIN_MESSAGE);
                } else {
                    int n = LazyCoderOptionPane.showConfirmDialog(null,
                            "(～￣▽￣)～  真的要删除\"" + className + "\"这个分类吗?", "确认一下",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.OK_OPTION) {
                        SysService.CLASSIFICATION_SERVICE.delClassification(className);//真正删除所有模块的代码
                        if (ModuleManagementPaneHolder.classTable != null) {
                            ModuleManagementPaneHolder.classTable.showClassList();
                        }
                        if (DataSourceEditHolder.moduleManagementPane != null) {
                            DataSourceEditHolder.moduleManagementPane.queryModule();
                        }
                        if (ModuleManagementPaneHolder.classNameComboBox != null) {
                            ModuleManagementPaneHolder.classNameComboBox.updateItems();
                        }
                        if (ModuleEditPaneHolder.currentModuleSelectMenu != null) {
                            ModuleEditPaneHolder.currentModuleSelectMenu.uploadModuleItems();
                        }

                        LazyCoderOptionPane.showMessageDialog(null, "已删除\"" + className + "\"分类所有数据！", "系统信息", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            } else {
                if (className.equals(DataSourceEditHolder.currentModule.getClassName())) {//当前编辑的模块就是这个分类的，不给删
                    LazyCoderOptionPane.showMessageDialog(null,
                            "(*^▽^*) 亲，您在\"模块设置\"里正在编辑的模块就是\"" + className + "\"分类的，如果真的需要删除，建议先切换到其他分类，以避免数据错乱", "系统信息", JOptionPane.PLAIN_MESSAGE);
                } else {
                    if (SysService.MODULE_INFO_SERVICE.selectHaveAddModuleFor(className)) {//如果该分类有添加过模块，不给删
                        LazyCoderOptionPane.showMessageDialog(null,
                                "o(´^｀)o 这个分类添加过模块了，真要删掉的话，把里面的模块改成其他分类吧", "系统信息", JOptionPane.PLAIN_MESSAGE);

                    } else {
                        int n = LazyCoderOptionPane.showConfirmDialog(null,
                                "(～￣▽￣)～  真的要删除\"" + className + "\"这个分类吗?", "确认一下",
                                JOptionPane.YES_NO_OPTION);
                        if (n == JOptionPane.OK_OPTION) {
                            SysService.CLASSIFICATION_SERVICE.delClassification(className);//真正删除所有模块的代码
                            if (ModuleManagementPaneHolder.classTable != null) {
                                ModuleManagementPaneHolder.classTable.showClassList();
                            }
                            if (DataSourceEditHolder.moduleManagementPane != null) {
                                DataSourceEditHolder.moduleManagementPane.queryModule();
                            }
                            if (ModuleManagementPaneHolder.classNameComboBox != null) {
                                ModuleManagementPaneHolder.classNameComboBox.updateItems();
                            }
                            if (ModuleEditPaneHolder.currentModuleSelectMenu != null) {
                                ModuleEditPaneHolder.currentModuleSelectMenu.uploadModuleItems();
                            }
                            if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
                                ModuleEditPaneHolder.relatedModuleInfoMenu.updateMenuItems();
                            }

                            LazyCoderOptionPane.showMessageDialog(null, "已删除\"" + className + "\"分类所有数据！", "系统信息", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        }
    };


}
