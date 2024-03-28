package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.featureSelectionModel.ModuleSetFeatureSelectionModel;
import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.SetMarkElement;
import com.lazycoder.service.vo.meta.ModuleSetMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.ModuleTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.ModuleSetOpratingContainerModel;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;

/***
 * 承载一条模块设置方法的基本组件**
 *
 * @author admin
 *
 */
public class ModuleSetOpratingContainer extends AbstractCommandOpratingContainer {

    /**
     *
     */
    private static final long serialVersionUID = 6231298479771123333L;

    private ModuleTypeOperatingContainerParam moduleTypeContainerParam;

    @Getter
    private ModuleSetMetaModel metaModel = null;

    private Module module = null;

    @Getter
    private String moduleTypeName = null;

    /**
     * 新建
     *
     * @param moduleTypeContainerParam
     * @param metaModel
     * @param expanded
     * @param canBeDelOrNot
     */
    public ModuleSetOpratingContainer(ModuleTypeOperatingContainerParam moduleTypeContainerParam,
                                      ModuleSetMetaModel metaModel, boolean expanded, boolean canBeDelOrNot) {
        super();
        // TODO Auto-generated constructor stub
        moduleTypeContainerParam.setThisOpratingContainer(this);
        this.moduleTypeContainerParam = moduleTypeContainerParam;
        this.module = moduleTypeContainerParam.getModule();
        this.moduleTypeName = moduleTypeContainerParam.getModuleSetType();

        this.metaModel = metaModel;
        if (metaModel.getOperatingModel().getHiddenState() == GeneralCommandOperatingModel.TRUE_) {
            this.hiddenState = true;
        } else {
            this.hiddenState = false;
        }
        init(expanded, canBeDelOrNot, moduleTypeContainerParam.getCodeControlPane());
        generateCodeAndOpratingContainer(moduleTypeContainerParam.getModuleSetFeatureSelectionModel());
        setAppropriateSize(PROPOTION);

//        moduleTypeContainerParam.setModuleSetFeatureSelectionModel(null);
    }

    /**
     * 还原
     *
     * @param moduleTypeContainerParam
     * @param model
     * @param expanded
     */
    public ModuleSetOpratingContainer(ModuleTypeOperatingContainerParam moduleTypeContainerParam,
                                      ModuleSetOpratingContainerModel model, boolean expanded) {
        // TODO Auto-generated constructor stub
        super();

        this.moduleTypeName = moduleTypeContainerParam.getModuleSetType();
        this.metaModel = model.getMetaModel();
        this.module = model.getModule();
        this.moduleTypeContainerParam = moduleTypeContainerParam;
        moduleTypeContainerParam.setThisOpratingContainer(this);

        restoreInit(model);
        init(expanded, model.isCanBeDelOrNot(), moduleTypeContainerParam.getCodeControlPane());
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
    private void generateCodeAndOpratingContainer(ModuleSetFeatureSelectionModel featureSelectionModel) {
        codeSerialNumber = CodeIDGenerator.generateCodeSerialNumber();
        pathFind = moduleTypeContainerParam.getCodeControlPane().getPathFind();

        if (metaModel != null) {
            setThisToolTipText(metaModel.getOperatingModel().getShowText());
            generateCode();

            // 写默认内容
            CommandContainerComponentParam deafaultCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                    metaModel.getOperatingModel().getDefaultControlStatementFormat());
            deafaultOperatingPane.generateOperationalContent(deafaultCodeGenerationalOpratingContainerParam);

            // 写隐藏内容
            if (this.hiddenState == true) {
                CommandContainerComponentParam hiddenCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                        metaModel.getOperatingModel().getHiddenControlStatementFormat());
                hiddenOperatingPane.generateOperationalContent(hiddenCodeGenerationalOpratingContainerParam);
            }
        }
    }

    private void generateCode() {
        if (pathFind != null) {
            ArrayList<CodeListLocation> codeListLocationArrayList;
            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            CodeListLocation codeListLocation;
            SetMarkElement markElement;
            String pathParam;
            CodeShowPane codeShowPane;
            boolean flag;
//            if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
//                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
//                    if (MarkElementName.SET_MARK.equals(pathFind.getMarkType())) {
            ModuleInfo moduleInfo = this.moduleTypeContainerParam.getModuleInfo() == null ?
                    SysService.MODULE_INFO_SERVICE.getModuleInfo(this.getModuleId()) : this.moduleTypeContainerParam.getModuleInfo();

            for (ModuleSetCodeModel codeModel : this.metaModel.getCodeModelList()) {
                boolean inserNewLineOrNot = true;
                if (null != codeModel.getCodeUsePropertyParam()) {
                    setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                    if (GeneralCommandCodeModel.contant(
                            setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                        inserNewLineOrNot = false;
                    }
                }
                commandAddRelatedAttribute = new CommandAddRelatedAttribute();
                commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
                commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                codeListLocationArrayList = new ArrayList<>();
                codeListLocation = new CodeListLocation();
                markElement = new SetMarkElement();
                markElement.setModuleId(metaModel.getOperatingModel().getModuleId());
                markElement.setClassificationSerial(codeModel.getTypeSerialNumber());
                markElement.setOperatingSerialNumber(codeModel.getOrdinal());
                markElement.setCodeNumber(codeModel.getCodeOrdinal());
                markElement.setCodeLabelId(codeModel.getCodeLabelId());
                codeListLocation.addCodePathFindForMark(markElement, pathFind);
                codeListLocationArrayList.add(codeListLocation);

                pathParam = codeModel.getPathParam();
                codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, this.moduleTypeContainerParam.getFormatControlPane());
                if (codeShowPane != null) {
                    flag = OpratingContainerStaticMethod.generateCode(this,
                            codeShowPane, commandAddRelatedAttribute,
                            codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
                            inserNewLineOrNot, codeListLocationArrayList);
                    if (flag) {
                        CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);

                        if (null != codeModel.getCodeUsePropertyParam()) {
                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                            OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                    setPropertyTempList, codeShowPane, moduleInfo, module, this.getModuleId()
                            );
                        }
                        OpratingContainerStaticMethod.addCommandContainerImportCodes(codeShowPane, codeModel.getImportCodeParam());
                    }
                }
            }
