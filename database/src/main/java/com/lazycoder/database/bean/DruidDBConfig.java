package com.lazycoder.database.bean;

import chy.frame.multidatasourcespringstarter.core.DataSourceChangeAop;
import chy.frame.multidatasourcespringstarter.core.transaction.MultiTransactionManagerAop;
import chy.frame.multidatasourcespringstarter.properties.DataSourceExtentProperties;
import com.lazycoder.lazycoderbaseconfiguration.LazyCoderBaseConfiguration;
import com.lazycoder.lazycodercommon.vo.DataSourceLabel;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@EnableConfigurationProperties(MultiDataSourceProperties.class)
@Configuration
@NoArgsConstructor
public class DruidDBConfig {

//    /**
//     * 当前程序目录
//     */
//    public static String sysDir;
//
//    static {
//        //生产环境用以下代码，目前似乎只能这样获取路径，用可选模板业务方法会在通过外部调用时获取到外部调用的路径导致报错
////        String path = System.getProperty("java.class.path");
////        int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
////        int lastIndex = path.lastIndexOf(File.separator) + 1;
////        sysDir = path.substring(firstIndex, lastIndex);
//
//        //开发环境用这句
//        sysDir = System.getProperty("user.dir") + File.separator + "app";
//    }

    @Value("${sqlitedb.urlPrefix}")
    private String sqlitedbUrlPrefix;

    //客户端使用的系统默认数据源的名字
    public static final String SYS_DATA_SOURCE = "sysDataSource";

    //系统使用的动态添加数据源的实例
    public static final String DATA_SOURCE_MANAGER = "dataSourceManager";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MultiDataSourceProperties multiDataSourceProperties;

    /**
     * 数据源管理实例，构造时注入配置文件里的数据源
     *
     * @return
     */
    @Bean(DruidDBConfig.DATA_SOURCE_MANAGER)
    public DynamicDataSource dataSourceManager() {
        //真正用来切换数据源的哥们
        DynamicDataSource dataSourceRouting = new DynamicDataSource();
        DataSource dataSource;
        DataSourceLabel dataSourceLabel;
        DataSourceExtentProperties dataSourceProperties;
        //拿到配置的所有配置信息
        Map<String, DataSourceExtentProperties> dataSourcePropertiesMap = multiDataSourceProperties.getDataSource();
        for (String dataSourceName : dataSourcePropertiesMap.keySet()) {
            dataSourceProperties = dataSourcePropertiesMap.get(dataSourceName);
            //根据配置文件生成dataSource
            dataSource = dataSource(dataSourceName, dataSourceProperties);

            dataSourceLabel = new DataSourceLabel();
            dataSourceLabel.setDataSourceName(dataSourceName);
            dataSourceLabel.setDbId(dataSourceName);

            //塞入
            dataSourceRouting.addDataSouce(dataSourceName, dataSource, dataSourceLabel);

            //设置一些连接池的额外值
            try {
                setExProperties(dataSource, dataSourceProperties);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                log.error("构造动态数据源切换实例出错：\n" + e.getMessage() + "\n\n\n");
            }
        }
        //把数据源的压入AbstractRoutingDataSource
        dataSourceRouting.buildDataSouce();
        return dataSourceRouting;
    }

    /**
     * 根据数据源属性生成数据源
     *
     * @param properties
     * @return
     */
    private DataSource dataSource(String dataSourceName, DataSourceProperties properties) {
        DataSourceBuilder factory;
        if (dataSourceName.equals(SYS_DATA_SOURCE)) {//系统数据源在这里指定传入用户路径，避免外部调用的时候用了外部调用所在路径导致出错，后续如有其他数据源随情况而定
            factory = DataSourceBuilder
                    .create(properties.getClassLoader())
                    .driverClassName(properties.getDriverClassName())
                    .url(sqlitedbUrlPrefix + LazyCoderBaseConfiguration.getSysDataSourceSqlite().getAbsolutePath()).username(properties.getUsername())
                    .password(properties.getPassword());

        } else {
            factory = DataSourceBuilder
                    .create(properties.getClassLoader())
                    .driverClassName(properties.getDriverClassName())
                    .url(properties.getUrl()).username(properties.getUsername())
                    .password(properties.getPassword());
            //如果配置文件另外再配其他数据源，需加上对应数据源信息
        }
        if (properties.getType() != null) {
            factory.type(properties.getType());
        }
        return factory.build();
    }

