package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.functionadd;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodePaneModelGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractCommandOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalFunctionOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.FunctionAddInternalOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.FunctionOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalFunctionOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FunctionAddInternalMethodOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FunctionOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractAdditiveMethodCodePane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.BaseFormatStructureModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalFunctionOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.FunctionAddInternalOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.FunctionOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.FunctionAddCodeControlPaneModel;
import java.awt.Component;
import java.util.ArrayList;

/**
 * 方法控制组件的，用来添加方法的面板
 *
 * @author admin
 */
public class FunctionAddCodeControlPane extends AbstractAdditiveMethodCodePane {

    /**
     *
     */
    private static final long serialVersionUID = 3951622106698827485L;

    private GeneralContainerComponentParam containerComponentParam;

    private FunctionAddControl controlElement;

    public FunctionAddCodeControlPane(PathFind pathFind, GeneralContainerComponentParam opratingContainerParam,
                                      FunctionAddControl controlElement) {
        super(opratingContainerParam.getFormatControlPane());
        // TODO Auto-generated constructor stub
        this.controlElement = controlElement;
        setPathFind(pathFind);
        setName(this.controlElement.getThisName());
        init(opratingContainerParam);
    }

    private void init(GeneralContainerComponentParam opratingContainerParam) {
        this.containerComponentParam = opratingContainerParam;
        this.containerComponentParam.setCodeControlPane(this);

//		this.containerComponentParam.setCodeSerialNumber(opratingContainerParam.getCodeSerialNumber());
//		this.containerComponentParam.setParentPathFind(opratingContainerParam.getParentPathFind());

//		if (PathFind.commandType == getPathFind().getMetaType()) {
//			this.containerComponentParam
//					.setFirstCommandOpratingContainer(opratingContainerParam.getFirstCommandOpratingContainer());
//		} else if (PathFind.formatType == getPathFind().getMetaType()) {
//			this.containerComponentParam.setFormatContainer(opratingContainerParam.getFormatContainer());
//		}
//        setToolTipText(opratingContainerParam.getCodeSerialNumber() + ":" + this.controlElement.getThis_name());
    }

    /**
     * 自动添加功能
     */
    public void autoGenerateOpratingContainers() {
        ArrayList<MethodAddStorageFormat> thisFunctionContainerList = this.controlElement.getFunctionList();
        if (thisFunctionContainerList != null) {
            FunctionAddInternalOpratingContainer opratingContainer;
            for (MethodAddStorageFormat methodAddStorageFormat : thisFunctionContainerList) {
                if (FunctionUseProperty.autoGenerateOnce.getSysDictionaryValue() == methodAddStorageFormat.getSetProperty() ||
                        FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue() == methodAddStorageFormat.getSetProperty()) {
                    opratingContainer = addInternalFunction(methodAddStorageFormat, true, false);
                    if (opratingContainer == null) {
                        String text = "无法添加\"" + methodAddStorageFormat.getShowText() + "\""
                                + "功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "无法添加功能拓展组件里面的方法：\"" + methodAddStorageFormat.getShowText() + "\"功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);
                    }
                } else if (FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue() == methodAddStorageFormat.getSetProperty()) {
                    opratingContainer = addInternalFunction(methodAddStorageFormat, false, false);
                    if (opratingContainer == null) {
                        String text = "无法添加\"" + methodAddStorageFormat.getShowText() + "\""
                                + "功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "无法添加功能拓展组件里面的方法：\"" + methodAddStorageFormat.getShowText() + "\"功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);
                    }
                }
            }
        }
    }

