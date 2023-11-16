package com.lazycoder.service.vo.meta.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.meta.ModuleSetMetaModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class ModuleSetMetaModelDeserializer implements ObjectDeserializer {

	@Override
	public ModuleSetMetaModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		ModuleSetMetaModel temp = JsonUtil.restoreByJSONObject(object, ModuleSetMetaModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
