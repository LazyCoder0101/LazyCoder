package com.lazycoder.uidatasourceedit.formatedit.additional.attachedfile;

import com.lazycoder.database.model.AttachedFile;
import com.lazycoder.uidatasourceedit.component.filesetting.AbstractFileSettingContainer;

/**
 * 附带文件设置组件
 *
 * @author admin
 */
public class AdditionalAttachedFileSettingContainer extends AbstractFileSettingContainer {

	/**
	 *
	 */
	private static final long serialVersionUID = 5230354321060068430L;

	private int additionalSerialNumber;

	public AdditionalAttachedFileSettingContainer(int additionalSerialNumber, int ordinal) {
		// TODO Auto-generated constructor stub
		super();
		this.ordinal = ordinal;
		this.additionalSerialNumber = additionalSerialNumber;
		hiddenButton.setText("文件" + ordinal);
		panel = getFileSettingPane();
		scrollPane.setViewportView(panel);
	}

	public AdditionalAttachedFileSettingContainer(int additionalSerialNumber, int ordinal, AttachedFile attachedFile) {
		// TODO Auto-generated constructor stub
		this(additionalSerialNumber, ordinal);
		((AdditionalAttachedFileSettingPane) panel).restore(attachedFile);
	}

	@Override
	public AdditionalAttachedFileSettingPane getFileSettingPane() {
		// TODO Auto-generated method stub
		return new AdditionalAttachedFileSettingPane(this.additionalSerialNumber, getOrdinal());
	}

	public AttachedFile getAttachedFile() {
		AttachedFile attachedFile = new AttachedFile();
		attachedFile.setAttachedFileType(AttachedFile.ADDITIONAL_TYPE);
		attachedFile.setOrdinal(getOrdinal());
		attachedFile.setAdditionalSerialNumber(additionalSerialNumber);
		attachedFile.setPath(panel.getSelectedPath());
		return attachedFile;
	}

}
