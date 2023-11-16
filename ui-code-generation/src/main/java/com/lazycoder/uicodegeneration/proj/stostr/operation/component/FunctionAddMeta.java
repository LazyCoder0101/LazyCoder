package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.FunctionAddControlDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.FunctionAddCodeControlPaneModel;
import lombok.Getter;
import lombok.Setter;

public class FunctionAddMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = FunctionAddControlDeserializer.class)
	@Setter
	private FunctionAddControl controlElement;

	@Getter
	@Setter
	private FunctionAddCodeControlPaneModel functionAddCodeControlPaneModel;

	public FunctionAddMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.FUNCTION_ADD);
	}


	@Override
	public FunctionAddControl getControlElement() {
		return controlElement;
	}

}
