package com.lazycoder.uicodegeneration.generalframe.variable.holder;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.generalframe.operation.MainFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainCustomVariableHolder extends CustomVariableHolder {

	@JSONField(deserialize = false, serialize = false)
	private MainFormatControlPane formatControlPane;

	public MainCustomVariableHolder(MainFormatControlPane formatControlPane) {
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
