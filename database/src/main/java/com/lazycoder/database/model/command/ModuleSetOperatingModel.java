package com.lazycoder.database.model.command;

import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设置方法控制
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class ModuleSetOperatingModel extends GeneralCommandOperatingModel {

    /**
     * 分类
     */
    private String className;

    /**
     * 模块名
     */
    private String moduleName;

    private String moduleId;

    /**
     * 模块序号
     */
    private int typeSerialNumber = 0;

    /**
     * 类型名称
     */
    private String typeName = null;

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

}
