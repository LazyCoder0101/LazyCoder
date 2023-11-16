package com.lazycoder.uidatasourceedit.moduleedit.toolbar.needmodule;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.AbstractModuleMenu;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class NeedModuleMenu extends AbstractModuleMenu implements ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 649324098112783261L;

    public NeedModuleMenu() {
        // TODO Auto-generated constructor stub
        super();
        showText();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                doClick();
            }
        });
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        if (Module.NEW_STATE.equals(module.getNeedModuleParam()) == false) {// 非新建状态
            ArrayList<Module> needModuleList = SysService.MODULE_SERVICE.getNeedModuleList(module);
            selectedModuleListTrue(needModuleList);

//			ArrayList<Module> noUseList = SysService.moduleService.getNoUseModuleList(module);//，目前屏蔽不能使用模块，该代码无效
//			setModuleListEnabledFalse(noUseList);

            showText();
        }
    }

    /**
     * 添加所有选项
     */
    private void addAllNeedModuleItems() {
        removeAll();
        NeedModuleMenuItem item;
        List<Module> list = SysService.MODULE_SERVICE.getModuleListExceptNonModuleAnd(DataSourceEditHolder.currentModule.getModuleId());
        if (list != null) {
            for (Module temp : list) {
                item = new NeedModuleMenuItem(temp);
                this.add(item);
            }
        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        addAllNeedModuleItems();
        showText();
    }

    @Override
    protected void updateMenuItem() {
        ArrayList<Module> selectedList = getCurrentSelectedModuleList();// 获取当前选中的模块列表
//		ArrayList<Module> canNotSelectedList = getCurrentCanNotSelectedModuleList();
        addAllNeedModuleItems();
        selectedModuleListTrue(selectedList);
//		setModuleListEnabledFalse(canNotSelectedList);
    }

    /**
     * 在本菜单选中对应模块后的对应操作
     * （根据当前选中的模块 让无需调用模块的这些选项进行失能 再获取这些模块需要用到的模块，在ModuleEditPaneHolder.needUseCodeFileEditPane显示模块的对应代码文件面板）
     *
     * @param module
     * @return
     */
    public boolean calculatedParameters(Module module) {
        boolean flag = false;
        ArrayList<Module> selectedList = getCurrentSelectedModuleList();// 获取当前选中的模块列表
//		ModuleEditPaneHolder.noUseModuleMenu.setModuleListEnabledFalse(selectedList);// 把【不需要调用模块的菜单】对应选项失能

        // List<Module> needModuleList = SysService.moduleService.getAllNeedUsedModuleList(selectedList);// 查看还有什么需要的模块列表
//        if (check(module, needModuleList) == true) {
//            flag = true;
//
//            selectedModuleListTrue(needModuleList);
////		ModuleEditPaneHolder.noUseModuleMenu.setModuleListEnabledFalse(otherNeedModuleList);// 把【不需要调用模块的菜单】对应选项失能
//
//            ModuleEditPaneHolder.needUseCodeFileEditPane.addNeedUseCodeFormatPaneByModuleList(needModuleList);// 根据其他需要的模块，把对应模块文件添加上
//            ModuleEditPaneHolder.needUseCodeFileEditPane.addNeedUseCodeFormatPaneByModuleList(selectedList);// 根据当前选中的模块，把对应的模块文件添加上
//
//            checkModuleAndTip(module, selectedList);
//
//
////		ArrayList<Module> noUseMosuleList = getCurrentNoSelectedModuleList();// 看看当前有哪些模块时不需要用的
////		ModuleEditPaneHolder.noUseModuleMenu.setModuleListEnabledTrue(noUseMosuleList);// 【不需要调用模块的菜单】对应的使能
//
////		Module temp;// 根据不需要使用的模块，把对应的模块文件去除
////		for (int i = 0; i < noUseMosuleList.size(); i++) {
////			temp = noUseMosuleList.get(i);
////			ModuleEditPaneHolder.needUseCodeFileEditPane.removeAllModuleCodePane(temp.getClassName(),
////					temp.getModuleName());
////		}
//            showText();
//        }
        requestFocus();
        return flag;
    }

    private boolean check(Module module, List<Module> list) {
        boolean flag = true;
        if (module.getNeedModuleParam().contains(DataSourceEditHolder.currentModule.getModuleId())) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(null, "\"" + module.getModuleName() + "\"模块已经设置了使用前需调用\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"模块，不能选这模块");
        }
        if (flag == true) {
            for (Module temp : list) {
                if (temp.getNeedModuleParam().contains(DataSourceEditHolder.currentModule.getModuleId())) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null, "使用该模块要用到\""
                            + temp.getModuleName() + "\"模块，可该模块已经设置了使用前需调用\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"模块，不能选这模块");
                    break;
                }
            }
        }
        return flag;
    }

    private void checkModuleAndTip(Module module, ArrayList<Module> selectedList) {
        for (Module temp : selectedList) {
            if (temp.getNeedModuleParam().contains(module.getModuleId())) {
                LazyCoderOptionPane.showMessageDialog(null, "注意一下，你事先设置了使用\"" + temp.getModuleName() + "\"模块需要用到\"" + module.getModuleName() + "模块\"的");
            }
        }
    }

    /**
     * 设置需要的模块参数
     *
     * @param module
     */
    public void setNeedModuleParam(Module module) {
        List<Module> moduleList = getCurrentSelectedModuleList();
        ArrayList<String> list = SysService.MODULE_SERVICE.getModuleIdList(moduleList);
        module.setNeedModuleParam(JsonUtil.getJsonStr(list));
    }

}
