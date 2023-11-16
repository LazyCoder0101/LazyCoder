package com.lazycoder.uicodegeneration.proj.stostr.codeshown;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.TheFormatStatement;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class TheFormatStatementDeserializer implements ObjectDeserializer {

	@Override
	public TheFormatStatement deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		return JsonUtil.restoreByJSONObject(temp, TheFormatStatement.class);
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
