package com.lazycoder.uidatasourceedit.component.filesetting;

import com.lazycoder.uiutils.folder.FolderPane;

public abstract class AbstractFileSettingContainerPane extends FolderPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -5725005620760460932L;

	public AbstractFileSettingContainerPane() {
		super(10);
		// TODO Auto-generated constructor stub
	}

	public abstract void addAttachedFileSettingContainer();

	public void delAttachedFileSettingContainer() {
		if (getComponentCount() > 0) {
			AbstractFileSettingContainer attachedFileSettingContainer = (AbstractFileSettingContainer) getComponent(
					getComponentCount() - 1);
			this.remove(attachedFileSettingContainer);
			tabs.remove(attachedFileSettingContainer);
			if (getParent() != null) {
				getParent().doLayout();
			}
		}
	}

}
