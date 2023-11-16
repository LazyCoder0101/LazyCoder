package com.lazycoder.service.vo.meta.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class InitMetaModelDeserializer implements ObjectDeserializer {

	@Override
	public InitMetaModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		InitMetaModel temp = JsonUtil.restoreByJSONObject(object, InitMetaModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
