package com.lazycoder.service.vo.meta.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.meta.FunctionMetaModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class FunctionMetaModelDeserializer implements ObjectDeserializer {

	@Override
	public FunctionMetaModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		FunctionMetaModel temp = JsonUtil.restoreByJSONObject(object, FunctionMetaModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
