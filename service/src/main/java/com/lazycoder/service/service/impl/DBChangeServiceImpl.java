package com.lazycoder.service.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.lazycoder.database.bean.DruidDBConfig;
import com.lazycoder.database.bean.DynamicDataSource;
import com.lazycoder.database.model.SysParam;
import com.lazycoder.lazycodercommon.vo.DataSourceInfo;
import com.lazycoder.lazycodercommon.vo.DataSourceLabel;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.DatabaseFrameFileMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.UserDataSourceIdentifyFile;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.UUIDUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

//@Slf4j
@PropertySource(value = {"classpath:application-db.properties"})
@Service
public class DBChangeServiceImpl implements DBChangeService {

    @Autowired
    @Qualifier(DruidDBConfig.DATA_SOURCE_MANAGER)
    private DynamicDataSource dynamicDataSource;


    @Value("${userdb.driverClassName}")
    private String userdbDriverClassName;

    @Value("${sqlitedb.urlPrefix}")
    private String sqlitedbUrlPrefix;

    @Value("${userdb.username}")
    private String userdbUsername;

    @Value("${userdb.password}")
    private String userPassword;

    public DBChangeServiceImpl() {
//	log.info("加载DBChangeServiceImpl");
    }

    /**
     * 查询某个用户数据源的使用状态
     *
     * @param dataSourceLabel
     * @return
     */
    public boolean getEnabledState(DataSourceLabel dataSourceLabel) {
        String currentDataSourceName = SysService.DB_CHANGE_SERVICE.getCurrentDataSourceId();
        SysService.DB_CHANGE_SERVICE.changeUserDB(dataSourceLabel.getDataSourceName(), dataSourceLabel.getDbId());
        boolean flag = SysService.SYS_PARAM_SERVICE.getEnabledState();
        SysService.DB_CHANGE_SERVICE.changeDb(currentDataSourceName);
        return flag;
    }

    /**
     * 根据 dBId 获取对应的用户数据源的信息
     *
     * @param dBId
     * @return
     */
    public DataSourceLabel getUserDataSourceInfoBy(String dBId) {
        DataSourceLabel dataSourceLabel = null;
        String theDbId;
        File identifyFileFileTemp;
        UserDataSourceIdentifyFile userDataSourceIdentifyFile;
        File[] files = SysFileStructure.getDataFileFolder().listFiles();
        for (File dataBase : files) {
            if (dataBase.isDirectory() == true) {
                identifyFileFileTemp = DatabaseFileStructure.getDBIdentifyFile(dataBase);
                if (identifyFileFileTemp.exists()) {
//                    content = FileUtil.getFileContent(identifyFileFileTemp.getAbsolutePath());
//                    userDataSourceIdentifyFile = JsonUtil.toBean(content, UserDataSourceIdentifyFile.class);
                    userDataSourceIdentifyFile = JsonUtil.fileToBean(identifyFileFileTemp.getAbsolutePath(),
                            UserDataSourceIdentifyFile.class
                    );
                    theDbId = userDataSourceIdentifyFile.getDbId();
                    if (theDbId != null && ("".equals(theDbId) == false)) {
                        if (theDbId.equals(dBId)) {
                            dataSourceLabel = new DataSourceLabel();
                            dataSourceLabel.setDbId(theDbId);
                            dataSourceLabel.setDataSourceName(userDataSourceIdentifyFile.getDbName());
                            break;
                        }
                    }
                }
            }
        }
        return dataSourceLabel;
    }

    /**
     * 获取所有的数据源信息
     *
     * @return
     */
    public ArrayList<DataSourceInfo> getAllDataSourceInfos() {
        String currentDataSourceName = getCurrentDataSourceId();
        ArrayList<DataSourceInfo> list = new ArrayList<DataSourceInfo>();
        findAllDataSourcesAndSetPutPath(SysFileStructure.getFileListFolder(), list);
        changeDb(currentDataSourceName);
        return list;
    }

