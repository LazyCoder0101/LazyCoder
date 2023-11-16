package com.lazycoder.uicodegeneration.component.operation.container.codeeditparam;

import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import lombok.Data;

@Data
public class AddCodeEditParamForMark extends AddCodeEditParam {

	/**
	 * 对比标记
	 */
	private BaseMarkElement thanMarkElement;

	private boolean addToLast = true;    //是否添加到最后，默认一定为true，设为false的话，要设置 nextCodeSerialNumber,即要插入的位置，其下一个功能的代码的CodeSerialNumber

	private Integer nextCodeSerialNumber = null;//如果要添加在某句代码前面，设置此参数

	public AddCodeEditParamForMark() {
		// TODO Auto-generated constructor stub
		super();
	}


}
