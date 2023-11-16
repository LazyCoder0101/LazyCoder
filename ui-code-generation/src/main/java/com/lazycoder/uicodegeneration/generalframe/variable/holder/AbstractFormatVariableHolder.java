package com.lazycoder.uicodegeneration.generalframe.variable.holder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractFormatVariableHolder extends AbstractVariableHolder {

	protected static void setParam(AbstractFormatVariableHolder formatVariableHolder,
								   JSONObject formatVariableHolderJSONObject) {
		JSONArray variableListJSONArray = getVariableListJSONArray(formatVariableHolderJSONObject);
		JSONObject variableJsonObject;
		FormatVariable formatVariable;
		for (int i = 0; i < variableListJSONArray.size(); i++) {
			variableJsonObject = variableListJSONArray.getJSONObject(i);
			formatVariable = FormatVariable.restoreByJSONObject(variableJsonObject);
			formatVariableHolder.variableList.add(formatVariable);
		}
	}

}
