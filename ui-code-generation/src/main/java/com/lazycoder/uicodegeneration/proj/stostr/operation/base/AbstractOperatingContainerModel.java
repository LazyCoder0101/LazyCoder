package com.lazycoder.uicodegeneration.proj.stostr.operation.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParamDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.CustomFunctionNameHolderDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.CustomVariableHolderDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uicodegeneration.proj.deserializer.OperatingPaneElementDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractOperatingContainerModel extends BaseFormatStructureModel {

	public static final String COMMAND_CONTAINER_TYPE = "commandContainerType";

	public static final String FORMAT_CONTAINER_TYPE = "formatContainerType";

	public static final String INFREQUENTLY_USED_SETTING_OPERATION_PANE_TPYE = "infrequentlyUsedSettingOperationPaneTpye";

	private String containerType = "";

	@JSONField(deserializeUsing = OperatingPaneElementDeserializer.class)
	private ArrayList<AbstractOpratingPaneElement> deafaultPaneElementList = new ArrayList<>();

	@JSONField(deserializeUsing = CustomVariableHolderDeserializer.class)
	private CustomVariableHolder customVariableHolder = new CustomVariableHolder();

	@JSONField(deserializeUsing = CustomFunctionNameHolderDeserializer.class)
	private CustomFunctionNameHolder customFunctionNameHolder = new CustomFunctionNameHolder();

	@JSONField(deserializeUsing = ContainerGenerateCodeResponseParamDeserializer.class)
	private ContainerGenerateCodeResponseParam thisCodeLocationInformation = new ContainerGenerateCodeResponseParam();

	public AbstractOperatingContainerModel(String containerType) {
		// TODO Auto-generated constructor stub
		this.containerType = containerType;
	}

	/**
	 * 获取容器类型类型
	 *
	 * @param elementJSONObject
	 * @return
	 */
	public static String getOperatingContainerType(JSONObject elementJSONObject) {
		return elementJSONObject.getString("containerType");
	}

}
