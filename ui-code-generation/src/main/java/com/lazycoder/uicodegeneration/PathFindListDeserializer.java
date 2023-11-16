package com.lazycoder.uicodegeneration;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PathFindListDeserializer implements ObjectDeserializer {

	@Override
	public ArrayList<PathFind> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<PathFind> out = new ArrayList<>();
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		PathFind pathFind;
		for (JSONObject base : baseList) {
			pathFind = JsonUtil.restoreByJSONObject(base, PathFind.class);
			out.add(pathFind);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
