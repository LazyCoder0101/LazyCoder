package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class FormatTypeOperatingModelDeserializer implements ObjectDeserializer {

	@Override
	public FormatTypeOperatingModel deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		FormatTypeOperatingModel temp = JsonUtil.restoreByJSONObject(object, FormatTypeOperatingModel.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
