package com.lazycoder.database.model.command;

import com.lazycoder.database.model.general.GeneralCommandPathCodeModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 初始化方法代码类
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class InitCodeModel extends GeneralCommandPathCodeModel {

	/**
	 * 是否为默认
	 */
	private int whetherTheDefault = FALSE_;

	private String moduleId;


}
