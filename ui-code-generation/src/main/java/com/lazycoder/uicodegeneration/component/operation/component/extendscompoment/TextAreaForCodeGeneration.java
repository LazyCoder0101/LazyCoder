package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAreaForCodeGeneration extends MyPopupButton {//MyTogglePopup {

	/**
	 *
	 */
	private static final long serialVersionUID = 4209234836806577618L;

	private static final ImageIcon TEXT_INPUT_ICON = new ImageIcon(
			SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "FunctionOperationComponent" + File.separator
					+ "TextInput" + File.separator + "文本框_蓝.png"),
			TEXT_INPUT_PRESSED_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
					+ "FunctionOperationComponent" + File.separator + "TextInput" + File.separator + "文本框_黑.png");
	public final static int THIS_HEIGHT = TEXT_INPUT_ICON.getIconHeight();
	protected JTextArea textArea;
	private JScrollPane scrollPane;

	public TextAreaForCodeGeneration() {
		buttonHeight = TEXT_INPUT_ICON.getIconHeight();
		buttonWidth = TEXT_INPUT_ICON.getIconWidth();
		paneWidth = 350;
		paneHeight = 200;
		setSelected(false);
		setIcon(TEXT_INPUT_ICON);
		setSelectedIcon(TEXT_INPUT_PRESSED_ICON);
		Dimension dd1 = new Dimension(TEXT_INPUT_ICON.getIconWidth(), THIS_HEIGHT);
		setPreferredSize(dd1);
		setMinimumSize(dd1);
		setMaximumSize(dd1);

		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		setInternalComponent(scrollPane);
	}

}
