package com.lazycoder.uidatasourceedit.moduleedit.toolbar.moduleselect;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class CurrentModuleSelectMenuItem extends JMenuItem {

    private Module module = null;

    private TheClassification classification = null;

    public CurrentModuleSelectMenuItem(Module module, TheClassification classification) {
        super(module.getModuleName());
        this.module = module;
        this.classification = classification;
        addActionListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (ModuleEditPaneHolder.currentModuleSelectMenu != null) {
                if ((DataSourceEditHolder.currentModule != null && !module.getModuleId().equals(DataSourceEditHolder.currentModule.getModuleId())) ||
                        (DataSourceEditHolder.currentModule == null)) {
                    ModuleEditPaneHolder.currentModuleSelectMenu.confirmToUser(module, classification);
                }
            }
        }
    };

    /**
     * 更新模块数据，（给保存模块编辑的数据以后调用，刷新数据）
     *
     * @param newData
     * @return 当前菜单是否为所需要更新的值
     */
    public boolean updateModuleData(Module newData) {
        boolean flag = false;
        if (newData.getModuleId().equals(module.getModuleId())) {
            this.module = newData;
            setText(newData.getModuleName());
            flag = true;
        }
        return flag;
    }

}
