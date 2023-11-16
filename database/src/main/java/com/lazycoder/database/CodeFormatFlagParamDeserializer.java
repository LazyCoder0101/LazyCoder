package com.lazycoder.database;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CodeFormatFlagParamDeserializer implements ObjectDeserializer {

	@Override
	public CodeFormatFlagParam deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		CodeFormatFlagParam codeFormatFlagParam = JsonUtil.restoreByJSONObject(temp, CodeFormatFlagParam.class);
		return codeFormatFlagParam;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
