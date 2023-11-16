package com.lazycoder.uicodegeneration.component.operation.component.typeset.format.main;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.meta.MainSetMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.container.MainSetContainer;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.MainSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.BaseFormatStructureModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.MainSetOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.MainSetTypeContainerModel;
import java.util.ArrayList;

public class MainSetCodeControlPane extends AbstractCodeControlPane {

    /**
     *
     */
    private static final long serialVersionUID = 4171341845093524425L;

    private MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam;

    public MainSetCodeControlPane(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam) {
        // TODO Auto-generated constructor stub
        super(mainSetTypeOperatingContainerParam.getFormatControlPane(), 20);
        this.mainSetTypeOperatingContainerParam = mainSetTypeOperatingContainerParam;
        this.mainSetTypeOperatingContainerParam.setCodeControlPane(this);
        PathFind pathFind = new PathFind(MarkElementName.MAIN_SET_TYPE_MARK, PathFind.COMMAND_TYPE);
        setPathFind(pathFind);
    }

    public MainSetCodeControlPane(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam,
                                  JSONObject mainSetTypeContainerModelJSONObject) {
        // TODO Auto-generated constructor stub
        super(mainSetTypeOperatingContainerParam.getFormatControlPane(), 20);
        this.mainSetTypeOperatingContainerParam = mainSetTypeOperatingContainerParam;
        this.mainSetTypeOperatingContainerParam.setCodeControlPane(this);
        PathFind pathFind = new PathFind(MarkElementName.MAIN_SET_TYPE_MARK, PathFind.COMMAND_TYPE);
        setPathFind(pathFind);
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        MainSetContainer mainSetContainer;
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof MainSetContainer) {
                mainSetContainer = (MainSetContainer) getComponent(i);
                mainSetContainer.delModuleOpratingContainerFromComponent(moduleId);
            }
        }
    }

    /**
     * 根据模型还原内容
     *
     * @param codeControlPaneModel
     */
    public void restoreContent(MainSetTypeContainerModel codeControlPaneModel) {

        MainSetContainer mainSetContainer;
        ArrayList<AbstractOperatingContainerModel> containerList = codeControlPaneModel.getContainerList();
        for (BaseFormatStructureModel baseFormatStructureModel : containerList) {
            if (baseFormatStructureModel instanceof MainSetOpratingContainerModel) {
                this.mainSetTypeOperatingContainerParam.setCodeControlPane(this);
                mainSetContainer = new MainSetContainer(mainSetTypeOperatingContainerParam,
                        (MainSetOpratingContainerModel) baseFormatStructureModel, true);
                addContainer(mainSetContainer);
            }
        }
    }

    /**
     * 添加相应的必填模板设置功能
     *
     * @param featureSelectionModel 相关信息
     * @param canBeDelOrNot         能否被删
     * @return
     */
    public MainSetContainer addOpratingPane(FormatTypeFeatureSelectionModel featureSelectionModel, boolean canBeDelOrNot) {
        mainSetTypeOperatingContainerParam.setFeatureSelectionModel(featureSelectionModel);
        mainSetTypeOperatingContainerParam.setCodeControlPane(this);
        MainSetContainer mainSetContainer = null;
        MainSetMetaModel metaModel = SysService.MAIN_SET_SERVICE
                .getMainSetMetaModel(mainSetTypeOperatingContainerParam.getFeatureSelectionModel());

        boolean flag = MainSetContainer.checkWhetherItMatches(mainSetTypeOperatingContainerParam, metaModel);
        if (flag) {
            mainSetContainer = new MainSetContainer(mainSetTypeOperatingContainerParam, metaModel,
                    true, canBeDelOrNot);
            addContainer(mainSetContainer);

            scrollToBottom();
        }
        return mainSetContainer;
    }

    @Override
    public MainSetTypeContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        MainSetTypeContainerModel model = new MainSetTypeContainerModel();
        super.setParam(model);
        return model;
    }

}
