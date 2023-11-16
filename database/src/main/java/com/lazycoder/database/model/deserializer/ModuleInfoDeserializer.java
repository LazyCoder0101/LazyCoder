package com.lazycoder.database.model.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class ModuleInfoDeserializer implements ObjectDeserializer {

	@Override
	public ModuleInfo deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		ModuleInfo temp = JsonUtil.restoreByJSONObject(object, ModuleInfo.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
