package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.code.CodeInputCodeElement;
import com.lazycoder.service.vo.element.lable.code.ConstantCodeElement;
import com.lazycoder.service.vo.element.lable.code.ContentChooseCodeElement;
import com.lazycoder.service.vo.element.lable.code.CustomMethodNameCodeElement;
import com.lazycoder.service.vo.element.lable.code.CustomVariableCodeElement;
import com.lazycoder.service.vo.element.lable.code.FileSelectorCodeElement;
import com.lazycoder.service.vo.element.lable.code.FunctionAddCodeElement;
import com.lazycoder.service.vo.element.lable.code.MethodChooseCodeElement;
import com.lazycoder.service.vo.element.lable.code.TextInputCodeElement;
import com.lazycoder.service.vo.element.lable.code.VariableCodeElement;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CodeLableElementListDeserializer implements ObjectDeserializer {


	protected static void deserializeCodeLable(ArrayList<BaseElementInterface> list, String labelType, JSONObject labelJSONObject) {
		ContentChooseCodeElement contentChooseCodeElementTemp;
		BaseLableElement temp = null;
		if (LabelElementName.TEXT_INPUT.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, TextInputCodeElement.class);

		} else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, ContentChooseCodeElement.class);

		} else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, FunctionAddCodeElement.class);

		} else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomVariableCodeElement.class);

		} else if (LabelElementName.VARIABLE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, VariableCodeElement.class);

		} else if (LabelElementName.CONSTANT.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, ConstantCodeElement.class);

		} else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, FileSelectorCodeElement.class);

		} else if (LabelElementName.CODE_INPUT.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, CodeInputCodeElement.class);

		} else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomMethodNameCodeElement.class);

		} else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, MethodChooseCodeElement.class);
		}
		if (temp != null) {
			list.add(temp);
		}
	}

	@Override
	public ArrayList<BaseElementInterface> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<BaseElementInterface> out = new ArrayList<>();
		String elementType, labelType;
		List<JSONObject> baseList = parser.parseArray(JSONObject.class);
		for (JSONObject base : baseList) {
			elementType = BaseElement.getElementType(base);
			if (ElementName.LABEL_ELEMENT.equals(elementType)) {
				labelType = BaseLableElement.getLabelType(base);
				deserializeCodeLable(out, labelType, base);
			}
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
