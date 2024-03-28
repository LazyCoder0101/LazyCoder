package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.service.vo.meta.FunctionMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodePaneModelGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FunctionOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.FunctionOpratingContainerModel;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;


public class FunctionOpratingContainer extends AbstractCommandOpratingContainer {

    /**
     *
     */
    private static final long serialVersionUID = -5875801546933534524L;

    private FunctionOperatingContainerParam functionOperatingContainerParam;

    @Getter
    private FunctionMetaModel metaModel = null;

    private String moduleId = null;

    private FunctionOpratingContainer(boolean expanded) {
        super();
        // TODO Auto-generated constructor stub
        init(expanded, true, null);
        setAppropriateSize(PROPOTION);
    }

    /**
     * 测试用
     *
     * @param functionOperatingContainerParam
     */
    public FunctionOpratingContainer(FunctionOperatingContainerParam functionOperatingContainerParam,
                                     FunctionMetaModel functionMetaModel) {
        // TODO Auto-generated constructor stub
        super();
        this.moduleId = functionOperatingContainerParam.getModule().getModuleId();
        functionOperatingContainerParam.setThisOpratingContainer(this);
        this.functionOperatingContainerParam = functionOperatingContainerParam;
        this.metaModel = functionMetaModel;

        if (functionMetaModel.getOperatingModel().getHiddenState() == GeneralCommandOperatingModel.TRUE_) {
            hiddenState = true;
        } else {
            hiddenState = false;
        }
        pathFind = new PathFind(MarkElementName.FUNCTION_MARK, PathFind.COMMAND_TYPE);
        codeSerialNumber = 1;
        init(true, true, functionOperatingContainerParam.getCodeControlPane());
        // 写默认内容
        CommandContainerComponentParam deafaultCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                functionMetaModel.getOperatingModel().getDefaultControlStatementFormat());
        deafaultOperatingPane.generateOperationalContent(deafaultCodeGenerationalOpratingContainerParam);

