package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.vo.element.mark.MainSetMarkElement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeList;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.AbstractMarkBean;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;

@Data
public class MainSetMarkBean extends AbstractMarkBean {

	public MainSetMarkBean() {
		// TODO Auto-generated constructor stub
		super();
		setMarkType(MarkElementName.MAIN_SET_TYPE_MARK);
	}

	public MainSetMarkBean(JSONObject elementJSONObject) {
		// TODO Auto-generated constructor stub
		this();
		setMarkElement(MainSetMarkElement.jsonParsing(elementJSONObject));
	}

	/**
	 * 根据 JSONObject 还原
	 *
	 * @param markBeanJSONObject
	 * @return
	 */
	public static MainSetMarkBean restoreByJSONObject(JSONObject markBeanJSONObject) {
		MainSetMarkBean markBean = JsonUtil.restoreByJSONObject(markBeanJSONObject, MainSetMarkBean.class);
		JSONObject codeListJSONObject = getCodeListJSONObject(markBeanJSONObject),
				markElementJSONObject = getMarkElementJSONObject(markBeanJSONObject);
		CodeList theCodeList = CodeList.restoreByJSONObject(codeListJSONObject);
		markBean.setCodeList(theCodeList);
		MainSetMarkElement markElement = MainSetMarkElement.jsonParsing(markElementJSONObject);
		markBean.setMarkElement(markElement);
		return markBean;
	}

	@Override
	public MainSetMarkElement getMarkElement() {
		return (MainSetMarkElement) markElement;
	}

}
