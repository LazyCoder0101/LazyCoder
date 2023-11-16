package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.meta.AdditionalSetMetaModel;
import com.lazycoder.service.vo.meta.deserializer.AdditionalSetMetaModelDeserializer;
import lombok.Data;

@Data
public class AdditionalSetOpratingContainerModel extends AbstractCommandOperatingContainerModel {

	@JSONField(deserializeUsing = AdditionalSetMetaModelDeserializer.class)
	private AdditionalSetMetaModel metaModel;

	private String additionalSetTypeName;

	public AdditionalSetOpratingContainerModel() {
		// TODO Auto-generated constructor stub
		super(ADDITIONAL_SER_OPERATING_CONTAINER);
	}

}