        if (hiddenState == true) {
            CommandContainerComponentParam hiddenCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                    functionMetaModel.getOperatingModel().getHiddenControlStatementFormat());
            hiddenOperatingPane.generateOperationalContent(hiddenCodeGenerationalOpratingContainerParam);
        }
        setAppropriateSize(PROPOTION);
    }

    /**
     * 新建
     *
     * @param functionOperatingContainerParam
     * @param expanded
     */
    public FunctionOpratingContainer(FunctionOperatingContainerParam functionOperatingContainerParam, FunctionMetaModel metaModel,
                                     boolean expanded) {
        super();

        this.moduleId = functionOperatingContainerParam.getModule().getModuleId();
        functionOperatingContainerParam.setThisOpratingContainer(this);
        this.functionOperatingContainerParam = functionOperatingContainerParam;
        this.metaModel = metaModel;

        if (GeneralCommandOperatingModel.TRUE_ == this.metaModel.getOperatingModel().getHiddenState()) {
            hiddenState = true;
        } else {
            hiddenState = false;
        }

        init(expanded, true, functionOperatingContainerParam.getCodeControlPane());
        generateCodeAndOpratingContainer();
        setAppropriateSize(PROPOTION);
    }

    /**
     * 还原
     *
     * @param functionOperatingContainerParam
     * @param model
     * @param expanded
     */
    public FunctionOpratingContainer(FunctionOperatingContainerParam functionOperatingContainerParam,
                                     FunctionOpratingContainerModel model, boolean expanded) {
        super();

        functionOperatingContainerParam.setThisOpratingContainer(this);
        this.functionOperatingContainerParam = functionOperatingContainerParam;

        this.moduleId = model.getModuleId();
        this.metaModel = model.getMetaModel();

        restoreInit(model);
        init(expanded, model.isCanBeDelOrNot(), functionOperatingContainerParam.getCodeControlPane());
        CommandContainerComponentParam commandContainerComponentParam = getContainerComponentParam(null);
        restorePaneContent(model, commandContainerComponentParam);
        setAppropriateSize(PROPOTION);
    }

    @Override
    protected void restorePaneContent(AbstractCommandOperatingContainerModel commandOperatingContainerModel, GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        super.restorePaneContent(commandOperatingContainerModel, codeGenerationalOpratingContainerParam);
        setThisToolTipText(metaModel.getOperatingModel().getShowText());
    }

    /**
     * 生成代码和组件
     */
    private void generateCodeAndOpratingContainer() {
        codeSerialNumber = CodeIDGenerator.generateCodeSerialNumber();
        pathFind = functionOperatingContainerParam.getCodeControlPane().getPathFind();

        if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
            if (pathFind.whetherToOperateAddDelOrNotForMark() == true) {// 直接在业务逻辑面板上更改
                this.functionOperatingContainerParam.setFirstCommandOpratingContainer(this);
            }
//        } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
        }

        if (metaModel != null) {
            setThisToolTipText(metaModel.getOperatingModel().getShowText());
            generateCode();
            // 写默认内容
            CommandContainerComponentParam deafaultCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                    metaModel.getOperatingModel().getDefaultControlStatementFormat());
            deafaultOperatingPane.generateOperationalContent(deafaultCodeGenerationalOpratingContainerParam);

            if (hiddenState == true) {
                CommandContainerComponentParam hiddenCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                        metaModel.getOperatingModel().getHiddenControlStatementFormat());
                hiddenOperatingPane.generateOperationalContent(hiddenCodeGenerationalOpratingContainerParam);
            }
        }
    }

    private void generateCode() {
        if (pathFind != null) {
            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;

            CommandAddRelatedAttribute commandAddRelatedAttribute;
            int situation = 0;
            if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
                    if (MarkElementName.FUNCTION_MARK.equals(pathFind.getMarkType()) ||
                            MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(pathFind.getMarkType())) {
                        situation = 2;//直接添加到业务逻辑面板
//                    } else {
//                        situation = 4;//初始化、模块设置、主函数设置、其他函数设置一类
                    }
                } else {
                    situation = 3;//添加到功能的功能拓展组件里面
                }
            } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
                if (pathFind.whetherToOperateForFormat()) {
                    situation = 1;//直接添加到格式
                } else {
                    situation = 3;//添加到功能的功能拓展组件里面
                }
            }

            ModuleInfo moduleInfo = this.functionOperatingContainerParam.getModuleInfo() == null ? SysService.MODULE_INFO_SERVICE.getModuleInfo(this.moduleId) : this.functionOperatingContainerParam.getModuleInfo();
            Module module = this.functionOperatingContainerParam.getModule() == null ? SysService.MODULE_SERVICE.getModuleById(this.moduleId) : this.functionOperatingContainerParam.getModule();

            ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation;
            CodeListLocation codeListLocation;
            boolean flag;
            switch (situation) {
                case 1://直接添加到格式
                    functionAddComponentPlaceCodeLocationInformation =
                            this.functionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                        CodeShowPane codeShowPane;
                        for (FunctionCodeModel codeModel : this.metaModel.getCodeModelList()) {
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

                                        commandAddRelatedAttribute = this.functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                        if (commandAddRelatedAttribute != null) {
                                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                            flag = OpratingContainerStaticMethod.generateCode(this,
                                                    codeShowPane, commandAddRelatedAttribute,
                                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),inserNewLineOrNot,
                                                    codeResponseParam.getCodeListLocationInfoList());
                                            if (flag) {
                                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);

                                                if (null != setPropertyTempList||setPropertyTempList.size()>0) {
                                                    OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                                            setPropertyTempList, codeShowPane, moduleInfo, module, this.getModuleId()
                                                    );
                                                }
                                                OpratingContainerStaticMethod.addCommandContainerImportCodes(codeShowPane, codeModel.getImportCodeParam());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 2://直接添加到业务逻辑面板
                    ArrayList<CodeListLocation> codeListLocationArrayList;
                    for (FunctionCodeModel codeModel : this.metaModel.getCodeModelList()) {
                        boolean inserNewLineOrNot = true;
                        if (null != codeModel.getCodeUsePropertyParam()) {
                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                            if (GeneralCommandCodeModel.contant(
                                    setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                                inserNewLineOrNot = false;
                            }
                        }
                        codeListLocationArrayList = new ArrayList<>();
                        commandAddRelatedAttribute = this.functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                        if (commandAddRelatedAttribute != null) {
                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                            codeListLocation = new CodeListLocation();
                            FunctionMarkElement markElement = new FunctionMarkElement();
                            markElement.setCodeLabelId(codeModel.getCodeLabelId());
                            codeListLocation.addCodePathFindForMark(markElement, pathFind);
                            codeListLocationArrayList.add(codeListLocation);

                            flag = OpratingContainerStaticMethod.generateCode(this,
                                    this.functionOperatingContainerParam.getFormatControlPane().getDefaultPane(), commandAddRelatedAttribute,
                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),inserNewLineOrNot,
                                    codeListLocationArrayList);
                            if (flag) {
                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(
                                        this.functionOperatingContainerParam.getFormatControlPane().getDefaultPane());

                                if (null != setPropertyTempList||setPropertyTempList.size()>0) {
                                    OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                            setPropertyTempList, this.functionOperatingContainerParam.getFormatControlPane().getDefaultPane()
                                            , moduleInfo, module, this.getModuleId()
                                    );
                                }
                                OpratingContainerStaticMethod.addCommandContainerImportCodes(this.functionOperatingContainerParam.getFormatControlPane().getDefaultPane(), codeModel.getImportCodeParam());
                            }
                        }
                    }
                    break;
                case 3://添加到功能的功能拓展组件里面
                    functionAddComponentPlaceCodeLocationInformation = this.functionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                        CodeShowPane codeShowPane;
                        for (FunctionCodeModel codeModel : this.metaModel.getCodeModelList()) {
                            boolean codeInserFlag = false;
                            boolean inserNewLineOrNot = true;
                            if (null != codeModel.getCodeUsePropertyParam()) {
                                setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                                if (GeneralCommandCodeModel.contant(
                                        setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                                    inserNewLineOrNot = false;
                                }
                            }

                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam : functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                                    if (codeShowPane != null) {
                                        commandAddRelatedAttribute = this.functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                        if (commandAddRelatedAttribute != null) {
                                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                            flag = OpratingContainerStaticMethod.generateCode(this,
                                                    codeShowPane, commandAddRelatedAttribute,
                                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),inserNewLineOrNot,
                                                    codeResponseParam.getCodeListLocationInfoList());
                                            if (flag) {
                                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
                                                codeInserFlag = true;

                                                if (null != setPropertyTempList||setPropertyTempList.size()>0) {
                                                    OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                                            setPropertyTempList, codeShowPane
                                                            , moduleInfo, module, this.getModuleId()
                                                    );
                                                }
                                                OpratingContainerStaticMethod.addCommandContainerImportCodes(codeShowPane, codeModel.getImportCodeParam());
                                            }
                                        }
                                    }
                                }
                            }
                            if (codeInserFlag == false &&
                                    CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                                            this.functionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                                commandAddRelatedAttribute = this.functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                OpratingContainerStaticMethod.generateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                        this, codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getImportCodeParam(),
                                        commandAddRelatedAttribute, this.functionOperatingContainerParam,
                                        codeModel.getCodeUsePropertyParam(),inserNewLineOrNot,
                                        moduleInfo, module, this.getModuleId()
                                );
                            }
                            if (codeInserFlag == false &&
                                    CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                                            this.functionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                                commandAddRelatedAttribute = this.functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                OpratingContainerStaticMethod.generateCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                        this, codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getImportCodeParam(),
                                        commandAddRelatedAttribute,
                                        this.functionOperatingContainerParam,
                                        codeModel.getCodeUsePropertyParam(),inserNewLineOrNot,
                                        moduleInfo, module, this.getModuleId(),
                                        this
                                );
                            }
                        }
                    }
                    break;
