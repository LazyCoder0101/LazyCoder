package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class InfrequentlyUsedSettingButtonForCodeGeneration extends MyPopupButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 4911355773546089611L;

	private static final ImageIcon INFREQUENTLY_USED_SETTING_ICON = new ImageIcon(
			SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "FunctionOperationComponent" + File.separator
					+ "InfrequentlyUsedSetting" + File.separator + "不常用图标_1.png"),
			INFREQUENTLY_USED_SETTING_PRESSED_ICON = new ImageIcon(
					SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "FunctionOperationComponent"
							+ File.separator + "InfrequentlyUsedSetting" + File.separator + "不常用图标_2.png");

//	public final static int THIS_HEIGHT = INFREQUENTLY_USED_SETTING_ICON.getIconHeight();

	protected JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public InfrequentlyUsedSettingButtonForCodeGeneration() {
		super(INFREQUENTLY_USED_SETTING_ICON);
		buttonHeight = INFREQUENTLY_USED_SETTING_ICON.getIconHeight()+7;
		buttonWidth = INFREQUENTLY_USED_SETTING_ICON.getIconWidth();
		paneWidth = 390;
		paneHeight = 300;
		init();
	}

	private void init() {
		scrollPane = new JScrollPane();
		setInternalComponent(scrollPane);
		setSelectedIcon(INFREQUENTLY_USED_SETTING_PRESSED_ICON);
	}


}
