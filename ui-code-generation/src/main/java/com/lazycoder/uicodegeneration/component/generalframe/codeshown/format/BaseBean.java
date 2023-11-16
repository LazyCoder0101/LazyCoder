package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format;

public interface BaseBean extends CodeGenerateBeanInterface {

	public String getTheBeanValue();

	/**
	 * 设置元素组件的内部值
	 *
	 * @param updateParam
	 */
	public void updateBeanValue(Object updateParam);

	/**
	 * 获取返回值的字数
	 *
	 * @return
	 */
	public int getValueLength();

}
