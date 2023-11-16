package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdditionalFunctionCodeModelListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<AdditionalFunctionCodeModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<AdditionalFunctionCodeModel> out = new ArrayList<>();
		AdditionalFunctionCodeModel object;
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		for (JSONObject base : baseList) {
			object = base.toJavaObject(AdditionalFunctionCodeModel.class);
			out.add(object);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
