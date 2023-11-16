package com.lazycoder.service.vo;

import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;

/**
 * 变量使用范围
 *
 * @author admin
 */
public enum AttributeUsageRange {

	/**
	 * 仅在 本功能 / 仅在本格式面板使用
	 */
	ApplyOnlyToItself(0,"本功能 / 本格式面板"),

	/**
	 * 仅应用于所添加的模板
	 */
	ApplyOnlyToAddedTemplates(1,"所添加的模板的变量"),

	/**
	 * 应用于所有
	 */
	ApplyToAll(2,"所有"),

	/**
	 * 仅在本模块使用（模块使用）
	 */
	ApplyOnlyToThisModule(3,"本模块"),

	ModulesRequiredForThisModule(4,"本模块需要使用的模块"),

	ThisModuleAndRequiredModules(5,"本模块以及需要使用的模块"),

	/**
	 * 仅在该类型模板使用（可选模板使用）
	 */
	ApplyOnlyToThisTypeOfTemplate(6,"该类型模板的变量"),

	VariableThatIncludesTheEntireOuterFunction(7,"包括整个外部功能的自带变量"),

	VariableThatFirstLayerAndAllTheFunctionsItContains(8,"第1层功能及其包含功能的自带变量");

	/**
	 * 组件显示文本
	 */
	private final String showText;

	private final int dictionaryValue;

	private AttributeUsageRange(int dictionaryValue, String showText) {
		// TODO Auto-generated constructor stub
		this.dictionaryValue = dictionaryValue;
		this.showText = showText;
	}

	/**
	 * 根据字典值转为对应枚举
	 *
	 * @param dictionaryValue
	 * @return
	 */
	public static AttributeUsageRange dictionaryValueToVariableUsageRange(int dictionaryValue) {
		AttributeUsageRange usageRange = null;
			for (AttributeUsageRange temp : AttributeUsageRange.values()) {
				if (temp.getDictionaryValue() == dictionaryValue) {
					usageRange = temp;
					break;
				}
			}
			return usageRange;
		}
//		switch (dictionaryValue) {
//			case 0:
//				return ApplyOnlyToItself;    //本方法 / 本格式面板
//			case 1:
//				return ApplyOnlyToAddedTemplates;    //所添加的模板
//			case 2:
//				return ApplyToAll;            //应用于所有
//			case 3:
//				return ApplyOnlyToThisModule;    //该模块
//			case 4:
//				return ModulesRequiredForThisModule;    //本模块需要的模块
//			case 5:
//				return ThisModuleAndRequiredModules;    //本模块以及需要的模块
//			case 6:
//				return ApplyOnlyToThisTypeOfTemplate;    //该类型模板
//
//			case 7:
//				return VariableThatIncludesTheEntireOuterFunction;
//
//			case 8:
//				return VariableThatFirstLayerAndAllTheFunctionsItContains;
//
//			default:
//				return null;


	/**
	 * 根据显示文字转换成对应枚举
	 *
	 * @param showText
	 * @return
	 */
	public static AttributeUsageRange convertShowTextIntoVariableUsageRange(String showText) {
		AttributeUsageRange temp = null;
		for (int i = 0; i < AttributeUsageRange.values().length; i++) {
			if (AttributeUsageRange.values()[i].showText.equals(showText)) {
				temp = AttributeUsageRange.values()[i];
				break;
			}
		}
		return temp;
	}

	/**
	 * 根据显示文字转换成字典
	 *
	 * @return
	 */
	public static int convertShowTextIntoDictionaryValue(String showText) {
		int dictionaryValue = -1;
		for (int i = 0; i < AttributeUsageRange.values().length; i++) {
			if (AttributeUsageRange.values()[i].showText.equals(showText)) {
				dictionaryValue = AttributeUsageRange.values()[i].ordinal();
				break;
			}
		}
		return dictionaryValue;
	}

	public String getShowText() {
		return showText;
	}

	/**
	 * 枚举对应字典值 （继承以后一律写 return ordinal(); ）
	 *
	 * @return
	 */
	public int getDictionaryValue() {
		return ordinal();
	}

}