//                    }
//                }
//            }
        }
    }

    /**
     * 查看这里是不是可以添加这个代码的本功能容器
     *
     * @param moduleTypeContainerParam
     * @param metaModel
     * @return
     */
    public static boolean checkWhetherItMatches(ModuleTypeOperatingContainerParam moduleTypeContainerParam,
                                                ModuleSetMetaModel metaModel) {
        boolean returnFlag = true;
        CommandAddRelatedAttribute commandAddRelatedAttribute;
        CodeListLocation codeListLocation;
        SetMarkElement markElement;
        String pathParam;
        CodeShowPane codeShowPane;
        boolean flag;
        PathFind pathFind = moduleTypeContainerParam.getCodeControlPane().getPathFind();

        for (ModuleSetCodeModel codeModel : metaModel.getCodeModelList()) {

            commandAddRelatedAttribute = new CommandAddRelatedAttribute();
            commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
            commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

            codeListLocation = new CodeListLocation();
            markElement = new SetMarkElement();
            markElement.setModuleId(metaModel.getOperatingModel().getModuleId());
            markElement.setClassificationSerial(codeModel.getTypeSerialNumber());
            markElement.setOperatingSerialNumber(codeModel.getOrdinal());
            markElement.setCodeNumber(codeModel.getCodeOrdinal());
            markElement.setCodeLabelId(codeModel.getCodeLabelId());
            codeListLocation.addCodePathFindForMark(markElement, pathFind);

            pathParam = codeModel.getPathParam();
            codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, moduleTypeContainerParam.getFormatControlPane());
            if (codeShowPane != null) {
                flag = OpratingContainerStaticMethod.checkGenerateCode(pathFind, codeShowPane, commandAddRelatedAttribute,
                        codeListLocation);
                if (!flag) {
                    returnFlag = false;
                    break;
                }
            }
        }
        return returnFlag;
    }
