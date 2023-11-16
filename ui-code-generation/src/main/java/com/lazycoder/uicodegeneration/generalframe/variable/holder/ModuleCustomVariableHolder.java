package com.lazycoder.uicodegeneration.generalframe.variable.holder;


import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleCustomVariableHolder extends CustomVariableHolder {

    private String className = "";

    private String moduleName = "";

    private String moduleId = "";

    public ModuleCustomVariableHolder(String className, String moduleName, String moduleId) {
        super();
        this.className = className;
        this.moduleName = moduleName;
        this.moduleId = moduleId;
    }

    @Override
    public void addVariable(AbstractVariable variable) {
        String toolTipTextOfHaveValue = "(￣.￣)  这是在\"" + moduleName + "\"" + "(" + className + ")" + "模块添加的";
        variable.setToolTipTextOfHaveValue(toolTipTextOfHaveValue);

        String toolTipTextOfHaveNotValue = "(￣ェ￣;)  看一下和\"" + moduleName + "\"模块相关的地方，还有内容没有填写呢";
        variable.setToolTipTextOfHaveValue(toolTipTextOfHaveNotValue);

        super.addVariable(variable);
    }


}
