package com.lazycoder.database.model.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModuleListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<Module> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<Module> list = new ArrayList<>();

		Module temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			String out = base.toJSONString();
			temp = JsonUtil.toBean(out, Module.class);
			list.add(temp);
		}
		return list;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}


}
