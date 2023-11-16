package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 功能拓展
 *
 * @author Administrator
 */
@Data
public class FunctionAddElement extends BaseLableElement {

	public FunctionAddElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.FUNCTION_ADD);
	}

}
