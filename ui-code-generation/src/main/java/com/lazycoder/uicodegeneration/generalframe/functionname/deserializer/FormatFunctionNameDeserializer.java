package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.FormatFunctionName;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class FormatFunctionNameDeserializer implements ObjectDeserializer {

	@Override
	public FormatFunctionName deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		FormatFunctionName temp = JsonUtil.restoreByJSONObject(object, FormatFunctionName.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
