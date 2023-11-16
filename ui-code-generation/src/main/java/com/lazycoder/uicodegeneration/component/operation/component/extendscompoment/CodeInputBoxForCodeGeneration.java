package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.CodeShowTextArea;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import java.awt.Font;
import java.io.File;
import javax.swing.ImageIcon;
import org.fife.ui.rtextarea.RTextScrollPane;


public class CodeInputBoxForCodeGeneration extends MyPopupButton {//MyTogglePopup {

	/**
	 *
	 */
	private static final long serialVersionUID = 4209234836806577618L;

	private static final ImageIcon CODE_INPUT_ICON = new ImageIcon(
			SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "FunctionOperationComponent" + File.separator
					+ "CodeInput" + File.separator + "code.png"),
			codeInputPressedIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
					+ "FunctionOperationComponent" + File.separator + "CodeInput" + File.separator + "code_write.png");
	protected CodeShowTextArea textArea;
	private RTextScrollPane scrollPane;

	public CodeInputBoxForCodeGeneration() {
		super(CODE_INPUT_ICON);
		buttonHeight = CODE_INPUT_ICON.getIconHeight() + 7;
		buttonWidth = CODE_INPUT_ICON.getIconWidth() + 22;
		paneWidth = 550;
		paneHeight = 200;
		setSelectedIcon(codeInputPressedIcon);
//		Dimension dd1 = new Dimension(codeInputIcon.getIconWidth(), codeInputIcon.getIconHeight());
//		setPreferredSize(dd1);
//		setMinimumSize(dd1);
//		setMaximumSize(dd1);

		textAreaInit();

//		addActionListener(listener);
	}

	private void textAreaInit() {
		textArea = new CodeShowTextArea();
		textArea.setText("");
		textArea.setEditable(true);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		textArea.setFont(new Font("宋体", Font.PLAIN, 20));
		scrollPane = new RTextScrollPane(textArea);
		setInternalComponent(scrollPane);
	}


}
