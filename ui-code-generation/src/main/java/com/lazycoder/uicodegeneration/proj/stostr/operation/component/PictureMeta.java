package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.PictureControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.PictureControlDeserializer;
import lombok.Setter;

public class PictureMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = PictureControlDeserializer.class)
	@Setter
	private PictureControl controlElement = new PictureControl();

	public PictureMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.PICTURE);
	}

	@Override
	public PictureControl getControlElement() {
		return controlElement;
	}

}
