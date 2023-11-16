package com.lazycoder.database.common.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModuleRelatedParamListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<ModuleRelatedParam> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<ModuleRelatedParam> list = new ArrayList<>();

		ModuleRelatedParam temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			String out = base.toJSONString();
			temp = JsonUtil.toBean(out, ModuleRelatedParam.class);
			list.add(temp);
		}
		return list;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}


}
