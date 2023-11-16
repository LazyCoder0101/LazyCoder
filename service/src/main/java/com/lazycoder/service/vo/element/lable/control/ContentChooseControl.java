package com.lazycoder.service.vo.element.lable.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import java.util.ArrayList;
import lombok.Data;

/**
 * 选择框元素
 *
 * @author Administrator
 */
@Data
@JSONType(orders = {"type", "labelType", "thisName", "optionId", "useNumbered", "selectList"})
public class ContentChooseControl extends ContentChooseElement {

	/**
	 * 选择默认序号
	 */
	@JSONField(ordinal = 6)
	private ArrayList<Integer> selectList = new ArrayList<Integer>();

	public ContentChooseControl(String optionId,String optionName){
		super(optionId,optionName);
	}

	public ContentChooseControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public ContentChooseElement controlComponentInformation() {
		ContentChooseElement element = new ContentChooseElement();
		element.setThisName(getThisName());
		element.setOptionId(getOptionId());
		element.setUseNumbered(getUseNumbered());
		element.setOptionName(this.getOptionName());
		return element;
	}

	/**
	 * 更改选项设置参数
	 *
	 * @param option
	 */
	public void updateParam(OptionDataModel option) {
		if (OptionDataModel.EXECLUSIVE == option.getOptionType()) {//单选
			for (int i = 0; i < selectList.size(); i++) {//看看对应的选值有没有>=valueNum,如果有，把这个值改为0
				int selectTemp = selectList.get(i);
				if (selectTemp >= option.getValueNum()) {
					selectList.set(i, 0);
				}
			}
		} else if (OptionDataModel.MULTIPLE == option.getOptionType()) {//多选
			for (int i = 0; i < selectList.size(); i++) {//看看对应的选值有没有>=valueNum,如果有，把这个值去掉
				int selectTemp = selectList.get(i);
				if (selectTemp >= option.getValueNum()) {
					selectList.remove(i);
					i = -1;
				}
			}
		}
	}

	public static ContentChooseControl restoreByJSONObject(JSONObject jSONObject) {
		ContentChooseControl contentChooseControl = JSON.toJavaObject(jSONObject, ContentChooseControl.class);
		return contentChooseControl;
	}


}
