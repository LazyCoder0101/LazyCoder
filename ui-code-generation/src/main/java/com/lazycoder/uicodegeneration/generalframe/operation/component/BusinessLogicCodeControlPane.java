package com.lazycoder.uicodegeneration.generalframe.operation.component;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.container.*;
import com.lazycoder.uicodegeneration.component.operation.container.component.BusinessLogicCodeControlPaneUI;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalFunctionOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FormatOpratingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FunctionOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.BusinessLogicCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.BaseFormatStructureModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalFormatContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalFunctionOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.FunctionOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.MainFormatContainerModel;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;

import java.awt.*;
import java.util.ArrayList;

public class BusinessLogicCodeControlPane extends AbstractAdditiveMethodCodePane {

    /**
     *
     */
    private static final long serialVersionUID = -4600970047182612380L;

    public BusinessLogicCodeControlPane(AbstractFormatControlPane formatControlPane) {
        super(formatControlPane);
        // TODO Auto-generated constructor stub
        PathFind pathFind = new PathFind(MarkElementName.FUNCTION_MARK, PathFind.COMMAND_TYPE);
        setPathFind(pathFind);
        setName("BusinessFunction");
    }

    /**
     * 根据模型还原内容
     *
     * @param codeControlPaneModel
     */
    public AbstractFormatContainer restoreContent(BusinessLogicCodeControlPaneModel codeControlPaneModel, ArrayList<ModuleRelatedParam> moduleRelatedParamList) {
        AbstractFormatContainer formatContainer = null;

        FunctionOpratingContainer functionOpratingContainer;
        FunctionOperatingContainerParam functionOperatingContainerParam;
        FunctionOpratingContainerModel functionOpratingContainerModel;

        ArrayList<AbstractOperatingContainerModel> containerList = codeControlPaneModel.getContainerList();
        for (BaseFormatStructureModel baseFormatStructureModel : containerList) {
            if (baseFormatStructureModel instanceof FunctionOpratingContainerModel) {
                functionOpratingContainerModel = (FunctionOpratingContainerModel) baseFormatStructureModel;

                for (ModuleRelatedParam moduleRelatedParam : moduleRelatedParamList) {
                    if (functionOpratingContainerModel.getModuleId().equals(moduleRelatedParam.getModule().getModuleId())) {
                        functionOperatingContainerParam = new FunctionOperatingContainerParam();
                        if (moduleRelatedParam != null) {
                            functionOperatingContainerParam.setModule(moduleRelatedParam.getModule());
                            functionOperatingContainerParam.setModuleInfo(moduleRelatedParam.getModuleInfo());
                        }
                        functionOperatingContainerParam.setCodeControlPane(this);
                        functionOperatingContainerParam.setFormatControlPane(getFormatControlPane());

                        functionOpratingContainer = new FunctionOpratingContainer(functionOperatingContainerParam,
                                functionOpratingContainerModel, true);
                        addContainer(functionOpratingContainer);

                        break;
                    }
                }

            } else if (baseFormatStructureModel instanceof MainFormatContainerModel) {
                PathFind pathFind = new PathFind(MarkElementName.MAIN_FORMAT_MARK, PathFind.FORMAT_TYPE);
                FormatOpratingContainerParam formatOpratingContainerParam = new FormatOpratingContainerParam();
                formatOpratingContainerParam.setFormatControlPane(getFormatControlPane());
                formatOpratingContainerParam.setParentPathFind(pathFind);
                formatOpratingContainerParam.setCodeSerialNumber(0);
                formatOpratingContainerParam.setCodeControlPane(this);

                formatContainer = new MainFormatContainer(formatOpratingContainerParam,
                        (MainFormatContainerModel) baseFormatStructureModel);
                addContainer(formatContainer);

            } else if (baseFormatStructureModel instanceof AdditionalFormatContainerModel) {
                PathFind pathFind = new PathFind(MarkElementName.ADDITIONAL_FORMAT_MARK, PathFind.FORMAT_TYPE);
                FormatOpratingContainerParam formatOpratingContainerParam = new FormatOpratingContainerParam();
                formatOpratingContainerParam.setFormatControlPane(getFormatControlPane());
                formatOpratingContainerParam.setParentPathFind(pathFind);
                formatOpratingContainerParam.setCodeSerialNumber(0);
                formatOpratingContainerParam.setCodeControlPane(this);

                formatContainer = new AdditionalFormatContainer(formatOpratingContainerParam,
                        (AdditionalFormatContainerModel) baseFormatStructureModel);
                addContainer(formatContainer);

            } else if (baseFormatStructureModel instanceof AdditionalFunctionOpratingContainerModel) {
                AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam = new AdditionalFunctionOperatingContainerParam();
                additionalFunctionOperatingContainerParam.setCodeControlPane(this);
                additionalFunctionOperatingContainerParam.setFormatControlPane(getFormatControlPane());

                AdditionalFunctionOpratingContainer additionalFunctionOpratingContainer = new AdditionalFunctionOpratingContainer(
                        additionalFunctionOperatingContainerParam,
                        (AdditionalFunctionOpratingContainerModel) baseFormatStructureModel, true);
                addContainer(additionalFunctionOpratingContainer);

            }
        }
        return formatContainer;
    }

