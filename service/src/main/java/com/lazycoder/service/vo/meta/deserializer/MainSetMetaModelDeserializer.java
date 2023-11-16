package com.lazycoder.service.vo.meta.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.meta.MainSetMetaModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MainSetMetaModelDeserializer implements ObjectDeserializer {

	@Override
	public MainSetMetaModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MainSetMetaModel temp = JsonUtil.restoreByJSONObject(object, MainSetMetaModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
