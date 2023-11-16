package com.lazycoder.uicodegeneration.component;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.generalframe.tool.VariableIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.ModuleFormatVariableHolder;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerationModuleFormatVariableHolder {

    public static ArrayList<ModuleFormatVariableHolder> moduleFormatVariableList = new ArrayList<>();

    /**
     * 添加模块
     *
     * @param moduleInfo
     */
    public static void addModule(ModuleInfo moduleInfo) {
        if (moduleInfo != null && moduleInfo.getNumOfVariable() > 0) {
            addThisModuleVariables(moduleInfo.getClassName(), moduleInfo.getModuleName(), moduleInfo.getModuleId());
        }
    }

    /**
     * 添加这个模块的变量
     *
     * @param className
     * @param moduleName
     * @param moduleId
     */
    private static void addThisModuleVariables(String className, String moduleName, String moduleId) {
        ModuleFormatVariableHolder moduleFormatVariableHolder = new ModuleFormatVariableHolder(className, moduleName, moduleId);

        FormatVariable formatVariable;
        int variableId;
        List<VariableData> list = SysService.VARIABLE_DATA_SERVICE.getModuleVariableDataList(moduleId);
        if (list != null) {
            ArrayList<String> dataTypeList, dataVariableTypeList, labelTypeList, labelVariableTypeList;
            for (VariableData temp : list) {

                variableId = VariableIDGenerator.generateCodeSerialNumber();

                formatVariable = new FormatVariable();
                formatVariable.setNoUserSelectionIsRequired(temp.isNoUserSelectionIsRequired());
                formatVariable.setRoleOfVariableName(temp.getRoleOfVariableName());
                formatVariable.setVariableId(variableId);
                formatVariable.setVariableRange(temp.getTheAvaliableRange());
                formatVariable.setVariableValue(temp.getVariableName());

                dataTypeList = formatVariable.getDataTypeList();
                dataVariableTypeList = VariableData.getDataTypeList(temp.getDataTypeParam());
                if (dataVariableTypeList != null) {
                    dataTypeList.addAll(dataVariableTypeList);
                }
                labelTypeList = formatVariable.getLabelTypeList();
                labelVariableTypeList = VariableData.getLabelTypeParam(temp);
                if (labelVariableTypeList != null) {
                    labelTypeList.addAll(labelVariableTypeList);
                }

                moduleFormatVariableHolder.addVariable(formatVariable);
            }
        }
        moduleFormatVariableList.add(moduleFormatVariableHolder);
    }

    /**
     * 获取对应模块的格式变量
     *
     * @param useModuleRelatedParamList
     * @return
     */
    public static ArrayList<ModuleFormatVariableHolder> getModuleFormatVariableList(ArrayList<ModuleRelatedParam> useModuleRelatedParamList) {
        ArrayList<ModuleFormatVariableHolder> out = new ArrayList<>(), tempAllVariableList = new ArrayList<>();
        tempAllVariableList.addAll(moduleFormatVariableList);

        ModuleFormatVariableHolder tempFormatVariable;
        for (ModuleRelatedParam moduleRelatedParamTemp : useModuleRelatedParamList) {

            for (int i = 0; i < tempAllVariableList.size(); i++) {
                tempFormatVariable = tempAllVariableList.get(i);
                if (moduleRelatedParamTemp.getModuleInfo().getModuleId().equals(tempFormatVariable.getModuleId())) {
                    out.add(tempFormatVariable);
                    tempAllVariableList.remove(i);
                    break;
                }
            }
        }
        return out;
    }


    public static ModuleFormatVariableHolder getModuleFormatVariable(String moduleId) {
        ModuleFormatVariableHolder moduleFormatVariableHolder = null;
        for (ModuleFormatVariableHolder temp : moduleFormatVariableList) {
            if (temp.getModuleId().equals(moduleId)) {
                moduleFormatVariableHolder = temp;
                break;
            }
        }
        return moduleFormatVariableHolder;
    }

    public static void delModule(String moduleId) {
        ModuleFormatVariableHolder moduleFormatVariableHolder = null;
        for (int i = 0; i < moduleFormatVariableList.size(); i++) {
            moduleFormatVariableHolder = moduleFormatVariableList.get(i);
            if (moduleFormatVariableHolder.getModuleId().equals(moduleId)) {
                moduleFormatVariableList.remove(i);
                break;
            }
        }
    }

}
