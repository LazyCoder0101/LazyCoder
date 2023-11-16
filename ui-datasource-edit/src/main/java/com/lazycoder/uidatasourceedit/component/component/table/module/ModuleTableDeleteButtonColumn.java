package com.lazycoder.uidatasourceedit.component.component.table.module;

import com.lazycoder.database.model.Module;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uiutils.component.ButtonColumn;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

public class ModuleTableDeleteButtonColumn extends ButtonColumn {

    /**
     *
     */
    private static final long serialVersionUID = -1185001240371362355L;

    public ModuleTableDeleteButtonColumn(ModuleTable table, int column) {
        super(table, column);
        // TODO Auto-generated constructor stub
        getEditButton().addActionListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            fireEditingStopped();

            Module moduleTemp = ((ModuleTable) getTable()).getModuleList().get(getTable().getSelectedRow());
            if (DataSourceEditHolder.currentModule != null && moduleTemp.getModuleId().equals(DataSourceEditHolder.currentModule.getModuleId())) {
                LazyCoderOptionPane.showMessageDialog(null, "(*^▽^*) 亲，您在\"模块设置\"里正在编辑的模块就是\"" + moduleTemp.getModuleName()
                        + "\"模块的，如果真的需要删除，建议切换到其他模块后再删除，以避免数据错乱", "系统信息", JOptionPane.PLAIN_MESSAGE);

            } else {
                int n = LazyCoderOptionPane.showConfirmDialog(null,
                        "(～￣▽￣)～  真的要删除\"" + moduleTemp.getModuleName() + "\"这个模块吗?", "确认一下",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.OK_OPTION) {
                    String moduleId = moduleTemp.getModuleId();
                    List<Module> useThisModuleList = SysService.MODULE_SERVICE.getModulesListThatUsedTheModule(moduleId),
                            noUseThisModuleList = SysService.MODULE_SERVICE.getModulesListThatCanNotUsedTheModule(moduleId);
                    if ((useThisModuleList == null || useThisModuleList.size() == 0) && (noUseThisModuleList == null || noUseThisModuleList.size() == 0)) {

                        SysService.DATABASE_NAME_SERVICE.delDataOfModule(moduleTemp.getClassName(), moduleTemp.getModuleName(), moduleId);

                        LazyCoderOptionPane.showMessageDialog(null, "已删除\"" + moduleTemp.getModuleName() + "\"模块所有数据！", "系统信息", JOptionPane.PLAIN_MESSAGE);

                        //刷新一下相关的组件
                        if (DataSourceEditHolder.moduleManagementPane != null) {
                            DataSourceEditHolder.moduleManagementPane.queryModule();
                        }

                        if (ModuleEditPaneHolder.currentModuleSelectMenu != null) {
                            ModuleEditPaneHolder.currentModuleSelectMenu.uploadModuleItems();
                        }
                        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
                        if (flag == true) {
                            if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
                                ModuleEditPaneHolder.relatedModuleInfoMenu.updateMenuItems();
                            }
                        }
                    } else {
                        if ((useThisModuleList != null && useThisModuleList.size() > 0) && (noUseThisModuleList != null && noUseThisModuleList.size() > 0)) {
                            String str = SysService.MODULE_SERVICE.getModuleListStr(useThisModuleList),
                                    nostr = SysService.MODULE_SERVICE.getModuleListStr(noUseThisModuleList);
                            LazyCoderOptionPane.showMessageDialog(null, "不能删除\"" + moduleTemp.getModuleName() + "\"模块，因为" + str + "以及" + nostr + "这些模块都有设置和这个模块关联，\n请在这些模块改了相关设置以后再进行删除！", "系统信息", JOptionPane.PLAIN_MESSAGE);

                        } else if (useThisModuleList != null && useThisModuleList.size() > 0) {
                            String str = SysService.MODULE_SERVICE.getModuleListStr(useThisModuleList);
                            LazyCoderOptionPane.showMessageDialog(null, "不能删除\"" + moduleTemp.getModuleName() + "\"模块，因为设置了" + str + "这些模块使用之前需要用到它", "系统信息", JOptionPane.PLAIN_MESSAGE);

                        } else if (noUseThisModuleList != null && noUseThisModuleList.size() > 0) {
                            String str = SysService.MODULE_SERVICE.getModuleListStr(noUseThisModuleList);
                            LazyCoderOptionPane.showMessageDialog(null, "不能删除\"" + moduleTemp.getModuleName() + "\"模块，因为设置了使用" + str + "这些模块的时候不能用到它，\n请在这些模块改了相关设置以后再进行删除！", "系统信息", JOptionPane.PLAIN_MESSAGE);

                        }
                    }
                }
            }
        }
    };


}
