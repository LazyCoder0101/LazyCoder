package com.lazycoder.uicodegeneration.generalframe.variable.holder;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainFormatVariableHolder extends AbstractFormatVariableHolder {

	private String flagName;

	/**
	 * @return
	 */
	public MainFormatVariableHolder(String flagName) {
		super();
		this.flagName = flagName;
	}

	public static MainFormatVariableHolder restoreByJSONObject(JSONObject jsonObject) {
		MainFormatVariableHolder formatVariableHolder = JsonUtil.restoreByJSONObject(jsonObject,
				MainFormatVariableHolder.class);
		setParam(formatVariableHolder, jsonObject);
		return formatVariableHolder;
	}

	@Override
	public void addVariable(AbstractVariable variable) {
		String toolTipText = "(￣.￣)  \"" + ((FormatVariable) variable).getRoleOfVariableName() + "\"是在\"" + flagName + "\"" + "原本就有的";
		variable.setToolTipTextOfHaveValue(toolTipText);
		super.addVariable(variable);
	}

}
