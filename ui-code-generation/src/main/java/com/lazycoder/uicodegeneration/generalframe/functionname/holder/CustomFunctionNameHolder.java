package com.lazycoder.uicodegeneration.generalframe.functionname.holder;

import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomFunctionNameHolder extends AbstractFunctionNameHolder {

	public void delCustomFunctionName(int functionNameId) {
		AbstractMethodName functionName;
		for (int i = 0; i < functionNameList.size(); i++) {
			functionName = functionNameList.get(i);
			if (functionName.getFunctionNameId() == functionNameId) {
				functionNameList.remove(i);
			}
		}
	}

}