    /**
     * 添加功能容器
     *
     * @param container
     */
    @Override
    public void addContainer(Folder container) {
        tabs.add(container);
        this.add(container);
        // 为该面板添加抽屉事件
        ((BusinessLogicCodeControlPaneUI) ui).addTab(container);
        SysUtil.updateFrameUI(container);
    }

    @Override
    protected void setFolderPaneUI() {
        setUI(new BusinessLogicCodeControlPaneUI());
    }

    public void clearAll() {
        tabs.clear();
        this.removeAll();
    }

    @Override
    public BusinessLogicCodeControlPaneModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        BusinessLogicCodeControlPaneModel model = new BusinessLogicCodeControlPaneModel();
        setParam(model);
        return model;
    }

    @Override
    protected void setThisParam(FunctionOperatingContainerParam functionOperatingContainerParam) {
        // TODO Auto-generated method stub
        functionOperatingContainerParam.setFormatControlPane(getFormatControlPane());
        functionOperatingContainerParam.setCommandAddRelatedAttribute(getCorrespondingCommandAddRelatedAttribute());
        functionOperatingContainerParam.setParentOpratingContainer(null);
    }

    @Override
    protected void setThisParam(AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam) {
        // TODO Auto-generated method stub
        additionalFunctionOperatingContainerParam.setFormatControlPane(getFormatControlPane());
        additionalFunctionOperatingContainerParam.setCommandAddRelatedAttribute(getCorrespondingCommandAddRelatedAttribute());
        additionalFunctionOperatingContainerParam.setParentOpratingContainer(null);
    }

    @Override
    public boolean canAddExternalMethods() {
        return true;
    }

    /**
     * 删除某个模块的业务方法
     *
     * @param moduleId
     */
    public void delModuleOpratingContainer(String moduleId) {
        Component component;
        FunctionOpratingContainer functionOpratingContainer;
        AdditionalFunctionOpratingContainer additionalFunctionOpratingContainer;
        for (int i = 0; i < getComponentCount(); i++) {
            component = getComponent(i);
            if (component instanceof FunctionOpratingContainer) {
                functionOpratingContainer = (FunctionOpratingContainer) component;
                functionOpratingContainer.delModuleOpratingContainer(moduleId);

            } else if (component instanceof MainFormatContainer) {
                MainFormatContainer mainFormatContainer = (MainFormatContainer) getComponent(i);
                mainFormatContainer.delModuleOpratingContainerFromComponent(moduleId);

            } else if (component instanceof AdditionalFormatContainer) {
                AdditionalFormatContainer formatContainer = (AdditionalFormatContainer) getComponent(i);
                formatContainer.delModuleOpratingContainerFromComponent(moduleId);

            } else if (component instanceof AdditionalFunctionOpratingContainer) {
                additionalFunctionOpratingContainer = (AdditionalFunctionOpratingContainer) getComponent(i);
                additionalFunctionOpratingContainer.delModuleOpratingContainerFromComponent(moduleId);
            }
        }
    }

    /**
     * 收起面板里面的控制容器当前展开的所有组件
     */
    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        Component component;
        for (int i = 0; i < getComponentCount(); i++) {
            component = getComponent(i);
            if (component instanceof OpratingContainerInterface) {
                ((OpratingContainerInterface) component).collapseThis();
            }
        }
    }

    @Override
    public CommandAddRelatedAttribute getCorrespondingCommandAddRelatedAttribute() {
        CommandAddRelatedAttribute commandAddRelatedAttribute = new CommandAddRelatedAttribute();
        commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
        commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
        return commandAddRelatedAttribute;
    }

}
