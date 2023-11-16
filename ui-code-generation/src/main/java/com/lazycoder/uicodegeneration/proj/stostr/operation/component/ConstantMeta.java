package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.ConstantControlDeserializer;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class ConstantMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = ConstantControlDeserializer.class)
	@Setter
	private ConstantControl controlElement = new ConstantControl();

	/**
	 * 用户最后输入到常量组件的面板的内容
	 */
	@Setter
	@Getter
	private ArrayList<String> inputConstantParam;

	public ConstantMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.CONSTANT);
	}

	@Override
	public ConstantControl getControlElement() {
		return controlElement;
	}


}
