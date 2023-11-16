package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition;

import lombok.Data;

/**
 * 生成源码组件所需条件
 *
 * @author Administrator
 */
@Data
public class CodeCondition {

	/**
	 * 其它文件名组件的使用
	 */
	private boolean useOfAdditionalFileChooseComponent = false;

	private boolean useOfThisFileName = false;

	/**
	 * @param useOfAdditionalFileChooseComponent
	 * @param useOfThisFileName
	 */
	public CodeCondition(boolean useOfAdditionalFileChooseComponent, boolean useOfThisFileName) {
		// TODO Auto-generated constructor stub
		this.useOfAdditionalFileChooseComponent = useOfAdditionalFileChooseComponent;
		this.useOfThisFileName = useOfThisFileName;
	}

}
