package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.TextInputLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.TextInputEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.Setter;

public class TextInputControlLabel extends TextInputLabel implements ControlLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -6644839973205068084L;

	@Setter
	private TextInputControl controlElement = new TextInputControl();

	/**
	 * 新建
	 *
	 * @param name
	 */
	public TextInputControlLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}


	/**
	 * 还原
	 *
	 * @param controlElement
	 */
	public TextInputControlLabel(TextInputControl controlElement) {
		// TODO Auto-generated constructor stub
		this.controlElement = controlElement;
		init(controlElement.getThisName());
	}

	public void init(String name) {
		setName(name);
		setUI(new ControlLabelButtonUI());
		setText(name);
		addActionListener(listener);
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new TextInputEditFrame(TextInputControlLabel.this);
		}
	};

	@Override
	public void deleteFromPanel() {}

	@Override
	public TextInputControl getControl() {
		return controlElement;
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		controlElement.setThisName(name);
	}

	@Override
	public TextInputControl property() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	public int getPresentForm() {
		return controlElement.getPresentForm();
	}

	public void setPresentForm(int presentForm) {
		this.controlElement.setPresentForm(presentForm);
	}

	public int getInputLimit() {
		return controlElement.getInputLimit();
	}

	public void setInputLimit(int inputLimit) {
		this.controlElement.setInputLimit(inputLimit);
	}

	public String getDefaultValue() {
		return controlElement.getDefaultValue();
	}

	public void setDefaultValue(String defaultValue) {
		this.controlElement.setDefaultValue(defaultValue);
	}

	public String getMinValue() {
		return controlElement.getMinValue();
	}

	public void setMinValue(String minValue) {
		this.controlElement.setMinValue(minValue);
	}

	public String getMaxValue() {
		return controlElement.getMaxValue();
	}

	public void setMaxValue(String maxValue) {
		this.controlElement.setMaxValue(maxValue);
	}

	public int getTextFieldShowSize() {
		return controlElement.getTextFieldShowSize();
	}

	public void setTextFieldShowSize(int textFieldShowSize) {
		this.controlElement.setTextFieldShowSize(textFieldShowSize);
	}

}