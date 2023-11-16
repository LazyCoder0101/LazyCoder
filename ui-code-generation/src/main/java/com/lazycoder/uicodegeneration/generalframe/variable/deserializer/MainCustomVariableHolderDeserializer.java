package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.MainCustomVariableHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MainCustomVariableHolderDeserializer implements ObjectDeserializer {

	@Override
	public MainCustomVariableHolder deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MainCustomVariableHolder temp = JsonUtil.restoreByJSONObject(object, MainCustomVariableHolder.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
