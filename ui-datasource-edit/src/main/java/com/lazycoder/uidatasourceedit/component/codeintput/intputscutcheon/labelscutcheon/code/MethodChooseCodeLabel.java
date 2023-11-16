package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.service.vo.element.lable.code.MethodChooseCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.MethodChooseLabel;

public class MethodChooseCodeLabel extends MethodChooseLabel implements CodeLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 6163623980497671537L;

	private MethodChooseCodeElement codeElement = new MethodChooseCodeElement();

	/**
	 * 新建
	 */
	public MethodChooseCodeLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param codeElement
	 */
	public MethodChooseCodeLabel(MethodChooseCodeElement codeElement) {
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
	public MethodChooseCodeElement property() {
		// TODO Auto-generated method stub
		return codeElement;
	}

}
