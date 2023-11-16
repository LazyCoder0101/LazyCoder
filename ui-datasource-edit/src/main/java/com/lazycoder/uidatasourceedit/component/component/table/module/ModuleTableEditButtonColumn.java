package com.lazycoder.uidatasourceedit.component.component.table.module;

import com.lazycoder.database.model.Module;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.modulemanagement.editframe.ModuleEditFrame;
import com.lazycoder.uiutils.component.ButtonColumn;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ModuleTableEditButtonColumn extends ButtonColumn {

    /**
     *
     */
    private static final long serialVersionUID = -8541413299935854944L;

    public ModuleTableEditButtonColumn(JTable table, int column) {
        super(table, column);
        // TODO Auto-generated constructor stub
        getEditButton().addActionListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            fireEditingStopped();
            String moduleName = (String) getTable().getModel().getValueAt(getTable().getSelectedRow(), ModuleColumn.MODULE_NAME),
                    className = (String) getTable().getModel().getValueAt(getTable().getSelectedRow(), ModuleColumn.CLASS_NAME);
            if (DataSourceEditHolder.currentModule == null) {
                Module module = SysService.MODULE_SERVICE.getModule(className, moduleName);
                if (module != null) {
                    new ModuleEditFrame(module);
                }
            }else {
                if (moduleName.equals(DataSourceEditHolder.currentModule.getModuleName()) && className.equals(DataSourceEditHolder.currentModule.getClassName())) {
                    LazyCoderOptionPane.showMessageDialog(null, "(*^▽^*) 亲，您在\"模块设置\"里正在编辑的模块就是\"" + moduleName
                            + "\"模块的，如果真的需要修改这模块，\n建议保存需要的数据后，切换到其他模块后再改，以避免数据错乱", "系统信息", JOptionPane.PLAIN_MESSAGE);

                } else {
                    Module module = SysService.MODULE_SERVICE.getModule(className, moduleName);
                    if (module != null) {
                        new ModuleEditFrame(module);
                    }
                }
            }
        }
    };

}
