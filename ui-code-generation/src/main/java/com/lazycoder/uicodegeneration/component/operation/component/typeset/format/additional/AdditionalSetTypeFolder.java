package com.lazycoder.uicodegeneration.component.operation.component.typeset.format.additional;

import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.BaseFormatStructureModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.AdditionalSetTypeContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.AdditionalSetTypeFolderModel;
import java.awt.Component;
import java.util.ArrayList;

public class AdditionalSetTypeFolder extends AbstractCodeControlPane {

    /**
     *
     */
    private static final long serialVersionUID = 2741707319042234475L;

    private AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam;

    /**
     * 新建
     *
     * @param additionalSetTypeOperatingContainerParam
     */
    public AdditionalSetTypeFolder(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam) {
        super(additionalSetTypeOperatingContainerParam.getFormatControlPane(), 0);
        this.additionalSetTypeOperatingContainerParam = additionalSetTypeOperatingContainerParam;
        // TODO Auto-generated constructor stub
        newInit();
    }

    public AdditionalSetTypeFolder(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam,
                                   AdditionalSetTypeFolderModel additionalSetTypeFolderModel) {
        super(additionalSetTypeOperatingContainerParam.getFormatControlPane(), 0);
        this.additionalSetTypeOperatingContainerParam = additionalSetTypeOperatingContainerParam;
        restoreInit(additionalSetTypeOperatingContainerParam, additionalSetTypeFolderModel);

    }


    private void newInit() {
        if (additionalSetTypeOperatingContainerParam.getAdditionalInfo().getNumOfSetCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
            ArrayList<String> list = SysService.FORMAT_INFO_SERVICE
                    .getSetTypeListParam(additionalSetTypeOperatingContainerParam.getAdditionalInfo());
            if (list != null) {
                AdditionalSetTypeContainer container;
                for (String temp : list) {
                    additionalSetTypeOperatingContainerParam.setAdditionalSetType(temp);
                    container = new AdditionalSetTypeContainer(additionalSetTypeOperatingContainerParam, true);
                    addContainer(container);
                }
            }
        }
    }

    private void restoreInit(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam,
                             AdditionalSetTypeFolderModel additionalSetTypeFolderModel) {
        if (additionalSetTypeOperatingContainerParam.getAdditionalInfo().getNumOfSetCodeType() > 0) {
            AdditionalSetTypeContainer container;
            AdditionalSetTypeContainerModel additionalSetTypeContainerModel;
            ArrayList<AdditionalSetTypeContainerModel> containerList = additionalSetTypeFolderModel
                    .getAdditionalSetTypeContainerModelList();
            for (BaseFormatStructureModel baseFormatStructureModel : containerList) {
                if (baseFormatStructureModel instanceof AdditionalSetTypeContainerModel) {
                    additionalSetTypeContainerModel = (AdditionalSetTypeContainerModel) baseFormatStructureModel;
                    additionalSetTypeOperatingContainerParam
                            .setAdditionalSetType(additionalSetTypeContainerModel.getAdditionalSetTypeName());

                    container = new AdditionalSetTypeContainer(additionalSetTypeOperatingContainerParam,
                            additionalSetTypeContainerModel, true);
                    addContainer(container);
                }
            }
        }
    }

    @Override
    public AdditionalSetTypeFolderModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        Component component;
        AdditionalSetTypeContainer container;
        AdditionalSetTypeFolderModel model = new AdditionalSetTypeFolderModel();
        if (additionalSetTypeOperatingContainerParam.getAdditionalInfo().getNumOfSetCodeType() > 0) {
            for (int i = 0; i < getComponentCount(); i++) {
                component = getComponent(i);
                if (component instanceof AdditionalSetTypeContainer) {
                    container = (AdditionalSetTypeContainer) component;
                    model.getAdditionalSetTypeContainerModelList().add(container.getFormatStructureModel());
                }
            }
        }
        return model;
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        // TODO Auto-generated method stub
        if (additionalSetTypeOperatingContainerParam.getAdditionalInfo().getNumOfSetCodeType() > 0) {
            AdditionalSetTypeContainer container;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof AdditionalSetTypeContainer) {
                    container = (AdditionalSetTypeContainer) getComponent(i);
                    container.delModuleOpratingContainerFromComponent(moduleId);
                }
            }
        }
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        if (additionalSetTypeOperatingContainerParam.getAdditionalInfo().getNumOfSetCodeType() > 0) {
            AdditionalSetTypeContainer container;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof AdditionalSetTypeContainer) {
                    container = (AdditionalSetTypeContainer) getComponent(i);
                    container.collapseThis();
                }
            }
        }
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        Component component;
        if (additionalSetTypeOperatingContainerParam.getAdditionalInfo().getNumOfSetCodeType() > 0) {
            AdditionalSetTypeContainer container;
            for (int i = 0; i < getComponentCount(); i++) {
                component = getComponent(i);
                if (component instanceof AdditionalSetTypeContainer) {
                    container = (AdditionalSetTypeContainer) component;
                    container.delThis();
                }
            }
        }
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        if (additionalSetTypeOperatingContainerParam.getAdditionalInfo().getNumOfSetCodeType() > 0) {
            AdditionalSetTypeContainer container;
            for (int i = 0; i < getComponentCount(); i++) {
                if (getComponent(i) instanceof AdditionalSetTypeContainer) {
                    container = (AdditionalSetTypeContainer) getComponent(i);
                    opratingContainerList.addAll(container.getAllOpratingContainer());
                }
            }
        }
        return opratingContainerList;
    }

    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        if (currentOpratingContainer != null) {
            if (additionalSetTypeOperatingContainerParam.getAdditionalInfo().getNumOfSetCodeType() > 0) {
                AdditionalSetTypeContainer container;
                for (int i = 0; i < getComponentCount(); i++) {
                    if (getComponent(i) instanceof AdditionalSetTypeContainer) {
                        container = (AdditionalSetTypeContainer) getComponent(i);
                        container.autoCollapse(currentOpratingContainer);
                    }
                }
            }
        }
    }


}
