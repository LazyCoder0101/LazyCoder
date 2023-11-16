package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.meta.MainSetMetaModel;
import com.lazycoder.service.vo.meta.deserializer.MainSetMetaModelDeserializer;
import lombok.Data;

@Data
public class MainSetOpratingContainerModel extends AbstractCommandOperatingContainerModel {

	@JSONField(deserializeUsing = MainSetMetaModelDeserializer.class)
	private MainSetMetaModel metaModel;

	private String mainSetTypeName;

	public MainSetOpratingContainerModel() {
		// TODO Auto-generated constructor stub
		super(MAIN_SET_OPERATING_CONTAINER);
	}

}
