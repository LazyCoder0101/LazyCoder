package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.TextInputControlDeserializer;
import lombok.Getter;
import lombok.Setter;

public class TextInputMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = TextInputControlDeserializer.class)
	@Setter
	private TextInputControl controlElement = new TextInputControl();

	/**
	 * 用户最后输入的内容
	 */
	@Setter
	@Getter
	private String inputContent = "";

	public TextInputMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.TEXT_INPUT);
	}

	@Override
	public TextInputControl getControlElement() {
		return controlElement;
	}

}
