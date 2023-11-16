package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.service.vo.element.lable.code.CodeInputCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.CodeInputLabel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CodeInputCodeLabel extends CodeInputLabel implements CodeLabelInterface{

	/**
	 *
	 */
	private static final long serialVersionUID = -810307363333841659L;

	private CodeInputCodeElement codeElement = new CodeInputCodeElement();


	/**
	 * 新建
	 */
	public CodeInputCodeLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param codeElement
	 */
	public CodeInputCodeLabel(CodeInputCodeElement codeElement) {
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
	public CodeInputCodeElement property() {
		// TODO Auto-generated method stub
		return codeElement;
	}

}
