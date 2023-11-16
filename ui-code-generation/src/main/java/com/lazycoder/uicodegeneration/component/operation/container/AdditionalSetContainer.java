package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.AdditionalSetMarkElement;
import com.lazycoder.service.vo.meta.AdditionalSetMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalSetOpratingContainerModel;
import java.io.File;
import java.util.ArrayList;
import lombok.Getter;

public class AdditionalSetContainer extends AbstractCommandOpratingContainer {

    /**
     *
     */
    private static final long serialVersionUID = 8090193256650187906L;

    private AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam;

    @Getter
    private AdditionalSetMetaModel metaModel = null;

    @Getter
    private String additionalSetTypeName = "";

    /**
     * 新建
     *
     * @param additionalSetTypeOperatingContainerParam
     * @param expanded
     * @param canBeDelOrNot
     */
    public AdditionalSetContainer(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam,
                                  AdditionalSetMetaModel metaModel, boolean expanded, boolean canBeDelOrNot) {
        super();
        // TODO Auto-generated constructor stub
        additionalSetTypeOperatingContainerParam.setThisOpratingContainer(this);
        this.additionalSetTypeOperatingContainerParam = additionalSetTypeOperatingContainerParam;
        this.metaModel = SysService.ADDITIONAL_SET_SERVICE
                .getAdditionalSetMetaModel(additionalSetTypeOperatingContainerParam.getFeatureSelectionModel());

        if (GeneralCommandOperatingModel.TRUE_ == metaModel.getOperatingModel().getHiddenState()) {
            this.hiddenState = true;
        } else {
            this.hiddenState = false;
        }
        init(expanded, canBeDelOrNot, additionalSetTypeOperatingContainerParam.getCodeControlPane());
        generateCodeAndOpratingContainer();
        setAppropriateSize(PROPOTION);

    }

    /**
     * 还原
     *
     * @param additionalSetTypeOperatingContainerParam
     * @param model
     * @param expanded
     */
    public AdditionalSetContainer(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam,
                                  AdditionalSetOpratingContainerModel model, boolean expanded) {
        // TODO Auto-generated constructor stub
        super();
        this.additionalSetTypeOperatingContainerParam = additionalSetTypeOperatingContainerParam;
        metaModel = model.getMetaModel();

        restoreInit(model);
        init(expanded, model.isCanBeDelOrNot(), additionalSetTypeOperatingContainerParam.getCodeControlPane());
        CommandContainerComponentParam commandContainerComponentParam = getContainerComponentParam(null);
        restorePaneContent(model, commandContainerComponentParam);
        setAppropriateSize(PROPOTION);
    }

