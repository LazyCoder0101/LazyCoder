package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.vo.element.lable.control.functionadd.FunctionAddCodeModel;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodePaneModelGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.functionadd.FunctionAddCodeControlPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FunctionAddInternalMethodOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.FunctionAddInternalOpratingContainerModel;
import java.io.File;
import java.util.ArrayList;
import lombok.Getter;


public class FunctionAddInternalOpratingContainer extends AbstractCommandOpratingContainer {

    /**
     *
     */
    private static final long serialVersionUID = -5875801546933534524L;

    @Getter
    private MethodAddStorageFormat methodAddStorageFormat = null;

    private FunctionAddInternalMethodOperatingContainerParam functionAddInternalMethodOperatingContainerParam = null;

    public FunctionAddInternalOpratingContainer(boolean expanded) {
        super();
        // TODO Auto-generated constructor stub
        init(expanded, true, null);
        setAppropriateSize(PROPOTION);
    }

    /**
     * 新建
     *
     * @param functionAddInternalMethodOperatingContainerParam
     * @param expanded
     */
    public FunctionAddInternalOpratingContainer(
            FunctionAddInternalMethodOperatingContainerParam functionAddInternalMethodOperatingContainerParam,
            boolean expanded, boolean canBeDelOrNot) {
        super();
        this.methodAddStorageFormat = functionAddInternalMethodOperatingContainerParam.getMethodAddStorageFormat();
        // this.functionAddInternalMethodOperatingContainerParam =
        // functionAddInternalMethodOperatingContainerParam;
        this.functionAddInternalMethodOperatingContainerParam = functionAddInternalMethodOperatingContainerParam;

        if (GeneralCommandOperatingModel.TRUE_ == functionAddInternalMethodOperatingContainerParam
                .getMethodAddStorageFormat().getHiddenState()) {
            hiddenState = true;
        } else {
            hiddenState = false;
        }
        init(expanded, canBeDelOrNot, functionAddInternalMethodOperatingContainerParam.getCodeControlPane());
        generateCodeAndOpratingContainer();
        setAppropriateSize(PROPOTION);

    }

    /**
     * 还原
     *
     * @param functionAddInternalMethodOperatingContainerParam
     * @param model
     * @param expanded
     */
    public FunctionAddInternalOpratingContainer(
            FunctionAddInternalMethodOperatingContainerParam functionAddInternalMethodOperatingContainerParam,
            FunctionAddInternalOpratingContainerModel model, boolean expanded) {
        super();
        this.methodAddStorageFormat = model.getMethodAddStorageFormat();
        this.functionAddInternalMethodOperatingContainerParam = functionAddInternalMethodOperatingContainerParam;

        restoreInit(model);
        init(expanded, model.isCanBeDelOrNot(), functionAddInternalMethodOperatingContainerParam.getCodeControlPane());
        CommandContainerComponentParam commandContainerComponentParam = getContainerComponentParam(null);
        restorePaneContent(model, commandContainerComponentParam);
        setAppropriateSize(PROPOTION);
    }

    @Override
    protected void restorePaneContent(AbstractCommandOperatingContainerModel commandOperatingContainerModel, GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        super.restorePaneContent(commandOperatingContainerModel, codeGenerationalOpratingContainerParam);
        setThisToolTipText(methodAddStorageFormat.getShowText());
    }

