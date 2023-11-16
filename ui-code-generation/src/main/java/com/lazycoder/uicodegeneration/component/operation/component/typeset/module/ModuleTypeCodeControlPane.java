package com.lazycoder.uicodegeneration.component.operation.component.typeset.module;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.featureSelectionModel.ModuleSetFeatureSelectionModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.meta.ModuleSetMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.container.ModuleSetOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.ModuleTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.BaseFormatStructureModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.ModuleSetOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.ModuleTypeContainerModel;
import java.util.ArrayList;

public class ModuleTypeCodeControlPane extends AbstractCodeControlPane {

    /**
     *
     */
    private static final long serialVersionUID = -8433035513373776802L;

    private ModuleTypeOperatingContainerParam moduleTypeContainerParam;

    public ModuleTypeCodeControlPane(ModuleTypeOperatingContainerParam moduleTypeContainerParam) {
        super(moduleTypeContainerParam.getFormatControlPane(), 20);
        // TODO Auto-generated constructor stub
        PathFind pathFind = new PathFind(MarkElementName.SET_MARK, PathFind.COMMAND_TYPE);
        setPathFind(pathFind);
        this.moduleTypeContainerParam = moduleTypeContainerParam;
        this.moduleTypeContainerParam.setCodeControlPane(this);
    }

    @Override
    public ModuleTypeContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        ModuleTypeContainerModel model = new ModuleTypeContainerModel();
        model.setModuleSetType(this.moduleTypeContainerParam.getModuleSetType());
        model.setModuleId(this.moduleTypeContainerParam.getModule().getModuleId());
        super.setParam(model);
        return model;
    }

    /**
     * 根据模型还原内容
     *
     * @param moduleTypeModel
     */
    public void restoreContent(ModuleTypeContainerModel moduleTypeModel) {
        ModuleSetOpratingContainerModel moduleSetOpratingContainerModel;
        ModuleSetOpratingContainer moduleSetOpratingContainer;
        ModuleTypeOperatingContainerParam moduleTypeOperatingContainerParam;

        ArrayList<AbstractOperatingContainerModel> containerList = moduleTypeModel.getContainerList();
        for (BaseFormatStructureModel baseFormatStructureModel : containerList) {
            if (baseFormatStructureModel instanceof ModuleSetOpratingContainerModel) {
                moduleSetOpratingContainerModel = (ModuleSetOpratingContainerModel) baseFormatStructureModel;

                moduleTypeOperatingContainerParam = new ModuleTypeOperatingContainerParam();
                moduleTypeOperatingContainerParam.setModuleSetType(moduleTypeModel.getModuleSetType());
                moduleTypeOperatingContainerParam.setCodeControlPane(this);
                moduleTypeOperatingContainerParam
                        .setFormatControlPane(this.moduleTypeContainerParam.getFormatControlPane());

                moduleTypeOperatingContainerParam.setModuleInfo(moduleTypeContainerParam.getModuleInfo());
                moduleTypeOperatingContainerParam.setModule(moduleTypeContainerParam.getModule());

                moduleSetOpratingContainer = new ModuleSetOpratingContainer(moduleTypeOperatingContainerParam,
                        moduleSetOpratingContainerModel, true);
                addContainer(moduleSetOpratingContainer);
            }
        }
    }

    public ModuleSetOpratingContainer addOpratingPane(ModuleSetFeatureSelectionModel featureSelectionModel, boolean canBeDelOrNot) {

        ModuleTypeOperatingContainerParam theModuleTypeOperatingContainerParam = new ModuleTypeOperatingContainerParam();
        theModuleTypeOperatingContainerParam.setModuleSetType(featureSelectionModel.getTypeName());
        theModuleTypeOperatingContainerParam.setCodeControlPane(this);
        theModuleTypeOperatingContainerParam.setFormatControlPane(this.moduleTypeContainerParam.getFormatControlPane());
        theModuleTypeOperatingContainerParam.setModuleSetFeatureSelectionModel(featureSelectionModel);

        theModuleTypeOperatingContainerParam.setModuleInfo(moduleTypeContainerParam.getModuleInfo());
        theModuleTypeOperatingContainerParam.setModule(moduleTypeContainerParam.getModule());

        ModuleSetMetaModel metaModel = SysService.MODULE_SET_SERVICE
                .getModuleSetMetaModel(featureSelectionModel);

        ModuleSetOpratingContainer opratingContainer = null;
        if (ModuleSetOpratingContainer.checkWhetherItMatches(theModuleTypeOperatingContainerParam, metaModel)) {

            opratingContainer = new ModuleSetOpratingContainer(
                    theModuleTypeOperatingContainerParam, metaModel, true, canBeDelOrNot);
            addContainer(opratingContainer);

            scrollToBottom();
        }
        return opratingContainer;
    }


}
