package com.lazycoder.uicodegeneration.component.operation.component.typeset.format.additional;

import com.lazycoder.service.fileStructure.GeneralImageManager;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 点击该按钮，隐藏或展开隐藏 隐藏的控制输入面板
 *
 * @author Administrator
 */
public class AdditionalSetTypeHiddenButton extends FoldButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -4396159862183059601L;

	public AdditionalSetTypeHiddenButton(String MainSetTypeName, boolean expanded) {
		super(expanded);
		// TODO Auto-generated constructor stub
		setText(MainSetTypeName);
		setUI(new AdditionalSetTypeHiddenButtonUI());
		setBackground(Color.cyan);

	}

}

class AdditionalSetTypeHiddenButtonUI extends FoldButtonUI {

	private static final int IMAGE_TAILING_GAP = 12;
	private static Icon iconExpanded;
	private static Icon iconFoldered;
	private static Icon hoveredExpanded;
	private static Icon hoveredFoldered;

	static {
		// 初始化
		iconExpanded = new ImageIcon(GeneralImageManager.ICON_EXPANDED);
		iconFoldered = new ImageIcon(GeneralImageManager.ICON_FOLDERED);
		hoveredExpanded = new ImageIcon(GeneralImageManager.HOVERED_EXPANDED);
		hoveredFoldered = new ImageIcon(GeneralImageManager.HOVERED_FOLDERED);
	}

	/**
	 * 图像邮编间隙
	 */
	private int imageTailingGap = IMAGE_TAILING_GAP;

}
