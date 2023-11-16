package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.deserializer;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.StringElement;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CodePaneElementListDeserializer extends CodeLableElementListDeserializer {

	@Override
	public ArrayList<BaseElementInterface> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<BaseElementInterface> out = new ArrayList<>();
		StringElement stringElement;
		String elementType, labelType;
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		for (JSONObject base : baseList) {
			elementType = BaseElement.getElementType(base);
			if (ElementName.STRING_ELEMENT.equals(elementType)) {
				stringElement = JsonUtil.restoreByJSONObject(base, StringElement.class);
				out.add(stringElement);

			} else if (ElementName.LABEL_ELEMENT.equals(elementType)) {
				labelType = BaseLableElement.getLabelType(base);
				deserializeCodeLable(out, labelType, base);
			}
		}
		return out;
	}


}
