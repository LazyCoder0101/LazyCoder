package com.lazycoder.uidatasourceedit.formatedit.additional.attachedfile;

import com.lazycoder.database.model.AttachedFile;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.filesetting.AbstractFileSettingPane;
import java.io.File;

public class AdditionalAttachedFileSettingPane extends AbstractFileSettingPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -5949486950162405656L;

	/**
	 * Create the panel.
	 */
	public AdditionalAttachedFileSettingPane(int additionalSerialNumber, int ordinal) {
		File sysDataFileFolder = SysFileStructure.getDataFileFolder();
		File attachedFileSettingFolder = DatabaseFileStructure.getAdditionalAttachedFileFolder(sysDataFileFolder,
				GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), additionalSerialNumber, ordinal);
		if (attachedFileSettingFolder.exists() == false) {
			attachedFileSettingFolder.mkdirs();
		}
		init(attachedFileSettingFolder);
	}

	protected void restore(AttachedFile attachedFile) {
		// TODO Auto-generated method stub
		pathTextField.setText(attachedFile.getPath());
	}

}
