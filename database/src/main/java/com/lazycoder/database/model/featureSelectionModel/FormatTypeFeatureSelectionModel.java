package com.lazycoder.database.model.featureSelectionModel;

import com.lazycoder.database.DataFormatType;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;

@Data
public class FormatTypeFeatureSelectionModel extends AbstractFeatureSelectionModel implements DataFormatType {

	private int formatType;

	private int additionalSerialNumber = 0;

	private int typeSerialNumber=0;

	private String typeName=null;

	private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

	public FormatTypeFeatureSelectionModel() {
		// TODO Auto-generated constructor stub
		super();
	}

	public static FormatTypeFeatureSelectionModel getMainFormatTypeFeatureSelectionModel(){
		FormatTypeFeatureSelectionModel featureSelectionModel = new FormatTypeFeatureSelectionModel();
		featureSelectionModel.setFormatType(MAIN_TYPE);
		return featureSelectionModel;
	}

	public static FormatTypeFeatureSelectionModel getAdditionalFormatTypeFeatureSelectionModel(){
		FormatTypeFeatureSelectionModel featureSelectionModel = new FormatTypeFeatureSelectionModel();
		featureSelectionModel.setFormatType(ADDITIONAL_TYPE);
		return featureSelectionModel;
	}

}
