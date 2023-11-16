package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.AdditionalSetOperatingModel;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class AdditionalSetOperatingModelDeserializer implements ObjectDeserializer {

	@Override
	public AdditionalSetOperatingModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalSetOperatingModel temp = JsonUtil.restoreByJSONObject(object, AdditionalSetOperatingModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
