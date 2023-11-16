package com.lazycoder.uicodegeneration.proj.stostr.operation.component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.PathFindDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import lombok.Data;

@Data
public abstract class AbstractLabelMeta extends AbstractOpratingPaneElement {

	@JSONField(deserializeUsing = PathFindDeserializer.class)
	private PathFind pathFind;

	private String lableType = "";

	public AbstractLabelMeta() {
		// TODO Auto-generated constructor stub
		setPaneElementType(LABEL_ELEMENT);
	}


	public static String getLableType(JSONObject param) {
		return param.getString("lableType");
	}

	public abstract Object getControlElement();

}
