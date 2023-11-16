package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput;

import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractCodeControlPaneModel;
import lombok.Data;

@Data
public class ModuleTypeContainerModel extends AbstractCodeControlPaneModel {

	private String moduleSetType = null;

	private String moduleId = null;

	public ModuleTypeContainerModel() {
		// TODO Auto-generated constructor stub
		setCodeControlPaneType(MODULE_TYPE_CODE_CONTROL_PANE);
	}

}
