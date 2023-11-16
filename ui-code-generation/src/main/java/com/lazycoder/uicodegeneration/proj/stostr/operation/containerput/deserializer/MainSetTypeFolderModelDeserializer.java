package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.MainSetTypeFolderModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MainSetTypeFolderModelDeserializer implements ObjectDeserializer {

	@Override
	public MainSetTypeFolderModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		MainSetTypeFolderModel temp = JsonUtil.restoreByJSONObject(object, MainSetTypeFolderModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
