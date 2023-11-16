package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.CodeInputControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.CodeInputLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.CodeInputEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.Setter;

public class CodeInputControlLabel extends CodeInputLabel implements ControlLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -5353647316757238765L;

	@Setter
	private CodeInputControl controlElement = new CodeInputControl();

	/**
	 * 新建
	 *
	 * @param name
	 */
	public CodeInputControlLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param controlElement
	 */
	public CodeInputControlLabel(CodeInputControl controlElement) {
		// TODO Auto-generated constructor stub
		this.controlElement = controlElement;
		init(controlElement.getThisName());
	}

	public void init(String name) {
		setName(name);
		setText(name);
		setUI(new ControlLabelButtonUI());
		addActionListener(listener);
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new CodeInputEditFrame(CodeInputControlLabel.this);
		}
	};

	@Override
	public void setName(String name) {
		super.setName(name);
		controlElement.setThisName(name);
	}

	@Override
	public CodeInputControl property() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	@Override
	public CodeInputControl getControl() {
		return controlElement;
	}

	public String getDefaultCode() {
		return controlElement.getDefaultCode();
	}

	public void setDefaultCode(String defaultCode) {
		controlElement.setDefaultCode(defaultCode);
	}

	public void setInputProgramingLanguageDictionaryValue(int inputProgramingLanguageDictionaryValue){
		controlElement.setInputProgramingLanguageDictionaryValue(inputProgramingLanguageDictionaryValue);
	}

	@Override
	public void deleteFromPanel() {}

}
