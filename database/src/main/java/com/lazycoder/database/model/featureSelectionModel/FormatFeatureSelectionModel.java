package com.lazycoder.database.model.featureSelectionModel;


import com.lazycoder.database.model.AbstractFormatInfo;
import lombok.Data;

@Data
public class FormatFeatureSelectionModel extends AbstractFeatureSelectionModel {

	/**
	 * 必填模板类型
	 */
	protected final static int MAIN_TYPE = AbstractFormatInfo.MAIN_TYPE;

	/**
	 * 可选模板类型
	 */
	protected final static int ADDITIONAL_TYPE = AbstractFormatInfo.ADDITIONAL_TYPE;

	/**
	 * 格式类型
	 */
	private int formatType = MAIN_TYPE;

	/**
	 * 分类名称
	 */
	private String typeName;

	public FormatFeatureSelectionModel() {
		// TODO Auto-generated constructor stub
		super();
	}


}
