package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.vo.element.mark.SetMarkElement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeList;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.AbstractMarkBean;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

@Data
public class SetMarkBean extends AbstractMarkBean {

	public SetMarkBean() {
		// TODO Auto-generated constructor stub
		super();
		setMarkType(MarkElementName.SET_MARK);
	}

	public SetMarkBean(JSONObject elementJSONObject) {
		// TODO Auto-generated constructor stub
		this();
		setMarkElement(SetMarkElement.jsonParsing(elementJSONObject));
	}

	/**
	 * 根据 JSONObject 还原
	 *
	 * @param markBeanJSONObject
	 * @return
	 */
	public static SetMarkBean restoreByJSONObject(JSONObject markBeanJSONObject) {
		SetMarkBean markBean = JsonUtil.restoreByJSONObject(markBeanJSONObject, SetMarkBean.class);
		JSONObject codeListJSONObject = getCodeListJSONObject(markBeanJSONObject),
				markElementJSONObject = getMarkElementJSONObject(markBeanJSONObject);
		CodeList theCodeList = CodeList.restoreByJSONObject(codeListJSONObject);
		markBean.setCodeList(theCodeList);
		SetMarkElement markElement = SetMarkElement.jsonParsing(markElementJSONObject);
		markBean.setMarkElement(markElement);
		return markBean;
	}

	@Override
	public SetMarkElement getMarkElement() {
		return (SetMarkElement) markElement;
	}

}