//    public static boolean checkWhetherItMatches(ModuleTypeOperatingContainerParam moduleTypeContainerParam,
//                                                ModuleSetMetaModel metaModel) {
//        boolean returnFlag = false, tempFlag = false;
//        ArrayList<ModuleSetCodeModel> codeModelList = metaModel.getCodeModelList();
//        if (codeModelList != null) {
//            AddCodeEditParamForMark addCodeEditParamForMark;
//
//            CodeShowPane correspondingCodePane;
//            PathFind pathFind = moduleTypeContainerParam.getCodeControlPane().getPathFind();
//            ArrayList<NeedFirstOpratingContainerParam> needFirstOpratingContainerParamList =
//                    getNeedFirstOpratingContainerParamList(metaModel, moduleTypeContainerParam.getFormatControlPane());
//
//            returnFlag = true;
//            for (ModuleSetCodeModel moduleSetCodeModelTemp : codeModelList) {
//                tempFlag = false;
//                for (NeedFirstOpratingContainerParam needFirstOpratingContainerParamTemp : needFirstOpratingContainerParamList) {
//                    correspondingCodePane = needFirstOpratingContainerParamTemp.getTheCodePane();
//
//                    addCodeEditParamForMark = new AddCodeEditParamForMark();
//                    addCodeEditParamForMark.setTrulyPathFind(pathFind);
//                    addCodeEditParamForMark.setCodeStatementParam(moduleSetCodeModelTemp.getCodeFormatParam());
//                    addCodeEditParamForMark.setThanMarkElement(needFirstOpratingContainerParamTemp.getThanMarkElement());
//                    if (null != moduleSetCodeModelTemp.getCodeLabelId()) {
//                        addCodeEditParamForMark.setCodeLabelId(moduleSetCodeModelTemp.getCodeLabelId());
//                        addCodeEditParamForMark.setAddToMethodAddComponent(true);
//                    }
//                    boolean flag = correspondingCodePane.checkWhetherItMatchesForMark(addCodeEditParamForMark);
//                    if (flag == true) {
//                        tempFlag = true;
//                        break;
//                    }
//                }
//                if (tempFlag == false) {
//                    returnFlag = false;
//                    break;
//                }
//            }
//        }
//        return returnFlag;
//    }


    /**
     * 设置组件参数
     *
     * @param controlStatementFormat
     * @return
     */
    @Override
    protected CommandContainerComponentParam getContainerComponentParam(String controlStatementFormat) {
        CommandContainerComponentParam codeGenerationalOpratingContainerParam = super.getContainerComponentParam(controlStatementFormat);
        codeGenerationalOpratingContainerParam
                .setFormatControlPane(this.moduleTypeContainerParam.getFormatControlPane());
        codeGenerationalOpratingContainerParam.setThisOpratingContainer(this);
        codeGenerationalOpratingContainerParam.setCodeControlPane(this.moduleTypeContainerParam.getCodeControlPane());
        codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);
        codeGenerationalOpratingContainerParam.setModuleInfo(moduleTypeContainerParam.getModuleInfo());
        codeGenerationalOpratingContainerParam.setModule(moduleTypeContainerParam.getModule());
        codeGenerationalOpratingContainerParam.setCodeSerialNumber(codeSerialNumber);
        codeGenerationalOpratingContainerParam.setControlStatementFormat(controlStatementFormat);
        codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(this);
        return codeGenerationalOpratingContainerParam;
    }

    /**
     * 获取模块设置分类对应的序号
     *
     * @return
     */
    public int getModuleSetOrdinal() {
        return metaModel.getOperatingModel().getTypeSerialNumber();
    }

    @Override
    protected CodeShowPane getDeafaultCodePane() {
        // TODO Auto-generated method stub
        return this.moduleTypeContainerParam.getFormatControlPane().getDefaultPane();
    }

    @Override
    public File getImageRootPath() {
        File file = SourceGenerateFileStructure.getModulePictureFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, module.getModuleId());
        return file;
    }

    @Override
    public int getOrdinalInUserDB() {
        return metaModel.getOperatingModel().getOrdinal();
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = SourceGenerateFileStructure.getModuleNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, module.getModuleId());
        return file;
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        ModuleSetOpratingContainerModel theModel = (ModuleSetOpratingContainerModel) model;
        super.setParam(theModel);
        theModel.setMetaModel(metaModel);
        theModel.setModule(module);
    }

    @Override
    public ModuleSetOpratingContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        ModuleSetOpratingContainerModel model = new ModuleSetOpratingContainerModel();
        setParam(model);
        return model;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return MarkElementName.SET_MARK;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return module.getClassName();
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return module.getModuleName();
    }

    @Override
    public String getModuleId() {
        return module.getModuleId();
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public SetMarkElement getCorrespondingMarkElement() {
        return new SetMarkElement();
    }

    @Override
    public AbstractCodeControlPane getCodeControlPane() {
        return this.moduleTypeContainerParam.getCodeControlPane();
    }

    /**
     * 获取该容器当前使用设置属性
     *
     * @return
     */
    public int getSetProperty() {
        if (metaModel != null) {
            return metaModel.getOperatingModel().getSetProperty();
        }
        return FunctionUseProperty.no.getSysDictionaryValue();
    }

    @Override
    public ModuleSetOpratingContainer getFirstCommandOpratingContainer() {
        return this;
    }

    @Override
    public AbstractFormatContainer getFormatContainer() {
        return null;
    }

    @Override
    public OpratingContainerInterface getParentOpratingContainer() {
        return null;
    }

}
