package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class TextInputControlDeserializer implements ObjectDeserializer {

	@Override
	public TextInputControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		TextInputControl temp = JsonUtil.restoreByJSONObject(object, TextInputControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
