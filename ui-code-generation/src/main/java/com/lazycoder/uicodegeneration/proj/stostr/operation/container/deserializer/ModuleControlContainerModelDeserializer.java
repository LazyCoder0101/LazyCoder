package com.lazycoder.uicodegeneration.proj.stostr.operation.container.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.ModuleControlContainerModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class ModuleControlContainerModelDeserializer implements ObjectDeserializer {

	@Override
	public ModuleControlContainerModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		ModuleControlContainerModel temp = JsonUtil.restoreByJSONObject(object, ModuleControlContainerModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
