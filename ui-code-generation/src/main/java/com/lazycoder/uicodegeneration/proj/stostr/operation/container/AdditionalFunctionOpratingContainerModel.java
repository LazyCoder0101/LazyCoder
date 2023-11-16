package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.lazycoder.service.vo.meta.AdditionalFunctionMetaModel;
import lombok.Data;

@Data
public class AdditionalFunctionOpratingContainerModel extends AbstractCommandOperatingContainerModel {

	private int additionalSerialNumber = 0;

	private AdditionalFunctionMetaModel metaModel = null;

	public AdditionalFunctionOpratingContainerModel() {
		// TODO Auto-generated constructor stub
		super(ADDITIONAL_FUNCTION_OPERATING_CONTAINER);
	}

}
