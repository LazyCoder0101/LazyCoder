package com.lazycoder.uicodegeneration.component.generalframe;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FormatControlPaneLableListDeserializer implements ObjectDeserializer {

	@Override
	public ArrayList<FormatControlPaneLable> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<FormatControlPaneLable> out = new ArrayList<>();
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		FormatControlPaneLable formatControlPaneLable;
		for (JSONObject base : baseList) {
			formatControlPaneLable = JsonUtil.restoreByJSONObject(base, FormatControlPaneLable.class);
			out.add(formatControlPaneLable);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}


}
