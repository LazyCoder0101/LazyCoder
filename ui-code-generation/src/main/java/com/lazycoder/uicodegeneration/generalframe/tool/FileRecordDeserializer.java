package com.lazycoder.uicodegeneration.generalframe.tool;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileRecordDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<FileRecord> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<FileRecord> out = new ArrayList<>();
		FileRecord fileRecord;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		for (JSONObject base : jsonArray) {
			fileRecord = JsonUtil.restoreByJSONObject(base, FileRecord.class);
			out.add(fileRecord);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
