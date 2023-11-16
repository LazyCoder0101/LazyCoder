package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.ModuleControlContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.deserializer.ModuleControlContainerModelDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer.ModuleTypeContainerModelListDeserializer;
import java.util.ArrayList;
import lombok.Data;

@Data
public class SettingContainerModel extends AbstractCodeControlPaneModel {
	/**
	 * 该类的containerList废弃不用
	 */
	@JSONField(deserializeUsing = ModuleControlContainerModelDeserializer.class)
	private ModuleControlContainerModel moduleControlContainerModel;

	@JSONField(deserializeUsing = ModuleTypeContainerModelListDeserializer.class)
	private ArrayList<ModuleTypeContainerModel> moduleTypeContainerModelList = new ArrayList<>();

	public SettingContainerModel() {
		// TODO Auto-generated constructor stub
		setCodeControlPaneType(SETTING_CONTAINER);
	}

}
