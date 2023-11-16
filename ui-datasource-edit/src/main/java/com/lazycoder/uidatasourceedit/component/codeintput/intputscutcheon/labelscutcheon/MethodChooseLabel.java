package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;

public class MethodChooseLabel extends MyButton implements LabelComponentIntetface {
	/**
	 * 方法选择组件
	 */
	private static final long serialVersionUID = -1419229112679179346L;

	private PassingComponentParams passingComponentParams = null;

	public MethodChooseLabel() {
		super("方法选择");
		this.setMaximumSize(new Dimension(80, 30));
		this.setMinimumSize(new Dimension(80, 30));
		this.setPreferredSize(new Dimension(80, 30));
	}

	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}


	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return LabelElementName.METHOD_CHOOSE;
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
