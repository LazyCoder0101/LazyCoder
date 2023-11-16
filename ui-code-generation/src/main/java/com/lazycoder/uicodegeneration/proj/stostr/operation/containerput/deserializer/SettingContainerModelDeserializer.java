package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.SettingContainerModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class SettingContainerModelDeserializer implements ObjectDeserializer {

	@Override
	public SettingContainerModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		SettingContainerModel temp = JsonUtil.restoreByJSONObject(object, SettingContainerModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
