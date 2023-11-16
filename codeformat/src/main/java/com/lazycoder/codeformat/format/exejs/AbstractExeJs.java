package com.lazycoder.codeformat.format.exejs;

import com.lazycoder.codeformat.utils.FileUtil;
import java.io.BufferedReader;

/**
 * 调用某个js文件里面的方法继承的抽象类
 *
 * @author admin
 */
public abstract class AbstractExeJs {

	protected final static String CONFIG_FOLDER = "codeFormatJs";// 放置js文件的文件夹名

	protected static BufferedReader readJSFile(String path) throws Exception {
//		return FileUtil.readFile(path);
		return FileUtil.getStaticResourceBr(path);
	}

}
