package com.lazycoder.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每一个命令模型对应的每一条代码，需要调用的引入代码的信息
 */
@NoArgsConstructor
@Data
public class CommandContainerImportCode {

    /**
     * 序号（属于第几条）
     */
    private int ordinal;

    /**
     * 代码
     */
    private String code;

    /**
     * 对应的标签id
     */
    private String codeLabelId;

}
