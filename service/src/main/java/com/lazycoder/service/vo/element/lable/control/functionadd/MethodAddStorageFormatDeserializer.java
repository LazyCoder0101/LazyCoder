package com.lazycoder.service.vo.element.lable.control.functionadd;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MethodAddStorageFormatDeserializer implements ObjectDeserializer {

	@Override
	public MethodAddStorageFormat deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MethodAddStorageFormat temp = JsonUtil.restoreByJSONObject(object, MethodAddStorageFormat.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
