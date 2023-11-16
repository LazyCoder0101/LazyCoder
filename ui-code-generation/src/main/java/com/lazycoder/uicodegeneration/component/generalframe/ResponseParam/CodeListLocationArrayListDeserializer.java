package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CodeListLocationArrayListDeserializer implements ObjectDeserializer {

	@Override
	public ArrayList<CodeListLocation> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<CodeListLocation> out = new ArrayList<>();
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		CodeListLocation codeListLocation;
		for (JSONObject base : baseList) {
			codeListLocation = JsonUtil.restoreByJSONObject(base, CodeListLocation.class);
			out.add(codeListLocation);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
