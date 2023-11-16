package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.CustomVariable;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CustomVariableDeserializer implements ObjectDeserializer {

	@Override
	public CustomVariable deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		CustomVariable variable = JsonUtil.restoreByJSONObject(temp, CustomVariable.class);
		return variable;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
