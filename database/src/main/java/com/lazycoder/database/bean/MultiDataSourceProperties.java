package com.lazycoder.database.bean;

import chy.frame.multidatasourcespringstarter.properties.DataSourceExtentProperties;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 源码摘自 https://github.com/cao2068959/multidatasource-spring-starter
 */
@ConfigurationProperties(prefix = "chy.multi")
public class MultiDataSourceProperties {

    Map<String, DataSourceExtentProperties> dataSource;

    public Map<String, DataSourceExtentProperties> getDataSource() {
        return dataSource;
    }

    public void setDataSource(Map<String, DataSourceExtentProperties> dataSource) {
        this.dataSource = dataSource;
    }

}
