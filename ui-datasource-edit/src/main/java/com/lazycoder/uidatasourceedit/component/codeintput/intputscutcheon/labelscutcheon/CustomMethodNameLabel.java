package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class CustomMethodNameLabel extends MyButton implements LabelComponentIntetface {

	/**
	 *
	 */
	private static final long serialVersionUID = 2555468782938337856L;
	private static Border border = BorderFactory.createLineBorder(new Color(0, 160, 225), 2, true);
	private PassingComponentParams passingComponentParams = null;

	public CustomMethodNameLabel() {
		super("自定义方法");
		this.setBorder(border);
		Dimension dd = new Dimension(80, 30);

		this.setMaximumSize(dd);
		this.setPreferredSize(dd);
		this.setMinimumSize(dd);
	}

	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return LabelElementName.CUSTOM_METHOD_NAME;
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