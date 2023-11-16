package com.lazycoder.uicodegeneration.generalframe.variable;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

/**
 * 自定义变量
 *
 * @author admin
 */
@Data
public class CustomVariable extends AbstractVariable {

	private boolean allowDuplicateNames = false;

	public CustomVariable() {
		// TODO Auto-generated constructor stub
		super();
		setType(CUSTOM_VARIABLE_TYPE);
	}

	public static CustomVariable restoreByJSONObject(JSONObject jsonObject) {
		CustomVariable formatVariable = JsonUtil.restoreByJSONObject(jsonObject, CustomVariable.class);
		return formatVariable;
	}

}
