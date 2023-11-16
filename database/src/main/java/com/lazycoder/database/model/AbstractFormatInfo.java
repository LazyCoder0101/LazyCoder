package com.lazycoder.database.model;

import com.lazycoder.database.DataFormatType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 格式信息
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public abstract class AbstractFormatInfo implements BaseModel , DataFormatType {

	/**
	 * 类型
	 */
	private int formatType = MAIN_TYPE;

	/**
	 * 变量的数量
	 */
	private int numOfVariable = 0;

	/**
	 * 控制部分有没有写内容
	 */
	private int formatState = TRUE_;

	/**
	 * 附带文件数量
	 */
	private int numOfAttachedFile = 0;

	private int functionNameNum = 0;

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
	 * 子代码文件的数量
	 */
	private int numOfSubCodeFile = 0;

	private int numOfFunctionCodeType = 0;

}
