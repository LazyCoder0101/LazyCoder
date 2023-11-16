package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.NoteControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.NoteControlDeserializer;
import lombok.Getter;
import lombok.Setter;

public class NoteMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = NoteControlDeserializer.class)
	@Setter
	private NoteControl controlElement = new NoteControl();

	@Getter
	@Setter
	private String note = "";

	public NoteMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.NOTE);
	}

	@Override
	public NoteControl getControlElement() {
		return controlElement;
	}


}
