package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class CloseButton extends MyButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -8722218096644557011L;

//	private GeneralCommandOpratingContainer opratingContainer;
	/**************************************************************************/
	private static ImageIcon closeNormalIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration"
			+ File.separator + "close" + File.separator + "close_normal.png"),
			closeHighlightIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "close"
					+ File.separator + "close_highlight.png"),
			closePressedIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "close"
					+ File.separator + "close_down.png");

	public CloseButton() {
		/**************************************************************************/
		super(closeNormalIcon);
		init();
	}

//	public CloseButton(GeneralCommandOpratingContainer opratingContainer) {
//		this();
////		this.opratingContainer = opratingContainer;
//	}

	private void init() {
		this.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
		this.setPressedIcon(closePressedIcon);
		this.setRolloverIcon(closeHighlightIcon);
		this.setFocusPainted(false);
		Dimension dd = new Dimension(20, 20);
		this.setMaximumSize(dd);
		this.setMinimumSize(dd);
		this.setPreferredSize(dd);
		setContentAreaFilled(false);
	}

//	public GeneralCommandOpratingContainer getOpratingContainer() {
//		return opratingContainer;
//	}

}
