package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;

public class ConstantLabel extends MyButton implements LabelComponentIntetface {
	/**
	 * 自定义常量数组
	 */
	public final static ImageIcon CONSTANT_ARRAY_ICON = new ImageIcon(
			SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator + "base_code_content_edit" + File.separator
					+ "label_element" + File.separator + "constant" + File.separator + "constan_array.png");
	/**
	 *
	 */
	private static final long serialVersionUID = -1319368254984047929L;
	private PassingComponentParams passingComponentParams = null;

	public ConstantLabel() {
		super(CONSTANT_ARRAY_ICON);
		// this.setEditable(false);
		Dimension dd = new Dimension(80, 30);
		this.setMaximumSize(dd);
		this.setMinimumSize(dd);
		this.setPreferredSize(dd);
	}

	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}


	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return LabelElementName.CONSTANT;
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
