package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CustomFunctionNameHolderDeserializer implements ObjectDeserializer {

	@Override
	public CustomFunctionNameHolder deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		CustomFunctionNameHolder functionNameHolder = JsonUtil.restoreByJSONObject(temp, CustomFunctionNameHolder.class);
		return functionNameHolder;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
