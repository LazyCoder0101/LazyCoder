package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormatDeserializer;
import lombok.Data;

@Data
public class FunctionAddInternalOpratingContainerModel extends AbstractCommandOperatingContainerModel {

	@JSONField(deserializeUsing = MethodAddStorageFormatDeserializer.class)
	private MethodAddStorageFormat methodAddStorageFormat=null;

	public FunctionAddInternalOpratingContainerModel() {
		// TODO Auto-generated constructor stub
		super(FUNCTION_ADD_INTERNAL_OPERATING_CONTAINER);
	}

}
