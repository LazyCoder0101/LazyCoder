package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 代码输入
 *
 * @author Administrator
 */
@Data
public class CodeInputElement extends BaseLableElement {

	public CodeInputElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.CODE_INPUT);
	}

}
