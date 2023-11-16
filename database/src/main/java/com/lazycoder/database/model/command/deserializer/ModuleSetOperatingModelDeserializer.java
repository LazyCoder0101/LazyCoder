package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class ModuleSetOperatingModelDeserializer implements ObjectDeserializer {

	@Override
	public ModuleSetOperatingModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		ModuleSetOperatingModel temp = JsonUtil.restoreByJSONObject(object, ModuleSetOperatingModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
