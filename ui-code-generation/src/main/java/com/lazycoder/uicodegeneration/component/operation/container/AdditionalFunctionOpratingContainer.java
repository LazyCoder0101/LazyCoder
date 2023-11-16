package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.service.vo.meta.AdditionalFunctionMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodePaneModelGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalFunctionOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalFunctionOpratingContainerModel;
import java.io.File;
import java.util.ArrayList;


public class AdditionalFunctionOpratingContainer extends AbstractCommandOpratingContainer {

    /**
     *
     */
    private static final long serialVersionUID = -4805544470131407475L;

    private int additionalSerialNumber = 0;

    private AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam;

    private AdditionalFunctionMetaModel metaModel = null;

    private AdditionalFunctionOpratingContainer(boolean expanded) {
        super();
        // TODO Auto-generated constructor stub
        init(expanded, true, null);
    }

    /**
     * 新建
     *
     * @param additionalFunctionOperatingContainerParam
     * @param expanded
     */
    public AdditionalFunctionOpratingContainer(
            AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam, AdditionalFunctionMetaModel metaModel, boolean expanded) {
        super();
        this.additionalSerialNumber = additionalFunctionOperatingContainerParam.getFeatureSelectionModel().getAdditionalSerialNumber();
        additionalFunctionOperatingContainerParam.setThisOpratingContainer(this);
        this.additionalFunctionOperatingContainerParam = additionalFunctionOperatingContainerParam;
        this.metaModel = metaModel;

        if (GeneralCommandOperatingModel.TRUE_ == metaModel.getOperatingModel().getHiddenState()) {
            hiddenState = true;
        } else {
            hiddenState = false;
        }
        init(expanded, true, additionalFunctionOperatingContainerParam.getCodeControlPane());

        generateCodeAndOpratingContainer();
        setAppropriateSize(PROPOTION);

        additionalFunctionOperatingContainerParam.setFeatureSelectionModel(null);
    }

    /**
     * 还原
     *
     * @param additionalFunctionOperatingContainerParam
     * @param model
     * @param expanded
     */
    public AdditionalFunctionOpratingContainer(
            AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam,
            AdditionalFunctionOpratingContainerModel model, boolean expanded) {
        super();
        this.additionalSerialNumber = model.getAdditionalSerialNumber();
        this.metaModel = model.getMetaModel();
        additionalFunctionOperatingContainerParam.setThisOpratingContainer(this);
        this.additionalFunctionOperatingContainerParam = additionalFunctionOperatingContainerParam;

        restoreInit(model);
        init(expanded, model.isCanBeDelOrNot(), additionalFunctionOperatingContainerParam.getCodeControlPane());
        CommandContainerComponentParam commandContainerComponentParam = getContainerComponentParam(null);
        restorePaneContent(model, commandContainerComponentParam);
        setAppropriateSize(PROPOTION);
    }

