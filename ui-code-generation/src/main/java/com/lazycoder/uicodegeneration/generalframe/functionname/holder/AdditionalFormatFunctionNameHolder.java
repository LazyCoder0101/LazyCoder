package com.lazycoder.uicodegeneration.generalframe.functionname.holder;

import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalFormatFunctionNameHolder extends AbstractFormatFunctionNameHolder {

	@Getter
	private int additionalSerialNumber = 0;

	private String additionalClassName = "";

	public AdditionalFormatFunctionNameHolder(int additionalSerialNumber) {
		super();
		this.additionalSerialNumber = additionalSerialNumber;
		this.additionalClassName = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalType(additionalSerialNumber);
	}

	@Override
	public void addFunctionName(AbstractMethodName functionName) {
		String toolTipText = "(￣.￣)  这是在\"" + additionalClassName + "\"" + "原本就有的";
		functionName.setToolTipTextOfHaveValue(toolTipText);
		super.addFunctionName(functionName);
	}


}
