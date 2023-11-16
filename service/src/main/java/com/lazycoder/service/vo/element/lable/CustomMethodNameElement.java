package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 自定义方法名
 *
 * @author Administrator
 */
@Data
public class CustomMethodNameElement extends BaseLableElement {

	public CustomMethodNameElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.CUSTOM_METHOD_NAME);
	}

}
