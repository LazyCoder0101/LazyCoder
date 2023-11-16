package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.CustomFunctionName;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CustomFunctionNameDeserializer implements ObjectDeserializer {

	@Override
	public CustomFunctionName deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		CustomFunctionName temp = JsonUtil.restoreByJSONObject(object, CustomFunctionName.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
