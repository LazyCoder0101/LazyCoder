package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.CustomMethodNameLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.CustomMethodNameEditFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.Setter;


public class CustomMethodNameControlLabel extends CustomMethodNameLabel implements ControlLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 6847267192950367076L;

	@Setter
	private CustomMethodNameControl controlElement = new CustomMethodNameControl();

	/**
	 * 新建
	 *
	 * @param name
	 */
	public CustomMethodNameControlLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param controlElement
	 */
	public CustomMethodNameControlLabel(CustomMethodNameControl controlElement) {
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
			new CustomMethodNameEditFrame(CustomMethodNameControlLabel.this);
		}
	};


	public String getMethodName() {
		return controlElement.getMethodName();
	}

	public void setMethodName(String methodName) {
		this.controlElement.setMethodName(methodName);
	}

	public String getLabelTypeParam() {
		return controlElement.getLabelTypeParam();
	}

	public void setLabelTypeParam(String labelTypeParam) {
		this.controlElement.setLabelTypeParam(labelTypeParam);
	}

	public int getTheAvaliableRange() {
		return controlElement.getTheAvaliableRange();
	}

	public void setTheAvaliableRange(int theAvaliableRange) {
		this.controlElement.setTheAvaliableRange(theAvaliableRange);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		controlElement.setThisName(name);
	}

	@Override
	public CustomMethodNameControl property() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	@Override
	public CustomMethodNameControl getControl() {
		return controlElement;
	}

	@Override
	public void deleteFromPanel() {}

}