//                case 4://初始化、模块设置、主函数设置、其他函数设置一类
//                    functionAddComponentPlaceCodeLocationInformation = this.functionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
//                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
//                        CodeShowPane codeShowPane;
//                        for (FunctionCodeModel codeModel : this.metaModel.getCodeModelList()) {
//                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam : functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
//                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
//                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
//                                    if (codeShowPane != null) {
//                                        commandAddRelatedAttribute = this.functionOperatingContainerParam.getCommandAddRelatedAttribute();
//                                        if (commandAddRelatedAttribute != null) {
//                                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                                            flag = OpratingContainerStaticMethod.generateCode(this,
//                                                    codeShowPane, commandAddRelatedAttribute,
//                                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
//                                                    codeResponseParam.getCodeListLocationInfoList());
//                                            if (flag == false) {
//                                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
//
//                                                if (null != codeModel.getCodeUsePropertyParam()) {
//                                                    setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
//                                                    OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
//                                                            setPropertyTempList,
//                                                            codeShowPane,
//                                                            moduleInfo, module, this.getModuleId()
//                                                    );
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    break;

            }
        }
    }


//    /**
//     * 处理代码属性的相关操作（命令）
//     *
//     * @param needFirstOpratingContainerParamList
//     * @param codeUsePropertyList                 根据 GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam()) 得到
//     */
//    private void handlesOperationsRelatedToCodePropertiesForCommand(ArrayList<NeedFirstOpratingContainerParam> needFirstOpratingContainerParamList, ArrayList<AbstractCodeUseProperty> codeUsePropertyList) {
//        inserNeedModuleImportCodeForCommand(needFirstOpratingContainerParamList, codeUsePropertyList);
//        inserThisModuleImportCodeForCommand(needFirstOpratingContainerParamList, codeUsePropertyList);
//    }

    /**
     * 插入本模块的代码
     */
