package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.component.CustomComboBox;
import java.awt.Dimension;
import java.util.Vector;

/**
 * 选项是单选时添加的组件
 */
public abstract class AbstractRadioComboboxLabel<E> extends CustomComboBox<E> implements LabelComponentIntetface {

	/**
	 *
	 */
	private static final long serialVersionUID = -2809281633688987167L;

	private PassingComponentParams passingComponentParams = null;

	public AbstractRadioComboboxLabel(E[] items) {
		super(items);
	}

	public AbstractRadioComboboxLabel(Vector<E> items) {
		super(items);
	}

	@Override
	public void deleteFromPanel() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getLabelType() {
		// TODO Auto-generated method stub
		return LabelElementName.CONTENT_CHOOSE;
	}

	public void setTheSize() {
		// TODO Auto-generated constructor stub
		int lenth = 0;
		String temp;
		for (int i = 0; i < getModel().getSize(); i++) {
			temp = (String) getModel().getElementAt(i);
			if (temp.length() > lenth) {
				lenth = temp.length();
			}
		}
		Dimension dd = new Dimension(lenth * 12 + 50, 30);
		if (lenth > 5) {
			dd = new Dimension(120, 30);
		}
		this.setPreferredSize(dd);
		this.setMinimumSize(dd);
		this.setMaximumSize(dd);
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