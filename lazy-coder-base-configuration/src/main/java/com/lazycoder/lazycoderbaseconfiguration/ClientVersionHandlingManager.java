package com.lazycoder.lazycoderbaseconfiguration;

/**
 * 后续每发布一个新的客户端，其对应的版本兼容等处理方法都写在这里
 */
public class ClientVersionHandlingManager {

    /**
     * 生成项目版本检测 （后续迭代，每发布一个新版本，都可以在这里写对应这个版本的对历史版本的兼容检测，这里仅作版本检测，后续处理另写）
     * @param proClientVersion 生成这个项目所使用的客户端，对应的客户端版本（最后点击生成代码的时候写入，这里只是给个初始值）
     * @param dataSourceClientVersion 生成这个项目所使用的数据源，对应的客户端版本（最后点击生成代码的时候写入，这里只是给个初始值）
     * @return
     */
    public static boolean proVersionDetection(String proClientVersion, String dataSourceClientVersion){

        return true;
    }

    /**
     * 懒农数据源版本检测（后续迭代，每发布一个新版本，都可以在这里对这个版本对历史版本的数据源的兼容检测，这里仅作版本检测，后续处理另写）
     * @param cliVer
     * @return
     */
    public static boolean dataSourceVersionDetection(String cliVer){

        return true;
    }

}
