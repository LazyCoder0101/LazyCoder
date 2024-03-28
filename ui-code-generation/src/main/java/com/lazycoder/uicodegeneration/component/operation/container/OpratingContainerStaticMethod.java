package com.lazycoder.uicodegeneration.component.operation.container;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.CommandContainerImportCode;
import com.lazycoder.database.model.ImportCode;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.NeedUseModuleImportCode;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodePaneModelGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.NeedFirstOpratingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.MainFormatControlPane;

import java.util.ArrayList;
import java.util.List;

public class OpratingContainerStaticMethod {


    /**
     * 设置首个需要参数的对应代码面板
     *
     * @param needFirstOpratingContainerParam 要设置的需要参数
     * @param formatControlPane               对应的控制面板
     * @param pathParam                       对应的路径信息
     */
    public static void setCodeFormatFlagParamCorrespondingCodePane(
            NeedFirstOpratingContainerParam needFirstOpratingContainerParam, AbstractFormatControlPane formatControlPane,
            String pathParam) {
        ArrayList<CodeFormatFlagParam> list = JSON.parseObject(pathParam,
                new TypeReference<ArrayList<CodeFormatFlagParam>>() {
                });

        if (list.size() > 0) {
            if (formatControlPane instanceof MainFormatControlPane) {// 必填模板控制面板，
                for (CodeFormatFlagParam codeFormatFlagParamTemp : list) {
                    if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParamTemp.getFormatType()) {

                        // 这里要获取真正对应的那个必填模板，因为可能有多个
                        if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParamTemp
                                .getFileType()) {
                            needFirstOpratingContainerParam.setTheCodePane(formatControlPane.getDefaultPane());
                        } else {
                            needFirstOpratingContainerParam.setTheCodePane(CodeGenerationFrameHolder.codeShowPanel
                                    .getCorrespondingCodePane(codeFormatFlagParamTemp));
                        }

                    } else if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParamTemp.getFormatType()) {// 必填模板或者模块代码文件
                        needFirstOpratingContainerParam.setTheCodePane(CodeGenerationFrameHolder.codeShowPanel
                                .getCorrespondingCodePane(codeFormatFlagParamTemp));
                    }
                }
            } else if (formatControlPane instanceof AdditionalFormatControlPane) {
                for (CodeFormatFlagParam codeFormatFlagParamTemp : list) {
                    if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParamTemp.getFormatType()) {

                        // 这里要获取真正对应的那个可选模板，因为可能有多个
                        if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParamTemp
                                .getFileType()) {
                            needFirstOpratingContainerParam.setTheCodePane(formatControlPane.getDefaultPane());
                        } else {
                            needFirstOpratingContainerParam.setTheCodePane(CodeGenerationFrameHolder.codeShowPanel
                                    .getCorrespondingCodePane(codeFormatFlagParamTemp));
                        }

                    } else if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParamTemp.getFormatType()) {// 必填模板或者模块代码文件
                        needFirstOpratingContainerParam.setTheCodePane(CodeGenerationFrameHolder.codeShowPanel
                                .getCorrespondingCodePane(codeFormatFlagParamTemp));
                    }
                }
            }
        }
    }

    /**
     * 插入本模块的代码
     */
