package com.lazycoder.uicodegeneration.component.operation.container.sendparam;


import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FunctionAddInternalMethodOperatingContainerParam extends GeneralContainerComponentParam {

    private MethodAddStorageFormat methodAddStorageFormat;

    private FunctionAddControl controlElement = null;

    private FunctionAddParam functionAddParam;

    /**
     * 功能拓展组件所对应的代码所在位置（在功能拓展面板中添加功能的时候才用到）
     */
    private ContainerGenerateCodeResponseParam functionAddComponentPlaceCodeLocationInformation = null;

    /**
     * 添加功能时，所在的面板的对应属性（没设置标签id）
     */
    private CommandAddRelatedAttribute commandAddRelatedAttribute = null;

}
