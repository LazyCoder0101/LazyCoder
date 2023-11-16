package com.lazycoder.database.model.command;

import lombok.Data;

/**
 * 可选模板的功能的控制实体
 *
 * @author Administrator
 */
@Data
public class AdditionalSetOperatingModel extends FormatTypeOperatingModel  {

	private int additionalSerialNumber = 0;

	public AdditionalSetOperatingModel() {
		// TODO Auto-generated constructor stub
		super();
		setFormatType(ADDITIONAL_TYPE);
	}


}
