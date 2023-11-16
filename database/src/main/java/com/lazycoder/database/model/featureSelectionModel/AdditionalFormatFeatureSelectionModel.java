package com.lazycoder.database.model.featureSelectionModel;

import lombok.Data;

@Data
public class AdditionalFormatFeatureSelectionModel extends FormatFeatureSelectionModel {

	/**
	 * 其他数量
	 */
	private int additionalSerialNumber = 0;

	public AdditionalFormatFeatureSelectionModel() {
		// TODO Auto-generated constructor stub
		setFormatType(ADDITIONAL_TYPE);
	}


}
