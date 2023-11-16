package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;

public class FileSelectorLabel extends MyButton implements LabelComponentIntetface {

	/**
	 *
	 */
	private static final long serialVersionUID = -4612444782628877124L;

	private PassingComponentParams passingComponentParams = null;

	/**
	 * 添加文件组件
	 */
	public FileSelectorLabel() {
		super();
		this.setMaximumSize(new Dimension(90, 30));
		this.setMinimumSize(new Dimension(90, 30));
		this.setPreferredSize(new Dimension(90, 30));
	}

	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return LabelElementName.FILE_SELECTOR;
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