package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.ModuleFormatVariableHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModuleFormatVariableListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<ModuleFormatVariableHolder> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<ModuleFormatVariableHolder> list = new ArrayList<>();

		ModuleFormatVariableHolder temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			temp = JsonUtil.restoreByJSONObject(base, ModuleFormatVariableHolder.class);
			list.add(temp);
		}
		return list;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
