package com.lazycoder.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统参数
 *
 * @author admin
 */
@NoArgsConstructor
@Data
public class SysParam implements BaseModel {

	/**
	 * 数据库id
	 */
	public final static int DBID = 0;

	/**
	 * 作者信息
	 */
	public final static int AUTHOR = 1;

	/**
	 * 数据源备注
	 */
	public final static int DATA_SOURCE_ANNOTATION = 2;

	/**
	 * 是否可以使用
	 */
	public final static int ENABLED_STATE = 3;

	/**
	 * 使用编程语言
	 */
	public final static int USE_PROGRAMING_LANGUAGE = 4;

	/**
	 * 编码格式
	 */
	public final static int ENCODING = 5;

	/**
	 * 重命名参数
	 */
	public final static int RENAME_FILE_PARAM = 6;

	/**
	 * 模块代码文件常用路径
	 */
	public final static int MODULE_CODE_FILE_PATH = 7;

	/**
	 * 模块附加文件常用路径
	 */
	public final static int MODULE_ATTACHED_FILE_PATH = 8;

	/**
	 * 上一次设置的项目路径（默认系统数据库专用）
	 */
	public final static int LAST_SET_PRO_PATH = 9;

	/**
	 * 是否自动定位到组件对应代码面板的位置
	 */
	public final static int AUTO_POSITION = 10;

	/**
	 * 生成这个数据源的客户端版本（最后保存时写入）
	 */
	public final static int DS_CLI_VER  =11;

	private int id = 0;

	/**
	 * 参数名称（注释）
	 */
	private String paramName = "";

	/**
	 * 参数对应的值
	 */
	private String paramValue = "";


}
