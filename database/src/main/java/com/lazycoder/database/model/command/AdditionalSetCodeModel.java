package com.lazycoder.database.model.command;

import lombok.Data;

/**
 * 设置方法的源码
 *
 * @author Administrator
 */
@Data
public class AdditionalSetCodeModel extends FormatTypeCodeModel {

	private int additionalSerialNumber = 0;

	public AdditionalSetCodeModel() {
		// TODO Auto-generated constructor stub
		super();
		setFormatType(ADDITIONAL_TYPE);
	}


}
