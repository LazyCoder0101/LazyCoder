package com.lazycoder.service.vo.element.lable.control.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class InfrequentlyUsedSettingControlDeserializer implements ObjectDeserializer {

	@Override
	public InfrequentlyUsedSettingControl deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		InfrequentlyUsedSettingControl temp = JsonUtil.restoreByJSONObject(object, InfrequentlyUsedSettingControl.class);
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
