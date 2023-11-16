package com.lazycoder.database.model;

import com.lazycoder.database.DataFormatType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附加文件
 *
 * @author admin
 */
@NoArgsConstructor
@Data
public class AttachedFile implements DataFormatType {

    /**
     * 类型
     */
    private int attachedFileType = MODULE_TYPE;

    private int additionalSerialNumber = 0;

    private String moduleId = "";

    /**
     * 序号
     */
    private int ordinal = 0;

    /**
     * 路径
     */
    private String path = "";


}
