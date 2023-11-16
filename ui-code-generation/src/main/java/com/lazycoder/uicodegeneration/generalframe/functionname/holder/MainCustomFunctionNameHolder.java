package com.lazycoder.uicodegeneration.generalframe.functionname.holder;


import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import com.lazycoder.uicodegeneration.generalframe.operation.MainFormatControlPane;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainCustomFunctionNameHolder extends CustomFunctionNameHolder {

	@JSONField(deserialize = false, serialize = false)
	private MainFormatControlPane formatControlPane;

	public MainCustomFunctionNameHolder(MainFormatControlPane formatControlPane) {
		super();
		this.formatControlPane = formatControlPane;
	}

	@Override
	public void addFunctionName(AbstractMethodName functionName) {
		String toolTipTextOfHaveValue = "(￣.￣)  这是在\"" + formatControlPane.getFileName() + "\"" + "添加的";
		functionName.setToolTipTextOfHaveValue(toolTipTextOfHaveValue);

		String toolTipTextOfHaveNotValue = "(￣ェ￣;)  看一下\"" + formatControlPane.getFileName() + "\"顶部那里，还有内容没有填写呢";
		functionName.setToolTipTextOfHaveNotValue(toolTipTextOfHaveNotValue);

		super.addFunctionName(functionName);
	}


}
