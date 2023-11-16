package com.lazycoder.uicodegeneration.generalframe.variable.holder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleFormatVariableHolder extends AbstractFormatVariableHolder {

    private String className = "";

    private String moduleName = "";

    private String moduleId = "";

    public ModuleFormatVariableHolder(String className, String moduleName, String moduleId) {
        super();
        this.className = className;
        this.moduleName = moduleName;
        this.moduleId = moduleId;
    }

    public static ModuleFormatVariableHolder restoreByJSONObject(String jsonObject) {
        ModuleFormatVariableHolder moduleFormatVariableHolder = JSON.parseObject(jsonObject,
                new TypeReference<ModuleFormatVariableHolder>() {
                });
        return moduleFormatVariableHolder;
    }

    @Override
    public void addVariable(AbstractVariable variable) {
        String toolTipText = "(￣.￣)  \"" + ((FormatVariable) variable).getRoleOfVariableName() + "\"是在\"" + moduleName + "\"" + "(" + className + ")" + "模块原本就有的";
        variable.setToolTipTextOfHaveValue(toolTipText);
        super.addVariable(variable);
    }

}
