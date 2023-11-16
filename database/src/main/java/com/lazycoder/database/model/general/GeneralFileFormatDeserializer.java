package com.lazycoder.database.model.general;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class GeneralFileFormatDeserializer implements ObjectDeserializer {

	@Override
	public GeneralFileFormat deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		return JsonUtil.restoreByJSONObject(temp, GeneralFileFormat.class);
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
