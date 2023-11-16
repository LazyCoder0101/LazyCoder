package com.lazycoder.uidatasourceedit.moduleedit.attachedfile;

import com.lazycoder.database.model.AttachedFile;
import com.lazycoder.database.model.SysParam;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.filesetting.AbstractFileSettingPane;
import java.io.File;

public class ModuleAttachedFileSettingPane extends AbstractFileSettingPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -6143716095326304148L;

	private int ordinal;

	/**
	 * Create the panel.
	 */
	public ModuleAttachedFileSettingPane(int ordinal) {
		this.ordinal = ordinal;
		File attachedFileSettingFolder = DatabaseFileStructure.getModuleAttachedFileFolder(
				SysFileStructure.getDataFileFolder(), GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
				DataSourceEditHolder.currentModule.getModuleId(), this.ordinal);
		if (attachedFileSettingFolder.exists() == false) {
			attachedFileSettingFolder.mkdirs();
		}
		init(attachedFileSettingFolder);
	}

	/**
	 * 新建
	 */
	public void newBuild() {
		String thePath = SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.MODULE_ATTACHED_FILE_PATH);
		if (thePath != null) {
			pathTextField.setText(thePath);
		}
	}

	/**
	 * 还原
	 *
	 * @param attachedFile
	 */
	protected void restore(AttachedFile attachedFile) {
		// TODO Auto-generated method stub
		pathTextField.setText(attachedFile.getPath());
	}

}
