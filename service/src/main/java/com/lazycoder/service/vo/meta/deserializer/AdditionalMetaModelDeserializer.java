package com.lazycoder.service.vo.meta.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class AdditionalMetaModelDeserializer implements ObjectDeserializer {

	@Override
	public AdditionalMetaModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalMetaModel temp = JsonUtil.restoreByJSONObject(object, AdditionalMetaModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
