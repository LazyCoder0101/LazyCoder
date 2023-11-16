package com.lazycoder.uicodegeneration.component.operation.container.sendparam;

import com.lazycoder.database.model.ModuleInfo;
import lombok.Getter;
import lombok.Setter;

public class InitOperatingContainerParam extends GeneralOperatingContainerParam {

	/**
	 * 模块信息
	 */
	@Getter
	@Setter
	private ModuleInfo moduleInfo = null;

	public InitOperatingContainerParam() {
		// TODO Auto-generated constructor stub
	}


}
