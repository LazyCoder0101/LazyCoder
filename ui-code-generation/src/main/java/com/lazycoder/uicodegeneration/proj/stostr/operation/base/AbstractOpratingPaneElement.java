package com.lazycoder.uicodegeneration.proj.stostr.operation.base;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.ElementName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractOpratingPaneElement extends BaseFormatStructureModel {

	public final static String STRING_ELEMENT = ElementName.STRING_ELEMENT;

	public final static String LABEL_ELEMENT = ElementName.LABEL_ELEMENT;

	private String paneElementType = STRING_ELEMENT;

	public static String getPaneElementType(JSONObject param) {
		return param.getString("paneElementType");
	}

}
