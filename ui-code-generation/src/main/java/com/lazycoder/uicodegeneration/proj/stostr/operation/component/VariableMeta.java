package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.VariableControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.variableControlDeserializer;
import lombok.Getter;
import lombok.Setter;

public class VariableMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = variableControlDeserializer.class)
	@Setter
	private VariableControl controlElement = new VariableControl();

	/**
	 * 用户最后选择的变量的id
	 */
	@Getter
	@Setter
	private Integer selectVariableId = null;

	public VariableMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.VARIABLE);
	}

	@Override
	public VariableControl getControlElement() {
		return controlElement;
	}

}
