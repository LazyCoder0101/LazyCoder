package com.lazycoder.service.fileStructure;

import java.io.File;

/**
 * 生成源码结构
 *
 * @author admin
 */
public class SourceGenerateFileStructure {

	/**
	 * 懒农项目标记文件
	 */
	private static final String LANNONG_PRO_FLAG_FILE_NAME = "pro" + SysFileStructure.PRO_SUFFIX;

	/**
	 * 获取项目路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getTheProjectPath(String projectParentPath, String projectName) {
		return new File(projectParentPath + File.separator + projectName);
	}

	public static File getLannongProFlagFile(String projectParentPath, String projectName) {
		return (new File(getTheProjectPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ LANNONG_PRO_FLAG_FILE_NAME));
	}

	/**
	 * 获取生成源码的使用文档路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getGenerateSourceUsingDocumentPath(String projectParentPath, String projectName) {
		return new File(getTheProjectPath(projectParentPath, projectName).getAbsolutePath() + File.separator + "使用文档");
	}

	/**
	 * 获取生成源码的源码路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getGenerateSourcePutPath(String projectParentPath, String projectName) {
		return new File(getTheProjectPath(projectParentPath, projectName).getAbsolutePath() + File.separator + "源码");
	}

	/**
	 * 获取源码文件夹里面的项目路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getTheProjectPathInGenerateSourcePutPath(String projectParentPath, String projectName) {
		return new File(getGenerateSourcePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ projectName);
	}

	/**
	 * 获取生成源码的懒农路径（存放还原内容的信息）
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getGenerateSourceLannongPath(String projectParentPath, String projectName) {
		return new File(
				getTheProjectPath(projectParentPath, projectName).getAbsolutePath() + File.separator + "lannong");
	}

	/**
	 * 获取生成源码的格式路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	private static File getGenerateSourceFormatPath(String projectParentPath, String projectName) {
		return new File(getGenerateSourceLannongPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ "format");
	}

	/**
	 * 获取必填模板信息存储文件的路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getMainTempFilePutPath(String projectParentPath, String projectName) {
		return new File(getGenerateSourceFormatPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ "main");
	}

	/**
	 * 必填模板的文件添加的放置文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getMainNeedFilePutPath(String projectParentPath, String projectName) {
		return new File(getMainTempFilePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator + "needFile");
	}

	/**
	 * 必填模板的图片文件放置文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getMainPictureFilePutPath(String projectParentPath, String projectName) {
		return new File(
				getMainTempFilePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator + "picture");
	}

	/**
	 * 必填模板的默认代码文件的存放路径文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getMainDefaultPath(String projectParentPath, String projectName) {
		return new File(
				getMainTempFilePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator + "default");
	}

	/**
	 * 必填模板的附带代码文件的存放路径文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getMainSubPath(String projectParentPath, String projectName) {
		return new File(
				getMainTempFilePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator + "sub");
	}

	/**
	 * 必填模板的操作面板文件的存放路径文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getMainOpratingPanePath(String projectParentPath, String projectName) {
		return new File(getMainTempFilePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ "opratingPane");
	}

	/**
	 * 获取模块信息存储文件的路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getModuleTempFilePutPath(String projectParentPath, String projectName) {
		return new File(getGenerateSourceLannongPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ "module");
	}

	/**
	 * 获取该分类的文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param className
	 * @return
	 */
//	public static File getClassFolder(String projectParentPath, String projectName, String className) {
//		return new File(getModuleTempFilePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator
//				+ className);
//	}

	/**
	 * 获取模块文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param moduleId
	 * @return
	 */
	public static File getModuleFolder(String projectParentPath, String projectName,
									   String moduleId) {
		return new File(getModuleTempFilePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ moduleId);
	}

	/**
	 * 模块的文件添加的放置文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param moduleId
	 * @return
	 */
	public static File getModuleNeedFilePutPath(String projectParentPath, String projectName, String moduleId) {
		return new File(getModuleFolder(projectParentPath, projectName, moduleId).getAbsolutePath()
				+ File.separator + "needFile");
	}

	/**
	 * 模块的图片文件放置文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param moduleId
	 * @return
	 */
	public static File getModulePictureFilePutPath(String projectParentPath, String projectName, String moduleId) {
		return new File(getModuleFolder(projectParentPath, projectName, moduleId).getAbsolutePath()
				+ File.separator + "picture");
	}

	/**
	 * 模块的附带代码文件的存放路径文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param moduleId
	 * @return
	 */
	public static File getModuleSubPath(String projectParentPath, String projectName, String moduleId) {
		return new File(getModuleFolder(projectParentPath, projectName, moduleId).getAbsolutePath()
				+ File.separator + "sub");
	}

	/**
	 * 获取可选模板信息存储文件的路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getAdditionalRootFilePutPath(String projectParentPath, String projectName) {
		return new File(getGenerateSourceFormatPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ "additional");
	}

	/**
	 * 获取可选模板信息存储文件的路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param additionalSerialNumber
	 * @return
	 */
	public static File getAdditionalTempFilePutPath(String projectParentPath, String projectName, int additionalSerialNumber) {
		return new File(getAdditionalRootFilePutPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ "additional" + additionalSerialNumber);
	}

	/**
	 * 可选模板的文件添加的放置文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param additionalSerialNumber
	 * @return
	 */
	public static File getAdditionalNeedFilePutPath(String projectParentPath, String projectName, int additionalSerialNumber) {
		return new File(getAdditionalTempFilePutPath(projectParentPath, projectName, additionalSerialNumber).getAbsolutePath()
				+ File.separator + "needFile");
	}

	/**
	 * 可选模板的图片文件放置文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param additionalSerialNumber
	 * @return
	 */
	public static File getAdditionalPictureFilePutPath(String projectParentPath, String projectName, int additionalSerialNumber) {
		return new File(getAdditionalTempFilePutPath(projectParentPath, projectName, additionalSerialNumber).getAbsolutePath()
				+ File.separator + "picture");
	}

	/**
	 * 可选模板的默认代码文件的存放路径文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param additionalSerialNumber
	 * @return
	 */
	public static File getAdditionalDefaultPath(String projectParentPath, String projectName, int additionalSerialNumber) {
		return new File(getAdditionalTempFilePutPath(projectParentPath, projectName, additionalSerialNumber).getAbsolutePath()
				+ File.separator + "default");
	}

	/**
	 * 可选模板的附带代码文件的存放路径文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param additionalSerialNumber
	 * @return
	 */
	public static File getAdditionalSubPath(String projectParentPath, String projectName, int additionalSerialNumber) {
		return new File(getAdditionalTempFilePutPath(projectParentPath, projectName, additionalSerialNumber).getAbsolutePath()
				+ File.separator + "sub");
	}

	/**
	 * 可选模板的操作面板的存放路径文件夹
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param additionalSerialNumber
	 * @return
	 */
	public static File getAdditionalOpratingPanePath(String projectParentPath, String projectName, int additionalSerialNumber) {
		return new File(getAdditionalTempFilePutPath(projectParentPath, projectName, additionalSerialNumber).getAbsolutePath()
				+ File.separator + "opratingPane");
	}

	/**
	 * 获取起始信息存储文件的路径
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getRootTempFilePutPath(String projectParentPath, String projectName) {
		return new File(getGenerateSourceLannongPath(projectParentPath, projectName).getAbsolutePath() + File.separator
				+ "root");
	}

}
