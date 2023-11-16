package com.lazycoder.service.vo.meta.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.meta.AdditionalFunctionMetaModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class AdditionalFunctionMetaModelDeserializer implements ObjectDeserializer {

	@Override
	public AdditionalFunctionMetaModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalFunctionMetaModel temp = JsonUtil.restoreByJSONObject(object, AdditionalFunctionMetaModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
