package com.lazycoder.uicodegeneration.generalframe.functionname.holder;


import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleCustomFunctionNameHolder extends CustomFunctionNameHolder {


    private String className = "";

    private String moduleName = "";

    private String moduleId = "";

    public ModuleCustomFunctionNameHolder(String className, String moduleName, String moduleId) {
        super();
        this.className = className;
        this.moduleName = moduleName;
        this.moduleId = moduleId;
    }

    @Override
    public void addFunctionName(AbstractMethodName functionName) {
        String toolTipTextOfHaveValue = "(￣.￣)  这是在\"" + moduleName + "\"" + "(" + className + ")" + "模块添加的";
        functionName.setToolTipTextOfHaveValue(toolTipTextOfHaveValue);

        String toolTipTextOfHaveNotValue = "(￣ェ￣;)  看一下和\"" + moduleName + "\"模块相关的地方，还有内容没有填写呢";
        functionName.setToolTipTextOfHaveNotValue(toolTipTextOfHaveNotValue);

        super.addFunctionName(functionName);
    }

}
