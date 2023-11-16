package com.lazycoder.uicodegeneration.generalframe.functionname;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

/**
 * 自定义方法名
 *
 * @author admin
 */
@Data
public class CustomFunctionName extends AbstractMethodName {

	public CustomFunctionName() {
		// TODO Auto-generated constructor stub
		super();
		setType(CUSTOM_FUNCTION_NAME_TYPE);
	}

	public static CustomFunctionName restoreByJSONObject(JSONObject jsonObject) {
		CustomFunctionName formatFunctionName = JsonUtil.restoreByJSONObject(jsonObject, CustomFunctionName.class);
		return formatFunctionName;
	}

}
