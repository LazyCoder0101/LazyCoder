package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class ContentChooseControlDeserializer implements ObjectDeserializer {

	@Override
	public ContentChooseControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		ContentChooseControl temp = JsonUtil.restoreByJSONObject(object, ContentChooseControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
