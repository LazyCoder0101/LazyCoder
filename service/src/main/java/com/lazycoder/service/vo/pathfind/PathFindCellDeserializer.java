package com.lazycoder.service.vo.pathfind;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PathFindCellDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<PathFindCell> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<PathFindCell> out = new ArrayList<>();
		PathFindCell pathFindCell;
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		for (JSONObject base : baseList) {
			pathFindCell = base.toJavaObject(PathFindCell.class);
			out.add(pathFindCell);
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
