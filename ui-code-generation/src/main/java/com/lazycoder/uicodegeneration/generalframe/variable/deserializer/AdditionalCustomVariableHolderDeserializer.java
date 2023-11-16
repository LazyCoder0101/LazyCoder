package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AdditionalCustomVariableHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class AdditionalCustomVariableHolderDeserializer implements ObjectDeserializer {

	@Override
	public AdditionalCustomVariableHolder deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalCustomVariableHolder temp = JsonUtil.restoreByJSONObject(object, AdditionalCustomVariableHolder.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
