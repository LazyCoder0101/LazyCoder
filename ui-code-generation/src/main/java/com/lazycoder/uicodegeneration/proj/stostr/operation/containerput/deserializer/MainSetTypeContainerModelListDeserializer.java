package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.MainSetTypeContainerModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainSetTypeContainerModelListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<MainSetTypeContainerModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<MainSetTypeContainerModel> out = new ArrayList<>();
		MainSetTypeContainerModel temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			temp = JsonUtil.restoreByJSONObject(base, MainSetTypeContainerModel.class);
			out.add(temp);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
