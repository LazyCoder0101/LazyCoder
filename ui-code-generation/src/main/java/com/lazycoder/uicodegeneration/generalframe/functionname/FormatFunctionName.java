package com.lazycoder.uicodegeneration.generalframe.functionname;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

/**
 * 格式方法名
 *
 * @author admin
 */
@Data
public class FormatFunctionName extends AbstractMethodName {

	/**
	 * 变量的作用名
	 */
	private String roleOfFunctionNameValue = "";

	public FormatFunctionName() {
		// TODO Auto-generated constructor stub
		super();
		setType(FORMAT_FUNCTION_NAME_TYPE);
	}


	public static FormatFunctionName restoreByJSONObject(JSONObject jsonObject) {
		FormatFunctionName formatFunctionName = JsonUtil.restoreByJSONObject(jsonObject, FormatFunctionName.class);
		return formatFunctionName;
	}

}
