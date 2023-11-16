package com.lazycoder.service.vo.element.mark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;

/**
 * 方法标记
 *
 * @author admin
 */
public class FunctionMarkElement extends BaseMarkElement {

	public FunctionMarkElement() {
		// TODO Auto-generated constructor stub
		setMarkType(MarkElementName.FUNCTION_MARK);
		codeLabelMatchingWeight =1;
	}

	public static FunctionMarkElement jsonParsing(JSONObject elementJSONObject) {
		return JSON.toJavaObject(elementJSONObject, FunctionMarkElement.class);
	}

	@Override
	public FunctionMarkElement clone() {
		FunctionMarkElement markElement = (FunctionMarkElement) super.clone();
		return markElement;
	}

}
