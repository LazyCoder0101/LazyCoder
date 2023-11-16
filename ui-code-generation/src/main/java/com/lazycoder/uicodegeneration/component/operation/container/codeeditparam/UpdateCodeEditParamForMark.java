package com.lazycoder.uicodegeneration.component.operation.container.codeeditparam;

import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import lombok.Data;

@Data
public class UpdateCodeEditParamForMark extends UpdateCodeEditParam {

	/**
	 * 对比标记
	 */
	private BaseMarkElement thanMarkElement;

	public UpdateCodeEditParamForMark() {
		// TODO Auto-generated constructor stub
		super();
	}


}
