package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;


import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.InfrequentlyUsedSettingLabel;

public class InfrequentlyUsedSettingCodeLabel extends InfrequentlyUsedSettingLabel implements CodeLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 725107038108893977L;

	private InfrequentlyUsedSettingControl code = new InfrequentlyUsedSettingControl();

	@Override
	public void setName(String name) {
		super.setName(name);
		code.setThisName(name);
	}

	private void init(String name) {
		setText(name);
		setName(name);
	}

	@Override
	public InfrequentlyUsedSettingControl property() {
		// TODO Auto-generated method stub
		return code;
	}

}