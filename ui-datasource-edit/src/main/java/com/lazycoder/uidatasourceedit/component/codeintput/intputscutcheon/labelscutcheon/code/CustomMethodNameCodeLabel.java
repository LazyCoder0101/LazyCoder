package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.service.vo.element.lable.code.CustomMethodNameCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.CustomMethodNameLabel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomMethodNameCodeLabel extends CustomMethodNameLabel implements CodeLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -4198149211427487806L;

	private CustomMethodNameCodeElement codeElement = new CustomMethodNameCodeElement();

	/**
	 * 新建
	 */
	public CustomMethodNameCodeLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param codeElement
	 */
	public CustomMethodNameCodeLabel(CustomMethodNameCodeElement codeElement) {
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
	public CustomMethodNameCodeElement property() {
		// TODO Auto-generated method stub
		return codeElement;
	}

}