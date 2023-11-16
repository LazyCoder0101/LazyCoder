package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.CorrespondingAdditionalDefaultFileControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class CorrespondingAdditionalDefaultFileControlDeserializer implements ObjectDeserializer {

	@Override
	public CorrespondingAdditionalDefaultFileControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		CorrespondingAdditionalDefaultFileControl temp = JsonUtil.restoreByJSONObject(object, CorrespondingAdditionalDefaultFileControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
