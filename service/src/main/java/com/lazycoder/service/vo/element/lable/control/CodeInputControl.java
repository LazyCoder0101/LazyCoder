package com.lazycoder.service.vo.element.lable.control;

import com.lazycoder.service.vo.element.lable.CodeInputElement;
import lombok.Data;

/**
 * 代码输入
 *
 * @author Administrator
 */
@Data
public class CodeInputControl extends CodeInputElement {

	private int inputProgramingLanguageDictionaryValue=1;

	/**
	 * 默认值
	 */
	private String defaultCode = "";

	public CodeInputControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public CodeInputElement controlComponentInformation() {
		CodeInputElement element = new CodeInputElement();
		element.setThisName(getThisName());
		return element;
	}

}
