package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CodePaneModelGenerateCodeResponseParamListDeserializer implements ObjectDeserializer {

	@Override
	public ArrayList<CodePaneModelGenerateCodeResponseParam> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<CodePaneModelGenerateCodeResponseParam> out = new ArrayList<>();
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam;
		for (JSONObject base : baseList) {
			codePaneModelGenerateCodeResponseParam = JsonUtil.restoreByJSONObject(base, CodePaneModelGenerateCodeResponseParam.class);
			out.add(codePaneModelGenerateCodeResponseParam);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
