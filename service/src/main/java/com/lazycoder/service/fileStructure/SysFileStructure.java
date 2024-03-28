package com.lazycoder.service.fileStructure;

import com.lazycoder.lazycoderbaseconfiguration.LazyCoderBaseConfiguration;
import com.lazycoder.service.service.SysService;
import com.lazycoder.utils.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * 系统文件结构
 *
 * @author admin
 */
public class SysFileStructure {

    /**
     * 懒农文件使用后缀
     */
    public static final String PRO_SUFFIX = ".lannong";

    /**
     * sqlite数据库后缀
     */
    public static final String SQLITE_SUFFIX = ".db";

//    /**
//     * 当前程序目录
//     */
////    public static final String sysDir = System.getProperty("user.dir") + File.separator + "app";
//    public static String sysDir = DruidDBConfig.sysDir;

    /**
     * 用户使用时添加数据源的文件存放路径
     *
     * @return
     */
    public static File getDataFileFolder() {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "fileDataPath");
    }

    /**
     * 根据此文件夹下的文件结构来进行列表显示
     *
     * @return
     */
    public static File getFileListFolder() {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "fileListPath");
    }

    /**
     * 系统图片路径
     *
     * @return
     */
    public static File getImageFolder() {
        return LazyCoderBaseConfiguration.getImageFolder();
    }

    /**
     * 操作提示的图片路径
     *
     * @param path 相对路径
     * @return
     */
    public static File getOperatingTipImageFolder(String path) {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "operatingTipImage" + File.separator + path);
    }

    /**
     * 用户使用时添加的sqlite数据库的存放路径
     *
     * @return
     */
    public static File getUserDBFolder() {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "lannong_db");
    }

    /**
     * 获取对应的sqlite数据库文件
     *
     * @param dataSourceName
     * @return
     */
    public static File getSqliteDBFile(String dataSourceName) {
        return new File(getUserDBFolder().getAbsolutePath() + File.separator + dataSourceName + SQLITE_SUFFIX);
    }

    /**
     * 系统数据库存放路径
     *
     * @return
     */
    public static File getSysDBFolder() {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "sysdb");
    }

    public static File getSysDataSourceSqlite() {
        return new File(getSysDBFolder().getAbsolutePath() + File.separator + "sysDataSource.db");
    }

    /**
     * 获取预览测试（生成代码测试）使用的存放项目的文件夹
     *
     * @return
     */
    public static File getPreviewTestProFolder() {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "previewTestPro");
    }

    /**
     * 获取预览测试（生成代码测试）对应项目的文件夹
     *
     * @return
     */
    public static File getPreviewTestProFolder(String projectName) {
        return new File(getPreviewTestProFolder().getAbsolutePath() + File.separator + projectName);
    }

    /**
     * 获取预览测试（生成代码测试）使用的显示文件路径的文件夹
     *
     * @return
     */
    public static File getPreviewTestShowFolder() {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "previewTestShow");
    }

    /**
     * 临时文件夹（用来放一些文件）
     *
     * @return
     */
    protected static File getTempFolder() {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "temp");
    }


    private static File appCoolFormatDll() {
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "CoolFormatLib.dll");
    }

    private static File userCoolFormatDll() {
        //return new File(System.getProperty("user.dir") + File.separator + "CoolFormatLib.dll");
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "CoolFormatLib.dll");
    }


    /**
     * 获取verilog的配置文件
     *
     * @return
     */
    public static File getVerilogSettingFile() {
        //return new File(System.getProperty("user.dir") + File.separator + "verilog"+File.separator+".verilog-format.properties");
        return new File(LazyCoderBaseConfiguration.sysDir + File.separator + "verilog" + File.separator + ".verilog-format.properties");
    }

    /**
     * 生成系统结构
     */
    public static void generateSysFileStrucure() {
        File file = getSysDBFolder();
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = getUserDBFolder();
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = getFileListFolder();
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = getDataFileFolder();
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = getTempFolder();
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = getPreviewTestProFolder();
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = getPreviewTestShowFolder();
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
    }

    /**
     * 复制CoolFormat的dll
     */
    public static void copyCoolFormatDll() {
        if (userCoolFormatDll().exists() == false) {
            if (appCoolFormatDll().exists() == true) {
                try {
                    FileUtil.fileCopyNormal(appCoolFormatDll(), userCoolFormatDll());
                    FileUtil.setFileHidden(appCoolFormatDll());
                } catch (IOException e) {
                    SysService.SYS_SERVICE_SERVICE.log_error("复制CoolFormat的dll出错，错误信息：" + e.getMessage());
                }
            } else {
                SysService.SYS_SERVICE_SERVICE.log_error("CoolFormat的dll丢失");
            }
        }
    }

}
