package com.lazycoder.database.model.general;

import com.lazycoder.database.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 代码储存模型类**@author Administrator
 *
 */
@NoArgsConstructor
@Data
public class GeneralCodeModel implements CodeModelInterface, BaseModel {

	/**
	 * 序号
	 */
	private int ordinal = 0;

	/**
	 * 代码序号
	 */
	private int codeOrdinal;

	/**
	 * 代码参数
	 */
	private String codeFormatParam;

}
