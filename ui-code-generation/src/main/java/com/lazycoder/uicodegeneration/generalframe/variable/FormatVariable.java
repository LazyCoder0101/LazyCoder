package com.lazycoder.uicodegeneration.generalframe.variable;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

/**
 * 格式变量
 *
 * @author admin
 */
@Data
public class FormatVariable extends AbstractVariable {

	/**
	 * 变量的作用名
	 */
	private String roleOfVariableName = "";

	public FormatVariable() {
		// TODO Auto-generated constructor stub
		super();
		setType(FORMAT_VARIABLE_TYPE);
	}


	public static FormatVariable restoreByJSONObject(JSONObject jsonObject) {
		FormatVariable formatVariable = JsonUtil.restoreByJSONObject(jsonObject, FormatVariable.class);
		return formatVariable;
	}

}
