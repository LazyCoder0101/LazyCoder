package com.lazycoder.uicodegeneration.component.operation.component.typeset.format.main;

import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.MainSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.BaseFormatStructureModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.MainSetTypeContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.MainSetTypeFolderModel;
import java.awt.Component;
import java.util.ArrayList;

public class MainSetTypeFolder extends AbstractCodeControlPane {

    /**
     *
     */
    private static final long serialVersionUID = 4263434751790367663L;

    private MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam;

    /**
     * 新建
     *
     * @param mainSetTypeOperatingContainerParam
     */
    public MainSetTypeFolder(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam) {
        super(mainSetTypeOperatingContainerParam.getFormatControlPane(), 0);
        this.mainSetTypeOperatingContainerParam = mainSetTypeOperatingContainerParam;
        // TODO Auto-generated constructor stub
        newInit();
    }

    /**
     * 还原
     *
     * @param mainSetTypeOperatingContainerParam
     * @param mainSetTypeFolderModel
     */
    public MainSetTypeFolder(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam,
                             MainSetTypeFolderModel mainSetTypeFolderModel) {
        // TODO Auto-generated constructor stub
        super(mainSetTypeOperatingContainerParam.getFormatControlPane(), 0);
        this.mainSetTypeOperatingContainerParam = mainSetTypeOperatingContainerParam;
        // TODO Auto-generated constructor stub
        restoreInit(mainSetTypeOperatingContainerParam, mainSetTypeFolderModel);
    }

    private void newInit() {
        if (mainSetTypeOperatingContainerParam.getMainInfo().getNumOfSetCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
            ArrayList<String> list = SysService.FORMAT_INFO_SERVICE
                    .getSetTypeListParam(mainSetTypeOperatingContainerParam.getMainInfo());
            if (list != null) {
                MainSetTypeContainer mainSetTypeContainer;
                for (String temp : list) {
                    mainSetTypeOperatingContainerParam.setMainSetType(temp);
                    mainSetTypeContainer = new MainSetTypeContainer(mainSetTypeOperatingContainerParam, true);
                    addContainer(mainSetTypeContainer);
                }
            }
        }
    }

    private void restoreInit(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam,
                             MainSetTypeFolderModel mainSetTypeFolderModel) {
        if (mainSetTypeOperatingContainerParam.getMainInfo().getNumOfSetCodeType() > 0) {
            MainSetTypeContainer mainSetTypeContainer;
            MainSetTypeContainerModel mainSetTypeContainerModel;
            ArrayList<MainSetTypeContainerModel> containerList = mainSetTypeFolderModel
                    .getMainSetTypeContainerModelList();
            for (BaseFormatStructureModel baseFormatStructureModel : containerList) {
                if (baseFormatStructureModel instanceof MainSetTypeContainerModel) {
                    mainSetTypeContainerModel = (MainSetTypeContainerModel) baseFormatStructureModel;
                    mainSetTypeOperatingContainerParam.setMainSetType(mainSetTypeContainerModel.getMainSetTypeName());

                    mainSetTypeContainer = new MainSetTypeContainer(mainSetTypeOperatingContainerParam,
                            mainSetTypeContainerModel, true);
                    addContainer(mainSetTypeContainer);
                }
            }
        }
    }

    @Override
    public void delThis() {
        Component component;
        MainSetTypeContainer mainSetTypeContainer;
        if (mainSetTypeOperatingContainerParam.getMainInfo().getNumOfSetCodeType() > 0) {
            for (int i = 0; i < getComponentCount(); i++) {
                component = getComponent(i);
                if (component instanceof MainSetTypeContainer) {
                    mainSetTypeContainer = (MainSetTypeContainer) component;
                    mainSetTypeContainer.delThis();
                }
            }
        }
    }

    @Override
    public MainSetTypeFolderModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        Component component;
        MainSetTypeContainer mainSetTypeContainer;
        MainSetTypeFolderModel model = new MainSetTypeFolderModel();
        if (mainSetTypeOperatingContainerParam.getMainInfo().getNumOfSetCodeType() > 0) {
            for (int i = 0; i < getComponentCount(); i++) {
                component = getComponent(i);
                if (component instanceof MainSetTypeContainer) {
                    mainSetTypeContainer = (MainSetTypeContainer) component;
                    model.getMainSetTypeContainerModelList().add(mainSetTypeContainer.getFormatStructureModel());
                }
            }
        }
        return model;
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        // TODO Auto-generated method stub
        if (mainSetTypeOperatingContainerParam.getMainInfo().getNumOfSetCodeType() > 0) {
            MainSetTypeContainer mainSetTypeContainer;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof MainSetTypeContainer) {
                    mainSetTypeContainer = (MainSetTypeContainer) getComponent(i);
                    mainSetTypeContainer.delModuleOpratingContainerFromComponent(moduleId);
                }
            }
        }
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        if (mainSetTypeOperatingContainerParam.getMainInfo().getNumOfSetCodeType() > 0) {
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof MainSetTypeContainer) {
                    ((MainSetTypeContainer) getComponent(i)).collapseThis();
                }
            }
        }
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        if (mainSetTypeOperatingContainerParam.getMainInfo().getNumOfSetCodeType() > 0) {
            MainSetTypeContainer mainSetTypeContainer;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof MainSetTypeContainer) {
                    mainSetTypeContainer = (MainSetTypeContainer) getComponent(i);
                    opratingContainerList.addAll(mainSetTypeContainer.getAllOpratingContainer());
                }
            }
        }
        return opratingContainerList;
    }

    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        if (currentOpratingContainer != null) {
            if (mainSetTypeOperatingContainerParam.getMainInfo().getNumOfSetCodeType() > 0) {
                MainSetTypeContainer mainSetTypeContainer;
                for (int i = 0; i < getComponentCount(); i++) {
                    if (getComponent(i) instanceof MainSetTypeContainer) {
                        mainSetTypeContainer = (MainSetTypeContainer) getComponent(i);
                        mainSetTypeContainer.autoCollapse(currentOpratingContainer);
                    }
                }
            }
        }
    }

}