    /**
     * 根据模型还原内容
     *
     * @param functionAddCodeControlPaneModel
     */
    public void restoreContent(FunctionAddCodeControlPaneModel functionAddCodeControlPaneModel) {

        ArrayList<AbstractOperatingContainerModel> containerList = functionAddCodeControlPaneModel.getContainerList();
        FunctionOpratingContainerModel functionOpratingContainerModel;
        ModuleRelatedParam moduleRelatedParamTemp;

        for (BaseFormatStructureModel baseFormatStructureModel : containerList) {
            if (baseFormatStructureModel instanceof FunctionOpratingContainerModel) {
                functionOpratingContainerModel = (FunctionOpratingContainerModel) baseFormatStructureModel;

                FunctionOperatingContainerParam functionOperatingContainerParam = new FunctionOperatingContainerParam();
                setThisParam(functionOperatingContainerParam);
                if (functionOpratingContainerModel.getModuleId()!=null){
                    moduleRelatedParamTemp = this.containerComponentParam.getFormatControlPane().getModuleRelatedParam(
                            functionOpratingContainerModel.getModuleId()
                    );
                    if (moduleRelatedParamTemp!=null){
                        functionOperatingContainerParam.setModuleInfo(moduleRelatedParamTemp.getModuleInfo());
                        functionOperatingContainerParam.setModule(moduleRelatedParamTemp.getModule());
                    }
                }

                FunctionOpratingContainer functionOpratingContainer = new FunctionOpratingContainer(
                        functionOperatingContainerParam, functionOpratingContainerModel,
                        true);
                addContainer(functionOpratingContainer);

            } else if (baseFormatStructureModel instanceof FunctionAddInternalOpratingContainerModel) {
                FunctionAddInternalMethodOperatingContainerParam functionAddInternalMethodOperatingContainerParam = new FunctionAddInternalMethodOperatingContainerParam();
                setFunctionAddInternalMethodOperatingContainerParam(functionAddInternalMethodOperatingContainerParam);

                FunctionAddInternalOpratingContainer functionAddInternalOpratingContainer = new FunctionAddInternalOpratingContainer(
                        functionAddInternalMethodOperatingContainerParam,
                        (FunctionAddInternalOpratingContainerModel) baseFormatStructureModel, true);
                addContainer(functionAddInternalOpratingContainer);

            } else if (baseFormatStructureModel instanceof AdditionalFunctionOpratingContainerModel) {

                AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam = new AdditionalFunctionOperatingContainerParam();
                setThisParam(additionalFunctionOperatingContainerParam);

                AdditionalFunctionOpratingContainer additionalFunctionOpratingContainer = new AdditionalFunctionOpratingContainer(
                        additionalFunctionOperatingContainerParam,
                        (AdditionalFunctionOpratingContainerModel) baseFormatStructureModel, true);
                addContainer(additionalFunctionOpratingContainer);
            }
        }
    }

    public void delTheModuleContent(String moduleId) {
        // TODO Auto-generated method stub
        Component component;
        for (int i = 0; i < getComponentCount(); i++) {
            component = getComponent(i);
            if (component instanceof FunctionOpratingContainer) {
                ((FunctionOpratingContainer) component).delModuleOpratingContainer(moduleId);
            } else if (component instanceof OpratingContainerInterface) {
                ((OpratingContainerInterface) component).delModuleOpratingContainerFromComponent(moduleId);
            }
        }
    }

    /**
     * 添加内部方法
     *
     * @param methodAddStorageFormat
     */
    public FunctionAddInternalOpratingContainer addInternalFunction(MethodAddStorageFormat methodAddStorageFormat, boolean canBeDelOrNot, boolean checkOrNot) {
        FunctionAddInternalOpratingContainer functionAddInternalOpratingContainer = null;

        FunctionAddInternalMethodOperatingContainerParam functionAddInternalMethodOperatingContainerParam = new FunctionAddInternalMethodOperatingContainerParam();
        setFunctionAddInternalMethodOperatingContainerParam(functionAddInternalMethodOperatingContainerParam);
        functionAddInternalMethodOperatingContainerParam.setMethodAddStorageFormat(methodAddStorageFormat);

        boolean flag = true;
        if (checkOrNot) {
            flag = FunctionAddInternalOpratingContainer.checkWhetherItMatches(functionAddInternalMethodOperatingContainerParam, methodAddStorageFormat);
        }

        if (flag) {
            if (canBeDelOrNot) {
                if (CodeGenerationFrameHolder.autoCollapseCheckBox != null) {
                    if (CodeGenerationFrameHolder.autoCollapseCheckBox.isSelected() == true) {
                        autoCollapse(null);//先把这个面板之前添加的方法都折叠起来
                    } else {
                        collapseThis();
                    }
                } else {
                    collapseThis();
                }
            }

            functionAddInternalOpratingContainer = new FunctionAddInternalOpratingContainer(
                    functionAddInternalMethodOperatingContainerParam, true, true);

            addContainer(functionAddInternalOpratingContainer);
            scrollToBottom();
        }
        return functionAddInternalOpratingContainer;
    }

//    @Override
//    public FunctionOpratingContainer addFunction(FunctionFeatureSelectionModel featureSelectionModel) {
//        if (CodeGenerationFrameHolder.autoCollapseCheckBox != null) {
//            if (CodeGenerationFrameHolder.autoCollapseCheckBox.isSelected() == true) {
//                autoCollapse(null);//先把这个面板之前添加的方法都折叠起来
//            }else {
//                collapseThis();
//            }
//        }else {
//            collapseThis();
//        }
//        return super.addFunction(featureSelectionModel);
//    }


//    @Override
//    public void addAdditionalFunction(AdditionalFunctionFeatureSelectionModel featureSelectionModel) {
//        if (CodeGenerationFrameHolder.autoCollapseCheckBox != null) {
//            if (CodeGenerationFrameHolder.autoCollapseCheckBox.isSelected() == true) {
//                autoCollapse(null);//先把这个面板之前添加的方法都折叠起来
//            }else {
//                collapseThis();
//            }
//        }else {
//            collapseThis();
//        }
//
//        super.addAdditionalFunction(featureSelectionModel);
//    }

