package com.lazycoder.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模块信息
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class ModuleInfo implements BaseModel {

	private String moduleId;

	/**
	 * 分类名
	 */
	private String className = "";

	/**
	 * 模块名
	 */
	private String moduleName = "";

	/**
	 * 引入代码的数量
	 */
	private int numOfImport = 0;

	/**
	 * 初始化代码的数量
	 */
	private int numOfInit = 0;

	/**
	 * 模块变量的数量
	 */
	private int numOfVariable = 0;

	/**
	 * 模块方法名的数量
	 */
	private int numOfFunctionName=0;

	/**
	 * 是否需要模块控制
	 */
	private int whetherModuleControlIsRequired = FALSE_;

	/**
	 * 添加文件数量
	 */
	private int numOfAddFile = 0;

	/**
	 * 添加文件参数
	 */
	private String addFileParam = "[]";

	/**
	 * 附带文件数量
	 */
	private int numOfAttachedFile = 0;

	/**
	 * 附带文件参数
	 */
	private String attachedFileParam = "[]";

	/**
	 * 设置代码种类数量
	 */
	private int numOfSetCodeType = 0;

	/**
	 * 设置代码类型列表
	 */
	private String setTheTypeOfSetCodeParam = "[]";

	/**
	 * 功能源码数量
	 */
	private int numOfFunctionCodeType = 0;

	/**
	 * 功能源码种类列表
	 */
	private String functionTheTypeOfSourceCodeParam = "[]";

	/**
	 * 需要使用的其他代码文件数量
	 */
	private int theNumOfAdditionalCodeFilesParamsThatNeedToBeUsed = 0;

	/**
	 * 需要使用到的其他代码文件
	 */
	private String additionalCodeFilesParamsThatNeedToBeUsed = "[]";

}
