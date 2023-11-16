package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.MainFormatFunctionNameHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MainFormatFunctionNameHolderDeserializer implements ObjectDeserializer {

	@Override
	public MainFormatFunctionNameHolder deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MainFormatFunctionNameHolder temp = JsonUtil.restoreByJSONObject(object, MainFormatFunctionNameHolder.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
