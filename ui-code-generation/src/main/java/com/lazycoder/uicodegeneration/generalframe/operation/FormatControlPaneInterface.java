package com.lazycoder.uicodegeneration.generalframe.operation;

import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import java.util.ArrayList;

public interface FormatControlPaneInterface {

	/**
	 * 对应的代码默认面板（继承该面板都要重写该方法）
	 *
	 * @return
	 */
	public CodeShowPane getDefaultPane();

	/**
	 * 获取对应的子代码面板
	 *
	 * @return
	 */
	public ArrayList<CodeShowPane> getSubCodePaneList();

	/**
	 * 获取目前用的的模块的所有代码面板
	 *
	 * @return
	 */
	public ArrayList<CodeShowPane> getModuleCodePaneList();


}
