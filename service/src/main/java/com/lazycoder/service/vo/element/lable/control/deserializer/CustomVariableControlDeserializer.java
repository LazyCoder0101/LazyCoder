package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CustomVariableControlDeserializer implements ObjectDeserializer {

	@Override
	public CustomVariableControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		CustomVariableControl temp = JsonUtil.restoreByJSONObject(object, CustomVariableControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
