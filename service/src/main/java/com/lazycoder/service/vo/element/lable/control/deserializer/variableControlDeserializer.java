package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.VariableControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class variableControlDeserializer implements ObjectDeserializer {

	@Override
	public VariableControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		VariableControl temp = JsonUtil.restoreByJSONObject(object, VariableControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
