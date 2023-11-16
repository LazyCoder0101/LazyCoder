package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;

public class CustomVariableLabel extends MyButton implements LabelComponentIntetface {
	/**
	 * 多个变量
	 */
	public final static ImageIcon CUSTOM_ARRAY_ICON = new ImageIcon(
			SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator + "base_code_content_edit" + File.separator
					+ "label_element" + File.separator + "custom_variable" + File.separator + "custom_array.png");
	/**
	 *
	 */
	private static final long serialVersionUID = -3492363933786387477L;
	private PassingComponentParams passingComponentParams = null;

	/**
	 * 添加在daima面板
	 */
	public CustomVariableLabel() {
		super(CUSTOM_ARRAY_ICON);
		Dimension dd = new Dimension(80, 30);
		this.setMaximumSize(dd);
		this.setMinimumSize(dd);
		this.setPreferredSize(dd);
		this.setFocusPainted(false);
	}

	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return LabelElementName.CUSTOM_VARIABLE;
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
		LabelComponentIntetface.addCorrespondingComponentResponseListener(this);
	}


	@Override
	public void setNavigate(boolean flag) {

	}

	@Override
	public BaseLableElement property() {
		return null;
	}

}