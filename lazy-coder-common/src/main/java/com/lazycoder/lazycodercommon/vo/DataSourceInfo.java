package com.lazycoder.lazycodercommon.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DataSourceInfo {

	/**
	 * 数据源名称
	 */
	private String dataSourceName = "";

	/**
	 * 放置路径
	 */
	private String putPath = "";

	/**
	 * 是否可以使用
	 */
	private boolean enabledState = true;

	/**
	 * 作者信息
	 */
	private String authorInfo = "";

	/**
	 * 数据源注释
	 */
	private String dataSourceAnnotation = "";

	/**
	 * 使用编程语言
	 */
	private ProgramingLanguage useProgramingLanguage = null;

	/**
	 * 数据库id
	 */
	private String dbId = null;

}
