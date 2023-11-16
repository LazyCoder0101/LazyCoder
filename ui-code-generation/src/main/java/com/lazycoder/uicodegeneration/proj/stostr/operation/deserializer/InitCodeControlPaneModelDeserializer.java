package com.lazycoder.uicodegeneration.proj.stostr.operation.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.InitCodeControlPaneModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class InitCodeControlPaneModelDeserializer implements ObjectDeserializer {

	@Override
	public InitCodeControlPaneModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		InitCodeControlPaneModel temp = JsonUtil.restoreByJSONObject(object, InitCodeControlPaneModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
