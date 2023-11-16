package com.lazycoder.uicodegeneration.generalframe.variable.holder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.GeneralVariableHolderSerializer;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.GeneralVariableHolderDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractVariableHolder {

	@JSONField(deserializeUsing = GeneralVariableHolderDeserializer.class, serializeUsing = GeneralVariableHolderSerializer.class)
	protected ArrayList<AbstractVariable> variableList = new ArrayList<>();

	protected static JSONArray getVariableListJSONArray(JSONObject variableHolderJSONObject) {
		JSONArray jsonArray = variableHolderJSONObject.getJSONArray("variableList");
		return jsonArray;
	}

	public void addVariable(AbstractVariable variable) {
		variableList.add(variable);
	}


}
