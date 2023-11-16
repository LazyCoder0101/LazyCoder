package com.lazycoder.service.vo.element.lable;


import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 常量
 *
 * @author Administrator
 */
@Data
public class ConstantElement extends BaseLableElement {

	public ConstantElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.CONSTANT);
	}

}
