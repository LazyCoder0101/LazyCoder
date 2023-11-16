package com.lazycoder.uicodegeneration.generalframe.tool;

public class CodeIDGenerator {

	/**
	 * 格式代码编号
	 */
	public static final int FORMAT_CODE_SERIAL_NUMBER = 0;

	private static int codeSerialNumber = 0;

	/**
	 * 生成代码编号
	 *
	 * @return
	 */
	public static int generateCodeSerialNumber() {
		codeSerialNumber++;
		return codeSerialNumber;
	}

	public static int getCodeSerialNumber() {
		return codeSerialNumber;
	}

	public static void setCodeSerialNumber(int codeSerialNumber) {
		CodeIDGenerator.codeSerialNumber = codeSerialNumber;
	}
}
