package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.InfrequentlyUsedSettingControlDeserializer;
import com.lazycoder.uicodegeneration.proj.deserializer.OperatingPaneElementDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class InfrequentlyUsedSettingMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = InfrequentlyUsedSettingControlDeserializer.class)
	@Setter
	private InfrequentlyUsedSettingControl controlElement = new InfrequentlyUsedSettingControl();

	@JSONField(deserializeUsing = OperatingPaneElementDeserializer.class)
	@Setter
	@Getter
	private ArrayList<AbstractOpratingPaneElement> paneElementList = new ArrayList<>();

	public InfrequentlyUsedSettingMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.INFREQUENTLY_USED_SETTING);
	}

	@Override
	public InfrequentlyUsedSettingControl getControlElement() {
		return controlElement;
	}


}
