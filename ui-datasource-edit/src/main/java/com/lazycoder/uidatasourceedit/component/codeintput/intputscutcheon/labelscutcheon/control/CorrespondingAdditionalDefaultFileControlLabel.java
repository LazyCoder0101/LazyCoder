package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.service.vo.element.lable.control.CorrespondingAdditionalDefaultFileControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.CorrespondingAdditionalDefaultFileLabel;
import com.lazycoder.utils.JsonUtil;
import lombok.Setter;

public class CorrespondingAdditionalDefaultFileControlLabel extends CorrespondingAdditionalDefaultFileLabel implements ControlLabelInterface {

	@Setter
	private CorrespondingAdditionalDefaultFileControl controlElement = new CorrespondingAdditionalDefaultFileControl();



	/**
	 * 新建
	 *
	 * @param name
	 */
	public CorrespondingAdditionalDefaultFileControlLabel(String name) {
		// TODO Auto-generated constructor stub
		super();
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param elementJSONObject
	 */
	public CorrespondingAdditionalDefaultFileControlLabel(JSONObject elementJSONObject) {
		// TODO Auto-generated constructor stub
		super();
		this.controlElement = JsonUtil.restoreByJSONObject(elementJSONObject, CorrespondingAdditionalDefaultFileControl.class);
		init(controlElement.getThisName());
	}

	/**
	 * 还原
	 *
	 * @param controlElement
	 */
	public CorrespondingAdditionalDefaultFileControlLabel(CorrespondingAdditionalDefaultFileControl controlElement) {
		// TODO Auto-generated constructor stub
		super();
		this.controlElement = controlElement;
		init(controlElement.getThisName());
	}

	public void init(String name) {
		setName(name);
		setText(name);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		controlElement.setThisName(name);
	}

	@Override
	public CorrespondingAdditionalDefaultFileControl property() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	@Override
	public CorrespondingAdditionalDefaultFileControl getControl() {
		return controlElement;
	}


	@Override
	public void deleteFromPanel() {

	}//setControlElement

}
