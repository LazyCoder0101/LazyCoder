package com.lazycoder.uicodegeneration.component;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.generalframe.functionname.FormatFunctionName;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.ModuleFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.tool.FunctionNameIDGenerator;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerationModuleFormatFunctionNameHolder {


    public static ArrayList<ModuleFormatFunctionNameHolder> moduleFormatFunctionNameList = new ArrayList<>();

    /**
     * 添加模块
     *
     * @param moduleInfo
     */
    public static void addModule(ModuleInfo moduleInfo) {
        if (moduleInfo != null && moduleInfo.getNumOfFunctionName() > 0) {
            ModuleFormatFunctionNameHolder moduleFormatFunctionNameHolder = new ModuleFormatFunctionNameHolder(
                    moduleInfo.getClassName(), moduleInfo.getModuleName(), moduleInfo.getModuleId());

            FormatFunctionName formatFunctionName;
            int functionNameId;
            List<FunctionNameData> list = SysService.FUNCTION_NAME_DATA_SERVICE.getModuleFunctionNameDataList(moduleInfo.getModuleId());
            if (list != null) {
                for (FunctionNameData temp : list) {

                    functionNameId = FunctionNameIDGenerator.generateCodeSerialNumber();

                    formatFunctionName = new FormatFunctionName();
                    formatFunctionName.setRoleOfFunctionNameValue(temp.getRoleOfFunctionName());
                    formatFunctionName.setFunctionNameId(functionNameId);
                    formatFunctionName.setFunctionNameRange(temp.getTheAvaliableRange());
                    formatFunctionName.setFunctionNameValue(temp.getFunctionName());
                    formatFunctionName.setFunctionNameType(FunctionNameData.getDataTypeList(temp));

                    moduleFormatFunctionNameHolder.addFunctionName(formatFunctionName);
                }
            }
            moduleFormatFunctionNameList.add(moduleFormatFunctionNameHolder);
        }
    }

    public static ModuleFormatFunctionNameHolder getModuleFormatFunctionName(String moduleId) {
        ModuleFormatFunctionNameHolder moduleFormatFunctionNameHolder = null;
        for (ModuleFormatFunctionNameHolder temp : moduleFormatFunctionNameList) {
            if (temp.getModuleId().equals(moduleId)) {
                moduleFormatFunctionNameHolder = temp;
                break;
            }
        }
        return moduleFormatFunctionNameHolder;
    }

    /**
     * 获取对应模块的格式方法名
     *
     * @param useModuleList
     * @return
     */
    public static ArrayList<ModuleFormatFunctionNameHolder> getModuleFormatFunctionNameList(ArrayList<ModuleRelatedParam> useModuleList) {
        ArrayList<ModuleFormatFunctionNameHolder> out = new ArrayList<>(), tempAllFunctionNameList = new ArrayList<>();
        tempAllFunctionNameList.addAll(moduleFormatFunctionNameList);

        ModuleFormatFunctionNameHolder tempFormatFunctionName;
        for (ModuleRelatedParam relatedParam : useModuleList) {

            for (int i = 0; i < tempAllFunctionNameList.size(); i++) {
                tempFormatFunctionName = tempAllFunctionNameList.get(i);
                if (relatedParam.getModuleInfo().getModuleId().equals(tempFormatFunctionName.getModuleId())) {
                    out.add(tempFormatFunctionName);
                    tempAllFunctionNameList.remove(i);
                    break;
                }
            }
        }
        return out;
    }

    public static void delModule(String moduleId) {
        ModuleFormatFunctionNameHolder moduleCustomFunctionNameHolder = null;
        for (int i = 0; i < moduleFormatFunctionNameList.size(); i++) {
            moduleCustomFunctionNameHolder = moduleFormatFunctionNameList.get(i);
            if (moduleCustomFunctionNameHolder.getModuleId().equals(moduleId)) {
                moduleFormatFunctionNameList.remove(i);
                break;
            }
        }
    }

}
