package com.lazycoder.database.model.command;

import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FormatTypeOperatingModel extends GeneralCommandOperatingModel implements DataFormatType {

	private int formatType = MAIN_TYPE;

	private int typeSerialNumber = 0;

	private String typeName;

	private int additionalSerialNumber = 0;

	private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

	public static FormatTypeOperatingModel getMainFormatTypeOperatingModel(){
		FormatTypeOperatingModel formatTypeOperatingModel = new FormatTypeOperatingModel();
		formatTypeOperatingModel.setFormatType(MAIN_TYPE);
		return formatTypeOperatingModel;
	}

	public static FormatTypeOperatingModel getAdditionalFormatTypeOperatingModel(){
		FormatTypeOperatingModel formatTypeOperatingModel = new FormatTypeOperatingModel();
		formatTypeOperatingModel.setFormatType(ADDITIONAL_TYPE);
		return formatTypeOperatingModel;
	}

}
