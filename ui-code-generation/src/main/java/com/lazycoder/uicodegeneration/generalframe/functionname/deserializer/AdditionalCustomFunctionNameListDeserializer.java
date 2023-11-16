package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AdditionalCustomFunctionNameHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class AdditionalCustomFunctionNameListDeserializer implements ObjectDeserializer {


	@Override
	public AdditionalCustomFunctionNameHolder deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalCustomFunctionNameHolder temp = JsonUtil.restoreByJSONObject(object, AdditionalCustomFunctionNameHolder.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
