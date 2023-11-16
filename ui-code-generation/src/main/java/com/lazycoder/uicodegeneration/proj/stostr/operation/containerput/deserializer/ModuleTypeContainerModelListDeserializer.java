package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.ModuleTypeContainerModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModuleTypeContainerModelListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<ModuleTypeContainerModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<ModuleTypeContainerModel> out = new ArrayList<>();
		ModuleTypeContainerModel moduleTypeContainerModel;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			moduleTypeContainerModel = JsonUtil.restoreByJSONObject(base, ModuleTypeContainerModel.class);
			out.add(moduleTypeContainerModel);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
