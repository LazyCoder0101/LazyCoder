package com.lazycoder.uidatasourceedit.moduleedit.toolbar.relatedmoduleinfomenu;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.service.ModuleUseSetting;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.AssociatedModule;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uiutils.mycomponent.multistatecomponent.LazyCoderMultiStateMenuItem;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 编辑当前编辑模块相关的模块信息的菜单项
 */
public class RelatedModuleInfoMenuItem extends LazyCoderMultiStateMenuItem {

    @Getter
    private Module module = null;

    /**
     * 关联模块
     */
    @Getter
    private ArrayList<Module> associatedModuleList = new ArrayList<>();

    //  模块相关信息菜单对应业务功能
    // 【寄生模块】：例如模块A已经设置需要使用模块B，A则为B的寄生模块
    // 【冲突模块】：例如模块A已经设置不能使用模块B，A则为B的冲突模块
    // 【事先使用】：例如模块A已经设置需要使用模块B，B则为A的事先使用模块
    // 【不能使用】：例如模块A已经设置不能使用模块B，B则为A的不能使用模块
    // 【还要使用】：例如模块A已经设置需要使用模块B，而使用模块B又要使用模块C，C则为A的预先使用模块
    // 【还要禁用】：例如模块A已经设置需要使用模块B，而使用模块B不能使用模块C，C则为A的还要禁用模块
    // 【事先并优先使用】：例如用户选了A模块，后来选了B模块，但B模块也需要事先使用A模块，则A为事先并优先使用的模块

    /**
     * ——当有某个模块对应的菜单项被选中以后
     * 当选中一个模块A以后，找出该模块A有什么模块B是可以使用的，
     * 检查A在不在【寄生模块】以及【冲突模块】这两个的范围之内，如果在这范围之内，给出提示”该模块已设置需要使用模块“+currentmodule （正常情况都不会，可略过该步骤）
     * 检查B里面，有没有什么模块在D【寄生模块】、【冲突模块】、【不能使用】、【还要禁用】的范围之内，如果在这范围之内，给出提示“模块”+A+"需要使用的模块D和本模块冲突"，不能使用模块"+A，然后取消选中模块A
     * 找出还有什么模块C是不能使用的，
     * 检查C里面，有没有模块E在【事先使用】、【还要使用】范围内，如果在这范围之内，提示“模块”+A+"已设置不能使用E，和本模块冲突，不能使用模块"+A，然后取消选中模块A
     * 如果以上都正常，把B里面所有模块都设置为【还要使用】，把C里面所有模块设置为【还要禁用】
     * 菜单更新，显示当前选中的模块名称
     */

    public RelatedModuleInfoMenuItem(Module module) {
        // TODO Auto-generated constructor stub
        super(module.getModuleName());
        this.module = module;
//        setEnableToolTipText(markSelected, "使用\"" + DataSourceEditHolder.moduleName + "\"模块前需要添加本模块");
//        setEnableToolTipText(markNo, "使用\"" + DataSourceEditHolder.moduleName + "\"模块时不能添加本模块");
//        setEnableToolTipText(markPreSelected, "选中（☑）的模块所需要的模块");
        addActionListener(actionListener);
        addItemListener(listener);

        setStyte();
    }

    private void setStyte(){
        //对用户屏蔽的模块，设置为灰色
        ArrayList<Integer> useSetting = ModuleStaticMethod.getUseSettingValues(this.module);
        if (useSetting.contains(ModuleUseSetting.USER_SHIELDING)) {
            setForeground(Color.gray);
        }else {
            setForeground(Color.black);
        }
    }

    private ItemListener listener = new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
            // TODO Auto-generated method stub
            if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
                ModuleEditPaneHolder.relatedModuleInfoMenu.doClick();
            }
        }
    };

    /**
     * 是否是该模块
     *
     * @param module
     * @return
     */
    public boolean isModule(Module module) {
        boolean flag = false;
        if (this.module.getModuleId().equals(module.getModuleId())) {
//        if ((this.module.getModuleName().equals(module.getModuleName())) && (this.module.getClassName().equals(module.getClassName()))) {
            flag = true;
        }
        return flag;
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
            if (flag == true) {
                if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
                    if (isModuleSelectedNull()) {
                        ModuleEditPaneHolder.relatedModuleInfoMenu.setModuleSelectedNull(RelatedModuleInfoMenuItem.this);

                    } else if (isPreUseModule()) {
                        boolean state = ModuleEditPaneHolder.relatedModuleInfoMenu.usingModule(RelatedModuleInfoMenuItem.this);
                        if (state == false) {
                            ModuleEditPaneHolder.relatedModuleInfoMenu.setModuleSelectedNull(RelatedModuleInfoMenuItem.this);
                        }

                    } else if (isCannotUseModule()) {
                        ModuleEditPaneHolder.relatedModuleInfoMenu.doNotUseModule(RelatedModuleInfoMenuItem.this);
                    }
                }
            }
        }
    };

    /**
     * 清空关联模块
     */
    public void clearAssociatedModuleList() {
        associatedModuleList = new ArrayList<>();
    }

    /**
     * 去掉关联模块
     *
     * @param module
     */
    public void removeAssociatedModule(Module module) {
        Module associatedModule;
        for (int i = 0; i < this.associatedModuleList.size(); i++) {
            associatedModule = this.associatedModuleList.get(i);
            if (associatedModule.getModuleId().equals(module.getModuleId())) {
                this.associatedModuleList.remove(i);
                break;
            }
        }
    }

    /**
     * 添加关联模块
     *
     * @param associatedModule
     */
    public void addAssociatedModule(AssociatedModule associatedModule) {
        if (associatedModule != null) {
            boolean flag = false;
            ArrayList<Module> amList = associatedModule.getCurrentAssociatedModuleList();//获取关联的模块
            for (Module moduleTemp : amList) {
                flag = false;
                for (Module associatedModuleTemp : associatedModuleList) {
                    if (associatedModuleTemp.getModuleId().equals(moduleTemp.getModuleId())) {//如果现在这个菜单的关联模块，包含了associatedModule里面的关联模块
                        flag = true;//进行标记
                        break;
                    }
                }
                if (flag == false) {//没有包含里面的关联模块，则添加该模块进去
                    this.associatedModuleList.add(moduleTemp);
                }
            }
        }
    }

    /**
     * 这个菜单项里面对应的模块是不是某个模块需要事先使用的，或者不能使用的模块
     *
     * @param list 一般传某个模块需要使用的，或者不能使用的模块的所有模块（调用 ModuleServiceImpl 里面的 getAllNeedUsedModuleList() 以及 getAllNoUsedModuleList()方法）
     * @return
     */
    public boolean containAssociatedModule(List<AssociatedModule> list) {
        boolean flag = false;
        if (list != null) {
            ArrayList<Module> tempList;
            for (AssociatedModule associatedModule : list) {
                tempList = associatedModule.getCurrentAssociatedModuleList();
                for (Module temp : tempList) {
                    if (this.module.getModuleId().equals(temp.getModuleId())) {
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 获取关联模块的字符串
     *
     * @return
     */
    public String getAssociatedModuleListStr() {
        return SysService.MODULE_SERVICE.getModuleListStr(associatedModuleList);
    }

    /**
     * 更新模块数据，（给保存模块编辑的数据以后调用，刷新数据）
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
        setStyte();
        return flag;
    }

}
