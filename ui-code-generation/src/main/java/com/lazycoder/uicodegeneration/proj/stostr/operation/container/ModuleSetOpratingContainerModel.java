package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.vo.meta.ModuleSetMetaModel;
import com.lazycoder.service.vo.meta.deserializer.ModuleSetMetaModelDeserializer;
import lombok.Data;

@Data
public class ModuleSetOpratingContainerModel extends AbstractCommandOperatingContainerModel {

	@JSONField(deserializeUsing = ModuleSetMetaModelDeserializer.class)
	private ModuleSetMetaModel metaModel;

	private Module module = null;

	public ModuleSetOpratingContainerModel() {
		// TODO Auto-generated constructor stub
		super(MODULE_SET_OPERATING_CONTAINER);
	}

}
