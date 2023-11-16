package com.lazycoder.service.vo.element.lable.control.functionadd;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FunctionAddControlFunctionListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<MethodAddStorageFormat> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<MethodAddStorageFormat> out = new ArrayList<>();
		MethodAddStorageFormat methodAddStorageFormat;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			methodAddStorageFormat = JsonUtil.restoreByJSONObject(base, MethodAddStorageFormat.class);
			out.add(methodAddStorageFormat);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
