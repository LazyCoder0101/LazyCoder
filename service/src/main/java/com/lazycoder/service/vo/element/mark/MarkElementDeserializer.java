package com.lazycoder.service.vo.element.mark;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MarkElementDeserializer implements ObjectDeserializer {


	@Override
	public BaseMarkElement deserialze(DefaultJSONParser parser, Type type, Object o) {
		BaseMarkElement markElement = null;
		JSONObject temp = parser.parseObject();
		String markType = BaseMarkElement.getMarkType(temp);
		if (MarkElementName.FUNCTION_MARK.equals(markType)) {
			markElement = JsonUtil.restoreByJSONObject(temp, FunctionMarkElement.class);

		} else if (MarkElementName.SET_MARK.equals(markType)) {
			markElement = JsonUtil.restoreByJSONObject(temp, SetMarkElement.class);

		} else if (MarkElementName.INIT_MARK.equals(markType)) {
			markElement = JsonUtil.restoreByJSONObject(temp, InitMarkElement.class);

		} else if (MarkElementName.IMPORT_MARK.equals(markType)) {
			markElement = JsonUtil.restoreByJSONObject(temp, ImportMarkElement.class);

		} else if (MarkElementName.MAIN_SET_TYPE_MARK.equals(markType)) {
			markElement = JsonUtil.restoreByJSONObject(temp, MainSetMarkElement.class);

		} else if (MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(markType)) {
			markElement = JsonUtil.restoreByJSONObject(temp, AdditionalSetMarkElement.class);

		} else if (MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(markType)) {
			markElement = JsonUtil.restoreByJSONObject(temp, AdditionalFunctionMarkElement.class);
		}
		return markElement;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
