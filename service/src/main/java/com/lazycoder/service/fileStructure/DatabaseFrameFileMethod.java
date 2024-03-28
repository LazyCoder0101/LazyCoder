package com.lazycoder.service.fileStructure;

import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.UserDataSourceIdentifyFile;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class DatabaseFrameFileMethod {

    /**
     * 在显示文件夹路径path生成数据库对应的文件夹
     *
     * @param path         生成路径
     * @param databaseName 数据库名
     */
    public static void generateDBFileInFileListPath(String path, String databaseName) {
        File file = new File(path + File.separator + databaseName);
        file.mkdirs();
        setDBIcon(file);
        File identifyFile = DatabaseFileStructure.getDBIdentifyFile(file);
        FileUtil.writeFile(identifyFile, DatabaseFileStructure.DB_FILE_IDENTIFY_SECRET_KEY);
        FileUtil.setFileHidden(identifyFile);
    }

    /**
     * 检测fileListPath里面的文件夹是否为数据源文件
     *
     * @return
     */
    public static boolean isDataSourceFile(File folder) {
        boolean flag = false;
        if (folder.isDirectory()) {
            File file = new File(
                    folder.getAbsolutePath() + File.separator + DatabaseFileStructure.DB_IDENTIFY_FILE_NAME);
            if (file.exists()) {
                String content = FileUtil.getFileContent(file.getAbsolutePath());
                if (DatabaseFileStructure.DB_FILE_IDENTIFY_SECRET_KEY
                        .equals(content)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 生成数据库目录（如果数据库目录遭到损坏，一定程度上可以还原文件结构）
     * @param databaseName
     * @param dbId
     * @param cliVer
     */
    public static void generateOrCheckDBFileStrucure(String databaseName, String dbId, String cliVer) {
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File dbFile = DatabaseFileStructure.getDatabaseFileRootPath(sysDataFolder, databaseName);
        if (dbFile.isDirectory() == false) {
            dbFile.mkdirs();
            setDBIcon(dbFile);
        }
        File file = DatabaseFileStructure.getDBIdentifyFile(dbFile);
        if (file.exists() == false) {
            UserDataSourceIdentifyFile userDataSourceIdentifyFile = new UserDataSourceIdentifyFile(databaseName, dbId, cliVer);
//            FileUtil.writeFile(file,JsonUtil.getJsonStr(userDataSourceIdentifyFile));
            JsonUtil.writeFile(file, userDataSourceIdentifyFile);
            FileUtil.setFileHidden(file);
        }
        file = DatabaseFileStructure.getSqliteDBRootFile(sysDataFolder, databaseName);
        if (file.isDirectory() == false) {
            file.mkdirs();
            FileUtil.setFileHidden(file);
        }
        file = DatabaseFileStructure.getTemplateFolder(sysDataFolder, databaseName);
        if (file.isDirectory() == false) {
            file.mkdirs();
            FileUtil.setFileHidden(file);
        }
        file = DatabaseFileStructure.getUserDocFolder(sysDataFolder, databaseName);
        if (file.isDirectory() == false) {
            file.mkdirs();
            FileUtil.setFileHidden(file);
        }

        file = DatabaseFileStructure.getDataFolder(sysDataFolder, databaseName);
        if (file.isDirectory() == false) {
            file.mkdirs();
            FileUtil.setFileHidden(file);
        }

        generateOrCheckMainFileStrucure(databaseName);
        file = DatabaseFileStructure.getAdditionalRootFolder(sysDataFolder, databaseName);
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        // file = DatabaseFileStructure.getModuleRootFolder(databaseName);
        // if (file.isDirectory() == false) {
        // file.mkdirs();
        // }
    }

    /**
     * 用于检查文件存放路径文件有没有哪些不能用的文件
     *
     * @return
     */
    public static ArrayList<File> getRubbishDataFile() {
        ArrayList<File> rubbishFileList = new ArrayList<>();
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File[] files = sysDataFolder.listFiles();
        String databaseName;
        for (File fileTemp : files) {
            boolean flag = true;
            databaseName = fileTemp.getName();
            File dbFile = DatabaseFileStructure.getDatabaseFileRootPath(sysDataFolder, databaseName);
            if (flag == true && dbFile.isDirectory() == false) {
                flag = false;
            }
            File file = DatabaseFileStructure.getDBIdentifyFile(dbFile);
            if (flag == true && file.exists() == false) {
                flag = false;
            }
            file = DatabaseFileStructure.getSqliteDBRootFile(sysDataFolder, databaseName);
            if (flag == true && file.isDirectory() == false) {
                flag = false;
            }
            file = DatabaseFileStructure.getTemplateFolder(sysDataFolder, databaseName);
            if (flag == true && file.isDirectory() == false) {
                flag = false;
            }
            file = DatabaseFileStructure.getUserDocFolder(sysDataFolder, databaseName);
            if (flag == true && file.isDirectory() == false) {
                flag = false;
            }

            file = DatabaseFileStructure.getDataFolder(sysDataFolder, databaseName);
            if (flag == true && file.isDirectory() == false) {
                flag = false;
            }
            file = DatabaseFileStructure.getMainNeedFileFolder(sysDataFolder, databaseName);
            if (flag == true && file.isDirectory() == false) {
                flag = false;
            }
            file = DatabaseFileStructure.getMainPictureFolder(sysDataFolder, databaseName);
            if (flag == true && file.isDirectory() == false) {
                flag = false;
            }

            file = DatabaseFileStructure.getAdditionalRootFolder(sysDataFolder, databaseName);
            if (flag == true && file.isDirectory() == false) {
                flag = false;
            }

            if (flag==false){
                rubbishFileList.add(fileTemp);
            }
        }
        return rubbishFileList;
    }

    /**
     * 检测该文件是否为懒农的数据源文件，且是否完整
     *
     * @param folderFile
     */
    public static boolean checkDBFileStrucure(File folderFile) {
        boolean flag = true;
        File parentFile = folderFile.getParentFile();

        File identifyFileFile = DatabaseFileStructure.getDBIdentifyFile(folderFile);
        if (identifyFileFile.exists() == true) {
            try {
//                String content = FileUtil.getFileContent(identifyFileFile.getAbsolutePath());//获取标志文件，主要是为了校验以及拿里面的数据源名称
//                UserDataSourceIdentifyFile userDataSourceIdentifyFile = JsonUtil.toBean(content, UserDataSourceIdentifyFile.class);
                UserDataSourceIdentifyFile userDataSourceIdentifyFile = JsonUtil.fileToBean(identifyFileFile.getAbsolutePath(),
                        UserDataSourceIdentifyFile.class);
                if (userDataSourceIdentifyFile == null) {
                    flag = false;
                } else {
                    if (userDataSourceIdentifyFile.getDbName() != null && ("".equals(userDataSourceIdentifyFile.getDbName()) == false)) {
                        if (DatabaseFileStructure.LAZY_CODER_DATE_SOURCE_SECRET_KEY.equals(userDataSourceIdentifyFile.getIdKey())) {//这里先判断一下，看看文件有没有问题

                            String databaseNameTemp = userDataSourceIdentifyFile.getDbName();//拿到记录的数据源名称，以该名称为准
                            File file = DatabaseFileStructure.getSqliteDBFile(parentFile, databaseNameTemp);
                            if (file.exists()) {//看看有没有对应名称的sqlite文件，有才继续监测
                                if (flag == true) {
                                    file = DatabaseFileStructure.getTemplateFolder(parentFile, databaseNameTemp);
                                    if (file.isDirectory() == false) {
                                        flag = false;
                                    }
                                }
                                if (flag == true) {
                                    file = DatabaseFileStructure.getUserDocFolder(parentFile, databaseNameTemp);
                                    if (file.isDirectory() == false) {
                                        flag = false;
                                    }
                                }

                                if (flag == true) {
                                    file = DatabaseFileStructure.getDataFolder(parentFile, databaseNameTemp);
                                    if (file.isDirectory() == false) {
                                        flag = false;
                                    }
                                }

                                if (flag == true) {
                                    file = DatabaseFileStructure.getAdditionalRootFolder(parentFile, databaseNameTemp);
                                    if (file.isDirectory() == false) {
                                        flag = false;
                                    }
                                }

                                if (flag == true) {
                                    file = DatabaseFileStructure.getMainNeedFileFolder(parentFile, databaseNameTemp);
                                    if (file.isDirectory() == false) {
                                        flag = false;
                                    }
                                }

                                if (flag == true) {
                                    file = DatabaseFileStructure.getMainPictureFolder(parentFile, databaseNameTemp);
                                    if (file.isDirectory() == false) {
                                        flag = false;
                                    }
                                }
                            } else {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    } else {
                        flag = false;
                        String content = FileUtil.getFileContent(identifyFileFile.getAbsolutePath());
                        SysService.SYS_SERVICE_SERVICE.log_error(
                                "懒农用户数据源标记文件有问题 文件路径：" + content
                        );
                    }
                }
            } catch (Exception e) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(
                        "懒农用户数据源标记文件解析异常 " + e.getMessage()
                );
            }
        } else {
            flag = false;
        }
        if (flag == true) {
            flag = SysService.DB_CHANGE_SERVICE.checkImportSqlite(folderFile);
        } else {
            LazyCoderOptionPane.showMessageDialog(null, "Σ(っ°Д°;)っ 这真的是数据源文件吗？如果是的话，这数据源文件被破坏了，没法使用了");
        }
        return flag;
    }

    /**
     * 生成对应的sqlite数据库文件，在这里仅是复制sqlite文件到文件夹
     */
    public static void generateDBFile(String databaseName) {
        try {
            //在这里不写入版本号，直到向数据库保存数据才写，生成的时候写意义不大
            File fromFile = new File(SysFileStructure.getSysDBFolder().getAbsolutePath() + File.separator + "newDB"
                    + SysFileStructure.SQLITE_SUFFIX),
                    toFile = SysFileStructure.getSqliteDBFile(databaseName);
            FileUtil.fileCopyNormal(fromFile, toFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDBIcon(File dbFile) {
        File oldIconFile = SysFileUtil.DB_ICO, newIconFile;
        try {
            newIconFile = new File(dbFile.getAbsolutePath() + File.separator + SysFileUtil.DB_ICON_FILE_NAME);
            if (newIconFile.exists()) {
                newIconFile.delete();
            }
            FileUtil.fileCopyNormal(oldIconFile, newIconFile);
            SysFileUtil.generateUpdateDBIconFile(dbFile);
            SysFileUtil.commandForSetIcon(dbFile);
            FileUtil.setFileHidden(newIconFile);
//            SysFileUtil.runBatFile(batFile);
//            if (batFile.exists()) {
//                batFile.delete();
//            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测或生成必填模板文件结构
     *
     * @param databaseName
     */
    private static void generateOrCheckMainFileStrucure(String databaseName) {
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File file = DatabaseFileStructure.getMainNeedFileFolder(sysDataFolder, databaseName);
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = DatabaseFileStructure.getMainPictureFolder(sysDataFolder, databaseName);
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
    }

    /**
     * 检测或生成可选模板文件结构
     *
     * @param databaseName
     */
    public static void generateOrCheckAdditionalFileStrucure(String databaseName, int ordinal) {
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File file = DatabaseFileStructure.getAdditionalNeedFileFolder(sysDataFolder, databaseName, ordinal);
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = DatabaseFileStructure.getAdditionalPictureFolder(sysDataFolder, databaseName, ordinal);
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
    }

    /**
     * 检测或生成模块文件结构
     *
     * @param databaseName
     * @param moduleId
     */
    public static void generateOrCheckModuleFileStrucure(String databaseName, String moduleId) {
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File file = DatabaseFileStructure.getModuleNeedFileFolder(sysDataFolder, databaseName, moduleId);
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = DatabaseFileStructure.getModulePictureFolder(sysDataFolder, databaseName, moduleId);
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
        file = DatabaseFileStructure.getModuleAttachedFileFolder(sysDataFolder, databaseName, moduleId);
        if (file.isDirectory() == false) {
            file.mkdirs();
        }
    }


    public static void deleteModule(String databaseName, String moduleId) {
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File folder = DatabaseFileStructure.getModuleFolder(sysDataFolder, databaseName, moduleId);
        if (folder.isDirectory()) {
            FileUtil.delFolder(folder.getAbsolutePath());
        }
    }


    /**
     * 在测试预览显示文件夹路径path生成项目对应的文件夹
     *
     * @param path         生成路径
     * @param proName 项目名
     */
    public static void generateProFileInPreviewTestShowPath(String path, String proName) {
        File file = new File(path + File.separator + proName);
        file.mkdirs();
        SourceGenerateFileMethod.setLannongProIcon(file);
        File identifyFile = DatabaseFileStructure.getPreviewTestProFileIdentifyFile(file);
        FileUtil.writeFile(identifyFile, DatabaseFileStructure.PREVIEW_TEST_PRO_FILE_IDENTIFY_SECRET_KEY);
        FileUtil.setFileHidden(identifyFile);
    }

    /**
     * 检测 预览测试 里面的文件夹是否为生成代码的测试文件
     *
     * @return
     */
    public static boolean isProFileInPreviewTestProFile(File folder) {
        boolean flag = false;
        if (folder.isDirectory()) {
            File file = new File(
                    folder.getAbsolutePath() + File.separator + DatabaseFileStructure.PREVIEW_TEST_PRO_IDENTIFY_FILE_NAME);
            if (file.exists()) {
                String content = FileUtil.getFileContent(file.getAbsolutePath());
                if (DatabaseFileStructure.PREVIEW_TEST_PRO_FILE_IDENTIFY_SECRET_KEY
                        .equals(content)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

}
