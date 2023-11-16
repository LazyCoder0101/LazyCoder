package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.variable;

import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;

public class ModuleVariableFolderHiddenButton extends FoldButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -7822164353880179167L;

	/**
	 * 是否隐藏面板
	 *
	 * @param expanded
	 */
	public ModuleVariableFolderHiddenButton(boolean expanded) {
		super(expanded);
		// TODO Auto-generated constructor stub
		setText("模块变量");
		setUI(new FoldButtonUI());
	}

}
