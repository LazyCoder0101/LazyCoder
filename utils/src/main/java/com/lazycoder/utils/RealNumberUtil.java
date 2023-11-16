package com.lazycoder.utils;

import java.util.regex.Pattern;

public class RealNumberUtil {

	/**
	 * 摘自 https://www.jb51.net/article/143784.html 判断是否为整数
	 *
	 * @param str 传入的字符串
	 * @return 是整数返回true, 否则返回false
	 */
	public static boolean isInteger(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 摘自 https://www.jb51.net/article/143784.htm 判断是否为浮点数
	 *
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 转为整数
	 *
	 * @param str
	 * @return
	 */
	public static int convertedToInteger(String str) {
		return Integer.parseInt(str);
	}

	/**
	 * 转为浮点数
	 *
	 * @param str
	 * @return
	 */
	public static double convertedToDouble(String str) {
		return Double.parseDouble(str);
	}

}
