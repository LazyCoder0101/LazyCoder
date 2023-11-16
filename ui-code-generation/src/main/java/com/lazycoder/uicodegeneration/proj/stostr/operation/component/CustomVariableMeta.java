package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.CustomVariableControlDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.CustomVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.CustomVariableListDeserializer;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;


public class CustomVariableMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = CustomVariableControlDeserializer.class)
	@Setter
	private CustomVariableControl controlElement = new CustomVariableControl();

	@JSONField(deserializeUsing = CustomVariableListDeserializer.class)
	@Setter
	@Getter
	private ArrayList<CustomVariable> inputCustomVariableList = new ArrayList<>();

	public CustomVariableMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.CUSTOM_VARIABLE);
	}

	@Override
	public CustomVariableControl getControlElement() {
		return controlElement;
	}


}
