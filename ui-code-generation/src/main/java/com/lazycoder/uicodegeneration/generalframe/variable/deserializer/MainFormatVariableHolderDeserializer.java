package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.MainFormatVariableHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MainFormatVariableHolderDeserializer implements ObjectDeserializer {

	@Override
	public MainFormatVariableHolder deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MainFormatVariableHolder temp = JsonUtil.restoreByJSONObject(object, MainFormatVariableHolder.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
