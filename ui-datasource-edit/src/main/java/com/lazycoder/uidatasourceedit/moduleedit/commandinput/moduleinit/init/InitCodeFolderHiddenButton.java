package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.init;

import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;

public class InitCodeFolderHiddenButton extends FoldButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -5650202760773788345L;

	/**
	 * 是否隐藏面板
	 *
	 * @param expanded
	 */
	public InitCodeFolderHiddenButton(boolean expanded) {
		super(expanded);
		// TODO Auto-generated constructor stub
		setText("初始化代码");
		setUI(new FoldButtonUI());
	}

}
