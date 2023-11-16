package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.AdditionalFunctionOperatingModel;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class AdditionalFunctionOperatingModelDeserializer implements ObjectDeserializer {

	@Override
	public AdditionalFunctionOperatingModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalFunctionOperatingModel temp = JsonUtil.restoreByJSONObject(object, AdditionalFunctionOperatingModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
