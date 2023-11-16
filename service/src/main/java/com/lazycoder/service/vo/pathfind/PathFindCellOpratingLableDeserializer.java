package com.lazycoder.service.vo.pathfind;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import java.lang.reflect.Type;

public class PathFindCellOpratingLableDeserializer implements ObjectDeserializer {

	@Override
	public BaseLableElement deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject temp = parser.parseObject();
		BaseLableElement lableElement = DeserializeElementMethods.getDeserializeControlElement(temp);
		return lableElement;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
