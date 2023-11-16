package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.service.vo.element.lable.code.CustomVariableCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.CustomVariableLabel;

public class CustomVariableCodeLabel extends CustomVariableLabel implements CodeLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -4080116861014696025L;

	private CustomVariableCodeElement codeElement = new CustomVariableCodeElement();

	/**
	 * 新建
	 */
	public CustomVariableCodeLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param codeElement
	 */
	public CustomVariableCodeLabel(CustomVariableCodeElement codeElement) {
		// TODO Auto-generated constructor stub
		this.codeElement = codeElement;
		init(codeElement.getThisName());
	}

	private void init(String name) {
		setText(name);
		setName(name);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		codeElement.setThisName(name);
	}

	@Override
	public CustomVariableCodeElement property() {
		// TODO Auto-generated method stub
		return codeElement;
	}

}