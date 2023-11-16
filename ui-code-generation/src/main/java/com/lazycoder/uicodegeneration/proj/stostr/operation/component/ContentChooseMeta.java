package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.element.lable.control.deserializer.ContentChooseControlDeserializer;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class ContentChooseMeta extends AbstractLabelMeta {

	@JSONField(deserializeUsing = ContentChooseControlDeserializer.class)
	@Setter
	private ContentChooseControl controlElement;

	/**
	 * 用户最后选择的选项序号
	 */
	@Setter
	@Getter
	private ArrayList<Integer> selectList = new ArrayList<>();

	public ContentChooseMeta() {
		// TODO Auto-generated constructor stub
		super();
		setLableType(LabelElementName.CONTENT_CHOOSE);
	}


	@Override
	public ContentChooseControl getControlElement() {
		return controlElement;
	}

}
