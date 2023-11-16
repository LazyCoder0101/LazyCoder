package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.AdditionalSetTypeFolderModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class AdditionalSetTypeFolderModelDeserializer implements ObjectDeserializer {

	@Override
	public AdditionalSetTypeFolderModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		AdditionalSetTypeFolderModel temp = JsonUtil.restoreByJSONObject(object, AdditionalSetTypeFolderModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
