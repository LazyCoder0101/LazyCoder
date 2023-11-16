package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import java.awt.Color;

/**
 * 点击该按钮，隐藏或展开隐藏 隐藏的控制输入面板
 *
 * @author Administrator
 */
public class FormatOpratinglHiddenButton extends FoldButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -4396159862183059601L;

	public FormatOpratinglHiddenButton(boolean expanded, String formatOpratingTitle) {
		super(expanded);
		// TODO Auto-generated constructor stub
		setText(formatOpratingTitle);
		setUI(new FoldButtonUI());
		setBackground(Color.cyan);

	}

}
