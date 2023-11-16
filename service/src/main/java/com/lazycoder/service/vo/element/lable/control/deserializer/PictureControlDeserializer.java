package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.PictureControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class PictureControlDeserializer implements ObjectDeserializer {

	@Override
	public PictureControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		PictureControl temp = JsonUtil.restoreByJSONObject(object, PictureControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
