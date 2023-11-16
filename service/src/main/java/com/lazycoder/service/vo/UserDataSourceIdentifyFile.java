package com.lazycoder.service.vo;

import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生成程序的数据源对应的写入内容
 */
@Data
@NoArgsConstructor
public class UserDataSourceIdentifyFile {

    private final String idKey = DatabaseFileStructure.LAZY_CODER_DATE_SOURCE_SECRET_KEY;

    private String dbName;

    private String dbId = null;

    /**
     * 最后生成这个数据源时，对应的客户端版本
     */
    private String cliVer = System.getProperty("clientVersion");

    public UserDataSourceIdentifyFile(String dbName, String dbId, String cliVer) {
        this.dbName = dbName;
        this.dbId = dbId;
        this.cliVer = cliVer;
    }

}
