package com.lazycoder.uicodegeneration.generalframe.tool;

public class FunctionNameIDGenerator {

	private static int functionNameSerialNumber = 0;

	/**
	 * 生成变量编号
	 *
	 * @return
	 */
	public static int generateCodeSerialNumber() {
		functionNameSerialNumber++;
		return functionNameSerialNumber;
	}

	public static void setFunctionNameSerialNumber(int functionNameSerialNumber) {
		FunctionNameIDGenerator.functionNameSerialNumber = functionNameSerialNumber;
	}

	public static int getFunctionNameSerialNumber() {
		return functionNameSerialNumber;
	}

}
