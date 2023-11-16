package com.lazycoder.uicodegeneration.proj.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOpratingPaneElement;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.AbstractLabelMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CodeInputMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.ConstantMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.ContentChooseMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CorrespondingAdditionalDefaultFileMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CustomMethodMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CustomVariableMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.FileSelectorMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.FunctionAddMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.InfrequentlyUsedSettingMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.MethodChooseMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.NoteMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.PictureMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.StringMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.TextInputMeta;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.VariableMeta;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OperatingPaneElementDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<AbstractOpratingPaneElement> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<AbstractOpratingPaneElement> paneElementList = new ArrayList<>();
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		String paneType, labelType;
		StringMeta stringMeta;
		for (JSONObject base : jsonArray) {
			paneType = AbstractOpratingPaneElement.getPaneElementType(base);
			if (AbstractOpratingPaneElement.STRING_ELEMENT.equals(paneType)) {
				stringMeta = base.toJavaObject(StringMeta.class);
				paneElementList.add(stringMeta);

			} else if (AbstractOpratingPaneElement.LABEL_ELEMENT.equals(paneType)) {
				labelType = AbstractLabelMeta.getLableType(base);
				if (LabelElementName.TEXT_INPUT.equals(labelType)) {
					TextInputMeta meta = base.toJavaObject(TextInputMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
					ContentChooseMeta meta = base.toJavaObject(ContentChooseMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
					FunctionAddMeta meta = base.toJavaObject(FunctionAddMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
					CustomVariableMeta meta = base.toJavaObject(CustomVariableMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.VARIABLE.equals(labelType)) {
					VariableMeta meta = base.toJavaObject(VariableMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.CONSTANT.equals(labelType)) {
					ConstantMeta meta = base.toJavaObject(ConstantMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
					FileSelectorMeta meta = base.toJavaObject(FileSelectorMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.NOTE.equals(labelType)) {
					NoteMeta meta = base.toJavaObject(NoteMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.PICTURE.equals(labelType)) {
					PictureMeta meta = base.toJavaObject(PictureMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.CODE_INPUT.equals(labelType)) {
					CodeInputMeta meta = base.toJavaObject(CodeInputMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.INFREQUENTLY_USED_SETTING.equals(labelType)) {
					InfrequentlyUsedSettingMeta meta = base.toJavaObject(InfrequentlyUsedSettingMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
					CustomMethodMeta meta = base.toJavaObject(CustomMethodMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
					MethodChooseMeta meta = base.toJavaObject(MethodChooseMeta.class);
					paneElementList.add(meta);

				} else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
					CorrespondingAdditionalDefaultFileMeta meta = base.toJavaObject(CorrespondingAdditionalDefaultFileMeta.class);
					paneElementList.add(meta);
				}
			}
		}
		return paneElementList;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
