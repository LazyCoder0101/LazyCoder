package com.lazycoder.lazycoderbaseconfiguration;

import java.io.File;

public class LazyCoderBaseConfiguration {

    /**
     * 系统目录
     */
    public static String sysDir;

    static {
        //生产环境用以下代码，目前似乎只能这样获取路径，用可选模板业务方法会在通过外部调用时获取到外部调用的路径导致报错
        String path = System.getProperty("java.class.path");
        int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
        int lastIndex = path.lastIndexOf(File.separator) + 1;
        sysDir = path.substring(firstIndex, lastIndex) + File.separator;

        //开发环境用这句
//        sysDir = System.getProperty("user.dir") + File.separator + "app";


        System.setProperty("clientDir", sysDir);//插入一个系统属性，供其他地方获取当前运行jar或者程序所在路径
        System.setProperty("clientVersion", "1.0.1");//在这里定义客户端的版本号

    }

    /**
     * 获取系统数据源的路径
     *
     * @return
     */
    public static File getSysDataSourceSqlite() {
        return new File(sysDir + File.separator + "sysdb" + File.separator + "sysDataSource.db");
    }

    /**
     * 系统图片路径
     *
     * @return
     */
    public static File getImageFolder() {
        return new File(sysDir + File.separator + "image");
    }

}
