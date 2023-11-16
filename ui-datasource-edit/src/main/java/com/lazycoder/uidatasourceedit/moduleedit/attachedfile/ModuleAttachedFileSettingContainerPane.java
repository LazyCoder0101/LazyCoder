package com.lazycoder.uidatasourceedit.moduleedit.attachedfile;

import com.lazycoder.database.model.AttachedFile;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.AttachedFileServiceImpl;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.filesetting.AbstractFileSettingContainerPane;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class ModuleAttachedFileSettingContainerPane extends AbstractFileSettingContainerPane {

    /**
     *
     */
    private static final long serialVersionUID = 2040019949039419769L;

    public final AttachedFileServiceImpl attachedFileServiceImpl = SysService.ATTACHED_FILE_SERVICE;

    public ModuleAttachedFileSettingContainerPane() {
        // TODO Auto-generated method stub
        super();
        if (DataSourceEditHolder.currentModule != null) {
            List<AttachedFile> attachedFileList = attachedFileServiceImpl.getModuleAttachedFileList(DataSourceEditHolder.currentModule.getModuleId());

            if (attachedFileList != null) {
                AttachedFile attachedFile;
                ModuleAttachedFileSettingContainer attachedFileSettingContainer;
                for (int i = 0; i < attachedFileList.size(); i++) {
                    attachedFile = attachedFileList.get(i);
                    attachedFileSettingContainer = new ModuleAttachedFileSettingContainer(
                            i + 1, attachedFile);
                    addContainer(attachedFileSettingContainer);
                }
            }
        }
    }

    @Override
    public void addAttachedFileSettingContainer() {
        ModuleAttachedFileSettingContainer attachedFileSettingContainer = new ModuleAttachedFileSettingContainer(
                getComponentCount() + 1);
        addContainer(attachedFileSettingContainer);

    }

    public void save() {
        if (DataSourceEditHolder.currentModule != null) {
            ModuleAttachedFileSettingContainer attachedFileSettingContainer;
            List<AttachedFile> list = new ArrayList<>();
            for (int i = 0; i < getComponentCount(); i++) {
                attachedFileSettingContainer = (ModuleAttachedFileSettingContainer) getComponent(i);
                list.add(attachedFileSettingContainer.getAttachedFile());
            }

            ModuleInfo moduleInfo = new ModuleInfo();
            moduleInfo.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
            moduleInfo.setClassName(DataSourceEditHolder.currentModule.getClassName());
            moduleInfo.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
            moduleInfo.setNumOfAttachedFile(getComponentCount());

            boolean flag = SysService.INPUT_DATA_SAVE_SERVICE.saveModuleAttachedFileInputData(moduleInfo, list);
            if (flag) {
                delRedundantFiles();
            } else {
                LazyCoderOptionPane.showMessageDialog(null, "(╥╯^╰╥) 不知道为什么，保存不了了，要不清理一下内存，或者重新打开软件再试试吧", "系统信息", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /**
     * 删除多余的文件（关闭窗口时调用）
     */
    public void delRedundantFiles() {
        if (DataSourceEditHolder.currentModule != null) {
            File sysFile = SysFileStructure.getDataFileFolder();

            int setFileNum = getComponentCount();
            File filePutFolder = DatabaseFileStructure.getModuleAttachedFileFolder(sysFile,
                    GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), DataSourceEditHolder.currentModule.getModuleId()), tempFolder;
            File[] files = filePutFolder.listFiles();
            if (files != null) {
                if (files.length > setFileNum) {
                    for (int i = setFileNum + 1; i <= files.length; i++) {
                        tempFolder = DatabaseFileStructure.getModuleAttachedFileFolder(sysFile,
                                GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), DataSourceEditHolder.currentModule.getModuleId(), i);
                        if (tempFolder.isDirectory()) {
                            FileUtil.delFolder(tempFolder.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

}
