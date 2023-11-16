package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.FileSelectorControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.FileSelectorControlDeserializer;
import lombok.Getter;
import lombok.Setter;

public class FileSelectorMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = FileSelectorControlDeserializer.class)
	@Setter
	private FileSelectorControl controlElement = new FileSelectorControl();

	@Setter
	@Getter
	private String selectFileName;

	public FileSelectorMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.FILE_SELECTOR);
	}

	@Override
	public FileSelectorControl getControlElement() {
		return controlElement;
	}

}
