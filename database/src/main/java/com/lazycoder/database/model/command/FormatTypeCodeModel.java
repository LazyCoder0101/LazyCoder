package com.lazycoder.database.model.command;

import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.general.GeneralCommandPathCodeModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FormatTypeCodeModel extends GeneralCommandPathCodeModel implements DataFormatType {

	private int formatType = MAIN_TYPE;

	private int additionalSerialNumber = 0;

	private String typeName;

	private int typeSerialNumber = 0;

	public static FormatTypeCodeModel creatAdditionalSetCodeModel(){
		FormatTypeCodeModel formatTypeCodeModel = new FormatTypeCodeModel();
		formatTypeCodeModel.setFormatType(ADDITIONAL_TYPE);
		return formatTypeCodeModel;
	}

	public static FormatTypeCodeModel creatMainSetCodeModel(){
		FormatTypeCodeModel formatTypeCodeModel = new FormatTypeCodeModel();
		formatTypeCodeModel.setFormatType(MAIN_TYPE);
		return formatTypeCodeModel;
	}


}
