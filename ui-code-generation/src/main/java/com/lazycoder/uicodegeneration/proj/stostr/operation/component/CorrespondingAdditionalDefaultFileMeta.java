package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.CorrespondingAdditionalDefaultFileControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.CorrespondingAdditionalDefaultFileControlDeserializer;
import lombok.Getter;
import lombok.Setter;

public class CorrespondingAdditionalDefaultFileMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = CorrespondingAdditionalDefaultFileControlDeserializer.class)
	@Setter
	private CorrespondingAdditionalDefaultFileControl controlElement = new CorrespondingAdditionalDefaultFileControl();

	/**
	 * 最后用户输入的内容
	 */
	@Setter
	@Getter
	private String selectedFileName="";

	public CorrespondingAdditionalDefaultFileMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE);
	}

	@Override
	public CorrespondingAdditionalDefaultFileControl getControlElement() {
		return controlElement;
	}

}
