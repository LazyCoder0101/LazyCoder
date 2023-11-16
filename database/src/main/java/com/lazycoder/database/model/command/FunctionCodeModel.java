package com.lazycoder.database.model.command;

import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FunctionCodeModel extends GeneralCommandCodeModel {

    /**
     * 类型名称
     */
    private String typeName = null;

    private int typeSerialNumber = 0;

    private String moduleId = "";

}