    /**
     * 获取目前导入的所有用户数据源的个数
     *
     * @return
     */
    public int getAllUsrDataSourceNum() {//目前直接判断系统中存放用户数据源的文件夹里面，有多少个文件，来作为当前用户数据源的个数判断
        File[] files = SysFileStructure.getDataFileFolder().listFiles();
        if (files != null) {
            return files.length;
        }
        return 0;
    }

    /**
     * 根据用户数据源目录下的文件依次录入数据源名称、路径这些数据源信息
     *
     * @param folder 调用时要传 SysFileStructure.getFileListFolder()
     * @param list
     */
    private static void findAllDataSourcesAndSetPutPath(File folder, ArrayList<DataSourceInfo> list) {
        DataSourceInfo dataSourceInfo;
        File[] files = folder.listFiles();
        File databaseFileRootPath, identifyFileFile;
        UserDataSourceIdentifyFile userDataSourceIdentifyFile;
        if (files != null) {
            for (File temp : files) {
                if (temp.isDirectory() == true) {
                    if (DatabaseFrameFileMethod.isDataSourceFile(temp) == true) {
                        databaseFileRootPath = DatabaseFileStructure.getDatabaseFileRootPath(SysFileStructure.getDataFileFolder(), temp.getName());
                        identifyFileFile = DatabaseFileStructure.getDBIdentifyFile(databaseFileRootPath);
                        if (identifyFileFile.exists()) {
//                            String content = FileUtil.getFileContent(identifyFileFile.getAbsolutePath());
//                            userDataSourceIdentifyFile = JsonUtil.toBean(content, UserDataSourceIdentifyFile.class);
                            userDataSourceIdentifyFile = JsonUtil.fileToBean(identifyFileFile.getAbsolutePath(),
                                    UserDataSourceIdentifyFile.class);

                            String dbId = userDataSourceIdentifyFile.getDbId();
                            String currentDbId = SysService.DB_CHANGE_SERVICE.getCurrentDataSourceId();
                            SysService.DB_CHANGE_SERVICE.changeUserDB(userDataSourceIdentifyFile.getDbName(), dbId);

                            dataSourceInfo = new DataSourceInfo();
                            dataSourceInfo.setDbId(dbId);
                            dataSourceInfo.setDataSourceName(userDataSourceIdentifyFile.getDbName());
                            dataSourceInfo.setPutPath(temp.getAbsolutePath()
                                    .replace(SysFileStructure.getFileListFolder().getAbsolutePath(), ""));
                            dataSourceInfo.setDataSourceAnnotation(SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.DATA_SOURCE_ANNOTATION));
                            dataSourceInfo.setAuthorInfo(SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.AUTHOR));
                            dataSourceInfo.setEnabledState(SysService.SYS_PARAM_SERVICE.getEnabledState());
                            dataSourceInfo.setUseProgramingLanguage(SysService.SYS_PARAM_SERVICE.getUseProgramingLanguage());
                            list.add(dataSourceInfo);

                            SysService.DB_CHANGE_SERVICE.changeDb(currentDbId);
                        }
                    } else {
                        ArrayList<DataSourceInfo> tempList = new ArrayList<DataSourceInfo>();
                        findAllDataSourcesAndSetPutPath(temp, tempList);
                        list.addAll(tempList);
                    }
                }
            }
        }
    }

    public DataSourceLabel getDataSourcesInPath(File dbPath) {
        DataSourceLabel dataSourceLabel = new DataSourceLabel();

        File dBIdentifyFileTemp = DatabaseFileStructure.getDBIdentifyFile(dbPath);
        if (dBIdentifyFileTemp.exists()) {
//            String content = FileUtil.getFileContent(dBIdentifyFileTemp.getAbsolutePath());
//            UserDataSourceIdentifyFile userDataSourceIdentifyFile = JsonUtil.toBean(content, UserDataSourceIdentifyFile.class);
            UserDataSourceIdentifyFile userDataSourceIdentifyFile = JsonUtil.fileToBean(
                    dBIdentifyFileTemp.getAbsolutePath(), UserDataSourceIdentifyFile.class
            );
            dataSourceLabel.setDataSourceName(userDataSourceIdentifyFile.getDbName());
            dataSourceLabel.setDbId(userDataSourceIdentifyFile.getDbId());
        }
        return dataSourceLabel;
    }

    /**
     * 设置用户数据库的唯一id
     *
     * @param dataSourceName
     * @param dbId
     * @return
     */
    public boolean setUserDBID(String dataSourceName, String dbId) {
        boolean flag = true;
        String currentDataSourceName = getCurrentDataSourceId();
        SysService.DB_CHANGE_SERVICE.changeUserDB(dataSourceName, dbId);
        SysService.SYS_PARAM_SERVICE.updateSysParam(SysParam.DBID, dbId);
        SysService.DB_CHANGE_SERVICE.changeDb(currentDataSourceName);
        return flag;
    }

