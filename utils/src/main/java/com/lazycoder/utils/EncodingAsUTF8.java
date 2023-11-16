package com.lazycoder.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class EncodingAsUTF8 {

	/**
	 * 所有文件转为UTF8编码
	 *
	 * @param inputFileEncode
	 * @param inputFileUrl
	 * @return
	 * @throws IOException
	 */
	public static String encodingAsUTF8(String inputFileEncode, String inputFileUrl) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(inputFileUrl), inputFileEncode));

		StringBuilder str = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			str.append(line + "\r\n");
		}
		bufferedReader.close();
		return str.toString();
	}

}
