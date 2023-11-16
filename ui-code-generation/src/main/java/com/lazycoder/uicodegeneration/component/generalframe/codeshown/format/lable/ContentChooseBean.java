package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.code.ContentChooseCodeElement;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.CodeGenerateBeanInterface;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.LabelBean;
import com.lazycoder.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;


public class ContentChooseBean extends ContentChooseCodeElement implements LabelBean, CodeGenerateBeanInterface {

	@Getter
	@Setter
	@JSONField(ordinal = 7)
	private String beanValue = "";

	public ContentChooseBean() {
		// TODO Auto-generated constructor stub
		super();
	}

	public static ContentChooseBean restoreByJson(JSONObject elementJSONObject) {
		ContentChooseBean contentChooseBean = JsonUtil.restoreByJSONObject(elementJSONObject, ContentChooseBean.class);
		return contentChooseBean;
	}

	@JSONField(deserialize = false, serialize = false)
	@Override
	public String getTheBeanValue() {
		// TODO Auto-generated method stub
		return beanValue;
	}



	@JSONField(deserialize = false, serialize = false)
	@Override
	public void updateBeanValue(Object updateParam) {
		// TODO Auto-generated method stub
//		if (TheOptionModel.execlusive == getOptionType()) {
//			this.beanValue = (String) updateParam;
//		} else if (TheOptionModel.multiple == getOptionType()) {
		String[] valueList = (String[]) updateParam;

		int groupNum = SysService.OPTION_SERVICE.getValueListGroupNumById(getOptionId());
		if (groupNum > getSelectGroup()) {
			this.beanValue = valueList[getSelectGroup()];
		} else {
			this.beanValue = valueList[0];

			String text = "这程序可能运行不了了，对应数据源和它对不上，\"" + getOptionName() + "\"这个选项只有" + groupNum + "个组可以选择，可这有一处代码却默认选第" + (getSelectGroup() + 1) + "组";
			String logtext = getClass() + "（生成程序异常）————\"" + getOptionName() + "\"这个选项，当前有" + groupNum + "个选项值，代码里面有个内容选择标签却默认选第" + (getSelectGroup() + 1) + "组";
			CodeGenerationFrameHolder.errorLogging(text, logtext);
		}
//		}
	}

	@JSONField(deserialize = false, serialize = false)
	@Override
	public void updateBeanValue(BaseLableElement opratingLabel, Object updateParam) {
		// TODO Auto-generated method stub
//		if (TheOptionModel.execlusive == getOptionType()) {
//			this.beanValue = (String) updateParam;

//		} else if (TheOptionModel.multiple == getOptionType()) {
		String[] valueList = (String[]) updateParam;

		int groupNum = SysService.OPTION_SERVICE.getValueListGroupNum(getOptionId());
		if (groupNum > getSelectGroup()) {
			this.beanValue = valueList[getSelectGroup()];
		} else {
			this.beanValue = valueList[0];

			String text = "这程序可能运行不了了，对应数据源和它对不上，\"" + getOptionName() + "\"这个选项只有" + groupNum + "个组可以选择，可这有一处代码却默认选第" + (getSelectGroup() + 1) + "组";
			String logtext = getClass() + "（生成程序异常）————\"" + getOptionName() + "\"这个选项，当前有" + groupNum + "个选项值，代码里面有个内容选择标签却默认选第" + (getSelectGroup() + 1) + "组";
			CodeGenerationFrameHolder.errorLogging(text, logtext);
		}
//		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return super.getType();
	}

	@JSONField(deserialize = false, serialize = false)
	@Override
	public int getValueLength() {
		// TODO Auto-generated method stub
		return beanValue.length();
	}

}