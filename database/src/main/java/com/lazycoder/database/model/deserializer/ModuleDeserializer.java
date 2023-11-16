package com.lazycoder.database.model.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.Module;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class ModuleDeserializer implements ObjectDeserializer {

	@Override
	public Module deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		Module temp = JsonUtil.restoreByJSONObject(object, Module.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
