package com.lazycoder.database.bean;

import chy.frame.multidatasourcespringstarter.core.DataSourceRouting;
import com.lazycoder.lazycodercommon.vo.DataSourceLabel;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

/**
 * 动态数据源切换使用
 */
public class DynamicDataSource extends DataSourceRouting {

    private Map<String, DataSourceLabel> dataSourceLabelMap = new HashMap();

//    private final Logger log = LoggerFactory.getLogger(getClass());

    public DynamicDataSource() {
//		log.info("延迟加载DynamicDataSource");
    }

    @Override
    protected Object determineCurrentLookupKey() {
        Object kty = super.determineCurrentLookupKey();
        return kty;
    }

    /**
     * 决定使用哪个数据源之前需要把多个数据源的信息以及默认数据源信息配置好
     *
     * @param defaultTargetDataSource 默认数据源
     * @param targetDataSources       目标数据源
     */
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }


    // 测试数据源连接是否有效
    public boolean checkConnectDatasource(String driveClass, DataSource dataSource) {
        try {
            Class.forName(driveClass);
            dataSource.getConnection();
            return true;
        } catch (Exception e) {
            logger.error("数据源测试连接无法连通：" + e.getMessage());
            return false;
        }
    }

    @Deprecated
    @Override
    public void addDataSouce(String name, DataSource dataSource) {
        super.addDataSouce(name, dataSource);
    }

    /**
     * 添加数据源以及对应的相关信息
     *
     * @param dbId            数据源唯一的id值
     * @param dataSource
     * @param dataSourceLabel
     */
    public void addDataSouce(String dbId, DataSource dataSource, DataSourceLabel dataSourceLabel) {
        super.addDataSouce(dbId, dataSource);
        dataSourceLabelMap.put(dbId, dataSourceLabel);
    }

//    @Override
//    public void changeDataSource(String name) {
//        super.changeDataSource(name);
//    }

    /**
     * 改变数据源以及对应的数据源信息
     *
     * @param dataSourceLabel
     */
    public boolean renameDataSource(DataSourceLabel dataSourceLabel) {
        DataSource dataSource = getDataSource(dataSourceLabel.getDbId());
        if (dataSource != null) {//当前有添加过这个数据源
            boolean flag = super.delDataSources(dataSourceLabel.getDbId());
            if (flag) {
                dataSourceLabelMap.remove(dataSourceLabel.getDbId());
                dataSourceLabelMap.put(dataSourceLabel.getDbId(), dataSourceLabel);
            }
            return flag;
//        }else {//当前没有添加过这个数据源
        }
        return true;
    }

    @Override
    public boolean delDataSources(String datasourceName) {
        dataSourceLabelMap.remove(datasourceName);
        return super.delDataSources(datasourceName);
    }

    public DataSourceLabel getCurrentDataSourceLabel() {
        return dataSourceLabelMap.get(getCurrentDataSourceName());
    }


}
