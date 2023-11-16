package com.lazycoder.database.model.command;

import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 初始化代码控制类
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class InitOperatingModel extends GeneralCommandOperatingModel {

	private String moduleId;

	/**
	 * 是否为默认
	 */
	private int whetherTheDefault = FALSE_;


}
