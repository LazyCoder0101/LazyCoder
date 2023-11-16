package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.code;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.AbstractFunctionCodeInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.CodeCondition;

public class AdditionalFunctionCodePane extends AbstractFunctionCodeInputPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -4486790934390270634L;

	public AdditionalFunctionCodePane(int AdditionalSerialNumber, ContainerModel model) {
		super(AdditionalSerialNumber, model);
	}

	@Override
	protected void menuInit() {
		CodeCondition codeCondition = new CodeCondition(true, false);
		menuInit(codeCondition);
	}

}
