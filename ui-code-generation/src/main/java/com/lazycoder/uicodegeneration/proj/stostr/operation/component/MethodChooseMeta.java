package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.MethodChooseControlDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.GeneralMethodNameDeserializer;
import lombok.Getter;
import lombok.Setter;


public class MethodChooseMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = MethodChooseControlDeserializer.class)
	@Setter
	private MethodChooseControl controlElement = new MethodChooseControl();

//	@Setter
//	@Getter
//	private Integer selectMethodNameId = null;

	@JSONField(deserializeUsing = GeneralMethodNameDeserializer.class)
	@Setter
	@Getter
	private AbstractMethodName methodName=null;

	public MethodChooseMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.METHOD_CHOOSE);
	}

	@Override
	public MethodChooseControl getControlElement() {
		return controlElement;
	}

}
