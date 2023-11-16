package com.lazycoder.service.vo.element.lable;


import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 变量
 *
 * @author Administrator
 */
@Data
public class VariableElement extends BaseLableElement {

	public VariableElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.VARIABLE);
	}
}