package com.lazycoder.service.vo.element.lable.code;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import lombok.Data;

/**
 * 选择框元素
 *
 * @author Administrator
 */
@Data
public class ContentChooseCodeElement extends ContentChooseElement {


	/**
	 * 显示哪組的选项
	 */
	@JSONField(ordinal = 6)
	private int selectGroup = 0;

	public ContentChooseCodeElement() {
		// TODO Auto-generated constructor stub
		super();
	}

	public ContentChooseCodeElement(String optionId,String optionName) {
		super(optionId,optionName);
	}

	/**
	 * 更改选项设置参数
	 *
	 * @param option
	 */
	public void updateParam(OptionDataModel option) {
		if (selectGroup >= option.getValueListGroupNum()) {//看看对应的值有没有>=valueListGroupNum,如果有，把这个值改为0
			selectGroup = 0;
		}
	}

	public static ContentChooseCodeElement restoreByJSONObject(JSONObject jSONObject) {
		ContentChooseCodeElement contentChooseCodeElement = JSON.toJavaObject(jSONObject, ContentChooseCodeElement.class);
		return contentChooseCodeElement;
	}

}
