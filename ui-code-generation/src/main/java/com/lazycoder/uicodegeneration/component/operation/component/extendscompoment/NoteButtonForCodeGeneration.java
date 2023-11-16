package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import java.awt.Font;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NoteButtonForCodeGeneration extends MyPopupButton{//} MyTogglePopup {

	/**
	 *
	 */
	private static final long serialVersionUID = 4209234836806577618L;

	private static final ImageIcon NOTE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
			+ "FunctionOperationComponent" + File.separator + "Note" + File.separator + "note.png"),
			NOTE_PRESSED_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
					+ "FunctionOperationComponent" + File.separator + "Note" + File.separator + "便签.png");

	protected JTextArea textArea;

	private JScrollPane scrollPane;

//	private NoteButtonPaneForCodeGeneration noteButtonPane = null;

	public NoteButtonForCodeGeneration() {
		super(NOTE_ICON);
		buttonHeight = NOTE_ICON.getIconHeight()+4;
		buttonWidth = NOTE_ICON.getIconWidth()+6;
		paneWidth = 400;
		paneHeight = 330;
		setSelected(false);
		setSelectedIcon(NOTE_PRESSED_ICON);
//		setIcon(noteIcon);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		scrollPane = new JScrollPane(textArea);
		setInternalComponent(scrollPane);
	}

	public void setNote(String text) {
		textArea.setText(text);
	}

}
