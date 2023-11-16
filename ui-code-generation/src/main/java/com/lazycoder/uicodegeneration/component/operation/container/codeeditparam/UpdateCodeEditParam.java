package com.lazycoder.uicodegeneration.component.operation.container.codeeditparam;

import com.lazycoder.service.vo.element.lable.BaseLableElement;
import lombok.Data;

@Data
public class UpdateCodeEditParam extends CodeEditParam {

	/**
	 * 组件值
	 */
	private Object updateParam;

	private BaseLableElement opratingLabel;

	/**
	 * 这次要进行更改的所在标记，或者功能拓展组件， 其对应的代码id编号
	 */
//	private String codeLabelId = null;

	public UpdateCodeEditParam() {
		// TODO Auto-generated constructor stub
		super();
	}


}
