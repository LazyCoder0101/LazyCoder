package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CustomVariableHolderDeserializer implements ObjectDeserializer {

	@Override
	public CustomVariableHolder deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		CustomVariableHolder variableHolder = JsonUtil.restoreByJSONObject(temp, CustomVariableHolder.class);
		return variableHolder;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