//    public static void inserThisModuleImportCodeForCommand(ArrayList<CodePaneModelGenerateCodeResponseParam> codePaneModelGenerateCodeResponseParamList,
//                                                           ArrayList<AbstractCodeUseProperty> codeUsePropertyList,
//                                                           ModuleInfo moduleInfo,
//                                                           Module module,
//                                                           String moduleId) {
//        CodeShowPane codeShowPane;
//        for (CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam : codePaneModelGenerateCodeResponseParamList) {
//            if (codePaneModelGenerateCodeResponseParam != null && codePaneModelGenerateCodeResponseParam.getCodeFormatFlagParam() != null) {
//                codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codePaneModelGenerateCodeResponseParam.getCodeFormatFlagParam());
//                if (codeShowPane != null) {
//                    if (codeUsePropertyList.size() == 0) {//codeUsePropertyList没有经过任何设置，默认不设置都要插入本模块引入代码
//                        addImportCodes(codeShowPane,
//                                moduleInfo,
//                                module,
//                                moduleId);
//                    } else if (codeUsePropertyList.size() > 0) {
//                        if (!GeneralCommandCodeModel.contant(
//                                codeUsePropertyList, AbstractCodeUseProperty.NoNeedToInsertImportCodeType)
//                        ) {//没有设置 AbstractCodeUseProperty.NoNeedToInsertImportCodeType（无需插入引入代码），此时还是要插入本模块的引入代码
//                            addImportCodes(codeShowPane,
//                                    moduleInfo,
//                                    module,
//                                    moduleId);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 插入所需模块的代码
//     */
//    public static void inserNeedModuleImportCodeForCommand(ArrayList<CodePaneModelGenerateCodeResponseParam> codePaneModelGenerateCodeResponseParamList,
//                                                           ArrayList<AbstractCodeUseProperty> codeUsePropertyList,
//                                                           AbstractCodeControlPane codeControlPane,
//                                                           String moduleId) {
//        CodeShowPane codeShowPane;
//        ModuleRelatedParam moduleRelatedParam;
//        AbstractFormatControlPane formatControlPane;
//        NeedUseModuleImportCode needUseModuleImportCode;
//        for (CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam : codePaneModelGenerateCodeResponseParamList) {
//            if (codePaneModelGenerateCodeResponseParam != null && codePaneModelGenerateCodeResponseParam.getCodeFormatFlagParam() != null) {
//                codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codePaneModelGenerateCodeResponseParam.getCodeFormatFlagParam());
//                if (codeShowPane != null && codeUsePropertyList.size() > 0) {
//                    //如果要添加所需模块的引入代码，在这里插入
//                    if (GeneralCommandCodeModel.contant(
//                            codeUsePropertyList, AbstractCodeUseProperty.NeedUseModuleImportCodeType)) {
//                        needUseModuleImportCode = GeneralCommandCodeModel.getNeedUseModuleImportCodeProperty(codeUsePropertyList);
//                        if (needUseModuleImportCode != null && needUseModuleImportCode.getNeedUseModuleList().size() > 0) {
//                            if (codeControlPane != null && codeControlPane.getFormatControlPane() != null) {
//                                formatControlPane = codeControlPane.getFormatControlPane();
//                                for (String needModuleTemp : needUseModuleImportCode.getNeedUseModuleList()) {
//                                    moduleRelatedParam = formatControlPane.getModuleRelatedParam(needModuleTemp);
//                                    if (moduleRelatedParam != null) {
//                                        addImportCodes(codeShowPane,
//                                                moduleRelatedParam.getModuleInfo(),
//                                                moduleRelatedParam.getModule(),
//                                                moduleId);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    /**
     * 获取对应的代码面板
     *
     * @param pathParam
     * @param formatControlPane
     * @return
     */
    public static CodeShowPane getCurrentChowPane(String pathParam, AbstractFormatControlPane formatControlPane) {
        CodeShowPane codeShowPane = null;
        ArrayList<CodeFormatFlagParam> list = JSON.parseObject(pathParam,
                new TypeReference<ArrayList<CodeFormatFlagParam>>() {
                });

        if (list.size() > 0) {
            if (formatControlPane instanceof MainFormatControlPane) {// 必填模板控制面板，
                for (CodeFormatFlagParam codeFormatFlagParamTemp : list) {
                    if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParamTemp.getFormatType()) {

                        // 这里要获取真正对应的那个必填模板，因为可能有多个
                        if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParamTemp
                                .getFileType()) {
                            codeShowPane = formatControlPane.getDefaultPane();
                            break;
                        } else {
                            codeShowPane = CodeGenerationFrameHolder.codeShowPanel
                                    .getCorrespondingCodePane(codeFormatFlagParamTemp);
                            break;
                        }

                    } else if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParamTemp.getFormatType()) {// 必填模板或者模块代码文件
                        codeShowPane = CodeGenerationFrameHolder.codeShowPanel
                                .getCorrespondingCodePane(codeFormatFlagParamTemp);
                        break;
                    }
                }
            } else if (formatControlPane instanceof AdditionalFormatControlPane) {
                for (CodeFormatFlagParam codeFormatFlagParamTemp : list) {
                    if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParamTemp.getFormatType()) {

                        // 这里要获取真正对应的那个可选模板，因为可能有多个
                        if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParamTemp
                                .getFileType()) {
                            codeShowPane = formatControlPane.getDefaultPane();
                            break;
                        } else {
                            codeShowPane = CodeGenerationFrameHolder.codeShowPanel
                                    .getCorrespondingCodePane(codeFormatFlagParamTemp);
                            break;
                        }

                    } else if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParamTemp.getFormatType()) {// 必填模板或者模块代码文件
                        codeShowPane = CodeGenerationFrameHolder.codeShowPanel
                                .getCorrespondingCodePane(codeFormatFlagParamTemp);
                        break;
                    }
                }
            }
        }
        return codeShowPane;
    }

    /**
     * 在指定位置生成代码
     *
     * @param opratingContainer
     * @param codeShowPane
     * @param commandAddRelatedAttribute
     * @param codeStatementParam
     * @param codeOrdinal
     * @param codeLabelId
     * @param inserNewLineOrNot
     * @param codeListLocationInfoList
     * @return
     */
    public static boolean generateCode(AbstractCommandOpratingContainer opratingContainer, CodeShowPane codeShowPane,
                                       CommandAddRelatedAttribute commandAddRelatedAttribute,
                                       String codeStatementParam, int codeOrdinal, String codeLabelId, boolean inserNewLineOrNot,
                                       ArrayList<CodeListLocation> codeListLocationInfoList) {
        boolean returnFlag = false;
        int situation = 1;
        PathFind containerPathFind = opratingContainer.getPathFind();//拿到功能当前的寻址参数
        if (containerPathFind != null) {
            if (PathFind.COMMAND_TYPE == containerPathFind.getMetaType()) {

                if (containerPathFind.whetherToOperateAddDelOrNotForMark()) {
                    if (MarkElementName.FUNCTION_MARK.equals(containerPathFind.getMarkType()) ||
                            MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(containerPathFind.getMarkType())) {
                        situation = 2;//直接添加到业务逻辑面板
                    } else {
                        situation = 4;//初始化、模块设置、主函数设置、其他函数设置一类
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

            CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam;
            ContainerGenerateCodeResponseParam containerGenerateCodeResponseParam = opratingContainer.thisCodeLocationInformation;//获取命令功能opratingContainer对应存储代码位置的变量，要往里面写入最终的代码位置
            for (CodeListLocation codeListLocation : codeListLocationInfoList) {
                switch (situation) {
                    case 1://直接添加到格式
                        if (PathFind.FORMAT_TYPE == codeListLocation.getMetaType()) {
                            AddCodeEditParamForFormat addCodeEditParamForFormat;
                            for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                                addCodeEditParamForFormat = new AddCodeEditParamForFormat();
                                addCodeEditParamForFormat.setCodeStatementParam(codeStatementParam);
                                addCodeEditParamForFormat.setTrulyPathFind(trulyPathFind);
                                addCodeEditParamForFormat.setCodeSerialNumber(opratingContainer.getCodeSerialNumber());
                                addCodeEditParamForFormat.setCodeOrdinal(codeOrdinal);
                                addCodeEditParamForFormat.setInserNewLineOrNot(inserNewLineOrNot);
                                addCodeEditParamForFormat.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                                codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToFormat(addCodeEditParamForFormat);
                                if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
                                    returnFlag = true;

                                    codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
                                    codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                                    containerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
                                }
                            }
                        }
                        break;
                    case 2://直接添加到业务逻辑面板
                        if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
                            AddCodeEditParamForMark addCodeEditParamForMark;
                            for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                                addCodeEditParamForMark = new AddCodeEditParamForMark();
                                addCodeEditParamForMark.setCodeStatementParam(codeStatementParam);
                                addCodeEditParamForMark.setThanMarkElement(codeListLocation.getThanMarkElement());
                                addCodeEditParamForMark.setCodeSerialNumber(opratingContainer.getCodeSerialNumber());
                                addCodeEditParamForMark.setCodeOrdinal(codeOrdinal);
                                addCodeEditParamForMark.setInserNewLineOrNot(inserNewLineOrNot);
                                addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                                addCodeEditParamForMark.setTrulyPathFind(trulyPathFind);
                                addCodeEditParamForMark.setAddToLast(commandAddRelatedAttribute.isAddToLast());
                                addCodeEditParamForMark.setNextCodeSerialNumber(commandAddRelatedAttribute.getNextCodeSerialNumber());
                                codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToMark(addCodeEditParamForMark);
                                if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
                                    returnFlag = true;

                                    codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
                                    codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                                    containerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
                                }
                            }
                        }
                        break;
                    case 3://添加到功能的功能拓展组件里面
                        if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
                            AddCodeEditParamForMark addCodeEditParamForMark;
                            for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                                addCodeEditParamForMark = new AddCodeEditParamForMark();
                                addCodeEditParamForMark.setCodeStatementParam(codeStatementParam);
                                addCodeEditParamForMark.setThanMarkElement(codeListLocation.getThanMarkElement());
                                addCodeEditParamForMark.setCodeSerialNumber(opratingContainer.getCodeSerialNumber());
                                addCodeEditParamForMark.setCodeOrdinal(codeOrdinal);
                                addCodeEditParamForMark.setInserNewLineOrNot(inserNewLineOrNot);
                                addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                                addCodeEditParamForMark.setTrulyPathFind(trulyPathFind);
                                addCodeEditParamForMark.setAddToLast(commandAddRelatedAttribute.isAddToLast());
                                addCodeEditParamForMark.setNextCodeSerialNumber(commandAddRelatedAttribute.getNextCodeSerialNumber());
                                codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToMark(addCodeEditParamForMark);
                                if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
                                    returnFlag = true;

                                    codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
                                    codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                                    containerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
                                }
                            }
                        } else if (PathFind.FORMAT_TYPE == codeListLocation.getMetaType()) {
                            AddCodeEditParamForFormat addCodeEditParamForFormat;
                            for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                                addCodeEditParamForFormat = new AddCodeEditParamForFormat();
                                addCodeEditParamForFormat.setCodeStatementParam(codeStatementParam);
                                addCodeEditParamForFormat.setTrulyPathFind(trulyPathFind);
                                addCodeEditParamForFormat.setCodeSerialNumber(opratingContainer.getCodeSerialNumber());
                                addCodeEditParamForFormat.setCodeOrdinal(codeOrdinal);
                                addCodeEditParamForFormat.setInserNewLineOrNot(inserNewLineOrNot);
                                addCodeEditParamForFormat.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                                codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToFormat(addCodeEditParamForFormat);
                                if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
                                    returnFlag = true;

                                    codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
                                    codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                                    containerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
                                }
                            }
                        }
                        break;
                    case 4://初始化、模块设置、主函数设置、其他函数设置一类
                        if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
                            AddCodeEditParamForMark addCodeEditParamForMark;
                            for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                                addCodeEditParamForMark = new AddCodeEditParamForMark();
                                addCodeEditParamForMark.setCodeStatementParam(codeStatementParam);
                                addCodeEditParamForMark.setThanMarkElement(codeListLocation.getThanMarkElement());
                                addCodeEditParamForMark.setCodeSerialNumber(opratingContainer.getCodeSerialNumber());
                                addCodeEditParamForMark.setCodeOrdinal(codeOrdinal);
                                addCodeEditParamForMark.setInserNewLineOrNot(inserNewLineOrNot);
                                addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                                addCodeEditParamForMark.setTrulyPathFind(trulyPathFind);
                                addCodeEditParamForMark.setAddToLast(commandAddRelatedAttribute.isAddToLast());
                                addCodeEditParamForMark.setNextCodeSerialNumber(commandAddRelatedAttribute.getNextCodeSerialNumber());
                                codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToMark(addCodeEditParamForMark);
                                if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
                                    returnFlag = true;

                                    codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
                                    codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                                    containerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
                                }
                            }
                        }
                        break;
                }
            }
        }
        return returnFlag;
    }

    public static boolean checkGenerateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
            PathFind containerPathFind,
            CommandAddRelatedAttribute commandAddRelatedAttribute,
            GeneralOperatingContainerParam operatingContainerParam) {
        boolean returnFlag = false;
        String codeLabelId = commandAddRelatedAttribute.getCodeLabelId();
        if (CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                commandAddRelatedAttribute.getOtherAttribute()) {//如果不合适，就直接添加到第1层组件这种情况

            if (containerPathFind != null) {
                if (PathFind.COMMAND_TYPE == containerPathFind.getMetaType()) {
                    AbstractCommandOpratingContainer firstCommandOpratingContainer =
                            operatingContainerParam.getFirstCommandOpratingContainer();//获取第1个命令功能
                    if (firstCommandOpratingContainer != null) {

                        BaseMarkElement markElement = firstCommandOpratingContainer.getCorrespondingMarkElement();//获取第1个命令功能对应的标记
                        if (markElement != null) {
                            if (codeLabelId != null) {
                                markElement.setCodeLabelId(codeLabelId);
                            }
                            CodeListLocation codeListLocation = new CodeListLocation();
                            PathFind originalPathFind = PathFind.getOnALayerOfPathFind(containerPathFind, 0);
                            codeListLocation.addCodePathFindForMark(markElement, originalPathFind);

                            ArrayList<CodeShowPane> codePaneList = firstCommandOpratingContainer.getCodePaneList();
                            if (codePaneList != null) {
                                for (CodeShowPane codeShowPaneTemp : codePaneList) {
                                    if (codeShowPaneTemp != null) {
                                        boolean flag = OpratingContainerStaticMethod.checkGenerateCode(
                                                originalPathFind, codeShowPaneTemp, commandAddRelatedAttribute.clone(),
                                                codeListLocation);
                                        if (flag) {
                                            returnFlag = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (PathFind.FORMAT_TYPE == containerPathFind.getMetaType()) {
                    AbstractFormatContainer formatContainer = operatingContainerParam.getFormatContainer();
                    if (formatContainer != null) {
                        ArrayList<CodeShowPane> formatCodePaneList = formatContainer.getCodePaneList();
                        for (CodeShowPane codeShowPane : formatCodePaneList) {
                            boolean flag = codeShowPane.checkAddCodeToFirstLayerFormat(containerPathFind, commandAddRelatedAttribute);
                            if (flag) {
                                returnFlag = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return returnFlag;
    }

    public static boolean generateCodeFor_ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
            AbstractCommandOpratingContainer opratingContainer,
            String codeStatementParam,
            int codeOrdinal,
            String importCodeParam,
            CommandAddRelatedAttribute commandAddRelatedAttribute,
            GeneralOperatingContainerParam operatingContainerParam,
            String codeUsePropertyParam,
            boolean inserNewLineOrNot,
            ModuleInfo moduleInfo, Module module,
            String moduleId) {
        boolean returnFlag = false;
        String codeLabelId = commandAddRelatedAttribute.getCodeLabelId();
        ContainerGenerateCodeResponseParam containerGenerateCodeResponseParam = opratingContainer.thisCodeLocationInformation;//获取命令功能opratingContainer对应存储代码位置的变量，要往里面写入最终的代码位置

        if (CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                commandAddRelatedAttribute.getOtherAttribute()) {//如果不合适，就直接添加到第1层组件这种情况
            CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam;

            PathFind containerPathFind = opratingContainer.getPathFind();//拿到功能当前的寻址参数
            if (containerPathFind != null) {
                AddCodeEditParamForMark addCodeEditParamForMark;
                ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;

                if (PathFind.COMMAND_TYPE == containerPathFind.getMetaType()) {
                    AbstractCommandOpratingContainer firstCommandOpratingContainer = operatingContainerParam.getFirstCommandOpratingContainer();
                    if (firstCommandOpratingContainer != null) {

                        BaseMarkElement markElement = firstCommandOpratingContainer.getCorrespondingMarkElement();//获取第1个命令功能对应的标记
                        if (markElement != null) {
                            if (codeLabelId != null) {
                                markElement.setCodeLabelId(codeLabelId);
                            }
                            PathFind originalPathFind = PathFind.getOnALayerOfPathFind(containerPathFind, 0);

                            ArrayList<CodeShowPane> codePaneList = firstCommandOpratingContainer.getCodePaneList();
                            if (codePaneList != null) {
                                for (CodeShowPane codeShowPaneTemp : codePaneList) {
                                    if (codeShowPaneTemp != null) {

                                        addCodeEditParamForMark = new AddCodeEditParamForMark();
                                        addCodeEditParamForMark.setCodeStatementParam(codeStatementParam);
                                        addCodeEditParamForMark.setThanMarkElement(markElement);
                                        addCodeEditParamForMark.setCodeSerialNumber(opratingContainer.getCodeSerialNumber());
                                        addCodeEditParamForMark.setCodeOrdinal(codeOrdinal);
                                        addCodeEditParamForMark.setInserNewLineOrNot(inserNewLineOrNot);
                                        addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                                        addCodeEditParamForMark.setTrulyPathFind(originalPathFind);
                                        addCodeEditParamForMark.setAddToLast(commandAddRelatedAttribute.isAddToLast());
                                        addCodeEditParamForMark.setNextCodeSerialNumber(commandAddRelatedAttribute.getNextCodeSerialNumber());
                                        codePaneModelGenerateCodeResponseParam = codeShowPaneTemp.addCodeToMark(addCodeEditParamForMark);
                                        if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
                                            returnFlag = true;

                                            codePaneModelGenerateCodeResponseParam.setCodeFormatFlagParam(codeShowPaneTemp.getCodeFormatFlagParam());
                                            codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
                                            codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                                            containerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);

                                            if (null != codeUsePropertyParam) {
                                                setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeUsePropertyParam);
                                                OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                                        setPropertyTempList,
                                                        codeShowPaneTemp,
                                                        moduleInfo, module, moduleId
                                                );
                                            }

                                            OpratingContainerStaticMethod.addCommandContainerImportCodes(
                                                    codeShowPaneTemp,
                                                    importCodeParam);
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else if (PathFind.FORMAT_TYPE == containerPathFind.getMetaType()) {
                    CodeListLocation formatCodeListLocation;
                    AbstractFormatContainer formatContainer = operatingContainerParam.getFormatContainer();
                    if (formatContainer != null) {
                        ArrayList<CodeShowPane> formatCodePaneList = formatContainer.getCodePaneList();
                        for (CodeShowPane codeShowPane : formatCodePaneList) {
                            int thisCursorPositionTemp = codeShowPane.addCodeToFirstLayerFormat(opratingContainer.codeSerialNumber,
                                    codeOrdinal,
                                    codeStatementParam, inserNewLineOrNot,
                                    containerPathFind, commandAddRelatedAttribute);
                            if (thisCursorPositionTemp > 0) {
                                codePaneModelGenerateCodeResponseParam = new CodePaneModelGenerateCodeResponseParam();
                                codePaneModelGenerateCodeResponseParam.setCodeFormatFlagParam(codeShowPane.getCodeFormatFlagParam());
                                codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                                codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);

                                formatCodeListLocation = new CodeListLocation();
                                formatCodeListLocation.addCodePathFindForFormat(PathFind.getOnALayerOfPathFind(containerPathFind, 1));

                                codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
                                codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(formatCodeListLocation);
                                codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
                                returnFlag = true;

                                if (null != codeUsePropertyParam) {
                                    setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeUsePropertyParam);
                                    OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                            setPropertyTempList,
                                            codeShowPane,
                                            moduleInfo, module, moduleId
                                    );
                                }
                            }

                            OpratingContainerStaticMethod.addCommandContainerImportCodes(
                                    codeShowPane,
                                    importCodeParam);
                        }
                    }
                }
            }
        }
        return returnFlag;
    }

//    public static boolean generateCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
//            AbstractCommandOpratingContainer opratingContainer1,
//            String codeStatementParam,
//            int codeOrdinal, String codeLabelId,
//            CommandAddRelatedAttribute commandAddRelatedAttribute,
//            AbstractCommandOpratingContainer paramOpratingContainer,
//            GeneralOperatingContainerParam operatingContainerParam
//    ) {
//        boolean returnFlag = false;
//        if (CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
//                commandAddRelatedAttribute.getOtherAttribute()) {//逐步向上查找
//            CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam;
//            if (paramOpratingContainer.getParentOpratingContainer() != null &&
//                    paramOpratingContainer.getParentOpratingContainer() instanceof AbstractCommandOpratingContainer) {
//                ContainerGenerateCodeResponseParam containerGenerateCodeResponseParam = paramOpratingContainer.thisCodeLocationInformation;//获取命令功能opratingContainer对应存储代码位置的变量，要往里面写入最终的代码位置
//
//                if (operatingContainerParam.getParentOpratingContainer() != null &&
//                        operatingContainerParam.getParentOpratingContainer() instanceof AbstractCommandOpratingContainer) {
//                    AbstractCommandOpratingContainer parentOpratingContainer = (AbstractCommandOpratingContainer) operatingContainerParam.getParentOpratingContainer();
//                    PathFind containerPathFind = parentOpratingContainer.pathFind;
//                    if (PathFind.COMMAND_TYPE == containerPathFind.getMetaType()) {
//                        BaseMarkElement thanMarkElement;
//                        CodeShowPane codeShowPane;
//                        ContainerGenerateCodeResponseParam parentOpratingContainerCodeLocationInformation = ContainerGenerateCodeResponseParam.
//                                getUniqueContainerGenerateCodeResponseParam(parentOpratingContainer.thisCodeLocationInformation);
//                        for (CodePaneModelGenerateCodeResponseParam parentCodeResponseParam : parentOpratingContainerCodeLocationInformation.getCodeListLocationInfoList()) {
//                            codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(parentCodeResponseParam.getCodeFormatFlagParam());
//                            codePaneModelGenerateCodeResponseParam = new CodePaneModelGenerateCodeResponseParam();
//                            if (codeShowPane != null) {
//                                codePaneModelGenerateCodeResponseParam.setCodeFormatFlagParam(codeShowPane.getCodeFormatFlagParam());
//                                codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
//                                codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
//
//                                //看看有没有命令类型的
//                                for (CodeListLocation codeListLocation : parentCodeResponseParam.getCodeListLocationInfoList()) {
//                                    if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
//                                        thanMarkElement = codeListLocation.getThanMarkElement();
//                                        if (thanMarkElement != null) {
//                                            if (null == thanMarkElement.getCodeLabelId()) {
//                                                if (null == commandAddRelatedAttribute.getCodeLabelId()) {
//                                                    returnFlag = true;
//                                                    codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
//                                                    codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(codeListLocation);
//
//                                                    int thisCursorPositionTemp = codeShowPane.addCodeToMark(
//                                                            thanMarkElement, paramOpratingContainer.codeSerialNumber,
//                                                            codeOrdinal,
//                                                            codeStatementParam, true, null
//                                                    );
//                                                    codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
//                                                }
//                                            } else {
//                                                if (thanMarkElement.getCodeLabelId().equals(commandAddRelatedAttribute.getCodeLabelId())) {
//                                                    returnFlag = true;
//                                                    codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
//                                                    codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(codeListLocation);
//
//                                                    int thisCursorPositionTemp = codeShowPane.addCodeToMark(
//                                                            thanMarkElement, paramOpratingContainer.codeSerialNumber,
//                                                            codeOrdinal,
//                                                            codeStatementParam, true, null
//                                                    );
//                                                    codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//
//                                //看看有没有格式类型的
//                                ArrayList<PathFind> formatPathFindList = CodePaneModelGenerateCodeResponseParam.
//                                        getUniquePathFindListForFormat(parentCodeResponseParam);
//                                if (formatPathFindList.size() > 0) {
//                                    boolean formatFlag = true;
//                                    CodeListLocation formatCodeListLocation;
//                                    for (PathFind formatPathFind : formatPathFindList) {
//                                        if (formatFlag) {
//                                            int thisCursorPositionTemp = codeShowPane.addCodeToFirstLayerFormat(paramOpratingContainer.codeSerialNumber,codeOrdinal, codeStatementParam,
//                                                    formatPathFind, commandAddRelatedAttribute);
//                                            if (thisCursorPositionTemp > 0) {
//                                                returnFlag = true;
//
//                                                formatCodeListLocation = new CodeListLocation();
//                                                formatCodeListLocation.addCodePathFindForFormat(formatPathFind);
//
//                                                codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
//                                                codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(formatCodeListLocation);
//                                                codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
//                                                formatFlag = false;
//                                            }
//                                        } else {
//                                            int thisCursorPositionTemp = codeShowPane.addCodeToFirstLayerFormat(paramOpratingContainer.codeSerialNumber,codeOrdinal,  codeStatementParam,
//                                                    formatPathFind, commandAddRelatedAttribute);
//                                            if (thisCursorPositionTemp > 0) {
//                                                returnFlag = true;
//
//                                                formatCodeListLocation = new CodeListLocation();
//                                                formatCodeListLocation.addCodePathFindForFormat(formatPathFind);
//
//                                                codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
//                                                codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(formatCodeListLocation);
//                                            }
//                                        }
//                                    }
//                                }
//
//                                if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
//                                    containerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
//                                }
//                            }
//                        }
//                    } else if (PathFind.FORMAT_TYPE == containerPathFind.getMetaType()) {
//                        CodeListLocation formatCodeListLocation;
//                        AbstractFormatContainer formatContainer = operatingContainerParam.getFormatContainer();
//                        if (formatContainer != null) {
//                            ArrayList<CodeShowPane> formatCodePaneList = formatContainer.getCodePaneList();
//                            for (CodeShowPane codeShowPane : formatCodePaneList) {
//                                int thisCursorPositionTemp = codeShowPane.addCodeToFirstLayerFormat(paramOpratingContainer.codeSerialNumber, codeOrdinal, codeStatementParam,
//                                        containerPathFind, commandAddRelatedAttribute);
//                                if (thisCursorPositionTemp > 0) {
//                                    codePaneModelGenerateCodeResponseParam = new CodePaneModelGenerateCodeResponseParam();
//                                    codePaneModelGenerateCodeResponseParam.setCodeFormatFlagParam(codeShowPane.getCodeFormatFlagParam());
//                                    codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
//                                    codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
//
//                                    formatCodeListLocation = new CodeListLocation();
//                                    formatCodeListLocation.addCodePathFindForFormat(PathFind.getOnALayerOfPathFind(containerPathFind, 1));
//
//                                    codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
//                                    codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(formatCodeListLocation);
//                                    codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
//                                    returnFlag = true;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return returnFlag;
//    }

    /**
     * 逐步向上检查，找到合适的位置进行添加
     *
     * @param thisOpratingContainer      真正要插入代码的对应功能
     * @param codeStatementParam         这次要生成代码的对应参数字符串
     * @param codeOrdinal                这次生成代码的序号
     * @param commandAddRelatedAttribute 添加命令的相关属性，比如对应的标签id，是逐步移位还是其他属性等
     * @param operatingContainerParam    生成代码所需的参数集合
     * @param codeUsePropertyParam       生成代码是的其他附带属性要求，比如上次引入代码等
     * @param moduleInfo                 生成模块代码才需要，模块信息
     * @param module                     生成模块代码才需要，模块
     * @param moduleId                   生成模块代码才需要，模块id
     * @param childOpratingContainer     本次检查的功能的子功能
     * @return
     */
    public static boolean generateCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
            AbstractCommandOpratingContainer thisOpratingContainer,
            String codeStatementParam,
            int codeOrdinal,
            String importCodeParam,
            CommandAddRelatedAttribute commandAddRelatedAttribute,
            GeneralOperatingContainerParam operatingContainerParam,
            String codeUsePropertyParam,
            boolean inserNewLineOrNot,
            ModuleInfo moduleInfo, Module module,
            String moduleId,
            AbstractCommandOpratingContainer childOpratingContainer
    ) {
        boolean returnFlag = false;
        String codeLabelId = commandAddRelatedAttribute.getCodeLabelId();
        if (CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                commandAddRelatedAttribute.getOtherAttribute()) {//逐步向上查找
            CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam;

            //如果是在格式的功能拓展组件直接添加的话，查看对应的功能拓展组件是否符合
            if (childOpratingContainer.getParentOpratingContainer() == null &&
                    PathFind.FORMAT_TYPE == childOpratingContainer.getPathFind().getMetaType() &&
                    childOpratingContainer.getPathFind().getPathList().size() == 2) {//直接添加到格式上的功能拓展组件的功能（此时childOpratingContainer肯定没有parentOpratingContainer，且为FORMAT_TYPE类型，另外pathFindList数值为2）

                ContainerGenerateCodeResponseParam thisContainerGenerateCodeResponseParam = thisOpratingContainer.thisCodeLocationInformation;

                //这种情况，判断childOpratingContainer的再上一层是否符合，实际上就是判断所在的功能拓展组件有没有这个codeLabelId，有的话就把找到对应位置插入代码
                PathFind containerPathFind = childOpratingContainer.getPathFind();
                CodeListLocation formatCodeListLocation;
                AbstractFormatContainer formatContainer = operatingContainerParam.getFormatContainer();
                if (formatContainer != null) {
                    ArrayList<CodeShowPane> formatCodePaneList = formatContainer.getCodePaneList();
                    for (CodeShowPane codeShowPane : formatCodePaneList) {
                        int thisCursorPositionTemp = codeShowPane.addCodeToFirstLayerFormat(thisOpratingContainer.getCodeSerialNumber(),
                                codeOrdinal,
                                codeStatementParam, inserNewLineOrNot,
                                containerPathFind, commandAddRelatedAttribute.clone());
                        if (thisCursorPositionTemp > 0) {
                            codePaneModelGenerateCodeResponseParam = new CodePaneModelGenerateCodeResponseParam();
                            codePaneModelGenerateCodeResponseParam.setCodeFormatFlagParam(codeShowPane.getCodeFormatFlagParam());
                            codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                            codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);

                            formatCodeListLocation = new CodeListLocation();
                            formatCodeListLocation.addCodePathFindForFormat(PathFind.getOnALayerOfPathFind(containerPathFind, 1));

                            codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
                            codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(formatCodeListLocation);
                            codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
                            thisContainerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
                            returnFlag = true;

                            if (null != codeUsePropertyParam) {
                                ArrayList<AbstractCodeUseProperty> setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeUsePropertyParam);
                                OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                        setPropertyTempList,
                                        codeShowPane,
                                        moduleInfo, module, moduleId
                                );
                            }

                            OpratingContainerStaticMethod.addCommandContainerImportCodes(
                                    codeShowPane,
                                    importCodeParam);
                        }
                    }
                }
            }

            //如果是第一个命令，直接获取它对应的标记看看是否符合
            if (!returnFlag) {
                if (childOpratingContainer.getParentOpratingContainer() == null &&
                        PathFind.COMMAND_TYPE == childOpratingContainer.getPathFind().getMetaType() &&
                        childOpratingContainer.getPathFind().whetherToOperateAddDelOrNotForMark()) {

                    ArrayList<AbstractCodeUseProperty> setPropertyTempList;
                    ContainerGenerateCodeResponseParam thisContainerGenerateCodeResponseParam = thisOpratingContainer.thisCodeLocationInformation;

                    BaseMarkElement markElement = childOpratingContainer.getCorrespondingMarkElement();//获取第1个命令功能对应的标记
                    if (markElement != null) {
                        if (codeLabelId != null) {
                            markElement.setCodeLabelId(codeLabelId);
                        }
                        PathFind originalPathFind = PathFind.getOnALayerOfPathFind(childOpratingContainer.getPathFind(), 0);

                        ArrayList<CodeShowPane> codePaneList = childOpratingContainer.getCodePaneList();
                        if (codePaneList != null) {
                            AddCodeEditParamForMark addCodeEditParamForMark;
                            for (CodeShowPane codeShowPaneTemp : codePaneList) {
                                if (codeShowPaneTemp != null) {
                                    addCodeEditParamForMark = new AddCodeEditParamForMark();
                                    addCodeEditParamForMark.setCodeStatementParam(codeStatementParam);
                                    addCodeEditParamForMark.setThanMarkElement(markElement);
                                    addCodeEditParamForMark.setCodeSerialNumber(thisOpratingContainer.getCodeSerialNumber());
                                    addCodeEditParamForMark.setCodeOrdinal(codeOrdinal);
                                    addCodeEditParamForMark.setInserNewLineOrNot(inserNewLineOrNot);
                                    addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                                    addCodeEditParamForMark.setTrulyPathFind(originalPathFind);
                                    addCodeEditParamForMark.setAddToLast(commandAddRelatedAttribute.isAddToLast());
                                    addCodeEditParamForMark.setNextCodeSerialNumber(commandAddRelatedAttribute.getNextCodeSerialNumber());
                                    codePaneModelGenerateCodeResponseParam = codeShowPaneTemp.addCodeToMark(addCodeEditParamForMark);
                                    if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
                                        returnFlag = true;

                                        codePaneModelGenerateCodeResponseParam.setCodeFormatFlagParam(codeShowPaneTemp.getCodeFormatFlagParam());
                                        codePaneModelGenerateCodeResponseParam.setCodeOrdinal(codeOrdinal);
                                        codePaneModelGenerateCodeResponseParam.setCodeLabelId(codeLabelId);
                                        thisContainerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);

                                        if (null != codeUsePropertyParam) {
                                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeUsePropertyParam);
                                            OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                                    setPropertyTempList,
                                                    codeShowPaneTemp,
                                                    moduleInfo, module, moduleId
                                            );
                                        }

                                        OpratingContainerStaticMethod.addCommandContainerImportCodes(
                                                codeShowPaneTemp,
                                                importCodeParam);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //是功能拓展组件里面添加的功能
            if (!returnFlag) {
                if (childOpratingContainer.getParentOpratingContainer() != null &&
                        childOpratingContainer.getParentOpratingContainer() instanceof AbstractCommandOpratingContainer) {
                    //不是上述情况直接拿它上一个功能的代码所在位置看看是否符合
                    ContainerGenerateCodeResponseParam thisContainerGenerateCodeResponseParam = thisOpratingContainer.thisCodeLocationInformation;

                    ArrayList<CodeListLocation> codeListLocationInfoList;
                    String codePaneCodeLabelIdTemp;
                    BaseMarkElement thanMarkElement;
                    boolean firFlag = true;

                    AbstractCommandOpratingContainer parentOpratingContainer = (AbstractCommandOpratingContainer) childOpratingContainer.getParentOpratingContainer();
                    ContainerGenerateCodeResponseParam parentContainerGenerateCodeResponseParam = parentOpratingContainer.getThisCodeLocationInformation();//拿到上一个功能的代码位置
                    ArrayList<CodePaneModelGenerateCodeResponseParam> parentCodeListLocationInfoList = parentContainerGenerateCodeResponseParam.getCodeListLocationInfoList();
                    CodeShowPane codeShowPane;
                    ArrayList<PathFind> pathFindTempList;
                    ArrayList<AbstractCodeUseProperty> setPropertyTempList;
                    for (CodePaneModelGenerateCodeResponseParam parentCodeResponseParam : parentCodeListLocationInfoList) {
                        codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(parentCodeResponseParam.getCodeFormatFlagParam());
                        firFlag = true;
                        codePaneCodeLabelIdTemp = parentCodeResponseParam.getCodeLabelId();
                        if ((codeLabelId == null && codePaneCodeLabelIdTemp == null) ||
                                (codeLabelId != null && codeLabelId.equals(codePaneCodeLabelIdTemp))) {//判断代码标签ID是否相等
                            codePaneModelGenerateCodeResponseParam = CodePaneModelGenerateCodeResponseParam.
                                    generateCodePaneModelGenerateCodeResponseParam(parentCodeResponseParam, codeOrdinal, codeLabelId);

                            codeListLocationInfoList = parentCodeResponseParam.getCodeListLocationInfoList();
                            for (CodeListLocation codeListLocationTemp : codeListLocationInfoList) {
                                pathFindTempList = codeListLocationTemp.getCodePathFindList();
                                if (PathFind.COMMAND_TYPE == codeListLocationTemp.getMetaType()) {
                                    for (PathFind pathFindTemp : pathFindTempList) {
                                        thanMarkElement = codeListLocationTemp.getThanMarkElement();
                                        if (thanMarkElement != null) {
                                            int thisCursorPositionTemp = codeShowPane.addCodeTo(thanMarkElement,
                                                    pathFindTemp, codeLabelId, thisOpratingContainer.getCodeSerialNumber(), codeOrdinal, inserNewLineOrNot, codeStatementParam, true, null);
                                            if (firFlag) {
                                                codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
                                            }
                                            firFlag = false;
                                        }
                                    }
                                } else if (PathFind.FORMAT_TYPE == codeListLocationTemp.getMetaType()) {
                                    for (PathFind pathFindTemp : pathFindTempList) {
                                        int thisCursorPositionTemp = codeShowPane.addCodeTo(null,
                                                pathFindTemp, codeLabelId, thisOpratingContainer.getCodeSerialNumber(), codeOrdinal,
                                                inserNewLineOrNot, codeStatementParam, true, null);
                                        if (firFlag) {
                                            codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
                                        }
                                        firFlag = false;
                                    }
                                }
                            }

                            thisContainerGenerateCodeResponseParam.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
                            returnFlag = true;

                            if (null != codeUsePropertyParam) {
                                setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeUsePropertyParam);
                                OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                        setPropertyTempList,
                                        codeShowPane,
                                        moduleInfo, module, moduleId
                                );
                            }
                        }
                    }

                    if (!returnFlag) {//本次检查parentOpratingContainer对应的代码位置，找不到合适的位置，继续再向上一层查看有没有合适的位置
                        returnFlag = generateCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                thisOpratingContainer, codeStatementParam, codeOrdinal, importCodeParam, commandAddRelatedAttribute, operatingContainerParam,
                                codeUsePropertyParam, inserNewLineOrNot, moduleInfo, module, moduleId, parentOpratingContainer
                        );

                    }
                }
            }
        }
        return returnFlag;
    }

    public static boolean checkCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
            AbstractCommandOpratingContainer thisOpratingContainer,
            CommandAddRelatedAttribute commandAddRelatedAttribute,
            GeneralOperatingContainerParam operatingContainerParam
    ) {
        boolean returnFlag = false;
        String codeLabelId = commandAddRelatedAttribute.getCodeLabelId();
        if (CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE ==
                commandAddRelatedAttribute.getOtherAttribute()) {//逐步向上查找

            AbstractCommandOpratingContainer theOpratingContainer = null;
            if (thisOpratingContainer != null) {
                theOpratingContainer = thisOpratingContainer;
            } else {
                theOpratingContainer = (AbstractCommandOpratingContainer) operatingContainerParam.getParentOpratingContainer();
            }

            if (theOpratingContainer != null &&
                    theOpratingContainer instanceof AbstractCommandOpratingContainer) {
                ArrayList<CodeListLocation> codeListLocationInfoList;
                String codePaneCodeLabelIdTemp;

                ContainerGenerateCodeResponseParam theContainerGenerateCodeResponseParam = theOpratingContainer.getThisCodeLocationInformation();//拿到上一个功能的代码位置
                ArrayList<CodePaneModelGenerateCodeResponseParam> theCodeListLocationInfoList = theContainerGenerateCodeResponseParam.getCodeListLocationInfoList();
                for (CodePaneModelGenerateCodeResponseParam theCodeResponseParam : theCodeListLocationInfoList) {
                    codePaneCodeLabelIdTemp = theCodeResponseParam.getCodeLabelId();
                    if ((codeLabelId == null && codePaneCodeLabelIdTemp == null) ||
                            (codeLabelId != null && codeLabelId.equals(codePaneCodeLabelIdTemp))) {//判断代码标签ID是否相等
                        codeListLocationInfoList = theCodeResponseParam.getCodeListLocationInfoList();
                        if (codeListLocationInfoList.size() > 0) {//到了某个层级有符合的位置
                            returnFlag = true;
                            break;
                        }
                    }
                    if (!returnFlag) {//本次方法没有找到合适的位置

                        //如果这次的theOpratingContainer是之前直接添加到格式上的话，那接下来要判断是否逐步添加的是直接在格式代码模型里面的功能拓展组件，在这里调用对应的检查方法看看是否符合
                        if (PathFind.FORMAT_TYPE == theOpratingContainer.getPathFind().getMetaType() &&
                                theOpratingContainer.getPathFind().getPathList().size() == 2) {//直接添加到格式上的话,PathFind().getPathList().size()为2

                            PathFind containerPathFind = theOpratingContainer.getPathFind();
                            AbstractFormatContainer formatContainer = operatingContainerParam.getFormatContainer();
                            if (formatContainer != null) {
                                ArrayList<CodeShowPane> formatCodePaneList = formatContainer.getCodePaneList();
                                for (CodeShowPane codeShowPane : formatCodePaneList) {
                                    boolean flag = codeShowPane.checkAddCodeToFirstLayerFormat(containerPathFind, commandAddRelatedAttribute.clone());
                                    if (flag) {
                                        returnFlag = true;
                                        break;
                                    }
                                }
                            }
                        }

                        if (!returnFlag) {
                            if (PathFind.COMMAND_TYPE == theOpratingContainer.getPathFind().getMetaType() &&
                                    theOpratingContainer.getPathFind().whetherToOperateAddDelOrNotForMark()) {
                                PathFind containerPathFind = PathFind.getOnALayerOfPathFind(theOpratingContainer.getPathFind(), 0);
                                BaseMarkElement markElement = theOpratingContainer.getCorrespondingMarkElement();//获取第1个命令功能对应的标记
                                if (markElement != null) {
                                    if (codeLabelId != null) {
                                        markElement.setCodeLabelId(codeLabelId);
                                    }
                                    CodeListLocation codeListLocation = new CodeListLocation();
                                    codeListLocation.addCodePathFindForMark(markElement, containerPathFind);

                                    ArrayList<CodeShowPane> codePaneList = theOpratingContainer.getCodePaneList();
                                    if (codePaneList != null) {
                                        for (CodeShowPane codeShowPaneTemp : codePaneList) {
                                            if (codeShowPaneTemp != null) {
                                                boolean flag = OpratingContainerStaticMethod.checkGenerateCode(
                                                        containerPathFind, codeShowPaneTemp, commandAddRelatedAttribute.clone(),
                                                        codeListLocation);
                                                if (flag) {
                                                    returnFlag = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (!returnFlag) {
                            //都找不到，获取上一个功能看看是否合适
                            if (theOpratingContainer.getParentOpratingContainer() != null &&
                                    theOpratingContainer.getParentOpratingContainer() instanceof AbstractCommandOpratingContainer) {
                                AbstractCommandOpratingContainer parentCommandOpratingContainer = (AbstractCommandOpratingContainer) theOpratingContainer.getParentOpratingContainer();
                                returnFlag = checkCodeFor_STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(
                                        parentCommandOpratingContainer, commandAddRelatedAttribute.clone(),
                                        operatingContainerParam
                                );
                            }
                        }

                    }
                }
            }
        }
        return returnFlag;
    }

    /**
     * 检测是否可以添加这句代码
     *
     * @param containerPathFind          这次要生成的功能的寻址信息
     * @param codeShowPane               要查的代码面板
     * @param commandAddRelatedAttribute 生成代码的相关属性，如移位属性，移位代码标记等
     * @param codeListLocation           对应要找的位置信息
     * @return
     */
    public static boolean checkGenerateCode(PathFind containerPathFind, CodeShowPane codeShowPane,
                                            CommandAddRelatedAttribute commandAddRelatedAttribute,
                                            CodeListLocation codeListLocation) {
        boolean returnFlag = false, flag;
        int situation = 1;
        if (containerPathFind != null) {
            if (PathFind.COMMAND_TYPE == containerPathFind.getMetaType()) {

                if (containerPathFind.whetherToOperateAddDelOrNotForMark()) {
                    if (MarkElementName.FUNCTION_MARK.equals(containerPathFind.getMarkType()) ||
                            MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(containerPathFind.getMarkType())) {
                        situation = 2;//直接添加到业务逻辑面板
                    } else {
                        situation = 4;//初始化、模块设置、主函数设置、其他函数设置一类
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

            switch (situation) {
                case 1://直接添加到格式
                    if (PathFind.FORMAT_TYPE == codeListLocation.getMetaType()) {
                        AddCodeEditParamForFormat addCodeEditParamForFormat;
                        for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                            addCodeEditParamForFormat = new AddCodeEditParamForFormat();
                            addCodeEditParamForFormat.setTrulyPathFind(trulyPathFind);
                            //addCodeEditParamForFormat.setCodeSerialNumber(codeSerialNumber);
                            addCodeEditParamForFormat.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                            flag = codeShowPane.checkWhetherItMatchesForFormat(addCodeEditParamForFormat);
                            if (flag) {
                                returnFlag = true;
                                break;
                            }
                        }
                    }
                    break;
                case 2://直接添加到业务逻辑面板
                    if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
                        AddCodeEditParamForMark addCodeEditParamForMark;
                        for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                            addCodeEditParamForMark = new AddCodeEditParamForMark();
                            addCodeEditParamForMark.setThanMarkElement(codeListLocation.getThanMarkElement());
                            //addCodeEditParamForMark.setCodeSerialNumber(codeSerialNumber);
                            addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                            addCodeEditParamForMark.setTrulyPathFind(trulyPathFind);
                            flag = codeShowPane.checkWhetherItMatchesForMark(addCodeEditParamForMark);
                            if (flag) {
                                returnFlag = true;
                                break;
                            }
                        }
                    }
                    break;
                case 3://添加到功能的功能拓展组件里面
                    if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
                        AddCodeEditParamForMark addCodeEditParamForMark;
                        for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                            addCodeEditParamForMark = new AddCodeEditParamForMark();
                            addCodeEditParamForMark.setThanMarkElement(codeListLocation.getThanMarkElement());
                            //addCodeEditParamForMark.setCodeSerialNumber(codeSerialNumber);
                            addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                            addCodeEditParamForMark.setTrulyPathFind(trulyPathFind);
                            flag = codeShowPane.checkWhetherItMatchesForMark(addCodeEditParamForMark);
                            if (flag) {
                                returnFlag = true;
                                break;
                            }
                        }
                    } else if (PathFind.FORMAT_TYPE == codeListLocation.getMetaType()) {
                        AddCodeEditParamForFormat addCodeEditParamForFormat;
                        for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                            addCodeEditParamForFormat = new AddCodeEditParamForFormat();
                            addCodeEditParamForFormat.setTrulyPathFind(trulyPathFind);
                            //addCodeEditParamForFormat.setCodeSerialNumber(codeSerialNumber);
                            addCodeEditParamForFormat.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                            flag = codeShowPane.checkWhetherItMatchesForFormat(addCodeEditParamForFormat);
                            if (flag) {
                                returnFlag = true;
                                break;
                            }
                        }
                    }
                    break;
                case 4://初始化、模块设置、主函数设置、其他函数设置一类
                    if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
                        AddCodeEditParamForMark addCodeEditParamForMark;
                        for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                            addCodeEditParamForMark = new AddCodeEditParamForMark();
                            addCodeEditParamForMark.setThanMarkElement(codeListLocation.getThanMarkElement());
                            //addCodeEditParamForMark.setCodeSerialNumber(codeSerialNumber);
                            addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute.clone());
                            addCodeEditParamForMark.setTrulyPathFind(trulyPathFind);
                            flag = codeShowPane.checkWhetherItMatchesForMark(addCodeEditParamForMark);
                            if (flag) {
                                returnFlag = true;
                                break;
                            }
                        }
                    }
                    break;
            }
        }
        return returnFlag;
    }


//    public static boolean generateCodeOnMark(CodePaneModelGenerateCodeResponseParam codeLocationInfo,
//                                             GeneralCommandCodeModel codeModel,
//                                             AbstractCommandOpratingContainer commandOpratingContainer) {
//        boolean editOrNot = false;
//        AddCodeEditParamForMark addCodeEditParamForMark;
//        BaseMarkElement thanMarkElement;
//        ArrayList<PathFind> codePathFindList;
//        CommandAddRelatedAttribute commandAddRelatedAttribute;
//        CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam;
//        CodeFormatFlagParam codeFormatFlagParam = codeLocationInfo.getCodeFormatFlagParam();
//        if (codeFormatFlagParam != null) {
//            CodeShowPane codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeFormatFlagParam);
//            if (codeShowPane != null) {
//                if (null != codeModel.getCodeUsePropertyParam()) {
//                    ArrayList<CodeListLocation> codeListLocationListTemp = codeLocationInfo.getCodeListLocationInfoList();
//                    for (CodeListLocation codeListLocation : codeListLocationListTemp) {
//                        editOrNot = false;
////                        if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {//要写的地方是放在标记组件中
//                        thanMarkElement = codeListLocation.getThanMarkElement();
//                        codePathFindList = codeListLocation.getCodePathFindList();
//                        for (PathFind pathFindTemp : codePathFindList) {
//                            if (pathFindTemp.whetherToOperateAddDelOrNotForMark() == true) {// 直接在业务逻辑面板上直接添加功能
//                                addCodeEditParamForMark = new AddCodeEditParamForMark();
//                                addCodeEditParamForMark.setCodeSerialNumber(commandOpratingContainer.getCodeSerialNumber());
//                                addCodeEditParamForMark.setTrulyPathFind(pathFindTemp);
//                                addCodeEditParamForMark.setCodeStatementParam(codeModel.getCodeFormatParam());
//                                addCodeEditParamForMark.setThanMarkElement(thanMarkElement);
//
//                                commandAddRelatedAttribute = new CommandAddRelatedAttribute();
//                                commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
//                                if (null != codeModel.getCodeLabelId()) {
//                                    commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//                                }
//                                addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute);
//                                codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToMark(addCodeEditParamForMark);
//                                if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
//                                    editOrNot = true;
//                                    commandOpratingContainer.thisCodeLocationInformation.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
//                                }
//                            }
//                        }
////                        }
//                    }
//                }
//            }
//        }
//        return editOrNot;
//    }

/**
 * 在命令功能中的功能拓展标签里面添加代码
 *
 * @param placeCodeLocationInfo
 * @param codeModel
 * @param commandOpratingContainer
 * @return
 */
//    public static boolean generateCodeOnFunctionAddComponentForMark(CodePaneModelGenerateCodeResponseParam placeCodeLocationInfo,
//                                                                    GeneralCommandCodeModel codeModel,
//                                                                    AbstractCommandOpratingContainer commandOpratingContainer, CommandAddRelatedAttribute commandAddRelatedAttribute) {
//        boolean editOrNot = false;
//        AddCodeEditParamForMark addCodeEditParamForMark;
//        BaseMarkElement thanMarkElement;
//        ArrayList<PathFind> codePathFindList;
////        CommandAddRelatedAttribute commandAddRelatedAttribute;
//        CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam;
//        CodeFormatFlagParam codeFormatFlagParam = placeCodeLocationInfo.getCodeFormatFlagParam();
//        if (codeFormatFlagParam != null) {
//            CodeShowPane codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeFormatFlagParam);
//            if (codeShowPane != null) {
//                ArrayList<CodeListLocation> codeListLocationListTemp = placeCodeLocationInfo.getCodeListLocationInfoList();
//                for (CodeListLocation codeListLocation : codeListLocationListTemp) {
//                    if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
//                        editOrNot = false;
//                        thanMarkElement = codeListLocation.getThanMarkElement();
//                        codePathFindList = codeListLocation.getCodePathFindList();
//                        for (PathFind pathFindTemp : codePathFindList) {
//                            addCodeEditParamForMark = new AddCodeEditParamForMark();
//                            addCodeEditParamForMark.setCodeSerialNumber(commandOpratingContainer.getCodeSerialNumber());
//                            addCodeEditParamForMark.setTrulyPathFind(pathFindTemp);
//                            addCodeEditParamForMark.setCodeStatementParam(codeModel.getCodeFormatParam());
//                            addCodeEditParamForMark.setThanMarkElement(thanMarkElement);
//
////                                commandAddRelatedAttribute = new CommandAddRelatedAttribute();
////                                commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_FUNCTION_ADD_COMPONENT_ADD_TYPE);
////                                if (null != codeModel.getCodeLabelId()) {
////                                    commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
////                                }
//                            addCodeEditParamForMark.setCommandAddRelatedAttribute(commandAddRelatedAttribute);
//                            codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToMark(addCodeEditParamForMark);
//                            if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
//                                editOrNot = true;
//                                commandOpratingContainer.thisCodeLocationInformation.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
//                            }
//                        }
//                    } else if (PathFind.FORMAT_TYPE == codeListLocation.getMetaType()) {
//                        editOrNot = false;
//                        AddCodeEditParamForFormat addCodeEditParamForFormat;
//                        codePathFindList = codeListLocation.getCodePathFindList();
//                        for (PathFind pathFindTemp : codePathFindList) {
//                            if (pathFindTemp.whetherToOperateForFormat()) {
//                                addCodeEditParamForFormat = new AddCodeEditParamForFormat();
//                                addCodeEditParamForFormat.setCodeStatementParam(codeModel.getCodeFormatParam());
//                                addCodeEditParamForFormat.setTrulyPathFind(pathFindTemp);
//                                addCodeEditParamForFormat.setCodeSerialNumber(commandOpratingContainer.getCodeSerialNumber());
//                                addCodeEditParamForFormat.setCommandAddRelatedAttribute(commandAddRelatedAttribute);
//                                codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToFormat(addCodeEditParamForFormat);
//                                if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
//                                    editOrNot = true;
//                                    commandOpratingContainer.thisCodeLocationInformation.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return editOrNot;
//    }

/**
 * 在格式类型的功能拓展组件上面生成代码（不管是直接在格式的功能拓展组件添加，还是在内部的功能上添加都用该方法）
 *
 * @param placeCodeLocationInfo
 * @param codeModel
 * @param commandAddRelatedAttribute
 * @return
 */
//    public static boolean generateCodeOnFormat(CodePaneModelGenerateCodeResponseParam placeCodeLocationInfo, GeneralCommandCodeModel codeModel,
//                                               CommandAddRelatedAttribute commandAddRelatedAttribute,
//                                               AbstractCommandOpratingContainer commandOpratingContainer) {
//        boolean editOrNot = false;
//        CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam;
//        CodeFormatFlagParam codeFormatFlagParam = placeCodeLocationInfo.getCodeFormatFlagParam();
//        if (codeFormatFlagParam != null) {
//            AddCodeEditParamForFormat addCodeEditParam;
//            ArrayList<PathFind> codePathFindList;
//            CodeShowPane codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeFormatFlagParam);
//            if (codeShowPane != null) {
//                ArrayList<CodeListLocation> codeListLocationListTemp = placeCodeLocationInfo.getCodeListLocationInfoList();
//                for (CodeListLocation codeListLocation : codeListLocationListTemp) {
//                    codePathFindList = codeListLocation.getCodePathFindList();
//                    for (PathFind pathFindTemp : codePathFindList) {
//                        addCodeEditParam = new AddCodeEditParamForFormat();
//                        addCodeEditParam.setCodeSerialNumber(commandOpratingContainer.getCodeSerialNumber());
//                        addCodeEditParam.setCodeStatementParam(codeModel.getCodeFormatParam());
//                        addCodeEditParam.setCommandAddRelatedAttribute(commandAddRelatedAttribute);
//                        addCodeEditParam.setTrulyPathFind(pathFindTemp);
//                        codePaneModelGenerateCodeResponseParam = codeShowPane.addCodeToFormat(addCodeEditParam);
//                        if (codePaneModelGenerateCodeResponseParam.isEditOrNot()) {
//                            editOrNot = true;
//                            commandOpratingContainer.thisCodeLocationInformation.addCodePaneForCodeFormatFlagParam(codePaneModelGenerateCodeResponseParam);
//                        }
//                    }
//                }
//            }
//        }
//        return editOrNot;
//    }


    /**
     * 处理代码属性的相关操作
     */
    public static void handlesOperationsRelatedToCodeProperties
    (ArrayList<AbstractCodeUseProperty> codeUsePropertyList,
     CodeShowPane codeShowPane, ModuleInfo moduleInfo, Module module,
     String moduleId) {

        inserNeedModuleImportCode(codeUsePropertyList, codeShowPane);
        inserThisModuleImportCode(codeUsePropertyList, codeShowPane, moduleInfo, module, moduleId);
    }

    /**
     * 插入本模块的引入代码
     *
     * @param codeUsePropertyList
     * @param codeShowPane
     * @param moduleInfo
     * @param module
     * @param moduleId
     */
    private static void inserThisModuleImportCode(ArrayList<AbstractCodeUseProperty> codeUsePropertyList,
                                                  CodeShowPane codeShowPane, ModuleInfo moduleInfo, Module module,
                                                  String moduleId) {
        if (codeShowPane != null) {
            if (codeUsePropertyList.size() == 0 || (!GeneralCommandCodeModel.contant(
                    codeUsePropertyList, AbstractCodeUseProperty.NoNeedToInsertImportCodeType))) {//需要插入本模块的代码
                addImportCodes(codeShowPane, moduleInfo, module, moduleId);
            }
        }
    }

    /**
     * 插入需要的模块的引入代码
     *
     * @param codeUsePropertyList
     * @param codeShowPane
     */
    private static void inserNeedModuleImportCode
    (ArrayList<AbstractCodeUseProperty> codeUsePropertyList, CodeShowPane codeShowPane) {
        if (codeShowPane != null) {
            if (GeneralCommandCodeModel.contant(
                    codeUsePropertyList, AbstractCodeUseProperty.NeedUseModuleImportCodeType)) {
                NeedUseModuleImportCode needUseModuleImportCode = GeneralCommandCodeModel.getNeedUseModuleImportCodeProperty(codeUsePropertyList);
                if (needUseModuleImportCode != null && needUseModuleImportCode.getNeedUseModuleList().size() > 0) {
                    for (String needModuleTemp : needUseModuleImportCode.getNeedUseModuleList()) {
                        Module module = SysService.MODULE_SERVICE.getModuleById(needModuleTemp);
                        ModuleInfo moduleInfo = SysService.MODULE_INFO_SERVICE.getModuleInfo(needModuleTemp);
                        if (module != null && moduleInfo != null) {
                            addImportCodes(codeShowPane, moduleInfo, module, needModuleTemp);
                        }
                    }
                }
            }
        }
    }


    /**
     * 添加引入代码
     *
     * @param codeShowPane 要添加的代码面板
     * @param moduleInfo   插入的引入代码，对应的模块信息
     * @param module       插入的引入代码，对应的模块
     * @param moduleId     是哪个模块要插入该模块的代码的
     */
    public static void addImportCodes(CodeShowPane codeShowPane, ModuleInfo moduleInfo, Module module, String
            moduleId) {
        if (moduleInfo != null) {
            if (moduleInfo.getNumOfImport() > 0) {// 此前添加过内容，显示之前编辑过的内容
                CodeFormatFlagParam codeFormatFlagParam = codeShowPane.getCodeFormatFlagParam();
                if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType() &&
                        Module.NEW_STATE.equals(module.getNeedModuleParam()) == false) {//如果是添加到模块类型的代码文件，查看是不是本模块或者本模块所需要的模块，不是的话，添加对应引入代码
                    if (!codeFormatFlagParam.getModuleId().equals(moduleId)) {
                        ArrayList<String> needModuleIdList = JSON.parseObject(module.getNeedModuleParam(), new TypeReference<ArrayList<String>>() {
                        });
                        ArrayList<String> moduleIdList = new ArrayList<>();
                        moduleIdList.add(module.getModuleId());
                        moduleIdList.addAll(needModuleIdList);

                        if (moduleIdList.contains(codeFormatFlagParam.getModuleId()) == false) {
                            List<ImportCode> list = SysService.IMPORT_CODE_SERVICE.getImportCodeList(moduleId);
                            ImportMarkElement markElement;
                            for (ImportCode importCode : list) {
                                markElement = new ImportMarkElement();
                                markElement.setModuleId(moduleId);
                                markElement.setOrdinal(importCode.getOrdinal());
                                markElement.setCodeLabelId(importCode.getCodeLabelId());

                                codeShowPane.addImportCode(markElement, importCode);
                            }
                        }
                    }
                } else if (CodeFormatFlagParam.MODULE_TYPE != codeFormatFlagParam.getFormatType()) {//主要模板或者可选模板的对应代码文件，需要添加引入代码
                    List<ImportCode> list = SysService.IMPORT_CODE_SERVICE.getImportCodeList(moduleId);
                    ImportMarkElement markElement;
                    for (ImportCode importCode : list) {
                        markElement = new ImportMarkElement();
                        markElement.setModuleId(moduleId);
                        markElement.setOrdinal(importCode.getOrdinal());
                        markElement.setCodeLabelId(importCode.getCodeLabelId());

                        codeShowPane.addImportCode(markElement, importCode);
                    }
                }
            }
        }
    }

    /**
     * 添加命令模型对应的引入代码
     *
     * @param codeShowPane
     * @param commandCodeModelImportCodeParam
     */
    public static void addCommandContainerImportCodes(CodeShowPane codeShowPane, String commandCodeModelImportCodeParam) {
        if (commandCodeModelImportCodeParam != null) {
            ArrayList<CommandContainerImportCode> list = JSON.parseObject(commandCodeModelImportCodeParam,
                    new TypeReference<ArrayList<CommandContainerImportCode>>() {
                    });
            if (codeShowPane != null) {
                ImportMarkElement markElement;
                ImportCode importCode;
                for (CommandContainerImportCode commandContainerImportCode : list) {
                    markElement = new ImportMarkElement();
                    markElement.setOrdinal(commandContainerImportCode.getOrdinal());
                    markElement.setCodeLabelId(commandContainerImportCode.getCodeLabelId());

                    importCode = new ImportCode();
                    importCode.setOrdinal(commandContainerImportCode.getOrdinal());
                    importCode.setCode(commandContainerImportCode.getCode());
                    importCode.setCodeLabelId(commandContainerImportCode.getCodeLabelId());

                    codeShowPane.addImportCode(markElement, importCode);
                }
            }
        }
    }

}
