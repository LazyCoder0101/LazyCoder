package com.lazycoder.uicodegeneration.generalframe;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.formodule.UsingObject;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生成代码界面再选择模块时需要的参数
 */
@NoArgsConstructor
@Data
public class SelectedModuleParam {

	/**
	 * 项目路径
	 */
	private String projectParentPath;

	/**
	 * 项目名
	 */
	private String projectName;

	/**
	 * 所使用的范围，必填模板还是可选模板
	 */
	private UsingObject usingObject;

	/**
	 * 当前已选择的模块
	 */
	private ArrayList<ModuleRelatedParam> moduleRelatedParamList;

	/**
	 * 对应的代码控制编辑面板的文件名
	 */
	private String correspondingPaneFileName;

}
