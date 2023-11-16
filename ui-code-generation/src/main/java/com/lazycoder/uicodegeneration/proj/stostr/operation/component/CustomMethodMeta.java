package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.CustomMethodNameControlDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.CustomFunctionName;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.CustomFunctionNameDeserializer;
import lombok.Getter;
import lombok.Setter;

public class CustomMethodMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = CustomMethodNameControlDeserializer.class)
	@Setter
	private CustomMethodNameControl controlElement = new CustomMethodNameControl();

	@JSONField(deserializeUsing = CustomFunctionNameDeserializer.class)
	@Getter
	@Setter
	private CustomFunctionName customFunctionName = null;

	public CustomMethodMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.CUSTOM_METHOD_NAME);
	}

	@Override
	public CustomMethodNameControl getControlElement() {
		return controlElement;
	}

}
