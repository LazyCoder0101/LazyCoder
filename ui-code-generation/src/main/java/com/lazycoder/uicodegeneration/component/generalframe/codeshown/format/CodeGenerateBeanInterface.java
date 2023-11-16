package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format;

/**
 * 组成格式语句还有代码语句的元素，其对应的存储格式都要继承该接口
 *
 * @author admin
 */
public interface CodeGenerateBeanInterface {

	/**
	 * 继承该接口，都要有一个字符变量，变量名是这个
	 */
	public final static String THIS_TYPE = "type";

	/**
	 * 对应的类型
	 *
	 * @return
	 */
	public String getType();

}
