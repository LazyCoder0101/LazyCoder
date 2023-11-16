package com.lazycoder.codeformat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FileUtil {

	public static String readFile(String path) {
		StringBuffer script = new StringBuffer();
		File file = new File(path);
		FileReader filereader;
		BufferedReader bufferreader;
		try {
			filereader = new FileReader(file);
			bufferreader = new BufferedReader(filereader);
			String tempString = null;
			try {
				while ((tempString = bufferreader.readLine()) != null) {
					script.append(tempString).append("\n");
				}
				bufferreader.close();
				filereader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return script.toString();
	}

	/**
	 * 访问resources文件夹下的静态文件（转成字符串）
	 *
	 * @return
	 */
	public static BufferedReader getStaticResourceBr(String path) {
		Resource resource = new ClassPathResource(path);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return br;
	}

	/**
	 * 获取静态资源文件里面的文件
	 * @param path
	 * @return
	 */
	public static File getStaticResourceFile(String path){
		try {
			return new ClassPathResource(path).getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
