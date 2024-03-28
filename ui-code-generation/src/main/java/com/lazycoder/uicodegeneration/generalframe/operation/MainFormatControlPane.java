package com.lazycoder.uicodegeneration.generalframe.operation;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.database.model.format.MainOperating;
import com.lazycoder.service.fileStructure.SourceGenerateProFile;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.FormatControlPaneLable;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.MainFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FormatOpratingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.functionname.FormatFunctionName;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.MainCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.MainFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.tool.FunctionNameIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.tool.VariableIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.MainCustomVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.MainFormatVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.MainFormatControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractFormatControlPaneModel;
import com.lazycoder.utils.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFormatControlPane extends AbstractFormatControlPane {

    /**
     *
     */
    private static final long serialVersionUID = -7062308611460514121L;

    private MainFormatContainer mainFormatContainer = null;

    private MainCustomVariableHolder customVariableHolder = null;

    private MainCustomFunctionNameHolder customFunctionNameHolder = null;

    private MainOperating operating = null;

    /**
     * 自带的变量，初始化时都放这里
     */
    private MainFormatVariableHolder formatVariableHolder = null;

    private MainFormatFunctionNameHolder formatFunctionNameHolder = null;

    /**
     * 还原
     */
    public MainFormatControlPane() {
        // TODO Auto-generated constructor stub
        super();
        init();
    }

    @Override
    protected AbstractFormatContainer restoreContent(AbstractFormatControlPaneModel formatControlPaneModel) {
        MainFormatControlPaneModel mainFormatControlPaneModel = (MainFormatControlPaneModel) formatControlPaneModel;
        this.haveBusinessLogicMarkFlag = formatControlPaneModel.isHaveBusinessLogicMarkFlag();
        this.operating = mainFormatControlPaneModel.getOperating();

        formatVariableHolder = mainFormatControlPaneModel.getFormatVariableHolder();
        formatFunctionNameHolder = mainFormatControlPaneModel.getFormatFunctionNameHolder();

        customVariableHolder = mainFormatControlPaneModel.getCustomVariableHolder();
        customVariableHolder.setFormatControlPane(this);

        customFunctionNameHolder = mainFormatControlPaneModel.getCustomFunctionNameHolder();
        customFunctionNameHolder.setFormatControlPane(this);

        AbstractFormatContainer theFormatContainer = super.restoreContent(formatControlPaneModel);
        mainFormatContainer = (MainFormatContainer) theFormatContainer;
        return theFormatContainer;
    }

    /**
     * 新建
     *
     * @param fileName
     * @param list
     */
    public MainFormatControlPane(String fileName, ArrayList<ModuleRelatedParam> list) {
        // TODO Auto-generated constructor stub
        super();
        this.fileName = fileName;
        init();

        formatVariableHolder = new MainFormatVariableHolder(this.getFileName());
        formatFunctionNameHolder = new MainFormatFunctionNameHolder(this.getFileName());
        customVariableHolder = new MainCustomVariableHolder(this);
        customFunctionNameHolder = new MainCustomFunctionNameHolder(this);

        MainInfo mainInfo = SysService.FORMAT_INFO_SERVICE.getMainInfo();
        addMainVariableList(mainInfo);
        addMethodNameList(mainInfo);

        CodeGenerationFrameHolder.codeShowPanel.addMain(fileName);// 添加必填模板的默认代码面板和子代码面板，并返回默认代码面板
        if (mainInfo != null) {
            if (mainInfo.getFormatState() == MainInfo.TRUE_) {
                PathFind pathFind = new PathFind(MarkElementName.MAIN_FORMAT_MARK, PathFind.FORMAT_TYPE);
                this.operating = SysService.MAIN_FORMAT_CODE_FILE_SERVICE.getMainOperating();// 获取必填模板的操作模型
                if (operating != null) {
                    if (operating.getOperatingContent() == true) {// 如果必填模板的格式写有内容，添加必填模板格式
                        FormatOpratingContainerParam formatOpratingContainerParam = new FormatOpratingContainerParam();
                        formatOpratingContainerParam.setFormatControlPane(this);
                        formatOpratingContainerParam.setParentPathFind(pathFind);
                        formatOpratingContainerParam.setCodeSerialNumber(0);
                        formatOpratingContainerParam
                                .setControlStatementFormat(operating.getDefaultControlStatementFormat());
                        formatOpratingContainerParam.setCodeControlPane(businessLogicCodePane);

                        mainFormatContainer = new MainFormatContainer(formatOpratingContainerParam, fileName, mainInfo);
                        haveBusinessLogicMarkFlag = getDefaultPane().checkHaveBusinessLogicMark();
                        businessLogicCodePane.addContainer(mainFormatContainer);
                    }
                }
            }
        }
        addInit(list);
    }

    private void addMainVariableList(MainInfo mainInfo) {
        if (mainInfo.getNumOfVariable() > 0) {
            List<VariableData> variableList = SysService.VARIABLE_DATA_SERVICE.getMainVariableDataList();
            if (variableList != null) {
                FormatVariable formatVariable;
                ArrayList<String> dataTypeList, dataVariableTypeList, labelTypeList, labelVariableTypeList;
                for (VariableData mainVariableDataTemp : variableList) {
                    int variableId = VariableIDGenerator.generateCodeSerialNumber();
                    formatVariable = new FormatVariable();
                    formatVariable.setVariableId(variableId);
                    formatVariable.setNoUserSelectionIsRequired(mainVariableDataTemp.isNoUserSelectionIsRequired());
                    formatVariable.setVariableRange(mainVariableDataTemp.getTheAvaliableRange());

                    dataTypeList = formatVariable.getDataTypeList();
                    dataVariableTypeList = VariableData
                            .getDataTypeList(mainVariableDataTemp.getDataTypeParam());
                    if (dataVariableTypeList != null) {
                        dataTypeList.addAll(dataVariableTypeList);
                    }

                    labelTypeList = formatVariable.getLabelTypeList();
                    labelVariableTypeList = VariableData.getLabelTypeParam(mainVariableDataTemp);
                    if (labelVariableTypeList != null) {
                        labelTypeList.addAll(labelVariableTypeList);
                    }

                    formatVariable.setVariableValue(mainVariableDataTemp.getVariableName());
                    formatVariable.setRoleOfVariableName(mainVariableDataTemp.getRoleOfVariableName());
                    formatVariableHolder.addVariable(formatVariable);
                }
            }
        }
    }

    private void addMethodNameList(MainInfo mainInfo) {
        if (mainInfo.getFunctionNameNum() > 0) {
            List<FunctionNameData> methodNameList = SysService.FUNCTION_NAME_DATA_SERVICE.getMainFunctionNameDataList();

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

                    if (formatFunctionNameHolder != null) {
                        formatFunctionNameHolder.addFunctionName(formatFunctionName);
                    }
                }
            }
        }
    }

    private void init() {
        flagLable = new FormatControlPaneLable();
        flagLable.setType(FormatControlPaneLable.MAIN_TYPE);
        flagLable.setDefaultFileName(fileName);
    }

    @Override
    public CodeShowPane getDefaultPane() {
        // TODO Auto-generated method stub
        return CodeGenerationFrameHolder.codeShowPanel.getMainDefaultCodeShowPane();
    }

    @Override
    public MainFormatControlPaneModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        MainFormatControlPaneModel model = new MainFormatControlPaneModel();
        setParam(model);
        return model;
    }

    @Override
    public FormatControlPaneLable getFormatControlPaneLable() {
        // TODO Auto-generated method stub
        FormatControlPaneLable formatControlPaneLable = new FormatControlPaneLable();
        formatControlPaneLable.setType(FormatControlPaneLable.MAIN_TYPE);
        formatControlPaneLable.setDefaultFileName(getDefaultPane().getFileName());
        return formatControlPaneLable;
    }

    @Override
    public void setParam(AbstractFormatControlPaneModel model) {
        // TODO Auto-generated method stub
        MainFormatControlPaneModel theModel = (MainFormatControlPaneModel) model;
        super.setParam(theModel);
        theModel.setFormatVariableHolder(formatVariableHolder);
        theModel.setFormatFunctionNameHolder(formatFunctionNameHolder);
        theModel.setCustomVariableHolder(customVariableHolder);
        theModel.setCustomFunctionNameHolder(customFunctionNameHolder);
        theModel.setOperating(this.operating);
    }

    @Override
    public void saveProjectFile() {
        // TODO Auto-generated method stub
        File file = SourceGenerateProFile.getMainOpratingPaneProFile(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName, this.fileName);
        MainFormatControlPaneModel mainFormatControlPaneModel = getFormatStructureModel();
//        FileUtil.writeFile(file, JsonUtil.getJsonStr(mainFormatControlPaneModel));
        JsonUtil.writeFile(file, mainFormatControlPaneModel);
    }

    @Override
    public MainFormatVariableHolder getFormatVariableHolder() {
        return formatVariableHolder;
    }

    @Override
    public MainCustomVariableHolder getThisCustomVariableHolder() {
        // TODO Auto-generated method stub
        return customVariableHolder;
    }

    @Override
    public MainCustomFunctionNameHolder getThisCustomFunctionNameHolder() {
        // TODO Auto-generated method stub
        return customFunctionNameHolder;
    }

    public MainFormatContainer getMainFormatContainer() {
        return mainFormatContainer;
    }

    @Override
    public MainFormatFunctionNameHolder getFormatFunctionNameHolder() {
        // TODO Auto-generated method stub
        return formatFunctionNameHolder;
    }

    @Override
    public ArrayList<CodeShowPane> getSubCodePaneList() {
        // TODO Auto-generated method stub
        return CodeGenerationFrameHolder.codeShowPanel.getMainSubCodeShowPaneList();
    }

}
