package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeList;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.AbstractMarkBean;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

@Data
public class FunctionMarkBean extends AbstractMarkBean {

	public FunctionMarkBean(JSONObject elementJSONObject) {
		// TODO Auto-generated constructor stub
		this();
		setMarkElement(FunctionMarkElement.jsonParsing(elementJSONObject));
	}

	public FunctionMarkBean() {
		// TODO Auto-generated constructor stub
		super();
		setMarkType(MarkElementName.FUNCTION_MARK);
	}

	/**
	 * 根据 JSONObject 还原
	 *
	 * @param markBeanJSONObject
	 * @return
	 */
	public static FunctionMarkBean restoreByJSONObject(JSONObject markBeanJSONObject) {
		FunctionMarkBean markBean = JsonUtil.restoreByJSONObject(markBeanJSONObject, FunctionMarkBean.class);
		JSONObject codeListJSONObject = getCodeListJSONObject(markBeanJSONObject),
				markElementJSONObject = getMarkElementJSONObject(markBeanJSONObject);
		CodeList theCodeList = CodeList.restoreByJSONObject(codeListJSONObject);
		markBean.setCodeList(theCodeList);
		FunctionMarkElement markElement = FunctionMarkElement.jsonParsing(markElementJSONObject);
		markBean.setMarkElement(markElement);
		return markBean;
	}

	@Override
	public FunctionMarkElement getMarkElement() {
		return (FunctionMarkElement) markElement;
	}


}
