package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class FormatVariableDeserializer implements ObjectDeserializer {

	@Override
	public FormatVariable deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		FormatVariable variable = JsonUtil.restoreByJSONObject(temp, FormatVariable.class);
		return variable;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
