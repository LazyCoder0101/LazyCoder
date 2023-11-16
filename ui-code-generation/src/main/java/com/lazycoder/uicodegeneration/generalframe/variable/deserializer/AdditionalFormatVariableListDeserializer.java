package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AdditionalFormatVariableHolder;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdditionalFormatVariableListDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<AdditionalFormatVariableHolder> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<AdditionalFormatVariableHolder> list = new ArrayList<>();

		AdditionalFormatVariableHolder temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			temp = JsonUtil.restoreByJSONObject(base, AdditionalFormatVariableHolder.class);
			list.add(temp);
		}
		return list;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
