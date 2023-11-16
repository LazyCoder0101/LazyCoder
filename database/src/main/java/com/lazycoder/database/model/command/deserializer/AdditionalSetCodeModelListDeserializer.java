package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.AdditionalSetCodeModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdditionalSetCodeModelListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<AdditionalSetCodeModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<AdditionalSetCodeModel> out = new ArrayList<>();
		AdditionalSetCodeModel object;
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		for (JSONObject base : baseList) {
			object = base.toJavaObject(AdditionalSetCodeModel.class);
			out.add(object);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
