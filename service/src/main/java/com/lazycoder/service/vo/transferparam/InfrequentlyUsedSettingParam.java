package com.lazycoder.service.vo.transferparam;

import com.lazycoder.service.vo.BasePane;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InfrequentlyUsedSettingParam {

	private String paneType;

	private String className = "";

	private String moduleName = "";

	private String moduleId = "";

	private BasePane thisPane = null;

	/**
	 * 可选模板序号（只有可选模板才用）
	 */
	private int additionalSerialNumber = 0;

}
