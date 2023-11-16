package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AdditionalFormatFunctionNameHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdditionalFormatFunctionNameListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<AdditionalFormatFunctionNameHolder> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<AdditionalFormatFunctionNameHolder> list = new ArrayList<>();

		AdditionalFormatFunctionNameHolder temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			temp = JsonUtil.restoreByJSONObject(base, AdditionalFormatFunctionNameHolder.class);
			list.add(temp);
		}
		return list;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
