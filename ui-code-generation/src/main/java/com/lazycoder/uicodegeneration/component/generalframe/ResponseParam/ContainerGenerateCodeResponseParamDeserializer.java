package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class ContainerGenerateCodeResponseParamDeserializer implements ObjectDeserializer {

	@Override
	public ContainerGenerateCodeResponseParam deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		ContainerGenerateCodeResponseParam containerGenerateCodeResponseParam = JsonUtil.restoreByJSONObject(temp, ContainerGenerateCodeResponseParam.class);
		return containerGenerateCodeResponseParam;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
