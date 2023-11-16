package com.lazycoder.database.model.featureSelectionModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class AbstractFeatureSelectionModel {

	/**
	 * 显示名称
	 */
	private String showText;

	/**
	 * 序号
	 */
	private int ordinal=0;

	private String noteListParam;

}
