package com.lazycoder.database.model.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class AdditionalInfoDeserializer implements ObjectDeserializer {

	@Override
	public AdditionalInfo deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalInfo temp = JsonUtil.restoreByJSONObject(object, AdditionalInfo.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
