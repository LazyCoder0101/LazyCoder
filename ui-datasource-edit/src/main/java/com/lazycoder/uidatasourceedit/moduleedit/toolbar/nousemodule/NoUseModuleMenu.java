package com.lazycoder.uidatasourceedit.moduleedit.toolbar.nousemodule;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.AbstractModuleMenu;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 该组件已废弃
 */
public class NoUseModuleMenu extends AbstractModuleMenu implements ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 5614471768336889342L;

    public NoUseModuleMenu() {
        // TODO Auto-generated constructor stub
        super();
        setToolTipText("(*^▽^*)  \n如果发现点击我没反应的话。。。\n不妨点一下旁边\"分类：\"右边那个下拉框再试试");
        showText();
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        if (Module.NEW_STATE.equals(module.getNoUseModuleParam()) == false) {// 非新建状态

            ArrayList<Module> noUseList = SysService.MODULE_SERVICE.getNoUseModuleList(module);
            selectedModuleListTrue(noUseList);

            ArrayList<Module> needModuleList = SysService.MODULE_SERVICE.getNeedModuleList(module);
            setModuleListEnabledFalse(needModuleList);
            showText();
        }
    }

    /**
     * 添加所有选项
     */
    private void addNoUseModuleItems() {
        removeAll();
        NoUseModuleMenuItem item;
        List<Module> list = SysService.MODULE_SERVICE.getModuleListExceptNonModuleAnd(DataSourceEditHolder.currentModule.getModuleId());
        if (list != null) {
            for (Module moduleTemp : list) {
                item = new NoUseModuleMenuItem(moduleTemp);
                this.add(item);
            }
        }
    }

    @Override
    protected void updateMenuItem() {
        ArrayList<Module> selectedList = getCurrentSelectedModuleList();// 获取当前选中的模块列表
        ArrayList<Module> canNotSelectedList = getCurrentCanNotSelectedModuleList();
        addNoUseModuleItems();
        selectedModuleListTrue(selectedList);
        setModuleListEnabledFalse(canNotSelectedList);
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        addNoUseModuleItems();
        showText();
    }


    /**
     * 获取需要调用的模块列表 让无需调用模块的这些选项进行失能 获取需要的模块还需要的模块 把这些模块也选上
     *
     * @return
     */
    public void calculatedParameters() {
        ArrayList<Module> list = getCurrentSelectedModuleList();//看看当前选中了什么模块，设置不能使用，把需要使用的模块里，这些菜单项进行失能，避免用户在那里又选中
//		ModuleEditPaneHolder.relatedModuleInfoMenu.setModuleListEnabledFalse(list);

        ArrayList<Module> noUseMosuleList = getCurrentNoSelectedModuleList();//选中当前没有选中的模块，把需要使用的模块里，这些菜单项进行使能，让用户继续可以选中
//		ModuleEditPaneHolder.relatedModuleInfoMenu.setModuleListEnabledTrue(noUseMosuleList);

        showText();
        requestFocus();
    }


    /**
     * 记录不能调用的模块
     *
     * @param module
     */
    public void setNoUseModuleParam(Module module) {
        List<Module> moduleList = getCurrentSelectedModuleList();
        ArrayList<String> list = SysService.MODULE_SERVICE.getModuleIdList(moduleList);
        module.setNoUseModuleParam(JsonUtil.getJsonStr(list));
    }

}
