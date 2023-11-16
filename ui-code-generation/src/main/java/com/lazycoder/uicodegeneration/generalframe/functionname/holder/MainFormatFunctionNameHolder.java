package com.lazycoder.uicodegeneration.generalframe.functionname.holder;

import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainFormatFunctionNameHolder extends AbstractFormatFunctionNameHolder {

	private String flagName;

	/**
	 * @return
	 */
	public MainFormatFunctionNameHolder(String flagName) {
		super();
		this.flagName = flagName;
	}

	@Override
	public void addFunctionName(AbstractMethodName functionName) {
		String toolTipText = "(￣.￣)  这是在\"" + flagName + "\"" + "原本就有的";
		functionName.setToolTipTextOfHaveValue(toolTipText);
		super.addFunctionName(functionName);
	}

}
