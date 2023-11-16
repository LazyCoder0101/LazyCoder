package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.MainCustomFunctionNameHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MainCustomFunctionNameHolderDeserializer implements ObjectDeserializer {

	@Override
	public MainCustomFunctionNameHolder deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MainCustomFunctionNameHolder temp = JsonUtil.restoreByJSONObject(object, MainCustomFunctionNameHolder.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
