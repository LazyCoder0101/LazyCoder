package com.lazycoder.uidatasourceedit.component.component;

import com.github.pagehelper.PageInfo;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.ModuleManagementPaneHolder;
import com.lazycoder.uidatasourceedit.component.component.table.module.ModuleTable;
import com.lazycoder.uiutils.component.PageNavigator;

public class ClassNameTablePageNavigator extends PageNavigator {

    private ModuleTable moduleTable = null;

    private PageInfo<Module> pageInfoList = null;

    public ClassNameTablePageNavigator(ModuleTable moduleTable) {
        this.moduleTable = moduleTable;

//        pageSize = 10;//默认显示10行
//        currentPage = 1;
        init();
        initPageStatus();
        go();
    }


//	public void initModuleTable() {
//		currentPage = 1;
//		pageInfoList = SysService.moduleService.getAllModuleList(currentPage, pageSize);
//		go();
//	}

    public void initModuleTable() {
        initPageStatus();
        go();
    }

    @Override
    protected synchronized void go() {
        if (this.moduleTable != null && ModuleManagementPaneHolder.classNameComboBox != null && ModuleManagementPaneHolder.managementPaneUsingRangeCombobox != null) {
            String classParam = ModuleManagementPaneHolder.classNameComboBox.getSelectedItem();
            UsingObject usingRange = ModuleManagementPaneHolder.managementPaneUsingRangeCombobox.getQueryParam();
            pageInfoList = SysService.MODULE_SERVICE.getModuleList(classParam, usingRange, currentPage, pageSize);
            // 得到总记录数
            totalCount = (int) pageInfoList.getTotal();
            if (totalCount > 0) {
                // 设置总页数
                pageCount = pageInfoList.getPages(); // 总页数
            }
            super.go();
            this.moduleTable.showModuleList(pageInfoList, currentPage, pageSize);
        }
    }
}
