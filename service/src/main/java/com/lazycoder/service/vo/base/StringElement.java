package com.lazycoder.service.vo.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.model.BaseModel;

/**
 * 文字元素
 *
 * @author admin
 */
public class StringElement extends BaseElement {

	private String text;

	public StringElement() {
		// TODO Auto-generated constructor stub
		this.type = ElementName.STRING_ELEMENT;
	}

	public StringElement(String text) {
		// TODO Auto-generated constructor stub
		this();
		this.text = text;
	}

	public static StringElement getStringElement(JSONObject elementJSONObject) {
		return JSON.toJavaObject(elementJSONObject, StringElement.class);
	}

	/**
	 * 获取字符串文本
	 *
	 * @param elementJSONObject
	 * @return
	 */
	public static String getStringText(JSONObject elementJSONObject) {
		return elementJSONObject.getString("text");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
