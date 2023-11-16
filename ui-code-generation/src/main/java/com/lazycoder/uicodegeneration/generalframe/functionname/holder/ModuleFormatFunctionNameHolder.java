package com.lazycoder.uicodegeneration.generalframe.functionname.holder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleFormatFunctionNameHolder extends AbstractFormatFunctionNameHolder {

    private String className = "";

    private String moduleName = "";

    private String moduleId = "";

    public ModuleFormatFunctionNameHolder(String className, String moduleName, String moduleId) {
        super();
        this.className = className;
        this.moduleName = moduleName;
        this.moduleId = moduleId;
    }

    public static ModuleFormatFunctionNameHolder restoreByJSONObject(String jsonObject) {
        ModuleFormatFunctionNameHolder moduleFormatVariableHolder = JSON.parseObject(jsonObject,
                new TypeReference<ModuleFormatFunctionNameHolder>() {
                });
        return moduleFormatVariableHolder;
    }

    @Override
    public void addFunctionName(AbstractMethodName functionName) {
        String toolTipText = "(￣.￣)  这是在\"" + moduleName + "\"" + "(" + className + ")" + "模块原本就有的";
        functionName.setToolTipTextOfHaveValue(toolTipText);
        super.addFunctionName(functionName);
    }

}
