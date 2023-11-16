package com.lazycoder.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TheClassification {

	/**
	 * 分类名
	 */
	private String className;

	/**
	 * 排序
	 */
	private int indexParam = 0;


}
