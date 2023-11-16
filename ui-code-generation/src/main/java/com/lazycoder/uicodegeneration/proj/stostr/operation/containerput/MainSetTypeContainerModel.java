package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput;

import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractCodeControlPaneModel;
import lombok.Data;

@Data
public class MainSetTypeContainerModel extends AbstractCodeControlPaneModel {

	private String mainSetTypeName = "";

	public MainSetTypeContainerModel() {
		// TODO Auto-generated constructor stub
		setCodeControlPaneType(MAIN_TYPE_CODE_CONTROL_PANE);
	}

}
