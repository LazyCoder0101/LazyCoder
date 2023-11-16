package com.lazycoder.database.common;

/**
 * 不能进行以下命名
 */
public enum NotNamed {

	nonModule("非模块", "非模块", null), // 非模块没有模块名

	general("通用", "通用", "通用"), // 通用选项

	main("必填模板", "main", "main"),

	mainSet("必填模板设置", "mainSet", "mainSet"),

	additional("可选模板", "additional", "additional"),

	additionalSet("可选模板设置", "additionalSet", "additionalSet"),

	additionalFuncition("可选模板功能", "additionalFuncition", "additionalFuncition");

	private final String notNameType;

	private final String className;

	private final String moduleName;

	private NotNamed(String notNameType, String className, String moduleName) {
		this.notNameType = notNameType;
		this.className = className;
		this.moduleName = moduleName;
	}

	public String getNotNameType() {
		return notNameType;
	}

	public String getClassName() {
		return className;
	}

	public String getModuleName() {
		return moduleName;
	}

}
