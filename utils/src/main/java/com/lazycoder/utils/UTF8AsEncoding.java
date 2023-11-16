package com.lazycoder.utils;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class UTF8AsEncoding {

	public static String detectFileCharset(File file, int detectLength) throws Exception {
		return detectFileCharset(new FileInputStream(file), detectLength);
	}

	public static String detectFileCharset(InputStream inputStream, int detectLength) throws Exception {
		String charset = null;
		byte[] buf = new byte[detectLength];
		UniversalDetector detector = new UniversalDetector(null);
		int nread;
		while ((nread = inputStream.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.handleData(buf, 0, buf.length);
		detector.dataEnd();
		charset = detector.getDetectedCharset();
		detector.reset();
		if (inputStream != null) {
			inputStream.close();
		}
		return charset;
	}

	public void detectAction() throws Exception {

//		String watchPath = charsetDetectToolController.getDetectPathTextField().getText();// 要转换的路径
//
//		File file = new File(watchPath);
//		int detectLength = Integer.parseInt(charsetDetectToolController.getDetectSizeTextField().getText().trim());
//		detectFileCharset(file, detectLength);

//
//		if (!file.exists()) {
//			try {
//				String fileCharset = detectFileCharset(new URL(watchPath).openStream(), detectLength);
//				charsetDetectToolController.getResultTextArea()
//						.appendText(watchPath + "         Charset: " + fileCharset + "\n");
//				return;
//			} catch (Exception e) {
//				log.error("URL检查失败！");
//			}
//			TooltipUtil.showToast("检测文本/文件夹/URL不存在或错误！");
//			return;
//		}
//		if (file.isDirectory()) {
//			Path path = file.toPath();
//			Iterator<Path> iterator = null;
//			AutoCloseable autoCloseable = null;
//			if (charsetDetectToolController.getIncludeSubdirectoryCheckBox().isSelected()) {如果选中包含子目录
//				Stream<Path> stream = Files.walk(path);
//				autoCloseable = stream;
//				iterator = stream.iterator();
//			} else {//没选包含子目录
//				DirectoryStream<Path> stream = Files.newDirectoryStream(path);
//				autoCloseable = stream;
//				iterator = stream.iterator();
//			}
//			try {
//				boolean sRegex = charsetDetectToolController.getFileNameSupportRegexCheckBox().isSelected();是否支持正则表达式
//				String fileNameContains = charsetDetectToolController.getFileNameContainsTextField().getText();文件名包含字符
//				String fileNameNotContains = charsetDetectToolController.getFileNameNotContainsTextField().getText();文件名不包含字符
//				Pattern fileNameCsPattern = null;
//				Pattern fileNameNCsPattern = null;
//				if (sRegex) {支持正则表达式
//					fileNameCsPattern = Pattern.compile(fileNameContains, Pattern.CASE_INSENSITIVE);
//					fileNameNCsPattern = Pattern.compile(fileNameNotContains, Pattern.CASE_INSENSITIVE);
//				}
//				while (iterator.hasNext()) {
//					Path nextPath = iterator.next();
//					if (Files.isRegularFile(nextPath)
//							&& DirectoryTreeUtil.ifMatchText(nextPath.getFileName().toString(), fileNameContains,
//									fileNameNotContains, sRegex, fileNameCsPattern, fileNameNCsPattern)) {
//						charsetDetectToolController.getResultTextArea().appendText(nextPath.toString()
//								+ "         Charset: " + detectFileCharset(nextPath.toFile(), detectLength) + "\n");
//					}
//				}
//			} finally {
//				if (autoCloseable != null) {
//					autoCloseable.close();
//				}
//			}
//		} else if (file.isFile()) {
//			charsetDetectToolController.getResultTextArea().appendText(
//					file.getAbsolutePath() + "         Charset: " + detectFileCharset(file, detectLength) + "\n");
//		}
	}
}
