package com.lazycoder.database.common.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class ModuleRelatedParamDeserializer implements ObjectDeserializer {

	@Override
	public ModuleRelatedParam deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		ModuleRelatedParam temp = JsonUtil.restoreByJSONObject(object, ModuleRelatedParam.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
