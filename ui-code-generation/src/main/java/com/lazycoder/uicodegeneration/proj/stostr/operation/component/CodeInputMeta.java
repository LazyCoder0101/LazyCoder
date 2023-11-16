package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.CodeInputControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.CodeInputControlDeserializer;
import lombok.Getter;
import lombok.Setter;

public class CodeInputMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = CodeInputControlDeserializer.class)
	@Setter
	private CodeInputControl controlElement = new CodeInputControl();

	/**
	 * 最后用户输入的内容
	 */
	@Setter
	@Getter
	private String inputContent;

	public CodeInputMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.CODE_INPUT);
	}


	@Override
	public CodeInputControl getControlElement() {
		return controlElement;
	}

}
