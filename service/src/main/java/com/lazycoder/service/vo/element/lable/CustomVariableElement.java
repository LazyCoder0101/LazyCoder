package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 自定义变量
 *
 * @author Administrator
 */
@Data
public class CustomVariableElement extends BaseLableElement {

	public CustomVariableElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.CUSTOM_VARIABLE);
	}
}
