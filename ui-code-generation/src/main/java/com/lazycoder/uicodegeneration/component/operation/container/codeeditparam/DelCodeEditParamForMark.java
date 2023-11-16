package com.lazycoder.uicodegeneration.component.operation.container.codeeditparam;

import com.lazycoder.service.vo.element.mark.BaseMarkElement;

public class DelCodeEditParamForMark extends DelCodeEditParam {

	/**
	 * 对比标记
	 */
	private BaseMarkElement thanMarkElement;

	public DelCodeEditParamForMark() {
		// TODO Auto-generated constructor stub
		super();
	}

	public BaseMarkElement getThanMarkElement() {
		return thanMarkElement;
	}

	public void setThanMarkElement(BaseMarkElement thanMarkElement) {
		this.thanMarkElement = thanMarkElement;
	}

}
