package com.lazycoder.database;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CodeFormatFlagParamArrayListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<CodeFormatFlagParam> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<CodeFormatFlagParam> out = new ArrayList<>();
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		CodeFormatFlagParam codeFormatFlagParam;
		for (JSONObject base : baseList) {
			codeFormatFlagParam = JsonUtil.restoreByJSONObject(base, CodeFormatFlagParam.class);
			out.add(codeFormatFlagParam);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
