package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.ModuleFormatFunctionNameHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModuleFormatFunctionNameListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<ModuleFormatFunctionNameHolder> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<ModuleFormatFunctionNameHolder> list = new ArrayList<>();

		ModuleFormatFunctionNameHolder temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			temp = JsonUtil.restoreByJSONObject(base, ModuleFormatFunctionNameHolder.class);
			list.add(temp);
		}
		return list;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
