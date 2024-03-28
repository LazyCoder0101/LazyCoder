package com.lazycoder.database.model;

import lombok.Data;

@Data
public class CommandContainerImportCodeQueryParam {

    /**
     * 对应命令模型的类型
     */
    private String markElementName;

    /**
     * 对应的分类序号，有的没有
     */
    private int typeSerialNumber;

    /**
     * 对应的命令模型的序号
     */
    private int operatingOrdinalNumber;

    /**
     * 对应的代码的序号
     */
    private int codeOrdinal;


}
