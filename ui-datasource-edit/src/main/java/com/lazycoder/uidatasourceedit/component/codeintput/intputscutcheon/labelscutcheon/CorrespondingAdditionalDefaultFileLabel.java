package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;

public class CorrespondingAdditionalDefaultFileLabel extends MyButton implements LabelComponentIntetface {

	/**
	 *
	 */
	private PassingComponentParams passingComponentParams = null;

	public CorrespondingAdditionalDefaultFileLabel() {
		super();
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
		return LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE;
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
	public void setNavigate(boolean flag) {}

	@Override
	public BaseLableElement property() {
		return null;
	}

}
