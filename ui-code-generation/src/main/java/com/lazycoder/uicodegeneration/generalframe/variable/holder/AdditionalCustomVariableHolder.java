package com.lazycoder.uicodegeneration.generalframe.variable.holder;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import lombok.Data;

@Data
public class AdditionalCustomVariableHolder extends CustomVariableHolder {

	@JSONField(deserialize = false, serialize = false)
	private AdditionalFormatControlPane formatControlPane;

	public AdditionalCustomVariableHolder(AdditionalFormatControlPane formatControlPane) {
		super();
		this.formatControlPane = formatControlPane;
	}

	@Override
	public void addVariable(AbstractVariable variable) {
		String toolTipTextOfHaveValue = "(￣.￣)  这是在\"" + formatControlPane.getFileName() + "\"" + "添加的";
		variable.setToolTipTextOfHaveValue(toolTipTextOfHaveValue);

		String toolTipTextOfHaveNotValue = "(￣ェ￣;)  看一下\"" + formatControlPane.getFileName() + "\"顶部那里，还有内容没有填写呢";
		variable.setToolTipTextOfHaveValue(toolTipTextOfHaveNotValue);

		super.addVariable(variable);
	}


}
