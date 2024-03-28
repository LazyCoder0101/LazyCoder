package com.lazycoder.uicodegeneration.generalframe.operation;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.service.fileStructure.SourceGenerateFileMethod;
import com.lazycoder.service.fileStructure.SourceGenerateProFile;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationAdditionalFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationAdditionalFormatVariableHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.FormatControlPaneLable;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FormatOpratingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.functionname.FormatFunctionName;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AdditionalCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AdditionalFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.tool.FunctionNameIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.tool.VariableIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AdditionalCustomVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AdditionalFormatVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.AdditionalFormatControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractFormatControlPaneModel;
import com.lazycoder.utils.JsonUtil;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdditionalFormatControlPane extends AbstractFormatControlPane {

    /**
     *
     */
    private static final long serialVersionUID = -7062308611460514121L;

    @Getter
    private AdditionalOperating operating = null;

    @Getter
    private AdditionalFormatContainer formatContainer = null;

    /**
     * 可选模板序号
     */
    private int additionalSerialNumber = 0;

    private boolean canBeDelOrNot = true;

    private AdditionalCustomVariableHolder customVariableHolder = null;

    private AdditionalCustomFunctionNameHolder customFunctionNameHolder = null;

    private AdditionalInfo additionalInfo;

    /**
     * 新建
     *
     * @param additionalSerialNumber
     * @param fileName
     * @param canBeDelOrNot
     */
    public AdditionalFormatControlPane(int additionalSerialNumber, String fileName, boolean canBeDelOrNot) {
        // TODO Auto-generated constructor stub
        super();
        this.additionalSerialNumber = additionalSerialNumber;
        this.fileName = fileName;
        this.canBeDelOrNot = canBeDelOrNot;
        init();
        this.customVariableHolder = new AdditionalCustomVariableHolder(this);
        this.customFunctionNameHolder = new AdditionalCustomFunctionNameHolder(this);

        CodeGenerationFrameHolder.featureSelectedPane.updateModuleList(this);
        CodeGenerationFrameHolder.codeShowPanel.addAdditionalDefaultFormatCodeFile(additionalSerialNumber, fileName);// 添加必填模板的默认代码面板和子代码面板，并返回默认代码面板
        // 后面在添加对应的方法选择

        PathFind pathFind = new PathFind(MarkElementName.ADDITIONAL_FORMAT_MARK, PathFind.FORMAT_TYPE);

        additionalInfo = SysService.FORMAT_INFO_SERVICE.getAdditionalInfo(additionalSerialNumber);
        if (additionalInfo != null) {
            boolean flag = CodeGenerationAdditionalFormatVariableHolder
                    .checkHaveTheFormatVariableOrNot(additionalSerialNumber);// 查看之前有没有添加过这个可选模板
            if (flag == false) {// 没添加过
                CodeGenerationFrameHolder.codeShowPanel.addAdditionalSubCodeFormatList(additionalSerialNumber);
                addFormatAdditionalVariableList(additionalInfo);
                addFormatAdditionalMethodNameList(additionalInfo);
                SourceGenerateFileMethod.copyAdditionalAttachedFileInSourceProject(
                        CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, additionalInfo);
            }
            if (additionalInfo.getFormatState() == AdditionalInfo.TRUE_) {
                operating = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalOperating(additionalSerialNumber);
                if (operating != null) {
                    if (operating.getOperatingContent() == true) {// 如果该可选模板的格式写有内容

                        FormatOpratingContainerParam formatOpratingContainerParam = new FormatOpratingContainerParam();
                        formatOpratingContainerParam.setParentPathFind(pathFind);
                        formatOpratingContainerParam.setCodeSerialNumber(0);
                        formatOpratingContainerParam
                                .setControlStatementFormat(operating.getDefaultControlStatementFormat());
                        formatOpratingContainerParam.setCodeControlPane(businessLogicCodePane);
                        formatOpratingContainerParam.setFormatControlPane(this);

                        formatContainer = new AdditionalFormatContainer(formatOpratingContainerParam, additionalSerialNumber,
                                fileName, additionalInfo);// 添加该可选模板的格式控制
                        this.haveBusinessLogicMarkFlag = getDefaultPane().checkHaveBusinessLogicMark();
                        businessLogicCodePane.addContainer(formatContainer);
                    }
                }
            }
        }
    }

    /**
     * 还原
     *
     * @param fileName
     * @param additionalSerialNumber
     * @param canBeDelOrNot
     */
    public AdditionalFormatControlPane(String fileName, int additionalSerialNumber, boolean canBeDelOrNot) {
        super();
        this.additionalSerialNumber = additionalSerialNumber;
        this.fileName = fileName;
        this.canBeDelOrNot = canBeDelOrNot;
        init();
    }

    @Override
    protected AbstractFormatContainer restoreContent(AbstractFormatControlPaneModel formatControlPaneModel) {
        AdditionalFormatControlPaneModel additionalFormatControlPaneModel = (AdditionalFormatControlPaneModel) formatControlPaneModel;
        this.haveBusinessLogicMarkFlag = formatControlPaneModel.isHaveBusinessLogicMarkFlag();
        this.operating = additionalFormatControlPaneModel.getOperating();
        this.customVariableHolder = additionalFormatControlPaneModel.getCustomVariableHolder();
        this.customVariableHolder.setFormatControlPane(this);
        this.customFunctionNameHolder = additionalFormatControlPaneModel.getCustomFunctionNameHolder();
        this.customFunctionNameHolder.setFormatControlPane(this);

        AbstractFormatContainer theFormatContainer = super.restoreContent(additionalFormatControlPaneModel);
        this.formatContainer = (AdditionalFormatContainer) theFormatContainer;
        return theFormatContainer;
    }

    /**
     * @param additionalInfo
     */
    private void addFormatAdditionalVariableList(AdditionalInfo additionalInfo) {
        if (additionalInfo.getNumOfVariable() > 0) {
            List<VariableData> variableList = SysService.VARIABLE_DATA_SERVICE.getAdditionalVariableDataList(additionalSerialNumber);
            AdditionalFormatVariableHolder additionalFormatVariableHolder = new AdditionalFormatVariableHolder(
                    additionalSerialNumber);

            if (variableList != null) {
                FormatVariable formatVariable;
                ArrayList<String> dataTypeList, dataVariableTypeList, labelTypeList, labelVariableTypeList;

                for (VariableData variableDataTemp : variableList) {
                    int variableId = VariableIDGenerator.generateCodeSerialNumber();
                    formatVariable = new FormatVariable();
                    formatVariable.setVariableId(variableId);
                    formatVariable.setNoUserSelectionIsRequired(variableDataTemp.isNoUserSelectionIsRequired());
                    formatVariable.setVariableRange(variableDataTemp.getTheAvaliableRange());

                    dataTypeList = formatVariable.getDataTypeList();
                    dataVariableTypeList = VariableData.getDataTypeList(variableDataTemp.getDataTypeParam());
                    if (dataVariableTypeList != null) {
                        dataTypeList.addAll(dataVariableTypeList);
                    }
                    labelTypeList = formatVariable.getLabelTypeList();
                    labelVariableTypeList = VariableData.getLabelTypeParam(variableDataTemp);
                    if (labelVariableTypeList != null) {
                        labelTypeList.addAll(labelVariableTypeList);
                    }

                    formatVariable.setVariableValue(variableDataTemp.getVariableName());
                    formatVariable.setRoleOfVariableName(variableDataTemp.getRoleOfVariableName());

                    if (additionalFormatVariableHolder != null) {
                        additionalFormatVariableHolder.addVariable(formatVariable);
                    }
                }
            }
            CodeGenerationAdditionalFormatVariableHolder.additionalFormatVariableList
                    .add(additionalFormatVariableHolder);
        }
    }

    private void addFormatAdditionalMethodNameList(AdditionalInfo additionalInfo) {
        if (additionalInfo.getNumOfVariable() > 0) {
            List<FunctionNameData> methodNameList = SysService.FUNCTION_NAME_DATA_SERVICE
                    .getAdditionalFunctionNameDataList(additionalSerialNumber);
            AdditionalFormatFunctionNameHolder additionalFormatVariableHolder = new AdditionalFormatFunctionNameHolder(
                    additionalSerialNumber);

            if (methodNameList != null) {
                FormatFunctionName formatFunctionName;
                ArrayList<String> methodNameTypeList, dataMethodNameTypeList;

                for (FunctionNameData methodNameDataTemp : methodNameList) {
                    int methodNameId = FunctionNameIDGenerator.generateCodeSerialNumber();
                    formatFunctionName = new FormatFunctionName();
                    formatFunctionName.setFunctionNameId(methodNameId);
                    formatFunctionName.setFunctionNameRange(methodNameDataTemp.getTheAvaliableRange());
                    methodNameTypeList = formatFunctionName.getFunctionNameType();
                    dataMethodNameTypeList = FunctionNameData.getDataTypeList(methodNameDataTemp.getDataTypeParam());
                    if (dataMethodNameTypeList != null) {
                        methodNameTypeList.addAll(dataMethodNameTypeList);
                    }
                    formatFunctionName.setFunctionNameValue(methodNameDataTemp.getFunctionName());
                    formatFunctionName.setRoleOfFunctionNameValue(methodNameDataTemp.getRoleOfFunctionName());

                    if (additionalFormatVariableHolder != null) {
                        additionalFormatVariableHolder.addFunctionName(formatFunctionName);
                    }
                }
            }
            CodeGenerationAdditionalFormatFunctionNameHolder.additionalFormatFunctionNameHolderList
                    .add(additionalFormatVariableHolder);
        }
    }

    private void init() {
        flagLable = new FormatControlPaneLable();
        flagLable.setType(FormatControlPaneLable.ADDITIONAL_TYPE);
        flagLable.setAdditionalSerialNumber(additionalSerialNumber);
        flagLable.setDefaultFileName(fileName);
    }

    @Override
    public CodeShowPane getDefaultPane() {
        // TODO Auto-generated method stub
        return CodeGenerationFrameHolder.codeShowPanel.getAdditionalDeafaultCodePane(this.additionalSerialNumber, this.fileName);
    }

    @Override
    public AdditionalFormatControlPaneModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        AdditionalFormatControlPaneModel model = new AdditionalFormatControlPaneModel();
        setParam(model);
        return model;
    }

    @Override
    public void setParam(AbstractFormatControlPaneModel model) {
        // TODO Auto-generated method stub
        AdditionalFormatControlPaneModel theModel = (AdditionalFormatControlPaneModel) model;
        super.setParam(theModel);
        theModel.setOperating(this.operating);
        theModel.setCustomVariableHolder(customVariableHolder);
        theModel.setCustomFunctionNameHolder(customFunctionNameHolder);
    }

    @Override
    public FormatControlPaneLable getFormatControlPaneLable() {
        // TODO Auto-generated method stub
        FormatControlPaneLable formatControlPaneLable = new FormatControlPaneLable();
        formatControlPaneLable.setType(FormatControlPaneLable.ADDITIONAL_TYPE);
        formatControlPaneLable.setAdditionalSerialNumber(additionalSerialNumber);
        formatControlPaneLable.setDefaultFileName(getDefaultPane().getFileName());
        formatControlPaneLable.setCanBeDelOrNot(this.canBeDelOrNot);
        return formatControlPaneLable;
    }

    @Override
    public int getAdditionalSerialNumber() {
        return additionalSerialNumber;
    }

    @Override
    public void saveProjectFile() {
        // TODO Auto-generated method stub
        File file = SourceGenerateProFile.getAdditionalOpratingPaneProFile(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName, this.fileName, additionalSerialNumber);
//        FileUtil.writeFile(file, JsonUtil.getJsonStr(getFormatStructureModel()));
        JsonUtil.writeFile(file, getFormatStructureModel());
    }

    @Override
    public AdditionalFormatVariableHolder getFormatVariableHolder() {
        return CodeGenerationAdditionalFormatVariableHolder.getAdditionalFormatVariable(additionalSerialNumber);
    }

    @Override
    public AdditionalCustomVariableHolder getThisCustomVariableHolder() {
        // TODO Auto-generated method stub
        return customVariableHolder;
    }

    @Override
    public AdditionalCustomFunctionNameHolder getThisCustomFunctionNameHolder() {
        // TODO Auto-generated method stub
        return customFunctionNameHolder;
    }

    @Override
    public AdditionalFormatFunctionNameHolder getFormatFunctionNameHolder() {
        // TODO Auto-generated method stub
        return CodeGenerationAdditionalFormatFunctionNameHolder.getAdditionalFormatMethodNameHolder(additionalSerialNumber);
    }

    protected void delThisPane() {
        // 删其他地方没有的，这里的模块
        ArrayList<ModuleRelatedParam> moduleList = new ArrayList<ModuleRelatedParam>();
        moduleList.addAll(useModuleRelatedParamList);
        delModuleList(moduleList);

        // 还有格式，和可能存在可选模板业务方法，在这删除
        for (int i = 0; i < businessLogicCodePane.getComponentCount(); i++) {
            if (getComponent(i) instanceof OpratingContainerInterface) {
                ((OpratingContainerInterface) getComponent(i)).delThis();
            }
        }
        if (formatContainer != null) {
            formatContainer.delThis();
        }
        // 删对应可选模板的格式变量
        int additionalFormatControlPaneNum = CodeGenerationFrameHolder.codeControlTabbedPane
                .getAdditionalFormatControlPaneNum(additionalSerialNumber);
        if (additionalFormatControlPaneNum == 1) {// 当前只添加了这个可选模板面板
            CodeGenerationAdditionalFormatVariableHolder.delTheAdditionalFormatVariable(additionalSerialNumber);// 删除对应的格式变量
            CodeGenerationAdditionalFormatFunctionNameHolder.delTheAdditionalFormatMethodName(additionalSerialNumber);// 删除对应的格式方法名
            SourceGenerateFileMethod.delAdditionalDataInProject(CodeGenerationFrameHolder.projectParentPath,
                    CodeGenerationFrameHolder.projectName, additionalSerialNumber);// 删除对应的项目数据
            if (this.additionalInfo == null) {
                additionalInfo = SysService.FORMAT_INFO_SERVICE.getAdditionalInfo(additionalSerialNumber);
            }
            if (additionalInfo != null) {
                SourceGenerateFileMethod.delAdditionalAttachedFileInSourceProject(
                        CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, additionalInfo);// 删除对应的附加文件数据
            }
            CodeGenerationFrameHolder.codeShowPanel.delTheAdditionalCodeFiles(additionalSerialNumber);// 删除对应的代码文件
            // 删除对应的附带文件
            /**
             * 删除对应其他文件的格式变量 删除对应其他文件的方法名变量 删除对应其他文件的数据存放文件 删除对应其他文件的代码文件 删除对应其他文件的附带文件
             */
        } else {
            CodeGenerationFrameHolder.codeShowPanel.delTheAdditionalDeafaultCodeFile(additionalSerialNumber, fileName);
        }
    }

    @Override
    public ArrayList<CodeShowPane> getSubCodePaneList() {
        // TODO Auto-generated method stub
        return CodeGenerationFrameHolder.codeShowPanel.getAdditionalSubCodePaneList(additionalSerialNumber);
    }

}
