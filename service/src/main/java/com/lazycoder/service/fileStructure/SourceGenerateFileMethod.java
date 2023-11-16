package com.lazycoder.service.fileStructure;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.AttachedFile;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.service.SysService;
import com.lazycoder.utils.CreateShortcut;
import com.lazycoder.utils.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SourceGenerateFileMethod {

    private static String logFlag = "源码结构生成日志";

//    public static void main(String[] args) {
//
//        try {
//            generateProjectFileStrucure("F:/懒农源码生成测试路径", "懒农项目");
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /**
     * 生成项目文件结构
     *
     * @param projectParentPath
     * @param projectName
     * @throws InterruptedException
     */
    public static void generateProjectFileStrucure(String projectParentPath, String projectName)
            throws InterruptedException {

        File file = SourceGenerateFileStructure.getTheProjectPath(projectParentPath, projectName);
        file.mkdirs();//生成这个项目的文件夹

        File logFile = new File(file.getAbsolutePath() + File.separator + "logs");//生成对应项目的日志文件夹
        logFile.mkdirs();
        FileUtil.setFileHidden(logFile);

        //把启动文件复制过去,并生成快捷方式
        File fromRunExeFile = SysFileUtil.PRO_RUN_EXE;
        if (fromRunExeFile.exists()) {
            File toRunExeFile = new File(file.getAbsolutePath() + File.separator + fromRunExeFile.getName());
            try {
                FileUtil.fileCopyNormal(fromRunExeFile, toRunExeFile);
                CreateShortcut.createLnk(file, toRunExeFile, "懒农");
                FileUtil.setFileHidden(toRunExeFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //设个标记，标记为懒农项目文件
        File proFlagFile = SourceGenerateFileStructure.getLannongProFlagFile(projectParentPath, projectName);
        FileUtil.writeFile(proFlagFile, DatabaseFileStructure.GENERATE_SOURCE_FILE_SECRET_KEY);
        FileUtil.setFileHidden(proFlagFile);

        //生成使用文档存放文件夹
        file = SourceGenerateFileStructure.getGenerateSourceUsingDocumentPath(projectParentPath, projectName);
        file.mkdirs();

        //生成源码存放文件夹
        file = SourceGenerateFileStructure.getTheProjectPathInGenerateSourcePutPath(projectParentPath, projectName);
        file.mkdirs();

        //在源码存放文件夹文件夹里面放一个项目名称的文件夹来放源码
        file = SourceGenerateFileStructure.getGenerateSourceLannongPath(projectParentPath, projectName);
        file.mkdirs();
        FileUtil.setFileHidden(file);

        //把格式（可选模板和必填模板的文件复制过去）
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File formatDB = DatabaseFileStructure.getFormatRootFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName());
        try {
            FileUtil.copyDir(formatDB.getAbsolutePath(), file.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //生成必填模板默认文件存放路径
        file = SourceGenerateFileStructure.getMainDefaultPath(projectParentPath, projectName);
        file.mkdirs();

        //生成必填模板其他文件存放路径
        file = SourceGenerateFileStructure.getMainSubPath(projectParentPath, projectName);
        file.mkdirs();

        //生成必填模板操作内容保存文件存放路径
        file = SourceGenerateFileStructure.getMainOpratingPanePath(projectParentPath, projectName);
        file.mkdirs();

        //每个可选模板都生成对应的默认文件存放路径、其他文件存放路径、操作内容保存文件存放路径
        file = SourceGenerateFileStructure.getAdditionalRootFilePutPath(projectParentPath, projectName);
        String[] strArray = file.list();
        if (strArray != null) {
            for (int i = 1; i <= strArray.length; i++) {
                file = SourceGenerateFileStructure.getAdditionalDefaultPath(projectParentPath, projectName, i);
                file.mkdirs();

                file = SourceGenerateFileStructure.getAdditionalSubPath(projectParentPath, projectName, i);
                file.mkdirs();

                file = SourceGenerateFileStructure.getAdditionalOpratingPanePath(projectParentPath, projectName, i);
                file.mkdirs();
            }
        }

        //生成起始文件存放路径
        file = SourceGenerateFileStructure.getRootTempFilePutPath(projectParentPath, projectName);
        file.mkdirs();

        //生成模块文件存放路径
        file = SourceGenerateFileStructure.getModuleTempFilePutPath(projectParentPath, projectName);
        file.mkdirs();

        file = SourceGenerateFileStructure.getTheProjectPath(projectParentPath, projectName);
        setLannongProIcon(file);//设置项目文件夹图标为懒农图标
    }

//    private static final Logger log = LoggerFactory.getLogger("检测结构日志");

    /**
     * 简要检查是否为懒农的项目文件，且是否完整
     */
    public static boolean checkLannongProFileStrucure(String projectParentPath, String projectName) {
        boolean flag = true;
        File file = SourceGenerateFileStructure.getLannongProFlagFile(projectParentPath, projectName);
        if (file.exists() == true) {
            if (!DatabaseFileStructure.GENERATE_SOURCE_FILE_SECRET_KEY
                    .equals(FileUtil.getFileContent(file.getAbsolutePath()))) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 秘钥错误" + FileUtil.getFileContent(file.getAbsolutePath()));
            }
        } else {
            flag = false;
            SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 1找不到文件" + file.getAbsolutePath());
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getRootTempFilePutPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 2找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateProFile.getCodeGenerationRootProFile(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 3找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getModuleTempFilePutPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 4找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getGenerateSourceUsingDocumentPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 5找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getGenerateSourcePutPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 6找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getGenerateSourceLannongPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 7找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getMainDefaultPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 8找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getMainSubPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 9找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getMainOpratingPanePath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 10找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getMainPictureFilePutPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 11找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getMainNeedFilePutPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 12找不到文件" + file.getAbsolutePath());
            }
        }

        if (flag == true) {
            file = SourceGenerateFileStructure.getAdditionalRootFilePutPath(projectParentPath, projectName);
            if (file.exists() == false) {
                flag = false;
                SysService.SYS_SERVICE_SERVICE.log_error(logFlag + " 13找不到文件" + file.getAbsolutePath());
            }
        }
        return flag;
    }

    /**
     * 把文件夹 projectFolder 的图标设为懒农图标
     *
     * @param projectFolder
     */
    public static void setLannongProIcon(File projectFolder) {
        File oldIconFile = SysFileUtil.LANNONG_ICO, newIconFile;
        try {
            newIconFile = new File(projectFolder.getAbsolutePath() + File.separator + SysFileUtil.LAZY_CODE_ICON_FILE_NAME);
            if (newIconFile.isHidden() == false) {
                FileUtil.fileCopyNormal(oldIconFile, newIconFile);
                SysFileUtil.generateUpdateLannongIconFile(projectFolder);
//            SysFileUtil.runBatFile(batFile);
//            if (batFile.exists()) {
//                batFile.delete();
//            }
                SysFileUtil.commandForSetIcon(projectFolder);
                FileUtil.setFileHidden(newIconFile);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把模板复制到项目里
     *
     * @param projectParentPath
     * @param projectName
     */
    public static void copyTemplateInProject(String projectParentPath, String projectName) {
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File templateFolder = DatabaseFileStructure.getTemplateFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName()),
                proFolderInSource = SourceGenerateFileStructure
                        .getTheProjectPathInGenerateSourcePutPath(projectParentPath, projectName);
        try {
            FileUtil.copyDirIn(templateFolder.getAbsolutePath(), proFolderInSource.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 把预先设置需要改为工程名的文件都重命名
     *
     * @param projectParentPath
     * @param projectName
     */
    public static void renameToNeedProjectFiles(String projectParentPath, String projectName) {
        File sourceFileTemp, toFileTemp;
        String ex;
        ArrayList<String> xxlist = SysService.SYS_PARAM_SERVICE.getRenameFileList();
        for (String renameTempTemp : xxlist) {
            if (renameTempTemp != null && ("".equals(renameTempTemp) == false)) {
                sourceFileTemp = new File(SourceGenerateFileStructure
                        .getTheProjectPathInGenerateSourcePutPath(projectParentPath, projectName).getAbsolutePath()
                        + File.separator + renameTempTemp);
                if (sourceFileTemp.exists()) {
                    ex = FileUtil.getFileEx(sourceFileTemp.getName());
                    toFileTemp = new File(sourceFileTemp.getParent() + File.separator + projectName + ex);
                    sourceFileTemp.renameTo(toFileTemp);
                }
            }
        }
    }

    /**
     * 把使用文档复制到生成的项目里
     *
     * @param projectParentPath
     * @param projectName
     */
    public static void copyUseDocInProject(String projectParentPath, String projectName) {
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File dataUseDocFolder = DatabaseFileStructure.getUserDocFolder(sysDataFolder,
                GeneralHolder.currentUserDataSourceLabel.getDataSourceName()),
                proUseDocFolder = SourceGenerateFileStructure.getGenerateSourceUsingDocumentPath(projectParentPath,
                        projectName);
        try {
            FileUtil.copyDirIn(dataUseDocFolder.getAbsolutePath(), proUseDocFolder.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 复制模块数据到项目
     *
     * @param projectParentPath
     * @param projectName
     * @param moduleInfo
     */
    public static void copyModuleDataInProject(String projectParentPath, String projectName, ModuleInfo moduleInfo) {
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        File dataModuleFolder = DatabaseFileStructure.getModuleFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                moduleInfo.getModuleId()),
                moduleTempFolder = SourceGenerateFileStructure.getModuleTempFilePutPath(projectParentPath, projectName);
        moduleTempFolder.mkdirs();
        //把用户数据库里面的对应模块文件夹复制到生成项目的分类的文件夹里
        try {
            FileUtil.copyDir(dataModuleFolder.getAbsolutePath(), moduleTempFolder.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File file = SourceGenerateFileStructure.getModuleSubPath(projectParentPath, projectName, moduleInfo.getModuleId());
        file.mkdirs();

    }

    /**
     * 把模块的附加文件复制到源文件项目
     *
     * @param projectParentPath
     * @param projectName
     * @param moduleInfo
     */
    public static void copyModuleAttachedFileInSourceProject(String projectParentPath, String projectName,
                                                             ModuleInfo moduleInfo) {
        if (moduleInfo.getNumOfAttachedFile() > 0) {
            List<AttachedFile> moduleAttachedFileList = SysService.ATTACHED_FILE_SERVICE
                    .getModuleAttachedFileList(moduleInfo.getModuleId());//从数据库里拿到对应模块的附带文件的路径信息
            if (moduleAttachedFileList != null) {
                File sysDataFolder = SysFileStructure.getDataFileFolder();
                File attachedFileSettingFolder, sourceFolderTemp;
                File[] listTemp;
                String putPath;
                for (AttachedFile attachedFileTemp : moduleAttachedFileList) {
                    putPath = SourceGenerateFileStructure
                            .getTheProjectPathInGenerateSourcePutPath(projectParentPath, projectName).getAbsolutePath()
                            + File.separator + attachedFileTemp.getPath();// 根据信息得到项目最终放置的路径
                    sourceFolderTemp = new File(putPath);// 最终放置路径文件夹
                    sourceFolderTemp.mkdirs();//按照用户设置的路径，生成对应的存放模块附加文件的文件夹

                    attachedFileSettingFolder = DatabaseFileStructure.getModuleAttachedFileFolder(sysDataFolder,
                            GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), moduleInfo.getModuleId(),
                            attachedFileTemp.getOrdinal());// 第attachedFileTemp.getOrdinal()个文件夹，要添加的文件在里面
                    listTemp = attachedFileSettingFolder.listFiles();//拿到这个文件夹里面所有文件
                    if (listTemp != null) {
                        for (File fileTemp : listTemp) {
                            if (fileTemp.isFile() == true) {//文件
                                try {
                                    FileUtil.fileCopyNormal(fileTemp,
                                            new File(putPath + File.separator + fileTemp.getName()));
                                } catch (FileNotFoundException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (fileTemp.isDirectory() == true) {//文件夹
                                try {
                                    FileUtil.copyDir(fileTemp.getAbsolutePath(), putPath);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除模块的附加文件
     *
     * @param projectParentPath
     * @param projectName
     * @param moduleInfo
     */
    public static void delModuleAttachedFileInSourceProject(String projectParentPath, String projectName,
                                                            ModuleInfo moduleInfo) {
        if (moduleInfo.getNumOfAttachedFile() > 0) {
            List<AttachedFile> moduleAttachedFileList = SysService.ATTACHED_FILE_SERVICE
                    .getModuleAttachedFileList(moduleInfo.getModuleId());
            if (moduleAttachedFileList != null) {
                File sysDataFolder = SysFileStructure.getDataFileFolder();

                File attachedFileSettingFolder, toFileTemp;
                File[] listTemp;
                String putPath;
                for (AttachedFile attachedFileTemp : moduleAttachedFileList) {

                    putPath = SourceGenerateFileStructure
                            .getTheProjectPathInGenerateSourcePutPath(projectParentPath, projectName).getAbsolutePath()
                            + File.separator + attachedFileTemp.getPath();// 最终存放的路径

                    attachedFileSettingFolder = DatabaseFileStructure.getModuleAttachedFileFolder(sysDataFolder,
                            GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), moduleInfo.getModuleId(),
                            attachedFileTemp.getOrdinal());// 存放第attachedFileTemp.getOrdinal()个文件的附带文件夹，从这里面拿要添加的文件
                    listTemp = attachedFileSettingFolder.listFiles();
                    if (listTemp != null) {
                        for (File fileTemp : listTemp) {
                            if (fileTemp.isFile() == true) {
                                toFileTemp = new File(putPath + File.separator + fileTemp.getName());
                                toFileTemp.delete();

                            } else if (fileTemp.isDirectory() == true) {
                                FileUtil.delFolder(putPath + File.separator + fileTemp.getName());
                                //这里有个问题，如果删除的文件夹由于开发者编辑导致，生成的项目里面，对应的文件夹存在不属于这个模块的文件（这样会误删），那这里就算是一个bug，以下相近的几个方法也一样，暂时认为应该没有这种情况
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 把可选模板的附加文件复制到源文件项目
     *
     * @param projectParentPath
     * @param projectName
     * @param additionalInfo
     */
    public static void copyAdditionalAttachedFileInSourceProject(String projectParentPath, String projectName,
                                                                 AdditionalInfo additionalInfo) {
        if (additionalInfo.getNumOfAttachedFile() > 0) {
            List<AttachedFile> moduleAttachedFileList = SysService.ATTACHED_FILE_SERVICE
                    .getAdditionalAttachedFileList(additionalInfo.getAdditionalSerialNumber());
            if (moduleAttachedFileList != null) {
                File attachedFileSettingFolder, sourceFolderTemp;
                File[] listTemp;
                String putPath;
                File sysDataFolder = SysFileStructure.getDataFileFolder();
                for (AttachedFile attachedFileTemp : moduleAttachedFileList) {

                    putPath = SourceGenerateFileStructure
                            .getTheProjectPathInGenerateSourcePutPath(projectParentPath, projectName).getAbsolutePath()
                            + File.separator + attachedFileTemp.getPath();
                    sourceFolderTemp = new File(putPath);// 最终放置路径
                    sourceFolderTemp.mkdirs();

                    attachedFileSettingFolder = DatabaseFileStructure.getAdditionalAttachedFileFolder(sysDataFolder,
                            GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), additionalInfo.getAdditionalSerialNumber(),
                            attachedFileTemp.getOrdinal());
                    listTemp = attachedFileSettingFolder.listFiles();
                    if (listTemp != null) {
                        for (File fileTemp : listTemp) {
                            if (fileTemp.isFile() == true) {
                                try {
                                    FileUtil.fileCopyNormal(fileTemp,
                                            new File(putPath + File.separator + fileTemp.getName()));
                                } catch (FileNotFoundException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (fileTemp.isDirectory() == true) {
                                try {
                                    FileUtil.copyDir(fileTemp.getAbsolutePath(), putPath);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除可选模板的附加文件
     *
     * @param projectParentPath
     * @param projectName
     * @param additionalInfo
     */
    public static void delAdditionalAttachedFileInSourceProject(String projectParentPath, String projectName,
                                                                AdditionalInfo additionalInfo) {
        if (additionalInfo.getNumOfAttachedFile() > 0) {
            List<AttachedFile> moduleAttachedFileList = SysService.ATTACHED_FILE_SERVICE
                    .getAdditionalAttachedFileList(additionalInfo.getAdditionalSerialNumber());// 拿到该其他文件的所有附带文件的存放路径数据
            if (moduleAttachedFileList != null) {
                File attachedFileSettingFolder, file1;
                File[] listTemp;
                String putPath, absolutePath;
                File sysDataFolder = SysFileStructure.getDataFileFolder();
                for (AttachedFile attachedFileTemp : moduleAttachedFileList) {

                    putPath = SourceGenerateFileStructure
                            .getTheProjectPathInGenerateSourcePutPath(projectParentPath, projectName).getAbsolutePath()
                            + File.separator + attachedFileTemp.getPath();
                    attachedFileSettingFolder = DatabaseFileStructure.getAdditionalAttachedFileFolder(sysDataFolder,
                            GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), additionalInfo.getAdditionalSerialNumber(),
                            attachedFileTemp.getOrdinal());// 第attachedFileTemp.getOrdinal()个附带文件的文件夹，要存放的文件在这里面
                    listTemp = attachedFileSettingFolder.listFiles();
                    if (listTemp != null) {
                        for (File fileTemp : listTemp) {
                            if (fileTemp.isFile() == true) {
                                file1 = new File(putPath + File.separator + fileTemp.getName());
                                if (file1 != null) {
                                    file1.delete();
                                }
                            } else if (fileTemp.isDirectory() == true) {
                                FileUtil.delFolder(putPath + File.separator + fileTemp.getName());
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 删除项目里的模块数据
     *
     * @param projectParentPath
     * @param projectName
     * @param moduleInfo
     */
    public static void delModuleDataInProject(String projectParentPath, String projectName, ModuleInfo moduleInfo) {
        File moduleFolder = SourceGenerateFileStructure.getModuleFolder(projectParentPath, projectName,
                moduleInfo.getModuleId());
        FileUtil.delFolder(moduleFolder.getAbsolutePath());
    }

    /**
     * 删除项目里的其他的对应数据文件
     *
     * @param projectParentPath
     * @param projectName
     * @param additionalSerialNumber
     */
    public static void delAdditionalDataInProject(String projectParentPath, String projectName, int additionalSerialNumber) {
        File file = SourceGenerateFileStructure.getAdditionalTempFilePutPath(projectParentPath, projectName,
                additionalSerialNumber);
        FileUtil.delFolder(file.getAbsolutePath());
    }

}

