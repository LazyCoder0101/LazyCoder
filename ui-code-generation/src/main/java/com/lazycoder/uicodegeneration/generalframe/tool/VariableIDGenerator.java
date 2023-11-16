package com.lazycoder.uicodegeneration.generalframe.tool;

public class VariableIDGenerator {

	private static int variableSerialNumber = 0;

	/**
	 * 生成变量编号
	 *
	 * @return
	 */
	public static int generateCodeSerialNumber() {
		variableSerialNumber++;
		return variableSerialNumber;
	}

	public static int getVariableSerialNumber() {
		return variableSerialNumber;
	}

	public static void setVariableSerialNumber(int variableSerialNumber) {
		VariableIDGenerator.variableSerialNumber = variableSerialNumber;
	}
}
