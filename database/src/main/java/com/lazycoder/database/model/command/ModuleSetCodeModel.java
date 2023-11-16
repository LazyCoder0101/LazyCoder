package com.lazycoder.database.model.command;

import com.lazycoder.database.model.general.GeneralCommandPathCodeModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设置方法的源码
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class ModuleSetCodeModel extends GeneralCommandPathCodeModel {

    /**
     * 类型名称
     */
    private String typeName = null;

    /**
     * 类型编号
     */
    private int typeSerialNumber = 0;

    private String moduleId;

}
