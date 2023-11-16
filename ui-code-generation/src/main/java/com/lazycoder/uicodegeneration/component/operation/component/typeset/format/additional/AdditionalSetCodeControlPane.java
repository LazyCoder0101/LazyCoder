package com.lazycoder.uicodegeneration.component.operation.component.typeset.format.additional;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.meta.AdditionalSetMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalSetContainer;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.BaseFormatStructureModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalSetOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.AdditionalSetTypeContainerModel;
import java.util.ArrayList;

public class AdditionalSetCodeControlPane extends AbstractCodeControlPane {

    /**
     *
     */
    private static final long serialVersionUID = 2381228844809378537L;

    private AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam;

    public AdditionalSetCodeControlPane(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam) {
        // TODO Auto-generated constructor stub
        super(additionalSetTypeOperatingContainerParam.getFormatControlPane(), 20);
        additionalSetTypeOperatingContainerParam.setCodeControlPane(this);

        this.additionalSetTypeOperatingContainerParam = additionalSetTypeOperatingContainerParam;
        PathFind pathFind = new PathFind(MarkElementName.ADDITIONAL_SET_TYPE_MARK, PathFind.COMMAND_TYPE);
        setPathFind(pathFind);
    }


    /**
     * 根据模型还原内容
     *
     * @param codeControlPaneModel
     */
    public void restoreContent(AdditionalSetTypeContainerModel codeControlPaneModel) {
        AdditionalSetContainer container;
        ArrayList<AbstractOperatingContainerModel> containerList = codeControlPaneModel.getContainerList();
        for (BaseFormatStructureModel baseFormatStructureModel : containerList) {
            if (baseFormatStructureModel instanceof AdditionalSetOpratingContainerModel) {
                this.additionalSetTypeOperatingContainerParam.setCodeControlPane(this);
                container = new AdditionalSetContainer(additionalSetTypeOperatingContainerParam,
                        (AdditionalSetOpratingContainerModel) baseFormatStructureModel, true);
                addContainer(container);
            }
        }
    }

    public AdditionalSetContainer addOpratingPane(FormatTypeFeatureSelectionModel featureSelectionModel, boolean canBeDelOrNot) {
        additionalSetTypeOperatingContainerParam.setFeatureSelectionModel(featureSelectionModel);
        additionalSetTypeOperatingContainerParam.setCodeControlPane(this);
        additionalSetTypeOperatingContainerParam.setAdditionalSetType(featureSelectionModel.getTypeName());

        AdditionalSetContainer container = null;
        AdditionalSetMetaModel metaModel = SysService.ADDITIONAL_SET_SERVICE
                .getAdditionalSetMetaModel(additionalSetTypeOperatingContainerParam.getFeatureSelectionModel());
        if (metaModel != null) {
            boolean flag = AdditionalSetContainer.checkWhetherItMatches(additionalSetTypeOperatingContainerParam, metaModel);
            if (flag) {
                container = new AdditionalSetContainer(additionalSetTypeOperatingContainerParam, metaModel, true, canBeDelOrNot);
                addContainer(container);

                scrollToBottom();
            }
        }
        return container;
    }

    @Override
    public AdditionalSetTypeContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        AdditionalSetTypeContainerModel model = new AdditionalSetTypeContainerModel();
        super.setParam(model);
        return model;
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        AdditionalSetContainer container;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof AdditionalSetContainer) {
                container = (AdditionalSetContainer) getComponent(i);
                container.delModuleOpratingContainerFromComponent(moduleId);
            }
        }
    }

}
