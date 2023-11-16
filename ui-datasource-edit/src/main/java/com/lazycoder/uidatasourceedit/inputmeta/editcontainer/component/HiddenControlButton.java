package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import java.io.File;
import javax.swing.ImageIcon;


/**
 * 点击该按钮，隐藏或展开隐藏 隐藏的控制输入面板
 *
 * @author Administrator
 */
public class HiddenControlButton extends FoldButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -4396159862183059601L;

	private static ImageIcon expandTrueIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator
			+ "base_code_content_edit" + File.separator + "code_input_pane" + File.separator + "expand_true.png"),

	expandFalseIcon = new ImageIcon(
			SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator
					+ "base_code_content_edit" + File.separator + "code_input_pane" + File.separator + "expand_false.png");

	public HiddenControlButton(boolean expanded) {
		super(expanded);
		// TODO Auto-generated constructor stub
		setIcon(expandTrueIcon);
		setContentAreaFilled(false);
		setUI(new FoldButtonUI());
	}

	@Override
	public void setExpanded(boolean expanded) {
		super.setExpanded(expanded);
		if (expanded){
			setIcon(expandTrueIcon);
		}else {
			setIcon(expandFalseIcon);
		}
	}
}