    /**
     * 生成代码和组件
     */
    private void generateCodeAndOpratingContainer() {
        codeSerialNumber = CodeIDGenerator.generateCodeSerialNumber();
        pathFind = this.functionAddInternalMethodOperatingContainerParam.getCodeControlPane().getPathFind();
        this.functionAddInternalMethodOperatingContainerParam.setCodeSerialNumber(codeSerialNumber);
        if (this.functionAddInternalMethodOperatingContainerParam.getMethodAddStorageFormat() != null) {
            if (hiddenAndInfoButton != null) {
                setThisToolTipText(this.functionAddInternalMethodOperatingContainerParam.getMethodAddStorageFormat().getShowText());
            }
            generateCode();

            MethodAddStorageFormat methodAddStorageFormat = this.functionAddInternalMethodOperatingContainerParam
                    .getMethodAddStorageFormat();
            // 写默认内容
            CommandContainerComponentParam deafaultCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                    methodAddStorageFormat.getDefaultControlStatementFormat());
            deafaultOperatingPane.generateOperationalContent(deafaultCodeGenerationalOpratingContainerParam);

            if (hiddenState == true) {
                CommandContainerComponentParam hiddenCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                        methodAddStorageFormat.getHiddenControlStatementFormat());
                hiddenOperatingPane.generateOperationalContent(hiddenCodeGenerationalOpratingContainerParam);
            }
        }
    }

    private void generateCode() {
        if (pathFind != null) {
            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            boolean flag;
            ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation;
            functionAddComponentPlaceCodeLocationInformation = this.functionAddInternalMethodOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
            if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                CodeShowPane codeShowPane;
                for (FunctionAddCodeModel codeModel : this.methodAddStorageFormat.getCodeModelList()) {
                    boolean codeInserFlag = false;
                    boolean inserNewLineOrNot = true;
                    if (null != codeModel.getCodeUsePropertyParam()) {
                        setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                        if (GeneralCommandCodeModel.contant(
                                setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                            inserNewLineOrNot = false;
                        }
                    }
                    for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
                            functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
                        if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
                            codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                            if (codeShowPane != null) {
                                commandAddRelatedAttribute = this.functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                if (commandAddRelatedAttribute != null) {
                                    commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                    flag = OpratingContainerStaticMethod.generateCode(this,
                                            codeShowPane, commandAddRelatedAttribute,
                                            codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
                                            inserNewLineOrNot, codeResponseParam.getCodeListLocationInfoList());
                                    if (flag) {
                                        CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
                                        codeInserFlag = true;
                                    }
                                }
                            }
                        }
                    }
                    if (codeInserFlag == false &&
                            CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE == this.functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                        commandAddRelatedAttribute = this.functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                        commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
                        OpratingContainerStaticMethod.generateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                this, codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(),
                                commandAddRelatedAttribute,
                                this.functionAddInternalMethodOperatingContainerParam,
                                null, inserNewLineOrNot, null, null, null
                        );
                    }
                    if (codeInserFlag == false &&
                            CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE == this.functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                        commandAddRelatedAttribute = this.functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                        commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
                        OpratingContainerStaticMethod.generateCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                this, codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(),
                                commandAddRelatedAttribute,
                                this.functionAddInternalMethodOperatingContainerParam,
                                null, inserNewLineOrNot, null, null, null, this
                        );
                    }
                }
            }
        }
    }

    /**
     * 查看这里是不是可以添加这个代码的本功能容器
     *
     * @param functionAddInternalMethodOperatingContainerParam
     * @param methodAddStorageFormat
     * @return
     */
    public static boolean checkWhetherItMatches(FunctionAddInternalMethodOperatingContainerParam
                                                        functionAddInternalMethodOperatingContainerParam,
                                                MethodAddStorageFormat methodAddStorageFormat) {
        boolean returnFlag = false;
        PathFind containerPathFind = functionAddInternalMethodOperatingContainerParam.getCodeControlPane().getPathFind();
        if (containerPathFind != null) {
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation;
            boolean flag, returnTempFlag = false;
            returnFlag = false;
            functionAddComponentPlaceCodeLocationInformation =
                    functionAddInternalMethodOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
            if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                CodeShowPane codeShowPane;
                for (FunctionAddCodeModel codeModel : methodAddStorageFormat.getCodeModelList()) {
                    returnTempFlag = false;
                    for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
                            functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
                        if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
                            codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                            if (codeShowPane != null) {
                                for (CodeListLocation functionAddCodeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
                                    commandAddRelatedAttribute = functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                    if (commandAddRelatedAttribute != null) {
                                        commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                        flag = OpratingContainerStaticMethod.checkGenerateCode(containerPathFind, codeShowPane, commandAddRelatedAttribute,
                                                 functionAddCodeListLocation);
                                        if (flag) {
                                            returnTempFlag = true;
                                            break;
                                        }
                                    }
                                    if (returnTempFlag) {//找到合适的位置，不需要再找
                                        break;
                                    }
                                }
                                if (returnTempFlag) {//找到合适的位置，不需要再找
                                    break;
                                }
                            }
                        }
                    }
                    if (returnTempFlag) {//某一句代码找不到可以插入的位置，退出，标记无法在这里插入
                        returnFlag = true;
                    } else {
                        if (CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                                functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                            if (OpratingContainerStaticMethod.checkGenerateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                    containerPathFind, functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute(),
                                    functionAddInternalMethodOperatingContainerParam
                            )) {
                                returnFlag = true;
                            } else {
                                returnFlag = false;
                                break;
                            }
                        }

                        if (CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                                functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                            if (OpratingContainerStaticMethod.checkCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                    null, functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute(),
                                    functionAddInternalMethodOperatingContainerParam
                            )) {
                                returnFlag = true;
                            } else {
                                returnFlag = false;
                                break;
                            }
                        }
                    }

                    if (returnFlag == false) {
                        break;
                    }

                }
            }
        }
        return returnFlag;
    }
