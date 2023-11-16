package com.lazycoder.uidatasourceedit.moduleedit.attachedfile;

import com.lazycoder.database.model.AttachedFile;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;;
import com.lazycoder.uidatasourceedit.component.filesetting.AbstractFileSettingContainer;

/**
 * 附带文件设置组件
 *
 * @author admin
 */
public class ModuleAttachedFileSettingContainer extends AbstractFileSettingContainer {

    /**
     *
     */
    private static final long serialVersionUID = 6386284886541127040L;

    /**
     * 新建
     *
     * @param ordinal
     */
    public ModuleAttachedFileSettingContainer(int ordinal) {
        // TODO Auto-generated constructor stub
        super(ordinal);
    }

    /**
     * 还原
     *
     * @param ordinal
     * @param attachedFile
     */
    public ModuleAttachedFileSettingContainer(int ordinal, AttachedFile attachedFile) {
        // TODO Auto-generated constructor stub
        super(ordinal);
        ((ModuleAttachedFileSettingPane) panel).restore(attachedFile);
    }

    @Override
    public ModuleAttachedFileSettingPane getFileSettingPane() {
        // TODO Auto-generated method stub
        return new ModuleAttachedFileSettingPane(getOrdinal());
    }

    public AttachedFile getAttachedFile() {
        AttachedFile attachedFile = new AttachedFile();
        if (DataSourceEditHolder.currentModule != null) {
            attachedFile.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
            attachedFile.setAttachedFileType(AttachedFile.MODULE_TYPE);
            attachedFile.setOrdinal(getOrdinal());
            attachedFile.setPath(panel.getSelectedPath());
        }
        return attachedFile;
    }

}
