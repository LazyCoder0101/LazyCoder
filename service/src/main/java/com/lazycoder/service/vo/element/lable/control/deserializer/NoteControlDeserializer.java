package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.NoteControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class NoteControlDeserializer implements ObjectDeserializer {

	@Override
	public NoteControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		NoteControl temp = JsonUtil.restoreByJSONObject(object, NoteControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
