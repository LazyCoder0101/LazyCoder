package com.lazycoder.database.model.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class MainInfoDeserializer implements ObjectDeserializer {

	@Override
	public MainInfo deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MainInfo temp = JsonUtil.restoreByJSONObject(object, MainInfo.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