//    private void inserThisModuleImportCodeForCommand(ArrayList<NeedFirstOpratingContainerParam> needFirstOpratingContainerParamList,
//                                                     ArrayList<AbstractCodeUseProperty> codeUsePropertyList) {
//        CodeShowPane codeShowPane;
//        for (NeedFirstOpratingContainerParam needFirstOpratingContainerParamTemp : needFirstOpratingContainerParamList) {
//            codeShowPane = needFirstOpratingContainerParamTemp.getTheCodePane();
//            if (codeShowPane != null) {
//                if (codeUsePropertyList.size() == 0) {
//                    addImportCodes(codeShowPane,
//                            this.functionOperatingContainerParam.getModuleInfo(),
//                            this.functionOperatingContainerParam.getModule(),
//                            getModuleId());
//                } else if (codeUsePropertyList.size() > 0) {
//                    if (!GeneralCommandCodeModel.contant(
//                            codeUsePropertyList, AbstractCodeUseProperty.NoNeedToInsertImportCodeType)
//                    ) {
//                        addImportCodes(codeShowPane,
//                                this.functionOperatingContainerParam.getModuleInfo(),
//                                this.functionOperatingContainerParam.getModule(),
//                                getModuleId());
//                    }
//                }
//            }
//        }
//    }


    /**
     * 查看这里是不是可以添加这个代码的本功能容器
     *
     * @param functionOperatingContainerParam
     * @param metaModel
     * @return
     */
    public static boolean checkWhetherItMatches(FunctionOperatingContainerParam functionOperatingContainerParam, FunctionMetaModel metaModel) {
        boolean returnFlag = false;

        PathFind containerPathFind = functionOperatingContainerParam.getCodeControlPane().getPathFind();
        if (containerPathFind != null) {
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            int situation = 0;
            if (PathFind.COMMAND_TYPE == containerPathFind.getMetaType()) {
                if (containerPathFind.whetherToOperateAddDelOrNotForMark()) {
                    if (MarkElementName.FUNCTION_MARK.equals(containerPathFind.getMarkType()) ||
                            MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(containerPathFind.getMarkType())) {
                        situation = 2;//直接添加到业务逻辑面板
//                    } else {
//                        situation = 4;//初始化、模块设置、主函数设置、其他函数设置、业务逻辑面板一类
                    }
                } else {
                    situation = 3;//添加到功能的功能拓展组件里面
                }
            } else if (PathFind.FORMAT_TYPE == containerPathFind.getMetaType()) {
                if (containerPathFind.whetherToOperateForFormat()) {
                    situation = 1;//直接添加到格式
                } else {
                    situation = 3;//添加到功能的功能拓展组件里面
                }
            }

            ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation;
            CodeListLocation codeListLocation;
            boolean flag, returnTempFlag = false;
            switch (situation) {
                case 1://直接添加到格式
                    functionAddComponentPlaceCodeLocationInformation =
                            functionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();

                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                        CodeShowPane codeShowPane;
                        for (FunctionCodeModel codeModel : metaModel.getCodeModelList()) {
                            returnTempFlag = false;
                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
                                    functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                                    if (codeShowPane != null) {
                                        for (CodeListLocation functionAddCodeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
                                            for (PathFind pathFindTemp : functionAddCodeListLocation.getCodePathFindList()) {
                                                commandAddRelatedAttribute = functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                                if (commandAddRelatedAttribute != null) {
                                                    commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                                    flag = OpratingContainerStaticMethod.checkGenerateCode(pathFindTemp, codeShowPane, commandAddRelatedAttribute,
                                                            functionAddCodeListLocation);
                                                    if (flag) {
                                                        returnTempFlag = true;
                                                        break;
                                                    }
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
                            if (returnTempFlag) {
                                returnFlag = true;
                            } else {//某一句代码找不到可以插入的位置，退出，标记无法在这里插入
                                returnFlag = false;
                                break;
                            }
                        }
                    }
                    break;
                case 2://直接添加到业务逻辑面板
                    returnTempFlag = true;
                    for (FunctionCodeModel codeModel : metaModel.getCodeModelList()) {
                        commandAddRelatedAttribute = functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                        if (commandAddRelatedAttribute != null) {
                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                            codeListLocation = new CodeListLocation();
                            FunctionMarkElement markElement = new FunctionMarkElement();
                            markElement.setCodeLabelId(codeModel.getCodeLabelId());
                            codeListLocation.addCodePathFindForMark(markElement, containerPathFind);

                            flag = OpratingContainerStaticMethod.checkGenerateCode(containerPathFind,
                                    functionOperatingContainerParam.getFormatControlPane().getDefaultPane()
                                    , commandAddRelatedAttribute,
                                    codeListLocation);
                            if (!flag) {//有一条代码找不到对应位置，标记无法插入，退出
                                returnTempFlag = false;
                                break;
                            }
                        }
                    }
                    if (returnTempFlag) {//能插入这句代码
                        returnFlag = true;
                    } else {//不能插入这句代码
                        returnFlag = false;
                    }
                    break;
                case 3://添加到功能的功能拓展组件里面
                    returnFlag = false;
                    functionAddComponentPlaceCodeLocationInformation =
                            functionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                        CodeShowPane codeShowPane;
                        for (FunctionCodeModel codeModel : metaModel.getCodeModelList()) {
                            returnTempFlag = false;
                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
                                    functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                                    if (codeShowPane != null) {
                                        for (CodeListLocation functionAddCodeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
                                            commandAddRelatedAttribute = functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
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
                                        functionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                                    if (OpratingContainerStaticMethod.checkGenerateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                            containerPathFind, functionOperatingContainerParam.getCommandAddRelatedAttribute(), functionOperatingContainerParam
                                    )) {
                                        returnFlag = true;
                                    } else {
                                        returnFlag = false;
                                        break;
                                    }
                                }

                                if (CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                                        functionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                                    if (OpratingContainerStaticMethod.checkCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                            null, functionOperatingContainerParam.getCommandAddRelatedAttribute(),
                                            functionOperatingContainerParam
                                    )) {
                                        returnFlag = true;
                                    } else {
                                        returnFlag = false;
                                        break;
                                    }
                                }
                            }

                            if (returnFlag==false){
                                break;
                            }

                        }
                    }
                    break;
//                case 4://初始化、模块设置、主函数设置、其他函数设置一类
//                    functionAddComponentPlaceCodeLocationInformation =
//                            functionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
//                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
//                        CodeShowPane codeShowPane;
//                        for (FunctionCodeModel codeModel : metaModel.getCodeModelList()) {
//                            returnTempFlag = false;
//                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
//                                    functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
//                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
//                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
//                                    if (codeShowPane != null) {
//                                        for (CodeListLocation functionAddCodeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
//                                            commandAddRelatedAttribute = functionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
//                                            if (commandAddRelatedAttribute != null) {
//                                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                                                flag = OpratingContainerStaticMethod.checkGenerateCode(containerPathFind, codeShowPane, commandAddRelatedAttribute,
//                                                        codeModel.getCodeFormatParam(), functionAddCodeListLocation);
//                                                if (flag) {
//                                                    returnTempFlag = true;
//                                                    break;
//                                                }
//                                            }
//                                        }
//                                        if (returnTempFlag) {//找到合适的位置，不需要再找
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            if (returnTempFlag) {//某一句代码找不到可以插入的位置，退出，标记无法在这里插入
//                                returnFlag = true;
//                            } else {
//                                returnFlag = false;
//                                break;
//                            }
//                        }
//                    }
//                    break;
            }
        }
        return returnFlag;
    }

    /**
     * 设置组件参数
     *
     * @param controlStatementFormat 生成面板组件和文字的参数
     * @return
     */
    @Override
    protected CommandContainerComponentParam getContainerComponentParam(String controlStatementFormat) {

        CommandContainerComponentParam codeGenerationalOpratingContainerParam = super.getContainerComponentParam(controlStatementFormat);
        codeGenerationalOpratingContainerParam.setThisOpratingContainer(this);
        codeGenerationalOpratingContainerParam
                .setCodeControlPane(this.functionOperatingContainerParam.getCodeControlPane());
        codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);

        codeGenerationalOpratingContainerParam.setModule(this.functionOperatingContainerParam.getModule());
        codeGenerationalOpratingContainerParam.setModuleInfo(this.functionOperatingContainerParam.getModuleInfo());

        codeGenerationalOpratingContainerParam.setCodeSerialNumber(codeSerialNumber);
        codeGenerationalOpratingContainerParam
                .setFormatControlPane(this.functionOperatingContainerParam.getFormatControlPane());
        codeGenerationalOpratingContainerParam.setControlStatementFormat(controlStatementFormat);

        if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
            if (MarkElementName.FUNCTION_MARK.equals(pathFind.getMarkType())
                    || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(pathFind.getMarkType())) {// 如果是写业务逻辑代码，而且是直接写在方法面板的话，把本次的方法操作组件设为首个命令操作组件，供后面的方法组件溯源从中获取信息知道代码应该写在哪
                if (pathFind.whetherToOperateAddDelOrNotForMark() == true) {
                    codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(this);
                    codeGenerationalOpratingContainerParam.setParentOpratingContainer(null);
                } else {// 是逻辑面板的方法组件，里面的方法组件，想办法拿到首组件
                    codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(
                            this.functionOperatingContainerParam.getFirstCommandOpratingContainer());
                    codeGenerationalOpratingContainerParam.setParentOpratingContainer(
                            this.functionOperatingContainerParam.getParentOpratingContainer());
                }
            } else {
                codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(
                        this.functionOperatingContainerParam.getFirstCommandOpratingContainer());
                codeGenerationalOpratingContainerParam.setParentOpratingContainer(
                        this.functionOperatingContainerParam.getParentOpratingContainer());
            }
        } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
            codeGenerationalOpratingContainerParam
                    .setFormatContainer(this.functionOperatingContainerParam.getFormatContainer());
            codeGenerationalOpratingContainerParam.setParentOpratingContainer(
                    this.functionOperatingContainerParam.getParentOpratingContainer());
        }
        return codeGenerationalOpratingContainerParam;
    }

    @Override
    protected CodeShowPane getDeafaultCodePane() {
        // TODO Auto-generated method stub
        return this.functionOperatingContainerParam.getFormatControlPane().getDefaultPane();
    }


    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        FunctionOpratingContainerModel theModel = (FunctionOpratingContainerModel) model;
        super.setParam(theModel);
        theModel.setModuleId(moduleId);
        theModel.setMetaModel(this.metaModel);
    }


    public void delModuleOpratingContainer(String moduleId) {
        // TODO Auto-generated method stub
        if (this.moduleId.equals(moduleId)) {
            delThis();
        } else {
            delModuleOpratingContainerFromComponent(moduleId);
        }
    }

    @Override
    public FunctionOpratingContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        FunctionOpratingContainerModel model = new FunctionOpratingContainerModel();
        setParam(model);
        return model;
    }

    @Override
    public File getImageRootPath() {
        File file = SourceGenerateFileStructure.getModulePictureFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleId);
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = SourceGenerateFileStructure.getModuleNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleId);
        return file;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return MarkElementName.FUNCTION_MARK;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return this.functionOperatingContainerParam.getModule().getClassName();
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return this.functionOperatingContainerParam.getModule().getModuleName();
    }

    @Override
    public int getOrdinalInUserDB() {
        return metaModel.getOperatingModel().getOrdinal();
    }

    @Override
    public String getModuleId() {
        return moduleId;
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public AbstractCommandOpratingContainer getFirstCommandOpratingContainer() {
        return functionOperatingContainerParam == null ? null : functionOperatingContainerParam.getFirstCommandOpratingContainer();
    }

    @Override
    public AbstractCodeControlPane getCodeControlPane() {
        return this.functionOperatingContainerParam.getCodeControlPane();
    }

    @Override
    public AbstractFormatContainer getFormatContainer() {
        return functionOperatingContainerParam == null ? null : functionOperatingContainerParam.getFormatContainer();
    }

    @Override
    public OpratingContainerInterface getParentOpratingContainer() {
        return this.functionOperatingContainerParam.getParentOpratingContainer();
    }

    @Override
    public FunctionMarkElement getCorrespondingMarkElement() {
        return new FunctionMarkElement();
    }

}
