package com.lazycoder.uicodegeneration.component.operation.component.typeset.module;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.formodule.ModuleInfoStaticMethod;
import com.lazycoder.uicodegeneration.component.operation.OperatingPaneBusinessTraverse;
import com.lazycoder.uicodegeneration.component.operation.container.ModuleControlContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.ModuleTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.ModuleControlContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.ModuleTypeContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.SettingContainerModel;
import java.awt.Component;
import java.util.ArrayList;

/**
 * 存放模块控制面板和对应的模块分类面板的面板，放在SettingFrame上
 *
 * @author admin
 */
public class SettingContainer extends AbstractCodeControlPane implements OperatingPaneBusinessTraverse {

    /**
     *
     */
    private static final long serialVersionUID = -1391022073897256338L;

    private ModuleControlContainer moduleControlContainer = null;

    private ModuleTypeOperatingContainerParam moduleTypeContainerParam = null;

    private ModuleInfo moduleInfo;

    /**
     * 新建
     *
     * @param moduleTypeContainerParam
     */
    public SettingContainer(ModuleTypeOperatingContainerParam moduleTypeContainerParam) {
        super(moduleTypeContainerParam.getFormatControlPane(), 0);
        this.moduleInfo = moduleTypeContainerParam.getModuleInfo();
        this.moduleTypeContainerParam = moduleTypeContainerParam;
        newInit();
    }

    /**
     * 还原
     *
     * @param moduleTypeContainerParam
     * @param settingContainerModel
     */
    public SettingContainer(ModuleTypeOperatingContainerParam moduleTypeContainerParam,
                            SettingContainerModel settingContainerModel) {
        super(moduleTypeContainerParam.getFormatControlPane(), 0);
        this.moduleInfo = moduleTypeContainerParam.getModuleInfo();
        this.moduleTypeContainerParam = moduleTypeContainerParam;

        restoreInit(settingContainerModel);
    }

    private void restoreInit(SettingContainerModel settingContainerModel) {

        if (moduleInfo != null) {
            if (ModuleInfo.TRUE_ == moduleInfo.getWhetherModuleControlIsRequired()) {// 添加模块控制窗口

                ModuleControlContainerModel moduleControlContainerModel = settingContainerModel
                        .getModuleControlContainerModel();
                if (moduleControlContainerModel != null) {
                    moduleControlContainer = new ModuleControlContainer();
                    this.moduleControlContainer.restore(settingContainerModel.getModuleControlContainerModel(),
                            moduleTypeContainerParam, this);
                    addContainer(moduleControlContainer);
                }
            }

            if (moduleInfo.getNumOfSetCodeType() > 0) {// 添加模块类型
                ModuleTypeOperatingContainerParam moduleTypeContainerParamTemp;
                ModuleTypeContainer moduleTypeContainer = null;
                ArrayList<ModuleTypeContainerModel> moduleTypeContainerModelList = settingContainerModel
                        .getModuleTypeContainerModelList();
                for (ModuleTypeContainerModel moduleTypeContainerModel : moduleTypeContainerModelList) {
                    moduleTypeContainerParamTemp = new ModuleTypeOperatingContainerParam();
                    moduleTypeContainerParamTemp.setModule(moduleTypeContainerParam.getModule());
                    moduleTypeContainerParamTemp.setModuleInfo(moduleTypeContainerParam.getModuleInfo());
                    moduleTypeContainerParamTemp.setFormatControlPane(moduleTypeContainerParam.getFormatControlPane());
                    moduleTypeContainerParamTemp.setModuleSetType(moduleTypeContainerModel.getModuleSetType());
                    //moduleTypeContainerParamTemp.setSetButton(this.moduleTypeContainerParam.getSetButton());

                    moduleTypeContainer = new ModuleTypeContainer(moduleTypeContainerModel,
                            moduleTypeContainerParamTemp, false);
                    addContainer(moduleTypeContainer);
                }
            }
        }
    }

    private void newInit() {
        if (moduleInfo != null) {
            if (ModuleInfo.TRUE_ == moduleInfo.getWhetherModuleControlIsRequired()) {// 添加模块控制窗口
                moduleControlContainer = new ModuleControlContainer();
                moduleControlContainer.build(moduleTypeContainerParam, this);
                addContainer(moduleControlContainer);
            }

            if (moduleInfo.getNumOfSetCodeType() > 0) {// 添加模块类型

                ModuleTypeContainer moduleTypeContainer = null;
                ArrayList<String> typeList = ModuleInfoStaticMethod.getModuleSetTypeListParam(moduleInfo);
                ModuleTypeOperatingContainerParam moduleTypeContainerParamTemp;
                if (typeList != null) {
                    for (int i = 0; i < typeList.size(); i++) {
                        moduleTypeContainerParamTemp = new ModuleTypeOperatingContainerParam();
                        moduleTypeContainerParamTemp.setModule(moduleTypeContainerParam.getModule());
                        moduleTypeContainerParamTemp.setModuleInfo(moduleTypeContainerParam.getModuleInfo());
                        moduleTypeContainerParamTemp
                                .setFormatControlPane(moduleTypeContainerParam.getFormatControlPane());
                        moduleTypeContainerParamTemp.setModuleSetType(typeList.get(i));
                        //moduleTypeContainerParamTemp.setSetButton(this.moduleTypeContainerParam.getSetButton());

                        moduleTypeContainer = new ModuleTypeContainer(moduleTypeContainerParamTemp, false);
                        addContainer(moduleTypeContainer);
                    }
                }
            }
        }
    }

