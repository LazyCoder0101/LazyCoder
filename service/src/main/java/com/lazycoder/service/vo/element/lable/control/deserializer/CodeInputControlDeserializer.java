package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.CodeInputControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CodeInputControlDeserializer implements ObjectDeserializer {

	@Override
	public CodeInputControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		CodeInputControl temp = JsonUtil.restoreByJSONObject(object, CodeInputControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
