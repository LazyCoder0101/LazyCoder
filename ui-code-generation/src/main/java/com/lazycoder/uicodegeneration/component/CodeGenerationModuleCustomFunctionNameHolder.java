package com.lazycoder.uicodegeneration.component;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.ModuleCustomFunctionNameHolder;
import java.util.ArrayList;

public class CodeGenerationModuleCustomFunctionNameHolder {

    public static ArrayList<ModuleCustomFunctionNameHolder> moduleCustomFunctionNameList = new ArrayList<>();

    /**
     * 添加模块
     *
     * @param moduleInfo
     */
    public static void addModule(ModuleInfo moduleInfo) {
        if (moduleInfo != null) {
            ModuleCustomFunctionNameHolder moduleCustomFunctionNameHolder = new ModuleCustomFunctionNameHolder(
                    moduleInfo.getClassName(), moduleInfo.getModuleName(), moduleInfo.getModuleId());
            moduleCustomFunctionNameList.add(moduleCustomFunctionNameHolder);
        }
    }

    public static ModuleCustomFunctionNameHolder getModuleCustomFunctionNameHolder(String moduleId) {
        ModuleCustomFunctionNameHolder moduleCustomFunctionNameHolder = null;
        for (ModuleCustomFunctionNameHolder temp : moduleCustomFunctionNameList) {
            if (temp.getModuleId().equals(moduleId)) {
                moduleCustomFunctionNameHolder = temp;
                break;
            }
        }
        return moduleCustomFunctionNameHolder;
    }

    /**
     * 获取对应模块的自定义方法名
     *
     * @param useModuleList
     * @return
     */
    public static ArrayList<ModuleCustomFunctionNameHolder> getModuleCustomFunctionNameList(ArrayList<ModuleRelatedParam> useModuleList) {
        ArrayList<ModuleCustomFunctionNameHolder> out = new ArrayList<>(), tempAllFunctionNameList = new ArrayList<>();
        tempAllFunctionNameList.addAll(moduleCustomFunctionNameList);

        ModuleCustomFunctionNameHolder tempFormatFunctionName;
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
        ModuleCustomFunctionNameHolder moduleCustomFunctionNameHolder = null;
        for (int i = 0; i < moduleCustomFunctionNameList.size(); i++) {
            moduleCustomFunctionNameHolder = moduleCustomFunctionNameList.get(i);
            if (moduleCustomFunctionNameHolder.getModuleId().equals(moduleId)) {
                moduleCustomFunctionNameList.remove(i);
                break;
            }
        }
    }

}
