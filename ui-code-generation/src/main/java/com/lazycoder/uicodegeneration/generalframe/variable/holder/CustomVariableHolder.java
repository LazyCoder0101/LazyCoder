package com.lazycoder.uicodegeneration.generalframe.variable.holder;

import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomVariableHolder extends AbstractVariableHolder {

	public void delCustomVariable(int variableId) {
		AbstractVariable variable;
		for (int i = 0; i < variableList.size(); i++) {
			variable = variableList.get(i);
			if (variable.getVariableId() == variableId) {
				variableList.remove(i);
			}
		}
	}

}