//    /**
//     * 添加用户数据源
//     *
//     * @param databaseName
//     * @return
//     */
//    public boolean addUserDatabase(String databaseName) {
//        boolean flag = selectExistOrNot(databaseName);
//        if (flag == false) {
//            flag = createUserDataSource(databaseName);
//        }
//        return flag;
//    }

    /**
     * 添加用户数据源
     *
     * @param databaseName
     * @return
     */
    public boolean addUserDatabase(String databaseName, String dbId) {
        boolean flag = selectExistOrNot(dbId);
        if (flag == false) {
            flag = createUserDataSource(databaseName, dbId);
        }
        return flag;
    }

//    /**
//     * 创建用户数据源
//     *
//     * @param databaseName
//     * @return
//     */
//    private boolean createUserDataSource(String databaseName) {
////        return createSqliteDataSource(databaseName, userdbUrlPrefix + databaseName + SysFileStructure.sqliteSuffix);
//        //在这里指定从用户路径那里获取系统数据源文件路径，避免从外部调用程序时把调用程序的路径带过去导致报错
//        return createSqliteDataSource(databaseName, sqlitedbUrlPrefix + SysFileStructure.getSqliteDBFile(databaseName).getAbsolutePath());
//    }//"jdbc:sqlite:" + sqlFile.getAbsolutePath());

    /**
     * 创建用户数据源
     *
     * @param databaseName
     * @return
     */
    private boolean createUserDataSource(String databaseName, String dbId) {
//        return createSqliteDataSource(databaseName, userdbUrlPrefix + databaseName + SysFileStructure.sqliteSuffix);
        //在这里指定从用户路径那里获取系统数据源文件路径，避免从外部调用程序时把调用程序的路径带过去导致报错
        DataSourceLabel dataSourceLabel = new DataSourceLabel();
        dataSourceLabel.setDataSourceName(databaseName);
        dataSourceLabel.setDbId(dbId);
        return createSqliteDataSource(dbId,
                sqlitedbUrlPrefix + SysFileStructure.getSqliteDBFile(databaseName).getAbsolutePath()
                , dataSourceLabel);
    }

    /**
     * 创建sqlite数据源
     *
     * @param dbId            数据源名称（如果是添加用户数据源，这里传唯一值UUID，别用用户自己随意命名那个名作为数据源名称，否则会出现由于名称问题，数据源有时无法正确指对，拿错数据源的情况，原因不明）
     * @param url
     * @param dataSourceLabel 对应这个数据源的一些信息，例如数据源名字，对应的uuid等
     * @return
     */
    public boolean createSqliteDataSource(String dbId, String url, DataSourceLabel dataSourceLabel) {
        boolean flag = true;
        try {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(dbId);
            druidDataSource.setDriverClassName(userdbDriverClassName);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(userdbUsername);
            druidDataSource.setPassword(userPassword);
            druidDataSource.setInitialSize(50); // 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
            druidDataSource.setMaxActive(100); // 最大连接池数量
            druidDataSource.setMaxWait(60000); // 获取连接时最大等待时间，单位毫秒。当链接数已经达到了最大链接数的时候，应用如果还要获取链接就会出现等待的现象，等待链接释放并回到链接池，如果等待的时间过长就应该踢掉这个等待，不然应用很可能出现雪崩现象
            druidDataSource.setMinIdle(40); // 最小连接池数量
            druidDataSource.setTestOnBorrow(true); // 申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
            druidDataSource.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
            druidDataSource.setValidationQuery("select 1");
            druidDataSource.setPoolPreparedStatements(false);
//            druidDataSource.setDefaultAutoCommit(false);
            druidDataSource.setMaxOpenPreparedStatements(0);
            // druidDataSource.setFilters("stat");//属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
            // druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
            // //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            // druidDataSource.setMinEvictableIdleTimeMillis(180000);
            // //配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
            // druidDataSource.setKeepAlive(true);
            // //打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select
            // * from
            // dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断
            // druidDataSource.setRemoveAbandoned(true); //是否移除泄露的连接/超过时间限制是否回收。
            // druidDataSource.setRemoveAbandonedTimeout(3600);
            // //泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里配置为1小时
            // druidDataSource.setLogAbandoned(true); ////移除泄露连接发生是是否记录日志

            druidDataSource.init();
            if (dynamicDataSource.checkConnectDatasource(userdbDriverClassName, druidDataSource)) {
                dynamicDataSource.addDataSouce(dbId, druidDataSource, dataSourceLabel);
                //把数据源的压入AbstractRoutingDataSource
                dynamicDataSource.buildDataSouce();
            } else {
                flag = false;
//                log.error("数据源配置有错误");
            }
        } catch (SQLException throwables) {
//            log.error("创建用户数据源初始化出错");
            throwables.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 切换到某个用户数据源
     *
     * @param databaseName
     * @param dbId
     * @return
     */
    @Override
    public boolean changeUserDB(String databaseName, String dbId) {
        DataSource dbSource = dynamicDataSource.getDataSource(dbId);
        if (dbSource == null) {//没有这数据源
            if (createUserDataSource(databaseName, dbId)) {//创建对应用户数据源成功
                changeDb(dbId);
//                log.info("----没有事先创建该数据源，创建并切换数据源：" + databaseName);
            } else {//创建数据源失败
                return false;
            }
        } else {
            changeDb(dbId);
//            log.info("----事先有创建这数据源，切换数据源：" + databaseName);
        }
        return true;
    }

    @Override
    public boolean changeDb(String datasourceId) {
        try {
            dynamicDataSource.changeDataSource(datasourceId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 切换到系统一开始默认打开的数据源
     */
    public void changeSYSDB() {
        changeDb(DruidDBConfig.SYS_DATA_SOURCE);
    }

    /**
     * 查看当前有没有添加过数据源 dataSourceName
     *
     * @param dataSourceName
     * @return
     */
    public boolean selectExistOrNot(String dataSourceName) {
        return dynamicDataSource.getDataSource(dataSourceName) == null ? false : true;
    }

    /**
     * 获取当前使用的数据源的名字
     *
     * @return
     */
    public String getCurrentDataSourceId() {
        return dynamicDataSource.getCurrentDataSourceName();
    }

//    public void addDatabase(DruidDataSource databasesource) {
//        dynamicDataSource.addDataSouce(databasesource.getName(), databasesource);
////        dataSourceMapper.add_database(databasesource);
//    }


    public void delDataBase(String datasourceId) {
        dynamicDataSource.delDataSources(datasourceId);
    }

    /**
     * 获取正在使用的用户数据源的数据库id
     *
     * @return
     */
    public ArrayList<String> getUsedUserDBId() {
        ArrayList<String> list = new ArrayList<>();
        String currentDataSourceName = getCurrentDataSourceId();
        String dbId;
        File[] files = SysFileStructure.getDataFileFolder().listFiles();
        File identifyFileFileTemp;
        UserDataSourceIdentifyFile userDataSourceIdentifyFile;
        for (File dataBase : files) {
            if (dataBase.isDirectory() == true) {
                identifyFileFileTemp = DatabaseFileStructure.getDBIdentifyFile(dataBase);
                if (identifyFileFileTemp.exists()) {
//                    content = FileUtil.getFileContent(identifyFileFileTemp.getAbsolutePath());
//                    userDataSourceIdentifyFile = JsonUtil.toBean(content, UserDataSourceIdentifyFile.class);
                    userDataSourceIdentifyFile = JsonUtil.fileToBean(
                            identifyFileFileTemp.getAbsolutePath(),
                            UserDataSourceIdentifyFile.class
                    );
                    dbId = userDataSourceIdentifyFile.getDbId();
                    if (dbId != null && ("".equals(dbId) == false)) {
                        changeUserDB(userDataSourceIdentifyFile.getDbName(), dbId);
                        boolean flag = SysService.SYS_PARAM_SERVICE.getEnabledState();
                        if (flag == true) {
                            list.add(dbId);
                        }
                    }
                }
            }
        }
        changeDb(currentDataSourceName);
        return list;
    }

    /**
     * 把引入的数据源和之前添加的进行比对，如果userid一样的，返回false
     *
     * @param folderFile
     * @return
     */
    public boolean checkImportSqlite(File folderFile) {
        boolean flag = true;
        if (folderFile != null && folderFile.isDirectory() == true) {//和目前导入的数据源进行比对，如果userid一样的，不给导入
            ArrayList<DataSourceInfo> allDataSourceInfoList = getAllDataSourceInfos();//获取当前所有添加的数据源信息
            File identifyFile = DatabaseFileStructure.getDBIdentifyFile(folderFile);
            if (identifyFile.exists()) {
//                String content = FileUtil.getFileContent(identifyFile.getAbsolutePath());
//                if (content != null && ("".equals(content) == false)) {
//                    UserDataSourceIdentifyFile userDataSourceIdentifyFile = JsonUtil.toBean(content, UserDataSourceIdentifyFile.class);
                UserDataSourceIdentifyFile userDataSourceIdentifyFile = JsonUtil.fileToBean(identifyFile.getAbsolutePath(),
                        UserDataSourceIdentifyFile.class);
                String importDbId = userDataSourceIdentifyFile.getDbId();
                for (DataSourceInfo dataSourceInfo : allDataSourceInfoList) {
                    if (importDbId.equals(dataSourceInfo.getDbId())) {//如果有发现一样id的不给导入
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;)  亲，这数据源和"
                                + dataSourceInfo.getDataSourceName() + "（路径：" + dataSourceInfo.getPutPath() + "）这个数据源好像是一样的，我暂时还没法支持导入同样的数据源");
                        break;
                    }
                    //                   }
                }
            }
        }
        return flag;
    }

    /**
     * 导出用户数据源
     *
     * @param oldDataSourceName
     * @param newDataSourceName
     * @param exPath
     * @return
     */
    public boolean exportUserDataSource(String oldDataSourceName, String newDataSourceName, File exPath) {
        boolean flag = true;
        try {
            exPath.mkdirs();
            File sysDSFolder = DatabaseFileStructure.getDatabaseFileRootPath(SysFileStructure.getDataFileFolder(), oldDataSourceName);
            FileUtil.copyDirIn(sysDSFolder.getAbsolutePath(), exPath.getAbsolutePath());//把数据源里面的文件都移到导出文件夹里面

            //先把所有文件取消隐藏，否则会报错
//            File[] fileList = exPath.listFiles();
//            for (File temp : fileList) {
//                FileUtil.clearFileHidden(temp);
//            }

            File file = DatabaseFileStructure.getDBIdentifyFile(exPath);//改写标志文件，更改里面的数据源名称
            String dbId = null;
            String cliVer = null;
            if (file.exists()) {
//                UserDataSourceIdentifyFile olduserDataSourceIdentifyFile = JsonUtil.toBean(
//                        FileUtil.getFileContent(file.getAbsolutePath()),
//                        UserDataSourceIdentifyFile.class);
                UserDataSourceIdentifyFile olduserDataSourceIdentifyFile = JsonUtil.fileToBean(
                        file.getAbsolutePath(),
                        UserDataSourceIdentifyFile.class);
                dbId = olduserDataSourceIdentifyFile.getDbId();

                //切换到该数据源，获取版本号以后再切换过来
                String currentDbId = SysService.DB_CHANGE_SERVICE.getCurrentDataSourceId();
                SysService.DB_CHANGE_SERVICE.changeUserDB(olduserDataSourceIdentifyFile.getDbName(), dbId);
                cliVer = SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.DS_CLI_VER);
                SysService.DB_CHANGE_SERVICE.changeDb(currentDbId);

                //dbId = dbId == null ? UUIDUtil.getUUID() : dbId;
                UserDataSourceIdentifyFile userDataSourceIdentifyFile = new UserDataSourceIdentifyFile(newDataSourceName, dbId, cliVer);
//            FileUtil.writeFile(file, JsonUtil.getJsonStr(userDataSourceIdentifyFile));
                JsonUtil.writeFile(file, userDataSourceIdentifyFile);

//            FileUtil.setFileHidden(file);

                File fromFile = SysFileStructure.getSqliteDBFile(oldDataSourceName);
                File toFile = DatabaseFileStructure.getSqliteDBFile(exPath.getParentFile(), newDataSourceName);
                if (fromFile.exists() == true) {
                    FileUtil.fileCopyNormal(fromFile, toFile);
                } else {
                    flag = false;
//                log.error("导出数据源异常，对应sql数据库为空：" + fromFile.getAbsolutePath());
                }
                DatabaseFrameFileMethod.setDBIcon(exPath);

                //把所有文件隐藏
                File[] fileList = exPath.listFiles();
                for (File temp : fileList) {
                    FileUtil.setFileHidden(temp);
                }
            } else {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error("数据源文件遭到莫名损坏");
            }
        } catch (IOException e) {
            flag = false;
//            log.error("导出数据源异常：" + e.getMessage());
        } catch (Exception e) {
            flag = false;
//            log.error("导出数据源出现异常：" + e.getMessage());
        }
        return flag;
    }

    /**
     * 导入用户数据源
     *
     * @param fromUserDSFile 要导入的数据源文件
     * @param newDSName      新起的数据源名字
     * @param originalDSName 原来的数据源名字
     * @param showPath       在显示面板里面显示的路径
     */
    public boolean importUserDataSource(File fromUserDSFile, String newDSName, String originalDSName, String
            showPath) {
        boolean flag = true;
        try {
//            File[] tempList = fromUserDSFile.listFiles();//先把引入的数据源里面所有文件都取消隐藏，否则会出现报错
//            for (File tempFile : tempList) {
//                FileUtil.clearFileHidden(tempFile);
//            }

            File sysDBFolder = SysFileStructure.getDataFileFolder(),
                    importFolder = DatabaseFileStructure
                            .getDatabaseFileRootPath(sysDBFolder, newDSName);
            importFolder.mkdirs();// 根据用户起的文件名创建对应的，用来存放该数据源文件数据的文件夹

            DatabaseFrameFileMethod.generateDBFileInFileListPath(
                    showPath, newDSName);// 在显示文件夹那里创建对应的文件，以标记该数据源当前显示的路径

            //把fromUserDSFile里面的文件复制过去
            FileUtil.copyDirIn(fromUserDSFile.getAbsolutePath(), importFolder.getAbsolutePath());// 把里面的文件数据都拷进去

            //根据新数据源名称生成对应标志文件
            File dBIdentifyFile = DatabaseFileStructure.getDBIdentifyFile(importFolder);
            String dbId = null;
            String cliVer = null;
            if (dBIdentifyFile.exists()) {
//                UserDataSourceIdentifyFile olduserDataSourceIdentifyFile = JsonUtil.toBean(
//                        FileUtil.getFileContent(dBIdentifyFile.getAbsolutePath()),
//                        UserDataSourceIdentifyFile.class);
                UserDataSourceIdentifyFile olduserDataSourceIdentifyFile = JsonUtil.fileToBean(
                        dBIdentifyFile.getAbsolutePath(),
                        UserDataSourceIdentifyFile.class);
                dbId = olduserDataSourceIdentifyFile.getDbId();
                cliVer = olduserDataSourceIdentifyFile.getCliVer();

                dbId = dbId == null ? UUIDUtil.getUUID() : dbId;

                dBIdentifyFile.delete();
                UserDataSourceIdentifyFile userDataSourceIdentifyFile = new UserDataSourceIdentifyFile(newDSName, dbId, cliVer);
//            FileUtil.writeFile(dBIdentifyFile, JsonUtil.getJsonStr(userDataSourceIdentifyFile));
                JsonUtil.writeFile(dBIdentifyFile, userDataSourceIdentifyFile);
//            FileUtil.setFileHidden(dBIdentifyFile);

                // 以下的操作是把数据源里面的sqlite数据库文件拷进系统存放用户的sqlite数据库文件的文件夹
                File originalNameFile = DatabaseFileStructure.getSqliteDBFile(sysDBFolder, newDSName, originalDSName),
                        newNameFile = DatabaseFileStructure.getSqliteDBFile(sysDBFolder, newDSName);

                originalNameFile.renameTo(newNameFile);
                File toFile = SysFileStructure.getSqliteDBFile(newDSName);

                FileUtil.fileCopyNormal(newNameFile, toFile);
                newNameFile.delete();

//            tempList = fromUserDSFile.listFiles();
//            for (File tempFile : tempList) {//把fromUserDSFile里面的所有文件都隐藏
//                FileUtil.setFileHidden(tempFile);
//            }

                File[] tempList = importFolder.listFiles();
                for (File tempFile : tempList) {//新引入的数据源里面的所有文件都隐藏
                    FileUtil.setFileHidden(tempFile);
                }

                DatabaseFrameFileMethod.setDBIcon(importFolder);

            } else {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error("导入数据源文件验证文件缺失");
            }
        } catch (IOException e) {
            flag = false;
//            log.error("导入用户数据源过程中出现文件异常：" + e.getMessage());
            // TODO Auto-generated catch block
        } catch (Exception e) {
            flag = false;
            // TODO Auto-generated catch block
//            log.error("导入用户数据源过程中出现异常：" + e.getMessage());
        }
        return flag;
    }

    /**
     * 检查添加的数据源名称是否符合
     *
     * @param path           显示文件夹那里对应的路径，不需要测这个直接填 null
     * @param dataSourceName
     * @return
     */
    public static boolean checkAddDataSourceName(String path, String dataSourceName) {
        boolean flag = true;
        if (dataSourceName != null) {
            if ("".equals(dataSourceName.trim())) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;)  亲，你要新建数据源，总得先告诉我名字吧");
            } else {
                if (FileUtil.isValidFileName(dataSourceName) == true) {
                    File sysDataFolder = SysFileStructure.getDataFileFolder();
                    File dbTemp = DatabaseFileStructure.getDatabaseFileRootPath(sysDataFolder, dataSourceName),
                            dbFileTemp = SysFileStructure.getSqliteDBFile(dataSourceName);
                    if (path != null) {
                        File showListTempFile = new File(path + File.separator + dataSourceName);
                        if (showListTempFile.isDirectory() == true) {
                            flag = false;
                            LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;)  亲，换个名字吧，有这数据源了");
                        }
                    }
                    if (flag == true) {
                        if (SysService.DB_CHANGE_SERVICE.selectExistOrNot(dataSourceName) == true) {//如果当前有这个名字的数据源，先不给起这名字
                            flag = false;
                            LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;)  亲，换个名字吧");
                        }
                    }
                    if (flag == true) {
                        if (dbTemp.isDirectory() == true) {
                            flag = false;
                            LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;)  亲，换个名字吧，有这数据源了");
                        }
                    }
                    if (flag == true) {
                        if (dbFileTemp.exists()) {
                            flag = false;
                            LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;)  亲，换个名字吧，有这数据源了" +
                                    "\n(PS：确定没有这数据源，一定要起这个名字的话，那退出系统，再创建数据源试试)");
                        }
                    }
                } else {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null, "(￣▽￣)／	换个名称吧，这名字。。。我感觉不合适");
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public DataSourceLabel getCurrentDataSourceLabel() {
        return dynamicDataSource.getCurrentDataSourceLabel();
    }

    /**
     * 改变数据源以及对应的数据源信息
     *
     * @param dataSourceLabel
     */
    public boolean renameDataSource(DataSourceLabel dataSourceLabel) {
        return dynamicDataSource.renameDataSource(dataSourceLabel);
    }

}
