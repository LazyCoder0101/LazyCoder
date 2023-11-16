package com.lazycoder.service.vo.meta.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.meta.AdditionalSetMetaModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class AdditionalSetMetaModelDeserializer implements ObjectDeserializer {

	@Override
	public AdditionalSetMetaModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalSetMetaModel temp = JsonUtil.restoreByJSONObject(object, AdditionalSetMetaModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
