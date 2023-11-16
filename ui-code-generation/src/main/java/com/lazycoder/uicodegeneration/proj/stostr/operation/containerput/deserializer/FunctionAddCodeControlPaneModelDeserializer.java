package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.FunctionAddCodeControlPaneModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class FunctionAddCodeControlPaneModelDeserializer implements ObjectDeserializer {

	@Override
	public FunctionAddCodeControlPaneModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		FunctionAddCodeControlPaneModel temp = JsonUtil.restoreByJSONObject(object, FunctionAddCodeControlPaneModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
