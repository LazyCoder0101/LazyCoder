package com.lazycoder.uicodegeneration.generalframe.variable.holder;

import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalFormatVariableHolder extends AbstractFormatVariableHolder {

	@Getter
	private int additionalSerialNumber = 0;

	private String additionalClassName = "";

	public AdditionalFormatVariableHolder(int additionalSerialNumber) {
		super();
		this.additionalSerialNumber = additionalSerialNumber;
		AdditionalOperating temp = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalOperating(additionalSerialNumber);
		this.additionalClassName = temp.getTypeName();
	}

	@Override
	public void addVariable(AbstractVariable variable) {
		String toolTipText = "(￣.￣)  \"" + ((FormatVariable) variable).getRoleOfVariableName() + "\"是在\"" + additionalClassName + "\"" + "原本就有的";
		variable.setToolTipTextOfHaveValue(toolTipText);
		super.addVariable(variable);
	}


}
