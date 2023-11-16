package com.lazycoder.database.model.general.command;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralCommandOperatingModel extends GeneralOperatingModel implements BaseModel {

	/**
	 * 序号
	 */
	private int ordinal;

	/**
	 * 显示描述
	 */
	private String showText;

	private String hiddenControlStatementFormat;

	/**
	 * 隐藏面板使用状态，true_:有使用 false_：没使用
	 */
	private int hiddenState = FALSE_;


}
