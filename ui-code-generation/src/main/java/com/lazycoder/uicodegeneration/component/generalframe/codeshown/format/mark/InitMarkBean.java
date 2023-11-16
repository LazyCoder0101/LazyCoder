package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeList;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.AbstractMarkBean;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

@Data
public class InitMarkBean extends AbstractMarkBean {


	public InitMarkBean() {
		// TODO Auto-generated constructor stub
		super();
		setMarkType(MarkElementName.INIT_MARK);
	}


	public InitMarkBean(JSONObject elementJSONObject) {
		// TODO Auto-generated constructor stub
		this();
		setMarkElement(InitMarkElement.jsonParsing(elementJSONObject));
	}

	/**
	 * 根据 JSONObject 还原
	 *
	 * @param markBeanJSONObject
	 * @return
	 */
	public static InitMarkBean restoreByJSONObject(JSONObject markBeanJSONObject) {
		InitMarkBean markBean = JsonUtil.restoreByJSONObject(markBeanJSONObject, InitMarkBean.class);
		JSONObject codeListJSONObject = getCodeListJSONObject(markBeanJSONObject),
				markElementJSONObject = getMarkElementJSONObject(markBeanJSONObject);
		CodeList theCodeList = CodeList.restoreByJSONObject(codeListJSONObject);
		markBean.setCodeList(theCodeList);
		InitMarkElement markElement = InitMarkElement.jsonParsing(markElementJSONObject);
		markBean.setMarkElement(markElement);
		return markBean;
	}

	@Override
	public InitMarkElement getMarkElement() {
		return (InitMarkElement) markElement;
	}

}
