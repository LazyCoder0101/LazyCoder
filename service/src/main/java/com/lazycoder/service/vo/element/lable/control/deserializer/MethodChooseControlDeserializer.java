package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MethodChooseControlDeserializer implements ObjectDeserializer {

	@Override
	public MethodChooseControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MethodChooseControl temp = JsonUtil.restoreByJSONObject(object, MethodChooseControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
