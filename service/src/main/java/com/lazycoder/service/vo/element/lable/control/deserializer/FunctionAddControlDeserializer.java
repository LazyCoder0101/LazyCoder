package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class FunctionAddControlDeserializer implements ObjectDeserializer {

	@Override
	public FunctionAddControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		FunctionAddControl temp = JsonUtil.restoreByJSONObject(object, FunctionAddControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
