package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.vo.element.mark.AdditionalSetMarkElement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeList;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.AbstractMarkBean;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

@Data
public class AdditionalSetMarkBean extends AbstractMarkBean {

	public AdditionalSetMarkBean() {
		// TODO Auto-generated constructor stub
		super();
		setMarkType(MarkElementName.ADDITIONAL_SET_TYPE_MARK);
	}

	public AdditionalSetMarkBean(JSONObject elementJSONObject) {
		// TODO Auto-generated constructor stub
		this();
		setMarkElement(AdditionalSetMarkElement.jsonParsing(elementJSONObject));
	}

	/**
	 * 根据 JSONObject 还原
	 *
	 * @param markBeanJSONObject
	 * @return
	 */
	public static AdditionalSetMarkBean restoreByJSONObject(JSONObject markBeanJSONObject) {
		AdditionalSetMarkBean markBean = JsonUtil.restoreByJSONObject(markBeanJSONObject, AdditionalSetMarkBean.class);
		JSONObject codeListJSONObject = getCodeListJSONObject(markBeanJSONObject),
				markElementJSONObject = getMarkElementJSONObject(markBeanJSONObject);
		CodeList theCodeList = CodeList.restoreByJSONObject(codeListJSONObject);
		markBean.setCodeList(theCodeList);
		AdditionalSetMarkElement markElement = AdditionalSetMarkElement.jsonParsing(markElementJSONObject);
		markBean.setMarkElement(markElement);
		return markBean;
	}

	@Override
	public AdditionalSetMarkElement getMarkElement() {
		return (AdditionalSetMarkElement) markElement;
	}


}
