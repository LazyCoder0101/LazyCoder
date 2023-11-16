package com.lazycoder.uicodegeneration.component.operation.container.codeeditparam;

import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import lombok.Data;

@Data
public class ReplaceCodeEditParamForMark extends CodeEditParam {

	/**
	 * 代码格式参数
	 */
	private String codeStatementParam;

	/**
	 * 对比标记
	 */
	private BaseMarkElement thanMarkElement;

	public ReplaceCodeEditParamForMark() {
		// TODO Auto-generated constructor stub
		super();
	}

}
