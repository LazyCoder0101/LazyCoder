package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 内容输入组件
 *
 * @author Administrator
 */
@Data
public class TextInputElement extends BaseLableElement {

	public TextInputElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.TEXT_INPUT);
	}
}
