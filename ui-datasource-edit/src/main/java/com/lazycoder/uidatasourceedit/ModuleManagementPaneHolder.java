package com.lazycoder.uidatasourceedit;

import com.lazycoder.uidatasourceedit.component.component.ClassNameTablePageNavigator;
import com.lazycoder.uidatasourceedit.component.component.table.classification.ClassificationTable;
import com.lazycoder.uidatasourceedit.component.component.table.module.ModuleTable;
import com.lazycoder.uidatasourceedit.modulemanagement.ModuleManagementPaneClassCombobox;
import com.lazycoder.uidatasourceedit.modulemanagement.ModuleManagementPaneUsingRangeCombobox;

public class ModuleManagementPaneHolder {

	/**
	 * 分类表
	 */
	public static ClassificationTable classTable = null;

	/**
	 * 模块表
	 */
	public static ModuleTable moduleTable = null;

	/**
	 * 分类下拉框（查询用）
	 */
	public static ModuleManagementPaneClassCombobox classNameComboBox = null;

	/**
	 * 模块表格下方的分页导航组件
	 */
	public static ClassNameTablePageNavigator navigator = null;

	/**
	 * 使用范围下拉框（查询用）
	 */
	public static ModuleManagementPaneUsingRangeCombobox managementPaneUsingRangeCombobox = null;

}
