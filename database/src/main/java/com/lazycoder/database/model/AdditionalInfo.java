package com.lazycoder.database.model;

import lombok.Data;

@Data
public class AdditionalInfo extends AbstractFormatInfo {

	private int additionalSerialNumber = 0;

	public AdditionalInfo() {
		// TODO Auto-generated constructor stub
		setFormatType(ADDITIONAL_TYPE);
	}

}
