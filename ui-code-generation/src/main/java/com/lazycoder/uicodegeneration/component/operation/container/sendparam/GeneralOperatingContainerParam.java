package com.lazycoder.uicodegeneration.component.operation.container.sendparam;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractCommandOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralOperatingContainerParam {

    /**
     * 放置这个组件的面板
     */
    protected AbstractCodeControlPane codeControlPane;

    protected Module module = null;

    protected ModuleInfo moduleInfo = null;

    /**
     * 首个操作组件
     */
    protected AbstractCommandOpratingContainer firstCommandOpratingContainer = null;

    /**
     * 对应格式组件(格式使用)
     */
    protected AbstractFormatContainer formatContainer = null;

    /**
     * 代码控制总面板
     */
    protected AbstractFormatControlPane formatControlPane = null;

    /**
     * 对应该OpratingContainer
     */
    private OpratingContainerInterface thisOpratingContainer = null;

    /**
     * 上一层的OpratingContainer
     */
    private OpratingContainerInterface parentOpratingContainer = null;

    private CustomVariableHolder thisCustomVariableHolder = null;

    private CustomFunctionNameHolder thisCustomFunctionNameHolder = null;

}
