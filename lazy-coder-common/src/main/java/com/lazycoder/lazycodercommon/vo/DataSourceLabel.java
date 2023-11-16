package com.lazycoder.lazycodercommon.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSourceLabel {

    /**
     * 数据源名称
     */
    private String dataSourceName = "";

    /**
     * 数据库id
     */
    private String dbId = null;

}
