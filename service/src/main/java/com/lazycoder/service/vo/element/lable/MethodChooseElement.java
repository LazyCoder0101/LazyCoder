package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 方法选择
 *
 * @author Administrator
 */
@Data
public class MethodChooseElement extends BaseLableElement {

	public MethodChooseElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.METHOD_CHOOSE);
	}

}
