package com.lazycoder.database.model.command;

import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 初始化代码控制类
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class FunctionOperatingModel extends GeneralCommandOperatingModel {

    /**
     * 默认的方法类型名称
     */
    public final static String DEFAULT_TYPE_NAME = "默认方法";

    private String moduleId = "";

    /**
     * 分类
     */
    private String className = "";

    /**
     * 模块名
     */
    private String moduleName = "";

    /**
     * 类型名称
     */
    private String typeName = null;

    private int typeSerialNumber = 0;

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

}
