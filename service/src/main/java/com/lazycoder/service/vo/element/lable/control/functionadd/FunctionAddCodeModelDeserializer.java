package com.lazycoder.service.vo.element.lable.control.functionadd;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FunctionAddCodeModelDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<FunctionAddCodeModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<FunctionAddCodeModel> out = new ArrayList<>();
		FunctionAddCodeModel functionAddCodeModel;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			functionAddCodeModel = JsonUtil.restoreByJSONObject(base, FunctionAddCodeModel.class);
			out.add(functionAddCodeModel);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
