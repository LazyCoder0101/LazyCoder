package com.lazycoder.main;

import com.lazycoder.lazycoderbaseconfiguration.ClientVersionHandlingManager;
import com.lazycoder.lazycodercommon.vo.DataSourceLabel;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.SourceGenerateFileMethod;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrameModel;
import com.lazycoder.uicodegeneration.generalframe.ProInit;
import com.lazycoder.uicodegeneration.generalframe.SourceGenerateModelGet;
import com.lazycoder.uidatasourceedit.DataSourceClassificationEditFrame;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * 客户端使用时务必要用的静态方法
 */
public class StartStaticMethod {


    /**
     * 该方法只应用于 CodeGenerationFrame 里的打开项目文件方法使用
     *
     * @param proInit
     * @return
     */
    public static boolean checkCanUseOrNot(ProInit proInit) {
        boolean flag = SourceGenerateFileMethod.checkLannongProFileStrucure(proInit.getProjectParentPath(),
                proInit.getProjectName());
        if (flag == true) {
            CodeGenerationFrameModel codeGenerationFrameModel = SourceGenerateModelGet
                    .getCodeGenerationFrameModel(proInit.getProjectParentPath(), proInit.getProjectName());// 获取根模型

            DataSourceLabel dataSourceLabel = SysService.DB_CHANGE_SERVICE.getUserDataSourceInfoBy(codeGenerationFrameModel.getUseUserDBID());//查一下当前有没有这个文件对应的数据源id
            if (dataSourceLabel == null) {//没这数据源id
                flag = false;
                Object[] options = {"导入对应数据源！", "算了，直接退出吧"};
                int temp = LazyCoderOptionPane.showOptionDialog(null,
                        "∑(っ°Д°;)っ	现在系统没有这项目对应的数据源，先去导入对应数据源吧", "系统提示",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                        options[0]);
                if (temp == 0) {// 导入对应数据源
                    new DataSourceClassificationEditFrame();
                } else if (temp == 1) {// 直接退出
                    System.exit(0);
                }

            } else {
                String proClientVersion = codeGenerationFrameModel.getProClientVersion();
                String dataSourceClientVersion = codeGenerationFrameModel.getDataSourceClientVersion();
                if (ClientVersionHandlingManager.proVersionDetection(proClientVersion, dataSourceClientVersion)) {//到这里先对需要打开的项目版本进行检测，确认是否兼容

                    String currenDbId = SysService.DB_CHANGE_SERVICE.getCurrentDataSourceId();
                    SysService.DB_CHANGE_SERVICE.changeUserDB(dataSourceLabel.getDataSourceName(), dataSourceLabel.getDbId());
                    GeneralHolder.currentUserDataSourceLabel = dataSourceLabel;
                    boolean enabledStateFlag = SysService.SYS_PARAM_SERVICE.getEnabledState();
                    if (enabledStateFlag == true) {//有这数据源id，而且能用
                        if (checkSourceFolderStatus(proInit)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
//                    if (SysService.MODULE_SERVICE.checkCanUseOrNot() == false) {//发现不能使用的话，切换到原来数据源，否则在该方法就切换到对应的数据源
//                        SysService.DB_CHANGE_SERVICE.changeDb(currenDbId);
//                        GeneralHolder.currentUserDataSourceLabel = null;
//                        flag = false;
//                    }

                    } else {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null,
                                "∑(っ°Д°;)っ	这文件在系统对应的" + dataSourceLabel.getDataSourceName() + "数据源已经很旧了吧，\n删掉原来的数据源重新导入新版的吧，不然我没法打开",
                                "系统提示",
                                JOptionPane.ERROR_MESSAGE);
                        SysService.DB_CHANGE_SERVICE.changeDb(currenDbId);
                        GeneralHolder.currentUserDataSourceLabel = null;
                        System.exit(0);
                    }
                }
            }
        } else {
            LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	这不是懒农生成的项目文件吧，或者已经破损了，没法打开了",
                    "系统提示",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return flag;
    }

    /**
     * 检查之前在源码文件夹下的源码有没有被删除
     *
     * @param proInit
     * @return
     */
    private static boolean checkSourceFolderStatus(ProInit proInit) {
        boolean flag = true;
        File sourceProFile = SourceGenerateFileStructure.getTheProjectPathInGenerateSourcePutPath
                (proInit.getProjectParentPath(), proInit.getProjectName());
        if (sourceProFile.isDirectory() == false) {//没有这个项目源码文件夹
            File sourcePutFolder = SourceGenerateFileStructure.getGenerateSourcePutPath
                    (proInit.getProjectParentPath(), proInit.getProjectName());
            File[] listFiles = sourcePutFolder.listFiles();
            if (listFiles.length == 1) {//检查一下这里是不是只有一个文件夹，如果是，直接把这个文件夹全部粗暴当成是之前系统自动生成的源码相关文件
                File childFile = listFiles[0];
                if (childFile.isDirectory()) {
                    childFile.renameTo(sourceProFile);
                } else {
                    LazyCoderOptionPane.showMessageDialog(null,
                            "这个懒农项目的源码文件夹里面，之前生成的源码都找不到了！   Σ(っ°Д°;)っ",
                            "系统提示",
                            JOptionPane.ERROR_MESSAGE);
                    flag = false;
                }
            } else {//源码文件夹下没有文件，或者有多个文件
                LazyCoderOptionPane.showMessageDialog(null,
                        "这个懒农项目的源码文件夹里面，之前生成的源码都找不到了！   Σ(っ°Д°;)っ",
                        "系统提示",
                        JOptionPane.ERROR_MESSAGE);
                flag = false;
            }
        }
        return flag;
    }

}
