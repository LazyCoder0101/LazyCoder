package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CustomMethodNameControlDeserializer implements ObjectDeserializer {

	@Override
	public CustomMethodNameControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		CustomMethodNameControl temp = JsonUtil.restoreByJSONObject(object, CustomMethodNameControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
