package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.CustomVariable;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomVariableListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<CustomVariable> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<CustomVariable> out = new ArrayList<>();
		CustomVariable customVariable;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			customVariable = JsonUtil.restoreByJSONObject(base, CustomVariable.class);
			out.add(customVariable);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
