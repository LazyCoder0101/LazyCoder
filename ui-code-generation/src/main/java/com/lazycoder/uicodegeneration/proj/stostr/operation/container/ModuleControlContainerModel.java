package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import lombok.Data;

@Data
public class ModuleControlContainerModel extends AbstractFormatOperatingContainerModel {

	private String className = null;

	private String moduleName = null;

	private String moduleId = null;

	public ModuleControlContainerModel() {
		// TODO Auto-generated constructor stub
		super(MODULE_CONTROL_CONTAINER);
	}

}