    @Override
    protected void init(boolean expanded, boolean canBeDelOrNot, AbstractCodeControlPane codeControlPane) {
        this.additionalSetTypeName = additionalSetTypeOperatingContainerParam.getAdditionalSetType();
        super.init(expanded, canBeDelOrNot, codeControlPane);
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
        pathFind = additionalSetTypeOperatingContainerParam.getCodeControlPane().getPathFind();

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
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            CodeListLocation codeListLocation;
            AdditionalSetMarkElement markElement;
            String pathParam;
            CodeShowPane codeShowPane;
            boolean flag;
            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;

            if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(pathFind.getMarkType())) {
                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
                    for (FormatTypeCodeModel codeModel : this.metaModel.getCodeModelList()) {
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
                        markElement = new AdditionalSetMarkElement();
                        markElement.setClassificationSerial(metaModel.getOperatingModel().getTypeSerialNumber());
                        markElement.setOperatingSerialNumber(codeModel.getOrdinal());
                        markElement.setCodeNumber(codeModel.getCodeOrdinal());
                        markElement.setCodeLabelId(codeModel.getCodeLabelId());
                        codeListLocation.addCodePathFindForMark(markElement, pathFind);
                        codeListLocationArrayList.add(codeListLocation);

                        pathParam = codeModel.getPathParam();
                        codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, this.additionalSetTypeOperatingContainerParam.getFormatControlPane());
                        if (codeShowPane != null) {
                            flag = OpratingContainerStaticMethod.generateCode(this,
                                    codeShowPane, commandAddRelatedAttribute,
                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
                                    inserNewLineOrNot, codeListLocationArrayList);
                            if (flag) {
                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 查看这里是不是可以添加这个代码的本功能容器
     *
     * @param additionalSetTypeOperatingContainerParam
     * @param metaModel
     * @return
     */
    public static boolean checkWhetherItMatches(AdditionalSetTypeOperatingContainerParam additionalSetTypeOperatingContainerParam,
                                                AdditionalSetMetaModel metaModel) {
        boolean returnFlag = false;
        PathFind pathFind = additionalSetTypeOperatingContainerParam.getCodeControlPane().getPathFind();

        if (pathFind != null) {
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            CodeListLocation codeListLocation;
            AdditionalSetMarkElement markElement;
            String pathParam;
            CodeShowPane codeShowPane;
            boolean flag;
            if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(pathFind.getMarkType())) {
                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
                    returnFlag = true;
                    for (FormatTypeCodeModel codeModel : metaModel.getCodeModelList()) {
                        commandAddRelatedAttribute = new CommandAddRelatedAttribute();
                        commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
                        commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
                        commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                        codeListLocation = new CodeListLocation();
                        markElement = new AdditionalSetMarkElement();
                        markElement.setClassificationSerial(metaModel.getOperatingModel().getTypeSerialNumber());
                        markElement.setOperatingSerialNumber(codeModel.getOrdinal());
                        markElement.setCodeNumber(codeModel.getCodeOrdinal());
                        markElement.setCodeLabelId(codeModel.getCodeLabelId());
                        codeListLocation.addCodePathFindForMark(markElement, pathFind);

                        pathParam = codeModel.getPathParam();
                        codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, additionalSetTypeOperatingContainerParam.getFormatControlPane());
                        if (codeShowPane != null) {
                            flag = OpratingContainerStaticMethod.checkGenerateCode(pathFind, codeShowPane,
                                    commandAddRelatedAttribute,  codeListLocation);

                            if (!flag) {
                                returnFlag = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return returnFlag;
    }


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
                .setFormatControlPane(this.additionalSetTypeOperatingContainerParam.getFormatControlPane());
        codeGenerationalOpratingContainerParam
                .setCodeControlPane(this.additionalSetTypeOperatingContainerParam.getCodeControlPane());
        codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);
        codeGenerationalOpratingContainerParam.setCodeSerialNumber(codeSerialNumber);
        codeGenerationalOpratingContainerParam.setControlStatementFormat(controlStatementFormat);
        codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(this);
        codeGenerationalOpratingContainerParam.setThisOpratingContainer(this);

        return codeGenerationalOpratingContainerParam;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return MarkElementName.ADDITIONAL_SET_TYPE_MARK;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return NotNamed.additionalSet.getClassName();
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return NotNamed.additionalSet.getModuleName();
    }

    @Override
    public String getModuleId() {
        return "";
    }

    @Override
    public AbstractCodeControlPane getCodeControlPane() {
        return this.additionalSetTypeOperatingContainerParam.getCodeControlPane();
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return additionalSetTypeOperatingContainerParam.getAdditionalInfo().getAdditionalSerialNumber();
    }

    @Override
    public int getOrdinalInUserDB() {
        return metaModel.getOperatingModel().getOrdinal();
    }

    @Override
    protected CodeShowPane getDeafaultCodePane() {
        // TODO Auto-generated method stub
        return this.additionalSetTypeOperatingContainerParam.getFormatControlPane().getDefaultPane();
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
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        AdditionalSetOpratingContainerModel theModel = (AdditionalSetOpratingContainerModel) model;
        super.setParam(theModel);
        theModel.setAdditionalSetTypeName(additionalSetTypeOperatingContainerParam.getAdditionalSetType());
        theModel.setMetaModel(metaModel);
    }

    @Override
    public AdditionalSetOpratingContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        AdditionalSetOpratingContainerModel theModel = new AdditionalSetOpratingContainerModel();
        setParam(theModel);
        return theModel;
    }

    @Override
    public AdditionalSetMarkElement getCorrespondingMarkElement() {
        return new AdditionalSetMarkElement();
    }

    @Override
    public AbstractCommandOpratingContainer getFirstCommandOpratingContainer() {
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
