package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CodeListDeserializer implements ObjectDeserializer {

	@Override
	public CodeList deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		CodeList temp = JsonUtil.restoreByJSONObject(object, CodeList.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