    /**
     * 使用反射把一些额外的配置设置进 DataSource 比如 druid dbcp 的一些额外配置线程池最大值等参数
     */
    private void setExProperties(DataSource dataSource, DataSourceExtentProperties dataSourceProperties) throws InvocationTargetException {
        Map<String, Object> pool = dataSourceProperties.getPool();
        if (pool == null) {
            return;
        }
        for (String key : pool.keySet()) {
            Object value = pool.get(key);
            Class<? extends DataSource> dataSourceClass = dataSource.getClass();
            try {
                //把key转驼峰
                String newKey = keyConver(key);
                Field field = getField(dataSourceClass, newKey);
                if (field == null) {
                    log.error("动态数据源配置错误：" + String.format("pool.%s = %s 没有正确映射 请检查参数是否正确", key, value));
                }
                setFieldValue(field, dataSource, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                log.error("根据配置文件的" + key + "字段配置数据源时出错：\n" + e.getMessage() + "\n\n\n");
            }
        }
    }

    /**
     * 对数据源的对应字段设值
     *
     * @param field
     * @param dataSource
     * @param value
     * @throws IllegalAccessException
     */
    private void setFieldValue(Field field, DataSource dataSource, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == boolean.class) {
            field.set(dataSource, Boolean.valueOf(value.toString()));

        } else if (field.getType() == java.lang.String.class) {
            field.set(dataSource, value.toString());

        } else if (field.getType() == int.class) {
            field.set(dataSource, Integer.parseInt(String.valueOf(value)));

        } else if (field.getType() == long.class) {
            field.set(dataSource, Long.valueOf(String.valueOf(value)));
        }
    }

    /**
     * 反射扫描属性,包括父类的
     *
     * @return
     */
    private Field getField(Class<? extends DataSource> dataSourceClass, String key) {
        try {
            Field field = dataSourceClass.getDeclaredField(key);
            return field;
        } catch (NoSuchFieldException e) {
        }
        Class<?> superclass = dataSourceClass.getSuperclass();
        try {
            return superclass.getDeclaredField(key);
        } catch (NoSuchFieldException e) {

        }
        return null;
    }

    private String keyConver(String data) {
        final StringBuffer sb = new StringBuffer();
        Matcher matcher = Pattern.compile("-(\\w)").matcher(data);
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 注册一个StatViewServlet druid监控页面配置1-帐号密码配置
     *
     * @return servlet registration bean
     */
//    @Bean
//    public ServletRegistrationBean druidStatViewServlet() {
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//        servletRegistrationBean.addInitParameter("loginUsername", "admin");
//        servletRegistrationBean.addInitParameter("loginPassword", "123456");
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }

    /**
     * 注册一个：filterRegistrationBean druid监控页面配置2-允许页面正常浏览
     *
     * @return filter registration bean
     */
//    @Bean
//    public FilterRegistrationBean druidStatFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        // 添加过滤规则.
//        filterRegistrationBean.addUrlPatterns("/*");
//        // 添加不需要忽略的格式信息.
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }
    @Bean
    @ConditionalOnBean(name = DATA_SOURCE_MANAGER)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_MANAGER) DataSource dataSource) throws Exception {
        //把数据源给注入进去
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 解决手动创建数据源后字段到bean属性名驼峰命名转换失效的问题
        sqlSessionFactoryBean.setConfiguration(configuration());
        // 设置mybatis的主配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 就是这句代码，只能指定单个mapper.xml文件，加通配符的话找不到文件
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 使用 chy.frame.multidatasourcespringstarter.annotation.DataSource 注解指定接口使用数据源需要用到的AOP
     *
     * @return
     */
    @Bean
    public DataSourceChangeAop changeAop() {
        DataSourceChangeAop dataSourceChangeAop = new DataSourceChangeAop();
        return dataSourceChangeAop;
    }

    /**
     * 使用 chy.frame.multidatasourcespringstarter.annotation.TransactionMulti 注解进行事务管理需要用到的AOP
     *
     * @return
     */
    @Bean
    public MultiTransactionManagerAop multiTransactionManagerAop() {
        MultiTransactionManagerAop multiTransactionManagerAop = new MultiTransactionManagerAop();
        return multiTransactionManagerAop;
    }

    /**
     * 读取驼峰命名设置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration configuration() {
        return new org.apache.ibatis.session.Configuration();
    }


}
