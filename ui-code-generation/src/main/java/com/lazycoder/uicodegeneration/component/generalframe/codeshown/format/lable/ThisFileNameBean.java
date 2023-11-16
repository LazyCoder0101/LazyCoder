package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.code.ThisFileNameCodeElement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.CodeGenerateBeanInterface;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.LabelBean;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;

public class ThisFileNameBean extends ThisFileNameCodeElement implements LabelBean, CodeGenerateBeanInterface {

	@Getter
	@Setter
	private String beanValue = "";

	public ThisFileNameBean() {
		// TODO Auto-generated constructor stub
		super();
	}

	public static ThisFileNameBean restoreByJson(JSONObject elementJSONObject) {
		return JsonUtil.restoreByJSONObject(elementJSONObject, ThisFileNameBean.class);
	}

	@JSONField(deserialize = false, serialize = false)
	@Override
	public String getTheBeanValue() {
		// TODO Auto-generated method stub
		return beanValue;
	}

	@Override
	public void updateBeanValue(Object updateParam) {
		// TODO Auto-generated method stub
		if (updateParam instanceof String) {
			if (getHaveSuffixOrNot() == FALSE_) {
				this.beanValue = FileUtil.getFileNameNoEx((String) updateParam);
			} else {
				this.beanValue = (String) updateParam;
			}
		}
	}

	/**
	 * 无需用此方法
	 */
	@Override
	public void updateBeanValue(BaseLableElement opratingLabel, Object updateParam) {
		// TODO Auto-generated method stub
		if (updateParam instanceof String) {
			if (getHaveSuffixOrNot() == FALSE_) {
				this.beanValue = FileUtil.getFileNameNoEx((String) updateParam);
			} else {
				this.beanValue = (String) updateParam;
			}
		}
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
