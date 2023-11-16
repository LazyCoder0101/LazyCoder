package com.lazycoder.database.model.command;

import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 其他代码控制类
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class AdditionalFunctionOperatingModel extends GeneralCommandOperatingModel {

    /**
     * 序号参数
     */
    private int additionalSerialNumber;

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

}
