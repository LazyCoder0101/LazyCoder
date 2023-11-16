package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class PathLabel extends MyButton implements LabelComponentIntetface {

	/**
	 * 路径组件
	 */
	private static final long serialVersionUID = -8782490073135793081L;
	private static ImageIcon pathIcon = new ImageIcon(
			SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator + "base_code_content_edit" + File.separator
					+ "label_element" + File.separator + "path" + File.separator + "path.png");
	private PassingComponentParams passingComponentParams = null;

	public PathLabel() {
		super("路径", pathIcon);
		Dimension dd = new Dimension(75, 30);
		this.setMaximumSize(dd);
		this.setMinimumSize(dd);
		this.setPreferredSize(dd);
		this.setUI(new
				BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		this.setFocusPainted(false);
	}

	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PassingComponentParams getPassingComponentParams() {
		// TODO Auto-generated method stub
		return passingComponentParams;
	}

	@Override
	public void setPassingComponentParams(PassingComponentParams passingComponentParams) {
		// TODO Auto-generated method stub
		this.passingComponentParams = passingComponentParams;
	}

	@Override
	public void setNavigate(boolean flag) {}

	@Override
	public BaseLableElement property() {
		return null;
	}

}
