package com.lazycoder.service.vo.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class BaseElement implements BaseElementInterface {

	@JSONField(ordinal = 1)
	@Setter
	protected String type;


	/**
	 * 查看根据字符串进行JSONObject解析后JSONObject的元素类型
	 *
	 * @param elementJSONObject
	 * @return
	 */
	public static String getElementType(JSONObject elementJSONObject) {
		return elementJSONObject.getString("type");
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
