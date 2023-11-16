package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.control.CodeInputControl;
import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.service.vo.element.lable.control.FileSelectorControl;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.service.vo.element.lable.control.NoteControl;
import com.lazycoder.service.vo.element.lable.control.PictureControl;
import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.service.vo.element.lable.control.VariableControl;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ControlLableElementListDeserializer implements ObjectDeserializer {


	protected static void deserializeControlLable(ArrayList<BaseElementInterface> list, String labelType, JSONObject labelJSONObject) {
		ContentChooseControl contentChooseControlTemp;
		BaseLableElement temp = null;
		if (LabelElementName.TEXT_INPUT.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, TextInputControl.class);

		} else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, ContentChooseControl.class);

		} else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, FunctionAddControl.class);

		} else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomVariableControl.class);

		} else if (LabelElementName.VARIABLE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, VariableControl.class);

		} else if (LabelElementName.CONSTANT.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, ConstantControl.class);

		} else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, FileSelectorControl.class);

		} else if (LabelElementName.NOTE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, NoteControl.class);

		} else if (LabelElementName.PICTURE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, PictureControl.class);

		} else if (LabelElementName.CODE_INPUT.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, CodeInputControl.class);

		} else if (LabelElementName.INFREQUENTLY_USED_SETTING.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, InfrequentlyUsedSettingControl.class);

		} else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomMethodNameControl.class);

		} else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
			temp = JsonUtil.restoreByJSONObject(labelJSONObject, MethodChooseControl.class);
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
				deserializeControlLable(out, labelType, base);
			}
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
