package com.lazycoder.uicodegeneration.proj.stostr.operation.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.BusinessLogicCodeControlPaneModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class BusinessLogicCodeControlPaneModelDeserializer implements ObjectDeserializer {

	@Override
	public BusinessLogicCodeControlPaneModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		BusinessLogicCodeControlPaneModel temp = JsonUtil.restoreByJSONObject(object, BusinessLogicCodeControlPaneModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