    /**
     * 生成代码和组件
     */
    private void generateCodeAndOpratingContainer() {
        codeSerialNumber = CodeIDGenerator.generateCodeSerialNumber();
        pathFind = this.additionalFunctionOperatingContainerParam.getCodeControlPane().getPathFind();

        if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {

            if (pathFind.whetherToOperateAddDelOrNotForMark() == true) {// 直接在业务逻辑面板上更改
                this.additionalFunctionOperatingContainerParam.setFirstCommandOpratingContainer(this);
            }
            //       } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {

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

    //    private void generateCode() {
//        if (pathFind != null) {
//            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
//            CommandAddRelatedAttribute commandAddRelatedAttribute;
//            CodeListLocation codeListLocation;
//            AdditionalFunctionMarkElement markElement;
//            String pathParam;
//            CodeShowPane codeShowPane;
//            boolean flag;
//
//            for (AdditionalFunctionCodeModel codeModel : this.metaModel.getCodeModelList()) {
//
//                commandAddRelatedAttribute = new CommandAddRelatedAttribute();
//                commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
//                commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
//                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                codeListLocation = new CodeListLocation();
//                markElement = new AdditionalFunctionMarkElement();
//                markElement.setModuleId(metaModel.getOperatingModel().getModuleId());
//                markElement.setClassificationSerial(codeModel.getTypeSerialNumber());
//                markElement.setOperatingSerialNumber(codeModel.getOrdinal());
//                markElement.setCodeNumber(codeModel.getCodeOrdinal());
//                markElement.setCodeLabelId(codeModel.getCodeLabelId());
//                codeListLocation.addCodePathFindForMark(markElement, pathFind);
//
//                pathParam = codeModel.getPathParam();
//                codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, this.moduleTypeContainerParam.getFormatControlPane());
//                if (codeShowPane != null) {
//                    flag = OpratingContainerStaticMethod.generateCode(this,
//                            codeShowPane, commandAddRelatedAttribute,
//                            codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
//                            codeListLocation);
//                    if (flag) {
//                        CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
//
//                        if (null != codeModel.getCodeUsePropertyParam()) {
//                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
//                            OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
//                                    setPropertyTempList, codeShowPane, moduleInfo, module, this.getModuleId()
//                            );
//                        }
//                    }
//                }
//            }
//        }
//    }
    private void generateCode() {
        if (pathFind != null) {
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
            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
            ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation;
            CodeListLocation codeListLocation;
            boolean flag;
            switch (situation) {
                case 1://直接添加到格式
                    functionAddComponentPlaceCodeLocationInformation =
                            this.additionalFunctionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                        CodeShowPane codeShowPane;
                        for (AdditionalFunctionCodeModel codeModel : this.metaModel.getCodeModelList()) {
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
                                        commandAddRelatedAttribute = this.additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                        if (commandAddRelatedAttribute != null) {
                                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                            flag = OpratingContainerStaticMethod.generateCode(this,
                                                    codeShowPane, commandAddRelatedAttribute,
                                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
                                                    inserNewLineOrNot,
                                                    codeResponseParam.getCodeListLocationInfoList());
                                            if (flag) {
                                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
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
                    FunctionMarkElement markElement;
                    for (AdditionalFunctionCodeModel codeModel : this.metaModel.getCodeModelList()) {
                        boolean inserNewLineOrNot = true;
                        if (null != codeModel.getCodeUsePropertyParam()) {
                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                            if (GeneralCommandCodeModel.contant(
                                    setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                                inserNewLineOrNot = false;
                            }
                        }
                        codeListLocationArrayList = new ArrayList<>();
                        commandAddRelatedAttribute = this.additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                        if (commandAddRelatedAttribute != null) {
                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                            codeListLocation = new CodeListLocation();
                            //AdditionalFunctionMarkElement markElement = new AdditionalFunctionMarkElement();
                            markElement = new FunctionMarkElement();//由于这个类型的功能也是放到业务逻辑标记的，采用该类
                            markElement.setCodeLabelId(codeModel.getCodeLabelId());
                            codeListLocation.addCodePathFindForMark(markElement, pathFind);
                            codeListLocationArrayList.add(codeListLocation);

                            OpratingContainerStaticMethod.generateCode(this,
                                    this.additionalFunctionOperatingContainerParam.getFormatControlPane().getDefaultPane(), commandAddRelatedAttribute,
                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
                                    inserNewLineOrNot,
                                    codeListLocationArrayList);
                        }
                    }
                    CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(
                            this.additionalFunctionOperatingContainerParam.getFormatControlPane().getDefaultPane());
                    break;
                case 3://添加到功能的功能拓展组件里面
                    functionAddComponentPlaceCodeLocationInformation = this.additionalFunctionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                        CodeShowPane codeShowPane;
                        for (AdditionalFunctionCodeModel codeModel : this.metaModel.getCodeModelList()) {
                            boolean inserNewLineOrNot = true;
                            if (null != codeModel.getCodeUsePropertyParam()) {
                                setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                                if (GeneralCommandCodeModel.contant(
                                        setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                                    inserNewLineOrNot = false;
                                }
                            }
                            boolean codeInserFlag = false;
                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam : functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                                    if (codeShowPane != null) {
                                        commandAddRelatedAttribute = this.additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                        if (commandAddRelatedAttribute != null) {
                                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                            flag = OpratingContainerStaticMethod.generateCode(this,
                                                    codeShowPane, commandAddRelatedAttribute,
                                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
                                                    inserNewLineOrNot,
                                                    codeResponseParam.getCodeListLocationInfoList());
                                            if (flag) {
                                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
                                                codeInserFlag = true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (codeInserFlag == false &&
                                    CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                                            this.additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                                commandAddRelatedAttribute = this.additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                OpratingContainerStaticMethod.generateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                        this, codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(),
                                        commandAddRelatedAttribute,
                                        this.additionalFunctionOperatingContainerParam,
                                        null, inserNewLineOrNot,null, null, null
                                );
                            }
                            if (codeInserFlag == false &&
                                    CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                                            this.additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                                commandAddRelatedAttribute = this.additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                                OpratingContainerStaticMethod.generateCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                        this, codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(),
                                        commandAddRelatedAttribute,
                                        this.additionalFunctionOperatingContainerParam,
                                        null, inserNewLineOrNot,
                                        null, null, null,this
                                );
                            }
                        }
                    }
                    break;
            }
        }
    }

//    private void generateCode() {
//        AddCodeEditParamForMark addCodeEditParamForMark;
//        ArrayList<NeedFirstOpratingContainerParam> needFirstOpratingContainerParamList;
//        CodeShowPane codeShowPane;
//
//        // 添加代码
//        if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
//            if (MarkElementName.FUNCTION_MARK.equals(pathFind.getMarkType())
//                    || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(pathFind.getMarkType())) {// 业务逻辑类型
//
//                if (pathFind.whetherToOperateAddDelOrNotForMark() == true) {// 直接在业务逻辑面板上添加方法
//                    needFirstOpratingContainerParamList = getNeedFirstOpratingContainerParamList();
//                    for (NeedFirstOpratingContainerParam needFirstOpratingContainerParamTemp : needFirstOpratingContainerParamList) {
//                        codeShowPane = needFirstOpratingContainerParamTemp.getTheCodePane();
//
//                        addCodeEditParamForMark = new AddCodeEditParamForMark();
//                        addCodeEditParamForMark.setCodeSerialNumber(codeSerialNumber);
//                        addCodeEditParamForMark.setTrulyPathFind(pathFind);
//
//                        addCodeEditParamForMark
//                                .setThanMarkElement(needFirstOpratingContainerParamTemp.getThanMarkElement());
//                        addCodeEditParamForMark.setCodeStatementParam(needFirstOpratingContainerParamTemp.getCodeStatementParam());
//                        boolean flag = codeShowPane
//                                .addCodeToMark(addCodeEditParamForMark);
//                        if (flag == true) {
//                            CodeGenerationFrameHolder.codeShowPanel
//                                    .setSelectedCodePane(codeShowPane);
//                        }
//                    }
//
//                } else {// 在业务逻辑的方法，里面的功能拓展组件上添加方法
//                    AbstractCommandOpratingContainer commandOpratingContainer = this.additionalFunctionOperatingContainerParam
//                            .getFirstCommandOpratingContainer();
//
//                    needFirstOpratingContainerParamList = commandOpratingContainer
//                            .getNeedFirstOpratingContainerParamList();
////                    for (NeedFirstOpratingContainerParam needFirstOpratingContainerParamTemp : needFirstOpratingContainerParamList) {
////                        codeShowPane = needFirstOpratingContainerParamTemp.getTheCodePane();
//
//                    ArrayList<AdditionalFunctionCodeModel> codeModelList = this.metaModel.getCodeModelList();
//                    if (codeModelList != null) {
////                                ArrayList<Integer> setPropertyTempList;
//                        for (AdditionalFunctionCodeModel codeModel : codeModelList) {
//                            for (NeedFirstOpratingContainerParam needFirstOpratingContainerParamTemp : needFirstOpratingContainerParamList) {
//                                codeShowPane = needFirstOpratingContainerParamTemp.getTheCodePane();
//
//
//                                addCodeEditParamForMark = new AddCodeEditParamForMark();
//                                addCodeEditParamForMark.setCodeSerialNumber(codeSerialNumber);
//                                addCodeEditParamForMark.setTrulyPathFind(pathFind);
//
//                                //获取到首个功能容器的对应写入代码的相关参数后，另外设置codeStatementParam和
//                                needFirstOpratingContainerParamTemp.setCodeStatementParam(codeModel.getCodeFormatParam());
////                                    if (codeModel.getCodeUsePropertyParam() == null) {
////                                        needFirstOpratingContainerParamTemp.setCodeUsePropertyList(new ArrayList<>());
////                                    } else {
////                                        setPropertyTempList = JSON.parseObject(codeModel.getCodeUsePropertyParam(), new TypeReference<ArrayList<Integer>>() {
////                                        });
////                                        needFirstOpratingContainerParamTemp.setCodeUsePropertyList(setPropertyTempList);
////                                    }
//                                if (null != codeModel.getCodeLabelId()) {
//                                    addCodeEditParamForMark.setAddToMethodAddComponent(true);
//                                    addCodeEditParamForMark.setCodeLabelId(codeModel.getCodeLabelId());
//                                }
//                                addCodeEditParamForMark.setCodeStatementParam(needFirstOpratingContainerParamTemp.getCodeStatementParam());
//                                addCodeEditParamForMark
//                                        .setThanMarkElement(needFirstOpratingContainerParamTemp.getThanMarkElement());
//
//                                boolean flag = codeShowPane
//                                        .addCodeToMark(addCodeEditParamForMark);
//                                if (flag == true) {
//                                    CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {//添加在初始化、设置、必填模板设置、可选模板设置
//                ArrayList<AdditionalFunctionCodeModel> codeModelList = this.metaModel.getCodeModelList();
//
//                AbstractCommandOpratingContainer commandOpratingContainer = this.additionalFunctionOperatingContainerParam
//                        .getFirstCommandOpratingContainer();
//
//                if (commandOpratingContainer != null) {
//                    needFirstOpratingContainerParamList = commandOpratingContainer.getNeedFirstOpratingContainerParamList();
//
//                    for (NeedFirstOpratingContainerParam needFirstOpratingContainerParamTemp : needFirstOpratingContainerParamList) {
//                        codeShowPane = needFirstOpratingContainerParamTemp.getTheCodePane();
//
//                        if (codeModelList != null) {
////                                ArrayList<Integer> setPropertyTempList;
//                            for (AdditionalFunctionCodeModel codeModel : codeModelList) {
//                                addCodeEditParamForMark = new AddCodeEditParamForMark();
//                                addCodeEditParamForMark.setCodeSerialNumber(codeSerialNumber);
//                                addCodeEditParamForMark.setTrulyPathFind(pathFind);
//
//                                //获取到首个功能容器的对应写入代码的相关参数后，另外设置codeStatementParam和
////                                    needFirstOpratingContainerParamTemp.setCodeStatementParam(codeModel.getCodeFormatParam());
////                                    if (codeModel.getCodeUsePropertyParam() == null) {
////                                        needFirstOpratingContainerParamTemp.setCodeUsePropertyList(new ArrayList<>());
////                                    } else {
////                                        setPropertyTempList = JSON.parseObject(codeModel.getCodeUsePropertyParam(), new TypeReference<ArrayList<Integer>>() {
////                                        });
////                                        needFirstOpratingContainerParamTemp.setCodeUsePropertyList(setPropertyTempList);
////                                    }
//                                if (null != codeModel.getCodeLabelId()) {
//                                    addCodeEditParamForMark.setAddToMethodAddComponent(true);
//                                    addCodeEditParamForMark.setCodeLabelId(codeModel.getCodeLabelId());
//                                }
//                                addCodeEditParamForMark.setCodeStatementParam(codeModel.getCodeFormatParam());
//                                addCodeEditParamForMark
//                                        .setThanMarkElement(needFirstOpratingContainerParamTemp.getThanMarkElement());
//
//                                boolean flag = codeShowPane
//                                        .addCodeToMark(addCodeEditParamForMark);
//                                if (flag == true) {
//                                    CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
//            AbstractFormatContainer formatContainer = this.additionalFunctionOperatingContainerParam
//                    .getFormatContainer();
//            if (formatContainer != null) {
//                ArrayList<AdditionalFunctionCodeModel> codeModelList = this.metaModel.getCodeModelList();
//                if (codeModelList != null) {
//                    for (AdditionalFunctionCodeModel codeModel : codeModelList) {
//                        AddCodeEditParamForFormat addCodeEditParam = new AddCodeEditParamForFormat();
//                        addCodeEditParam.setCodeSerialNumber(codeSerialNumber);
//                        addCodeEditParam.setTrulyPathFind(pathFind);
//
//                        addCodeEditParam.setCodeStatementParam(codeModel.getCodeFormatParam());
//                        if (null != codeModel.getCodeLabelId()) {
//                            addCodeEditParam.setCodeLabelId(codeModel.getCodeLabelId());
//                            addCodeEditParam.setAddToMethodAddComponent(true);
//                        }
//                        formatContainer.addCode(addCodeEditParam);
//                    }
//                }
//            }
//        }
//    }

    @Override
    protected void restorePaneContent(AbstractCommandOperatingContainerModel commandOperatingContainerModel, GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        super.restorePaneContent(commandOperatingContainerModel, codeGenerationalOpratingContainerParam);
        setThisToolTipText(metaModel.getOperatingModel().getShowText());
    }

    /**
     * 查看这里是不是可以添加这个代码的本功能容器
     *
     * @param additionalFunctionOperatingContainerParam
     * @param metaModel
     * @return
     */
    public static boolean checkWhetherItMatches(AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam, AdditionalFunctionMetaModel metaModel) {
        boolean returnFlag = false;

        PathFind containerPathFind = additionalFunctionOperatingContainerParam.getCodeControlPane().getPathFind();
        if (containerPathFind != null) {
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            int situation = 0;
            if (PathFind.COMMAND_TYPE == containerPathFind.getMetaType()) {
                if (containerPathFind.whetherToOperateAddDelOrNotForMark()) {
                    if (MarkElementName.FUNCTION_MARK.equals(containerPathFind.getMarkType()) ||
                            MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(containerPathFind.getMarkType())) {
                        situation = 2;//直接添加到业务逻辑面板
                    }
                } else {
                    situation = 3;//添加到功能的功能拓展组件里面
                }
            } else if (PathFind.FORMAT_TYPE == containerPathFind.getMetaType()) {
                if (containerPathFind.whetherToOperateForFormat()) {
                    situation = 1;//直接添加到格式
                } else {
                    situation = 3;//添加到功能功能拓展的功能拓展组件里面
                }
            }

            ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation;
            CodeListLocation codeListLocation;
            boolean flag, returnTempFlag = false;
            switch (situation) {
                case 1://直接添加到格式
                    functionAddComponentPlaceCodeLocationInformation =
                            additionalFunctionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();

                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                        CodeShowPane codeShowPane;
                        for (AdditionalFunctionCodeModel codeModel : metaModel.getCodeModelList()) {
                            returnTempFlag = false;
                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
                                    functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                                    if (codeShowPane != null) {
                                        for (CodeListLocation functionAddCodeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
                                            for (PathFind pathFindTemp : functionAddCodeListLocation.getCodePathFindList()) {
                                                commandAddRelatedAttribute = additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
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
                    for (AdditionalFunctionCodeModel codeModel : metaModel.getCodeModelList()) {
                        commandAddRelatedAttribute = additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
                        if (commandAddRelatedAttribute != null) {
                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                            codeListLocation = new CodeListLocation();
                            FunctionMarkElement markElement = new FunctionMarkElement();
                            markElement.setCodeLabelId(codeModel.getCodeLabelId());
                            codeListLocation.addCodePathFindForMark(markElement, containerPathFind);

                            flag = OpratingContainerStaticMethod.checkGenerateCode(containerPathFind,
                                    additionalFunctionOperatingContainerParam.getFormatControlPane().getDefaultPane()
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
                    functionAddComponentPlaceCodeLocationInformation =
                            additionalFunctionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
                        CodeShowPane codeShowPane;
                        for (AdditionalFunctionCodeModel codeModel : metaModel.getCodeModelList()) {
                            returnTempFlag = false;
                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
                                    functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                                    if (codeShowPane != null) {
                                        for (CodeListLocation functionAddCodeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
                                            commandAddRelatedAttribute = additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
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
                                        additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                                    if (OpratingContainerStaticMethod.checkGenerateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                            containerPathFind, additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute(), additionalFunctionOperatingContainerParam
                                    )) {
                                        returnFlag = true;
                                    } else {
                                        returnFlag = false;
                                        break;
                                    }
                                }

                                if (CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                                        additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
                                    if (OpratingContainerStaticMethod.checkCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                            null, additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute(),
                                            additionalFunctionOperatingContainerParam
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
            }
        }
        return returnFlag;
    }
//    public static boolean checkWhetherItMatches(AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam,
//                                                AdditionalFunctionMetaModel metaModel) {
//        boolean returnFlag = false;
//
//        PathFind pathFind = additionalFunctionOperatingContainerParam.getCodeControlPane().getPathFind();
//        if (pathFind != null) {
//
//            CommandAddRelatedAttribute commandAddRelatedAttribute;
//            int situation = 0;
//            if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
//                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
//                    if (MarkElementName.FUNCTION_MARK.equals(pathFind.getMarkType()) ||
//                            MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(pathFind.getMarkType())) {
//                        situation = 2;//直接添加到业务逻辑面板
////                    } else {
////                        situation = 4;//初始化、模块设置、主函数设置、其他函数设置、业务逻辑面板一类
//                    }
//                } else {
//                    situation = 3;//添加到功能的功能拓展组件里面
//                }
//            } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
//                if (pathFind.whetherToOperateForFormat()) {
//                    situation = 1;//直接添加到格式
//                } else {
//                    situation = 3;//添加到功能的功能拓展组件里面
//                }
//            }
//
//            ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation;
//            CodeListLocation codeListLocation;
//            boolean flag, returnTempFlag = false;
//            switch (situation) {
//                case 1://直接添加到格式
//                    functionAddComponentPlaceCodeLocationInformation =
//                            additionalFunctionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
//                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展添加组件的代码所在位置
//                        CodeShowPane codeShowPane;
//                        for (AdditionalFunctionCodeModel codeModel : metaModel.getCodeModelList()) {
//                            returnTempFlag = false;
//
//                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam :
//                                    functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
//                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
//                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
//                                    if (codeShowPane != null) {
//                                        for (CodeListLocation functionAddCodeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
//                                            commandAddRelatedAttribute = additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
//                                            if (commandAddRelatedAttribute != null) {
//                                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                                                flag = OpratingContainerStaticMethod.checkGenerateCode(pathFind, codeShowPane, commandAddRelatedAttribute,
//                                                        codeModel.getCodeFormatParam(), functionAddCodeListLocation);
//                                                if (flag) {
//                                                    returnTempFlag = true;
//                                                    //checkFlag = false;
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
//                case 2://直接添加到业务逻辑面板
//                    returnTempFlag = true;
//                    for (AdditionalFunctionCodeModel codeModel : metaModel.getCodeModelList()) {
//                        commandAddRelatedAttribute = additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
//                        if (commandAddRelatedAttribute != null) {
//                            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                            codeListLocation = new CodeListLocation();
//                            //AdditionalFunctionMarkElement markElement = new AdditionalFunctionMarkElement();
//                            FunctionMarkElement markElement = new FunctionMarkElement();
//                            markElement.setCodeLabelId(codeModel.getCodeLabelId());
//                            codeListLocation.addCodePathFindForMark(markElement, pathFind);
//
//                            flag = OpratingContainerStaticMethod.checkGenerateCode(pathFind,
//                                    additionalFunctionOperatingContainerParam.getFormatControlPane().getDefaultPane()
//                                    , commandAddRelatedAttribute,
//                                    codeModel.getCodeFormatParam(), codeListLocation);
//                            if (!flag) {//有一条代码找不到对应位置，标记无法插入，退出
//                                returnTempFlag = false;
//                                break;
//                            }
//                        }
//                    }
//                    if (returnTempFlag) {//能插入这句代码
//                        returnFlag = true;
//                    } else {//不能插入这句代码
//                        returnFlag = false;
//                    }
//                    break;
//                case 3://添加到功能的功能拓展组件里面
//                    functionAddComponentPlaceCodeLocationInformation = additionalFunctionOperatingContainerParam.getFunctionAddComponentPlaceCodeLocationInformation();
//                    if (functionAddComponentPlaceCodeLocationInformation != null) {//直接添加到格式的话，必定是添加到格式里面的功能拓展组件上，先判断有没有拿到功能拓展组件的代码所在位置
//                        CodeShowPane codeShowPane;
//                        for (AdditionalFunctionCodeModel codeModel : metaModel.getCodeModelList()) {
//                            returnTempFlag = false;
//                            for (CodePaneModelGenerateCodeResponseParam codeResponseParam : functionAddComponentPlaceCodeLocationInformation.getCodeListLocationInfoList()) {
//                                if (codeResponseParam != null && codeResponseParam.getCodeFormatFlagParam() != null) {
//                                    codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
//                                    if (codeShowPane != null) {
//                                        for (CodeListLocation codeListLocationTemp : codeResponseParam.getCodeListLocationInfoList()) {
//                                            commandAddRelatedAttribute = additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().clone();
//                                            if (commandAddRelatedAttribute != null) {
//                                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                                                flag = OpratingContainerStaticMethod.checkGenerateCode(pathFind,
//                                                        additionalFunctionOperatingContainerParam.getFormatControlPane().getDefaultPane()
//                                                        , commandAddRelatedAttribute,
//                                                        codeModel.getCodeFormatParam(), codeListLocationTemp);
//                                                if (flag) {
//                                                    returnTempFlag = true;//有地方插入
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
//                                if (CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
//                                        additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
//                                    if (OpratingContainerStaticMethod.checkGenerateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
//                                            pathFind, additionalFunctionOperatingContainerParam.getCommandAddRelatedAttribute(), additionalFunctionOperatingContainerParam
//                                    )) {
//                                        returnFlag = true;
//                                    } else {
//                                        returnFlag = false;
//                                        break;
//                                    }
//                                } else {
//                                    returnFlag = false;
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                    break;
//            }
//        }
//        return returnFlag;
//    }

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
                .setCodeControlPane(this.additionalFunctionOperatingContainerParam.getCodeControlPane());
        codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);
        codeGenerationalOpratingContainerParam.setCodeSerialNumber(codeSerialNumber);
        codeGenerationalOpratingContainerParam
                .setFormatControlPane(this.additionalFunctionOperatingContainerParam.getFormatControlPane());
        codeGenerationalOpratingContainerParam.setControlStatementFormat(controlStatementFormat);

        if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
            if (MarkElementName.FUNCTION_MARK.equals(pathFind.getMarkType())
                    || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(pathFind.getMarkType())) {
                if (pathFind.whetherToOperateAddDelOrNotForMark() == true) {
                    codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(this);
                    codeGenerationalOpratingContainerParam.setParentOpratingContainer(null);
                } else {// 是逻辑面板的功能拓展组件，里面的功能拓展组件，想办法拿到首组件
                    codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(
                            this.additionalFunctionOperatingContainerParam.getFirstCommandOpratingContainer());
                    codeGenerationalOpratingContainerParam.setParentOpratingContainer(
                            this.additionalFunctionOperatingContainerParam.getParentOpratingContainer());
                }
            } else {
                codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(
                        this.additionalFunctionOperatingContainerParam.getFirstCommandOpratingContainer());
                codeGenerationalOpratingContainerParam.setParentOpratingContainer(
                        this.additionalFunctionOperatingContainerParam.getParentOpratingContainer());
            }
        } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
            codeGenerationalOpratingContainerParam
                    .setFormatContainer(this.additionalFunctionOperatingContainerParam.getFormatContainer());
            codeGenerationalOpratingContainerParam.setParentOpratingContainer(
                    this.additionalFunctionOperatingContainerParam.getParentOpratingContainer());
        }
        return codeGenerationalOpratingContainerParam;
    }

    @Override
    public int getOrdinalInUserDB() {
        return metaModel.getOperatingModel().getOrdinal();
    }

    @Override
    protected CodeShowPane getDeafaultCodePane() {
        // TODO Auto-generated method stub
        return this.additionalFunctionOperatingContainerParam.getFormatControlPane().getDefaultPane();
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        AdditionalFunctionOpratingContainerModel additionalFunctionOpratingContainerModel = (AdditionalFunctionOpratingContainerModel) model;
        additionalFunctionOpratingContainerModel.setAdditionalSerialNumber(this.additionalSerialNumber);
        additionalFunctionOpratingContainerModel.setMetaModel(this.metaModel);
        super.setParam(model);
    }

    @Override
    public AdditionalFunctionOpratingContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        AdditionalFunctionOpratingContainerModel model = new AdditionalFunctionOpratingContainerModel();
        setParam(model);
        return model;
    }

    @Override
    public File getImageRootPath() {
        File file = SourceGenerateFileStructure.getAdditionalPictureFilePutPath(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName, getAdditionalSerialNumber());
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = SourceGenerateFileStructure.getAdditionalNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName, getAdditionalSerialNumber());
        return file;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return MarkElementName.ADDITIONAL_FUNCTION_MARK;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public String getModuleId() {
        return "";
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return this.additionalSerialNumber;
    }

    @Override
    public AbstractCodeControlPane getCodeControlPane() {
        return this.additionalFunctionOperatingContainerParam.getCodeControlPane();
    }

    @Override
    public AbstractCommandOpratingContainer getFirstCommandOpratingContainer() {
        if (additionalFunctionOperatingContainerParam != null) {
            return additionalFunctionOperatingContainerParam.getFirstCommandOpratingContainer();
        }
        return null;
    }

    @Override
    public AbstractFormatContainer getFormatContainer() {
        if (additionalFunctionOperatingContainerParam != null) {
            return additionalFunctionOperatingContainerParam.getFormatContainer();
        }
        return null;
    }

    @Override
    public OpratingContainerInterface getParentOpratingContainer() {
        return this.additionalFunctionOperatingContainerParam.getParentOpratingContainer();
    }

    @Override
    public FunctionMarkElement getCorrespondingMarkElement() {
        return new FunctionMarkElement();
    }


}
