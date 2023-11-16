package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.control;

import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;

public class ModuleControlFolderHiddenButton extends FoldButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 5740274730285659668L;

	/**
	 * 是否隐藏面板
	 *
	 * @param expanded
	 */
	public ModuleControlFolderHiddenButton(boolean expanded) {
		super(expanded);
		// TODO Auto-generated constructor stub
		setText("模块控制");
		setUI(new FoldButtonUI());
	}

}