//    public static boolean checkWhetherItMatches(FunctionAddInternalMethodOperatingContainerParam
//                                                        functionAddInternalMethodOperatingContainerParam,
//                                                MethodAddStorageFormat methodAddStorageFormat) {
//        boolean returnFlag = false;
//        PathFind pathFind = functionAddInternalMethodOperatingContainerParam.getCodeControlPane().getPathFind();
//
//        if (pathFind != null) {
//            CommandAddRelatedAttribute commandAddRelatedAttribute;
//
//            boolean flag, checkFlag;
//            ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation =
//                    functionAddInternalMethodOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
//            if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
//                CodeShowPane codeShowPane;
//                returnFlag = true;
//                for (FunctionAddCodeModel codeModel : methodAddStorageFormat.getCodeModelList()) {
//                    checkFlag = false;
//
//                    for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
//                            functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
//                        if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
//                            codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
//                            if (codeShowPane != null) {
//
//                                commandAddRelatedAttribute = functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().clone();
//                                if (commandAddRelatedAttribute != null) {
//                                    commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                                    for (CodeListLocation codeListLocationTemp : codeResponseParam.getCodeListLocationInfoList()) {
//                                        flag = OpratingContainerStaticMethod.checkGenerateCode(pathFind, codeShowPane,
//                                                commandAddRelatedAttribute, codeModel.getCodeFormatParam(), codeListLocationTemp);
//                                        if (flag) {
//                                            checkFlag = true;
//                                            break;
//                                        }
//                                    }
//                                    if (checkFlag) {
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    if (checkFlag) {
//                        returnFlag = true;
//                    } else {
//                        if (CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
//                                functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
//                            if (OpratingContainerStaticMethod.checkGenerateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
//                                    pathFind, functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute(), functionAddInternalMethodOperatingContainerParam
//                            )) {
//                                returnFlag = true;
//                            } else {
//                                returnFlag = false;
//                            }
//                        }
//                        if (CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
//                                functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
//                            if (OpratingContainerStaticMethod.checkCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
//                                    null, functionAddInternalMethodOperatingContainerParam.getCommandAddRelatedAttribute(),
//                                    functionAddInternalMethodOperatingContainerParam
//                            )) {
//                                returnFlag = true;
//                            } else {
//                                returnFlag = false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return returnFlag;
//    }


//    看看功能拓展的生成代码那里究竟有没有问题，按理说，它从第一个命令组件拿到的参数是用来找到对应合适的标记（对应的初始化等）的，
//    它代码模型里面的那个标签是找里面对应添加的那句代码模型里面的标签的

    /**
     * 设置组件参数
     *
     * @param controlStatementFormat 生成面板组件和文字的参数
     * @return
     */
    @Override
    protected CommandContainerComponentParam getContainerComponentParam(String controlStatementFormat) {

        CommandContainerComponentParam codeGenerationalOpratingContainerParam = super.getContainerComponentParam(controlStatementFormat);
        codeGenerationalOpratingContainerParam
                .setFormatControlPane(functionAddInternalMethodOperatingContainerParam.getFormatControlPane());
        codeGenerationalOpratingContainerParam.setThisOpratingContainer(this);
        codeGenerationalOpratingContainerParam
                .setCodeControlPane(this.functionAddInternalMethodOperatingContainerParam.getCodeControlPane());
        codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);

        codeGenerationalOpratingContainerParam.setModule(this.functionAddInternalMethodOperatingContainerParam.getModule());
        codeGenerationalOpratingContainerParam.setModuleInfo(this.functionAddInternalMethodOperatingContainerParam.getModuleInfo());

        codeGenerationalOpratingContainerParam.setCodeSerialNumber(codeSerialNumber);
        codeGenerationalOpratingContainerParam.setControlStatementFormat(controlStatementFormat);
        if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
            if (MarkElementName.FUNCTION_MARK.equals(pathFind.getMarkType())
                    || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(pathFind.getMarkType())) {// 如果是写业务逻辑代码，而且是直接写在方法面板的话，把本次的命令操作组件设为首个命令操作组件，供后面的命令操作组件溯源从中获取信息知道代码应该写在哪
                if (pathFind.whetherToOperateAddDelOrNotForMark() == true) {
                    //正常情况永远不会走这里
                    codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(this);
                    codeGenerationalOpratingContainerParam.setParentOpratingContainer(null);
                } else {// 是逻辑面板的功能拓展组件，里面的功能拓展组件，想办法拿到首组件
                    FunctionAddCodeControlPane functionAddCodeControlPane = (FunctionAddCodeControlPane) functionAddInternalMethodOperatingContainerParam
                            .getCodeControlPane();
                    GeneralContainerComponentParam commandOpratingContainerParam = functionAddCodeControlPane
                            .getFunctionAddOperatingContainerParam();
                    codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(
                            commandOpratingContainerParam.getFirstCommandOpratingContainer());
                    codeGenerationalOpratingContainerParam.setParentOpratingContainer(commandOpratingContainerParam.getParentOpratingContainer());
                }
            } else {
                FunctionAddCodeControlPane functionAddCodeControlPane = (FunctionAddCodeControlPane) functionAddInternalMethodOperatingContainerParam
                        .getCodeControlPane();
                GeneralContainerComponentParam commandOpratingContainerParam = functionAddCodeControlPane
                        .getFunctionAddOperatingContainerParam();
                codeGenerationalOpratingContainerParam
                        .setFirstCommandOpratingContainer(commandOpratingContainerParam.getFirstCommandOpratingContainer());
                codeGenerationalOpratingContainerParam.setParentOpratingContainer(commandOpratingContainerParam.getParentOpratingContainer());

            }
        } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
            FunctionAddCodeControlPane functionAddCodeControlPane = (FunctionAddCodeControlPane) functionAddInternalMethodOperatingContainerParam
                    .getCodeControlPane();
            GeneralContainerComponentParam commandOpratingContainerParam = functionAddCodeControlPane
                    .getFunctionAddOperatingContainerParam();
            codeGenerationalOpratingContainerParam
                    .setFormatContainer(commandOpratingContainerParam.getFormatContainer());
            codeGenerationalOpratingContainerParam.setParentOpratingContainer(commandOpratingContainerParam.getParentOpratingContainer());
        }
        return codeGenerationalOpratingContainerParam;
    }

    @Override
    public File getImageRootPath() {
        File file = null;
        if (functionAddInternalMethodOperatingContainerParam.getParentOpratingContainer() != null) {
            file = functionAddInternalMethodOperatingContainerParam.getParentOpratingContainer().getImageRootPath();
        }
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = null;
        if (functionAddInternalMethodOperatingContainerParam.getParentOpratingContainer() != null) {
            file = functionAddInternalMethodOperatingContainerParam.getParentOpratingContainer().getFileSelectorRootPath();
        }
        return file;
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        FunctionAddInternalOpratingContainerModel theModel = (FunctionAddInternalOpratingContainerModel) model;
        super.setParam(theModel);
        theModel.setMethodAddStorageFormat(this.methodAddStorageFormat);
    }

    @Override
    public FunctionAddInternalOpratingContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        FunctionAddInternalOpratingContainerModel model = new FunctionAddInternalOpratingContainerModel();
        setParam(model);
        return model;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return functionAddInternalMethodOperatingContainerParam.getFunctionAddParam().getPaneType();
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return functionAddInternalMethodOperatingContainerParam.getFunctionAddParam().getClassName();
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return functionAddInternalMethodOperatingContainerParam.getFunctionAddParam().getModuleName();
    }

    @Override
    public String getModuleId() {
        return functionAddInternalMethodOperatingContainerParam.getFunctionAddParam().getModuleId();
    }

    @Override
    public int getOrdinalInUserDB() {
        return methodAddStorageFormat.getOrdinal();
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return functionAddInternalMethodOperatingContainerParam.getFunctionAddParam().getAdditionalSerialNumber();
    }

    @Override
    protected CodeShowPane getDeafaultCodePane() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractCommandOpratingContainer getFirstCommandOpratingContainer() {
        if (functionAddInternalMethodOperatingContainerParam != null) {
            return functionAddInternalMethodOperatingContainerParam.getFirstCommandOpratingContainer();
        }
        return null;
    }

    @Override
    public AbstractCodeControlPane getCodeControlPane() {
        return this.functionAddInternalMethodOperatingContainerParam.getCodeControlPane();
    }

    @Override
    public AbstractFormatContainer getFormatContainer() {
        if (functionAddInternalMethodOperatingContainerParam != null) {
            return functionAddInternalMethodOperatingContainerParam.getFormatContainer();
        }
        return null;
    }

    @Override
    public OpratingContainerInterface getParentOpratingContainer() {
        return this.functionAddInternalMethodOperatingContainerParam.getParentOpratingContainer();
    }

    @Override
    public BaseMarkElement getCorrespondingMarkElement() {
        //程序如果没有错误的话不会向本类调用此方法
        return null;
    }

}