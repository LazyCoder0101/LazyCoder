package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import com.lazycoder.uicodegeneration.generalframe.functionname.CustomFunctionName;
import com.lazycoder.uicodegeneration.generalframe.functionname.FormatFunctionName;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class GeneralMethodNameDeserializer implements ObjectDeserializer {

	@Override
	public AbstractMethodName deserialze(DefaultJSONParser parser, Type type, Object o) {
		JSONObject object = parser.parseObject();
		int methodNameType = object.getIntValue(AbstractMethodName.getTypeFieldName());
		AbstractMethodName temp=null;
		if(AbstractMethodName.FORMAT_FUNCTION_NAME_TYPE ==methodNameType){
			temp = JsonUtil.restoreByJSONObject(object, FormatFunctionName.class);
		}else if (AbstractMethodName.CUSTOM_FUNCTION_NAME_TYPE ==methodNameType){
			temp = JsonUtil.restoreByJSONObject(object, CustomFunctionName.class);
		}
		return temp;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
