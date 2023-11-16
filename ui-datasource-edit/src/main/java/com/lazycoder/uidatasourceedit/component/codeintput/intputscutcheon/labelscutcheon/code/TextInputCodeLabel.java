package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.service.vo.element.lable.code.TextInputCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.TextInputLabel;

public class TextInputCodeLabel extends TextInputLabel implements CodeLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -5851292962080592062L;

	private TextInputCodeElement codeElement = new TextInputCodeElement();

	/**
	 * 新建
	 */
	public TextInputCodeLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param codeElement
	 */
	public TextInputCodeLabel(TextInputCodeElement codeElement) {
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
	public TextInputCodeElement property() {
		// TODO Auto-generated method stub
		return codeElement;
	}

}