    @Override
    public SettingContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        Component component;
        ModuleTypeContainer moduleTypeContainer;
        SettingContainerModel model = new SettingContainerModel();

        int flag = moduleInfo.getWhetherModuleControlIsRequired();
        if (ModuleInfo.TRUE_ == flag) {// 添加模块控制窗口
            model.setModuleControlContainerModel(this.moduleControlContainer.getFormatStructureModel());
        }
        if (moduleInfo.getNumOfSetCodeType() > 0) {
            for (int i = 0; i < getComponentCount(); i++) {
                component = getComponent(i);
                if (component instanceof ModuleTypeContainer) {
                    moduleTypeContainer = (ModuleTypeContainer) component;
                    model.getModuleTypeContainerModelList().add(moduleTypeContainer.getFormatStructureModel());
                }
            }
        }
        return model;
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        if (ModuleInfo.TRUE_ == moduleInfo.getWhetherModuleControlIsRequired()) {
            opratingContainerList.add(moduleControlContainer);
            opratingContainerList.addAll(moduleControlContainer.getAllOpratingContainerListInThis());
        }
        if (moduleInfo.getNumOfSetCodeType() > 0) {
            ModuleTypeContainer moduleTypeContainer = null;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof ModuleTypeContainer) {
                    moduleTypeContainer = (ModuleTypeContainer) getComponent(i);
                    opratingContainerList.addAll(moduleTypeContainer.getAllOpratingContainerList());
                }
            }
        }
        return opratingContainerList;
    }

    /**
     * 从各个方法那里的组件进入，一个个删除里面的模块
     *
     * @param moduleId
     */
    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        if (ModuleInfo.TRUE_ == moduleInfo.getWhetherModuleControlIsRequired()) {// 添加模块控制窗口
            moduleControlContainer.delModuleOpratingContainerFromComponent(moduleId);
        }
        if (moduleInfo.getNumOfSetCodeType() > 0) {
            ModuleTypeContainer moduleTypeContainer = null;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof ModuleTypeContainer) {
                    moduleTypeContainer = (ModuleTypeContainer) getComponent(i);
                    moduleTypeContainer.delModuleOpratingContainerFromComponent(moduleId);
                }
            }
        }
    }

    public void delModuleOpratingContainer() {
        if (ModuleInfo.TRUE_ == moduleInfo.getWhetherModuleControlIsRequired()) {// 添加模块控制窗口
            moduleControlContainer.delThis();
        }
        if (moduleInfo.getNumOfSetCodeType() > 0) {
            ModuleTypeContainer moduleTypeContainer = null;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof ModuleTypeContainer) {
                    moduleTypeContainer = (ModuleTypeContainer) getComponent(i);
                    moduleTypeContainer.delThis();
                }
            }
        }
    }

    /**
     * 收取里面的组件
     */
    @Override
    public void collapseThis() {
        if (ModuleInfo.TRUE_ == moduleInfo.getWhetherModuleControlIsRequired()) {// 添加模块控制窗口
            moduleControlContainer.collapseThis();
        }
        if (moduleInfo.getNumOfSetCodeType() > 0) {
            ModuleTypeContainer moduleTypeContainer = null;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof ModuleTypeContainer) {
                    moduleTypeContainer = (ModuleTypeContainer) getComponent(i);
                    moduleTypeContainer.collapseThis();
                }
            }
        }
    }

    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        if (currentOpratingContainer != null) {
            if (ModuleInfo.TRUE_ == moduleInfo.getWhetherModuleControlIsRequired()) {// 添加模块控制窗口
                moduleControlContainer.autoCollapse(currentOpratingContainer);
            }
            if (moduleInfo.getNumOfSetCodeType() > 0) {
                if (MarkElementName.SET_MARK.equals(currentOpratingContainer.getPaneType())) {
                    ModuleTypeContainer moduleTypeContainer = null;
                    for (int i = 0; i < getComponentCount(); i++) {
                        if (getComponent(i) instanceof ModuleTypeContainer) {
                            moduleTypeContainer = (ModuleTypeContainer) getComponent(i);
                            moduleTypeContainer.autoCollapse(currentOpratingContainer);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void functionNameSynchronousChange(int functionNameId) {
        if (ModuleInfo.TRUE_ == moduleInfo.getWhetherModuleControlIsRequired()) {// 添加模块控制窗口
            moduleControlContainer.functionNameSynchronousChange(functionNameId);
        }
        if (moduleInfo.getNumOfSetCodeType() > 0) {
            ModuleTypeContainer moduleTypeContainer = null;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof ModuleTypeContainer) {
                    moduleTypeContainer = (ModuleTypeContainer) getComponent(i);
                    moduleTypeContainer.functionNameSynchronousChange(functionNameId);
                }
            }
        }
    }

    @Override
    public void functionNameSynchronousDelete(int functionNameId) {

    }

    @Override
    public void variableSynchronousChange(int variableId) {

    }

    @Override
    public void variableSynchronousDelete(int variableId) {

    }
}
