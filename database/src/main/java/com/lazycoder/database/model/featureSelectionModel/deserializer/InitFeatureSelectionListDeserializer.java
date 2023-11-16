package com.lazycoder.database.model.featureSelectionModel.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InitFeatureSelectionListDeserializer implements ObjectDeserializer {


	@Override
	public List<InitFeatureSelectonModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
		List<InitFeatureSelectonModel> list = new ArrayList<>();

		InitFeatureSelectonModel temp;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			temp = JsonUtil.restoreByJSONObject(base, InitFeatureSelectonModel.class);
			list.add(temp);
		}
		return list;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