    public FunctionAddControl getControlElement() {
        return controlElement;
    }

    /**
     * 获取该面板的存储模型
     */
    @Override
    public FunctionAddCodeControlPaneModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        FunctionAddCodeControlPaneModel functionAddCodeControlPaneModel = new FunctionAddCodeControlPaneModel();
        setParam(functionAddCodeControlPaneModel);
        return functionAddCodeControlPaneModel;
    }

    @Override
    public void setParam(AbstractCodeControlPaneModel model) {
        // TODO Auto-generated method stub
        FunctionAddCodeControlPaneModel theModel = (FunctionAddCodeControlPaneModel) model;
        super.setParam(theModel);
    }

    public GeneralContainerComponentParam getFunctionAddOperatingContainerParam() {
        return containerComponentParam;
    }

    @Override
    protected void setThisParam(FunctionOperatingContainerParam functionOperatingContainerParam) {
        // TODO Auto-generated method stub
        functionOperatingContainerParam.setFunctionAddControlLabel(getControlElement());
        functionOperatingContainerParam.setFormatControlPane(this.containerComponentParam.getFormatControlPane());
        functionOperatingContainerParam.setCodeControlPane(this);

        functionOperatingContainerParam.setThisCustomFunctionNameHolder(this.containerComponentParam.getThisCustomFunctionNameHolder());
        functionOperatingContainerParam.setThisCustomVariableHolder(this.containerComponentParam.getThisCustomVariableHolder());

        functionOperatingContainerParam.setModule(this.containerComponentParam.getModule());
        functionOperatingContainerParam.setModuleInfo(this.containerComponentParam.getModuleInfo());

//        if (PathFind.COMMAND_TYPE == getPathFind().getMetaType()) {
        functionOperatingContainerParam
                .setFirstCommandOpratingContainer(containerComponentParam.getFirstCommandOpratingContainer());

//        } else if (PathFind.FORMAT_TYPE == getPathFind().getMetaType()) {
        functionOperatingContainerParam.setFormatContainer(containerComponentParam.getFormatContainer());
//        }
        functionOperatingContainerParam.setFunctionAddComponentPlaceCodeLocationInformation(getCodeLocationInformation());//设置功能拓展所在的代码位置
        functionOperatingContainerParam.setCommandAddRelatedAttribute(getCorrespondingCommandAddRelatedAttribute());

        functionOperatingContainerParam.setParentOpratingContainer(containerComponentParam.getThisOpratingContainer());
    }


    private void setFunctionAddInternalMethodOperatingContainerParam(
            FunctionAddInternalMethodOperatingContainerParam functionAddInternalMethodOperatingContainerParam) {

        functionAddInternalMethodOperatingContainerParam
                .setFormatControlPane(this.containerComponentParam.getFormatControlPane());
        functionAddInternalMethodOperatingContainerParam
                .setFirstCommandOpratingContainer(this.containerComponentParam.getFirstCommandOpratingContainer());
        functionAddInternalMethodOperatingContainerParam
                .setFormatContainer(this.containerComponentParam.getFormatContainer());
        functionAddInternalMethodOperatingContainerParam.setCodeControlPane(this);
        functionAddInternalMethodOperatingContainerParam
                .setOperatingComponentPlacePane(this.containerComponentParam.getOperatingComponentPlacePane());
        functionAddInternalMethodOperatingContainerParam
                .setThisOpratingContainer(this.containerComponentParam.getThisOpratingContainer());
        functionAddInternalMethodOperatingContainerParam.setControlElement(controlElement);

        functionAddInternalMethodOperatingContainerParam.setThisCustomFunctionNameHolder(this.containerComponentParam.getThisCustomFunctionNameHolder());
        functionAddInternalMethodOperatingContainerParam.setThisCustomVariableHolder(this.containerComponentParam.getThisCustomVariableHolder());

        functionAddInternalMethodOperatingContainerParam.setModule(this.containerComponentParam.getModule());
        functionAddInternalMethodOperatingContainerParam.setModuleInfo(this.containerComponentParam.getModuleInfo());

        OpratingContainerInterface thisOpratingContainer = containerComponentParam.getThisOpratingContainer();

        FunctionAddParam functionAddParam = new FunctionAddParam();
        functionAddParam.setPaneType(thisOpratingContainer.getPaneType());
        functionAddParam.setClassName(thisOpratingContainer.getClassName());
        functionAddParam.setModuleName(thisOpratingContainer.getModuleName());
        functionAddParam.setModuleId(thisOpratingContainer.getModuleId());
        functionAddParam.setAdditionalSerialNumber(thisOpratingContainer.getAdditionalSerialNumber());

        functionAddInternalMethodOperatingContainerParam.setFunctionAddParam(functionAddParam);

        functionAddInternalMethodOperatingContainerParam.setFunctionAddComponentPlaceCodeLocationInformation(getCodeLocationInformation());//设置功能拓展所在的代码位置
        functionAddInternalMethodOperatingContainerParam.setCommandAddRelatedAttribute(getCorrespondingCommandAddRelatedAttribute());

        functionAddInternalMethodOperatingContainerParam.setParentOpratingContainer(thisOpratingContainer);
    }

    @Override
    protected void setThisParam(AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam) {
        // TODO Auto-generated method stub
        additionalFunctionOperatingContainerParam
                .setFormatControlPane(this.containerComponentParam.getFormatControlPane());
        additionalFunctionOperatingContainerParam.setCodeControlPane(this);

        additionalFunctionOperatingContainerParam.setThisCustomFunctionNameHolder(this.containerComponentParam.getThisCustomFunctionNameHolder());
        additionalFunctionOperatingContainerParam.setThisCustomVariableHolder(this.containerComponentParam.getThisCustomVariableHolder());

        additionalFunctionOperatingContainerParam
                .setFirstCommandOpratingContainer(containerComponentParam.getFirstCommandOpratingContainer());
        additionalFunctionOperatingContainerParam.setFormatContainer(containerComponentParam.getFormatContainer());

        additionalFunctionOperatingContainerParam.setModule(this.containerComponentParam.getModule());
        additionalFunctionOperatingContainerParam.setModuleInfo(this.containerComponentParam.getModuleInfo());


        additionalFunctionOperatingContainerParam.setFunctionAddComponentPlaceCodeLocationInformation(getCodeLocationInformation());//设置功能拓展组件所在的代码位置
        additionalFunctionOperatingContainerParam.setCommandAddRelatedAttribute(getCorrespondingCommandAddRelatedAttribute());

        additionalFunctionOperatingContainerParam.setParentOpratingContainer(containerComponentParam.getThisOpratingContainer());
    }

    @Override
    public boolean canAddExternalMethods() {
        boolean flag = true;
        if (this.controlElement.isOnlInternalCodeIsAllowed() == true) {//如果只允许添加内部使用方法
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    public ContainerGenerateCodeResponseParam getCodeLocationInformation() {
        ContainerGenerateCodeResponseParam returnThisCodeLocationInformation = null;
        if (PathFind.COMMAND_TYPE == getPathFind().getMetaType()) {
            OpratingContainerInterface thisOpratingContainer = this.containerComponentParam.getThisOpratingContainer();
            if (null != thisOpratingContainer &&
                    thisOpratingContainer instanceof AbstractCommandOpratingContainer) {
                AbstractCommandOpratingContainer commandOpratingContainer = (AbstractCommandOpratingContainer) thisOpratingContainer;
                ContainerGenerateCodeResponseParam thisOpratingContainerCodeLocationInformation = commandOpratingContainer.getThisCodeLocationInformation();

                PathFindCell pathFindCell = new PathFindCell(this.containerComponentParam.getCodeSerialNumber(),
                        controlElement, this.containerComponentParam.getPaneType());
                returnThisCodeLocationInformation = ContainerGenerateCodeResponseParam.generateContainerGenerateCodeResponseParam(
                        thisOpratingContainerCodeLocationInformation, pathFindCell
                );
            }
        } else if (PathFind.FORMAT_TYPE == getPathFind().getMetaType()) {
//            CodeShowPane codeShowPane;
            OpratingContainerInterface thisOpratingContainer = this.containerComponentParam.getThisOpratingContainer();
            if (thisOpratingContainer != null) {
                if (getPathFind().whetherToOperateForFormat()) {//格式里面的功能拓展组件
                    if (thisOpratingContainer instanceof AbstractFormatContainer) {
                        AbstractFormatContainer formatContainer = (AbstractFormatContainer) thisOpratingContainer;
                        returnThisCodeLocationInformation = new ContainerGenerateCodeResponseParam();
                        ArrayList<CodeFormatFlagParam> codePaneRelatedInfoList = formatContainer.getCodePaneRelatedInfoList();

//                        ArrayList<String> codeLabelIdList;
                        CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponse;
                        CodeListLocation codeListLocation;
                        for (CodeFormatFlagParam codeFormatFlagParam : codePaneRelatedInfoList) {
//                            codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeFormatFlagParam);
//                            if (codeShowPane != null) {
//                                codeLabelIdList = codeShowPane.getUnitFormatFirstLayerFunctionAddBeanCodeLabelIdList(getPathFind());
//                                if (codeLabelIdList.size() > 0) {
//                                    for (String codeLabelIdTemp : codeLabelIdList) {
                            codePaneModelGenerateCodeResponse = new CodePaneModelGenerateCodeResponseParam();
                            codePaneModelGenerateCodeResponse.setCodeFormatFlagParam(codeFormatFlagParam);
//                            codePaneModelGenerateCodeResponse.setMetaType(PathFind.FORMAT_TYPE);
//                            codePaneModelGenerateCodeResponse.setEditOrNot(true);
//                            codePaneModelGenerateCodeResponse.setCodeLabelId(codeLabelIdTemp);

                            codeListLocation = new CodeListLocation();
                            codeListLocation.addCodePathFindForFormat(getPathFind());
                            codePaneModelGenerateCodeResponse.addCodeListLocationInfoList(codeListLocation);

                            returnThisCodeLocationInformation.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponse);
//                                    }
//                                }
//                            }
                        }
                    }
                } else {//格式的功能里面的功能拓展组件
                    if (thisOpratingContainer instanceof AbstractCommandOpratingContainer) {
                        AbstractCommandOpratingContainer commandOpratingContainer = (AbstractCommandOpratingContainer) thisOpratingContainer;
                        ContainerGenerateCodeResponseParam thisOpratingContainerCodeLocationInformation = commandOpratingContainer.getThisCodeLocationInformation();

                        PathFindCell pathFindCell = new PathFindCell(this.containerComponentParam.getCodeSerialNumber(),
                                controlElement, this.containerComponentParam.getPaneType());
                        returnThisCodeLocationInformation = ContainerGenerateCodeResponseParam.generateContainerGenerateCodeResponseParam(
                                thisOpratingContainerCodeLocationInformation, pathFindCell
                        );
                    }
                }
            }
        }
        return returnThisCodeLocationInformation;
    }


    @Override
    public CommandAddRelatedAttribute getCorrespondingCommandAddRelatedAttribute() {
        CommandAddRelatedAttribute commandAddRelatedAttribute = new CommandAddRelatedAttribute();
        commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_FUNCTION_ADD_COMPONENT_ADD_TYPE);
        commandAddRelatedAttribute.setOtherAttribute(this.controlElement.getOtherAttribute());
        return commandAddRelatedAttribute;
    }

}
