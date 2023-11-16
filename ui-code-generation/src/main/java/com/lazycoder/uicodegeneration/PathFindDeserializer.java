package com.lazycoder.uicodegeneration;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class PathFindDeserializer implements ObjectDeserializer {

	@Override
	public PathFind deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		PathFind pathFind = JsonUtil.restoreByJSONObject(temp, PathFind.class);
		return pathFind;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
