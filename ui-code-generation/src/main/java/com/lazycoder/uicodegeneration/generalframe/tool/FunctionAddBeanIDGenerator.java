package com.lazycoder.uicodegeneration.generalframe.tool;

/**
 * 方法拓展的代码标签对应实体的编号id生成
 */
public class FunctionAddBeanIDGenerator {

	private static int functionAddBeanSerialNumber = 0;

	/**
	 * 生成代码编号
	 *
	 * @return
	 */
	public static int generateCodeSerialNumber() {
		functionAddBeanSerialNumber++;
		return functionAddBeanSerialNumber;
	}

	public static void setFunctionAddBeanSerialNumber(int functionAddBeanSerialNumber) {
		FunctionAddBeanIDGenerator.functionAddBeanSerialNumber = functionAddBeanSerialNumber;
	}

	public static int getFunctionAddBeanSerialNumber() {
		return functionAddBeanSerialNumber;
	}
}
