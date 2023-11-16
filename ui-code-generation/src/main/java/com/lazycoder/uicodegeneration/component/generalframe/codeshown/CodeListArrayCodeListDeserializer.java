package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CodeListArrayCodeListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<TheCodeStatement> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<TheCodeStatement> out = new ArrayList<>();
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		TheCodeStatement codeStatement;
		for (JSONObject base : baseList) {
			codeStatement = JsonUtil.restoreByJSONObject(base, TheCodeStatement.class);
			out.add(codeStatement);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
