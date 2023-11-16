package com.lazycoder.uicodegeneration.proj.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractFormatOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalFormatContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalFunctionOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AdditionalSetOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.FunctionAddInternalOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.FunctionOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.InitOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.MainFormatContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.MainSetOpratingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.ModuleControlContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.ModuleSetOpratingContainerModel;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneralCodeControlPaneModelDeserializer implements ObjectDeserializer {


	@Override
	public ArrayList<AbstractOperatingContainerModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
		ArrayList<AbstractOperatingContainerModel> out = new ArrayList<>();
		AbstractOperatingContainerModel opratingContainerModel;
		List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
		String containerType, comandType, formatType;
		for (JSONObject base : jsonArray) {
			containerType = AbstractOperatingContainerModel.getOperatingContainerType(base);
			if (AbstractOperatingContainerModel.COMMAND_CONTAINER_TYPE.equals(containerType)) {
				comandType = AbstractCommandOperatingContainerModel.getCommandType(base);

				if (AbstractCommandOperatingContainerModel.FUNCTION_OPERATING_CONTAINER.equals(comandType)) {
					opratingContainerModel = base.toJavaObject(FunctionOpratingContainerModel.class);
					out.add(opratingContainerModel);

				} else if (AbstractCommandOperatingContainerModel.MODULE_SET_OPERATING_CONTAINER.equals(comandType)) {
					opratingContainerModel = base.toJavaObject(ModuleSetOpratingContainerModel.class);
					out.add(opratingContainerModel);

				} else if (AbstractCommandOperatingContainerModel.INIT_OPERATING_CONTAINER.equals(comandType)) {
					opratingContainerModel = base.toJavaObject(InitOpratingContainerModel.class);
					out.add(opratingContainerModel);

				} else if (AbstractCommandOperatingContainerModel.FUNCTION_ADD_INTERNAL_OPERATING_CONTAINER.equals(comandType)) {
					opratingContainerModel = base.toJavaObject(FunctionAddInternalOpratingContainerModel.class);
					out.add(opratingContainerModel);

				} else if (AbstractCommandOperatingContainerModel.MAIN_SET_OPERATING_CONTAINER.equals(comandType)) {
					opratingContainerModel = base.toJavaObject(MainSetOpratingContainerModel.class);
					out.add(opratingContainerModel);

				} else if (AbstractCommandOperatingContainerModel.ADDITIONAL_SER_OPERATING_CONTAINER.equals(comandType)) {
					opratingContainerModel = base.toJavaObject(AdditionalSetOpratingContainerModel.class);
					out.add(opratingContainerModel);

				} else if (AbstractCommandOperatingContainerModel.ADDITIONAL_FUNCTION_OPERATING_CONTAINER.equals(comandType)) {
					opratingContainerModel = base.toJavaObject(AdditionalFunctionOpratingContainerModel.class);
					out.add(opratingContainerModel);
				}
			} else if (AbstractOperatingContainerModel.FORMAT_CONTAINER_TYPE.equals(containerType)) {
				formatType = AbstractFormatOperatingContainerModel.getFormatType(base);

				if (AbstractFormatOperatingContainerModel.MODULE_CONTROL_CONTAINER.equals(formatType)) {
					opratingContainerModel = base.toJavaObject(ModuleControlContainerModel.class);
					out.add(opratingContainerModel);

				} else if (AbstractFormatOperatingContainerModel.MAIN_FORMAT_OPERATING_CONTAINER.equals(formatType)) {
					opratingContainerModel = base.toJavaObject(MainFormatContainerModel.class);
					out.add(opratingContainerModel);

				} else if (AbstractFormatOperatingContainerModel.ADDITIONAL_FORMAT_OPERATING_CONTAINER.equals(formatType)) {
					opratingContainerModel = base.toJavaObject(AdditionalFormatContainerModel.class);
					out.add(opratingContainerModel);

				}
//            } else if (OperatingContainerModel.infrequentlyUsedSettingOperationPaneTpye.equals(containerType)) {
//                InfrequentlyUsedSettingOperationPaneModel opratingContainerModel = base.toJavaObject(InfrequentlyUsedSettingOperationPaneModel.class);
//                out.add(opratingContainerModel);
			}
		}
		return out;
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}


}
