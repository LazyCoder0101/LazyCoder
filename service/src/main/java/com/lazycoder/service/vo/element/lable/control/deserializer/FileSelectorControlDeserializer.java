package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.FileSelectorControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class FileSelectorControlDeserializer implements ObjectDeserializer {

	@Override
	public FileSelectorControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		FileSelectorControl temp = JsonUtil.restoreByJSONObject(object, FileSelectorControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
