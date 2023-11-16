package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.customvariable;


import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.uicodegeneration.component.operation.component.CustomVariableMutipleInputBox;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;

/**
 * 传给内部的变量输入框需要的参数
 *
 * @author admin
 */
public class CustomVariableParam {

	private String showVariableName = "";

	private CustomVariableMutipleInputBox customVariableMutipleInoutBox;

	private CustomVariableControl controlElement;

	private AbstractFormatControlPane formatControlPane;

	public CustomVariableParam() {
		// TODO Auto-generated constructor stub
	}

	public CustomVariableMutipleInputBox getCustomVariableMutipleInoutBox() {
		return customVariableMutipleInoutBox;
	}

	public void setCustomVariableMutipleInoutBox(CustomVariableMutipleInputBox customVariableMutipleInoutBox) {
		this.customVariableMutipleInoutBox = customVariableMutipleInoutBox;
	}

	public String getShowVariableName() {
		return showVariableName;
	}

	public void setShowVariableName(String showVariableName) {
		this.showVariableName = showVariableName;
	}

	public AbstractFormatControlPane getFormatControlPane() {
		return formatControlPane;
	}

	public void setFormatControlPane(AbstractFormatControlPane formatControlPane) {
		this.formatControlPane = formatControlPane;
	}

	public CustomVariableControl getControlElement() {
		return controlElement;
	}

	public void setControlElement(CustomVariableControl controlElement) {
		this.controlElement = controlElement;
	}

}
