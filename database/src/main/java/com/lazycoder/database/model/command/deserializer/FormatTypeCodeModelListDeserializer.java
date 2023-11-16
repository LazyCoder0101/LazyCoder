package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FormatTypeCodeModelListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<FormatTypeCodeModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<FormatTypeCodeModel> out = new ArrayList<>();
		FormatTypeCodeModel object;
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		for (JSONObject base : baseList) {
			object = base.toJavaObject(FormatTypeCodeModel.class);
			out.add(object);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
