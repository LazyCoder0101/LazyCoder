package com.lazycoder.service;

public interface BaseEnum {

	/**
	 * 枚举对应字典值 （继承以后一律写 return ordinal(); ）
	 *
	 * @return
	 */
	public int getDictionaryValue();

	/**
	 * 根据字典值转为对应枚举
	 *
	 * @param dictionaryValue
	 * @return
	 */
	public BaseEnum dictionaryValueToVariableUsageRange(int dictionaryValue);

}
