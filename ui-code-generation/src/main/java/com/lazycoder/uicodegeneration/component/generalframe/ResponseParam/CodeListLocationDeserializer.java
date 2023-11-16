package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CodeListLocationDeserializer implements ObjectDeserializer {

	@Override
	public CodeListLocation deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		CodeListLocation codeListLocation = JsonUtil.restoreByJSONObject(temp, CodeListLocation.class);
		return codeListLocation;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
