package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput;

import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractCodeControlPaneModel;
import lombok.Data;

@Data
public class AdditionalSetTypeContainerModel extends AbstractCodeControlPaneModel {

	private String additionalSetTypeName = "";

	public AdditionalSetTypeContainerModel() {
		// TODO Auto-generated constructor stub
		setCodeControlPaneType(ADDITIONAL_TYPE_CODE_CONTROL_PANE);
	}

}
