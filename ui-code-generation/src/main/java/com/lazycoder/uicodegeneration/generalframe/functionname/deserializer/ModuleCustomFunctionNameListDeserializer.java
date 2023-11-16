package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.ModuleCustomFunctionNameHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModuleCustomFunctionNameListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<ModuleCustomFunctionNameHolder> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<ModuleCustomFunctionNameHolder> list = new ArrayList<>();

		ModuleCustomFunctionNameHolder temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
//            temp= JsonUtil.restoreByJSONObject(base, ModuleCustomFunctionNameHolder.class);
			String out = base.toJSONString();
			temp = JsonUtil.toBean(out, ModuleCustomFunctionNameHolder.class);
			list.add(temp);
		}
		return list;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}


}
