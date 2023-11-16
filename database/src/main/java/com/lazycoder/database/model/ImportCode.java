package com.lazycoder.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 引入代码
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class ImportCode {


	private String moduleId;

	/**
	 * 序号
	 */
	private int ordinal;

	/**
	 * 代码
	 */
	private String code;

	private String codeLabelId = null;

}
