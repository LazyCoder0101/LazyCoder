package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.vo.element.mark.MainSetMarkElement;
import com.lazycoder.service.vo.meta.MainSetMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.MainSetTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.MainSetOpratingContainerModel;
import java.io.File;
import java.util.ArrayList;
import lombok.Getter;

public class MainSetContainer extends AbstractCommandOpratingContainer {

    /**
     *
     */
    private static final long serialVersionUID = 8090193256650187906L;

//	private static final double propotion = 0.233;

    private MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam;

    @Getter
    private MainSetMetaModel metaModel = null;

    @Getter
    private String mainSetTypeName = "";

    /**
     * 新建
     *
     * @param mainSetTypeOperatingContainerParam
     * @param metaModel
     * @param expanded
     * @param canBeDelOrNot
     */
    public MainSetContainer(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam, MainSetMetaModel metaModel,
                            boolean expanded, boolean canBeDelOrNot) {
        super();
        // TODO Auto-generated constructor stub
        mainSetTypeOperatingContainerParam.setThisOpratingContainer(this);
        this.mainSetTypeOperatingContainerParam = mainSetTypeOperatingContainerParam;
        this.metaModel = metaModel;

        if (GeneralCommandOperatingModel.TRUE_ == metaModel.getOperatingModel().getHiddenState()) {
            this.hiddenState = true;
        } else {
            this.hiddenState = false;
        }
        init(expanded, canBeDelOrNot, mainSetTypeOperatingContainerParam.getCodeControlPane());
        generateCodeAndOpratingContainer();
        setAppropriateSize(PROPOTION);
    }

    /**
     * 还原
     *
     * @param mainSetTypeOperatingContainerParam
     * @param model
     * @param expanded
     */
    public MainSetContainer(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam,
                            MainSetOpratingContainerModel model, boolean expanded) {
        // TODO Auto-generated constructor stub
        super();

        this.mainSetTypeOperatingContainerParam = mainSetTypeOperatingContainerParam;
        metaModel = model.getMetaModel();
        mainSetTypeOperatingContainerParam.setThisOpratingContainer(this);

        restoreInit(model);
        init(expanded, model.isCanBeDelOrNot(), mainSetTypeOperatingContainerParam.getCodeControlPane());
        CommandContainerComponentParam commandContainerComponentParam = getContainerComponentParam(null);
        restorePaneContent(model, commandContainerComponentParam);
        setAppropriateSize(PROPOTION);
    }

    @Override
    protected void restorePaneContent(AbstractCommandOperatingContainerModel commandOperatingContainerModel, GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        super.restorePaneContent(commandOperatingContainerModel, codeGenerationalOpratingContainerParam);
        setThisToolTipText(metaModel.getOperatingModel().getShowText());
    }

    @Override
    protected void init(boolean expanded, boolean canBeDelOrNot, AbstractCodeControlPane codeControlPane) {
        this.mainSetTypeName = metaModel.getOperatingModel().getTypeName();
        super.init(expanded, canBeDelOrNot, codeControlPane);
    }

    /**
     * 生成代码和组件
     */
    private void generateCodeAndOpratingContainer() {
        codeSerialNumber = CodeIDGenerator.generateCodeSerialNumber();
        pathFind = mainSetTypeOperatingContainerParam.getCodeControlPane().getPathFind();

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
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            ArrayList<CodeListLocation> codeListLocationArrayList;
            CodeListLocation codeListLocation;
            MainSetMarkElement markElement;
            String pathParam;
            CodeShowPane codeShowPane;
            boolean flag;
            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
            if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.MAIN_SET_TYPE_MARK.equals(pathFind.getMarkType())) {
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
                        markElement = new MainSetMarkElement();
                        markElement.setClassificationSerial(metaModel.getOperatingModel().getTypeSerialNumber());
                        markElement.setOperatingSerialNumber(codeModel.getOrdinal());
                        markElement.setCodeNumber(codeModel.getCodeOrdinal());
                        markElement.setCodeLabelId(codeModel.getCodeLabelId());
                        codeListLocation.addCodePathFindForMark(markElement, pathFind);
                        codeListLocationArrayList.add(codeListLocation);

                        pathParam = codeModel.getPathParam();
                        codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, this.mainSetTypeOperatingContainerParam.getFormatControlPane());
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
     * @param mainSetTypeOperatingContainerParam
     * @param metaModel
     * @return
     */
    public static boolean checkWhetherItMatches(MainSetTypeOperatingContainerParam mainSetTypeOperatingContainerParam,
                                                MainSetMetaModel metaModel) {
        boolean returnFlag = true;
        CommandAddRelatedAttribute commandAddRelatedAttribute;
        CodeListLocation codeListLocation;
        MainSetMarkElement markElement;
        String pathParam;
        CodeShowPane codeShowPane;
        boolean flag;
        PathFind pathFind = mainSetTypeOperatingContainerParam.getCodeControlPane().getPathFind();

        for (FormatTypeCodeModel codeModel : metaModel.getCodeModelList()) {

            commandAddRelatedAttribute = new CommandAddRelatedAttribute();
            commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
            commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
            commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

            codeListLocation = new CodeListLocation();
            markElement = new MainSetMarkElement();
            markElement.setClassificationSerial(metaModel.getOperatingModel().getTypeSerialNumber());
            markElement.setOperatingSerialNumber(codeModel.getOrdinal());
            markElement.setCodeNumber(codeModel.getCodeOrdinal());
            markElement.setCodeLabelId(codeModel.getCodeLabelId());
            codeListLocation.addCodePathFindForMark(markElement, pathFind);

            pathParam = codeModel.getPathParam();
            codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, mainSetTypeOperatingContainerParam.getFormatControlPane());
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
                .setFormatControlPane(this.mainSetTypeOperatingContainerParam.getFormatControlPane());
        codeGenerationalOpratingContainerParam
                .setCodeControlPane(this.mainSetTypeOperatingContainerParam.getCodeControlPane());
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
        return MarkElementName.MAIN_SET_TYPE_MARK;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return NotNamed.mainSet.getClassName();
    }

    @Override
    public int getOrdinalInUserDB() {
        return metaModel.getOperatingModel().getOrdinal();
    }

    @Override
    public String getModuleId() {
        return "";
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return NotNamed.mainSet.getModuleName();
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected CodeShowPane getDeafaultCodePane() {
        // TODO Auto-generated method stub
        return this.mainSetTypeOperatingContainerParam.getFormatControlPane().getDefaultPane();
    }

    @Override
    public File getImageRootPath() {
        File file = SourceGenerateFileStructure.getMainPictureFilePutPath(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName);
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = SourceGenerateFileStructure.getMainNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName);
        return file;
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        MainSetOpratingContainerModel theModel = (MainSetOpratingContainerModel) model;
        super.setParam(theModel);
        theModel.setMainSetTypeName(mainSetTypeOperatingContainerParam.getMainSetType());
        theModel.setMetaModel(metaModel);
    }

    @Override
    public MainSetOpratingContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        MainSetOpratingContainerModel theModel = new MainSetOpratingContainerModel();
        setParam(theModel);
        return theModel;
    }

    @Override
    public AbstractCodeControlPane getCodeControlPane() {
        return this.mainSetTypeOperatingContainerParam.getCodeControlPane();
    }

    @Override
    public MainSetMarkElement getCorrespondingMarkElement() {
        return new MainSetMarkElement();
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
