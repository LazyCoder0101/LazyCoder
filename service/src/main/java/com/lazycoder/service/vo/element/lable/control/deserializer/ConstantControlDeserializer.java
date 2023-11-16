package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class ConstantControlDeserializer implements ObjectDeserializer {

	@Override
	public ConstantControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		ConstantControl temp = JsonUtil.restoreByJSONObject(object, ConstantControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
