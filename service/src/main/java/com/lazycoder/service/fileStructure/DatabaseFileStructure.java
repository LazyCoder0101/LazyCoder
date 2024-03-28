package com.lazycoder.service.fileStructure;

import com.lazycoder.database.common.NotNamed;
import java.io.File;

public class DatabaseFileStructure {

	/**
	 * 显示预览测试文件夹（显示）对应预览测试文件的识别秘钥（lazycoder_preview_test_pro）
	 */
	public static final String PREVIEW_TEST_PRO_FILE_IDENTIFY_SECRET_KEY = "dd55c2ca0b1f86e40f1d137e228fd2a6";

	/**
	 * 显示文件夹对应数据库文件的识别秘钥
	 */
	public static final String DB_FILE_IDENTIFY_SECRET_KEY = "13f599f025b855b1a72fd55c7b3d38d4";

	/**
	 * 懒农导入数据源文件秘钥
	 */
	public static final String LAZY_CODER_DATE_SOURCE_SECRET_KEY = "bbd9161fd47ce79aa5552b3d8cf6b517";

	/**
	 * 生成源码文件的校验秘钥
	 */
	public static final String GENERATE_SOURCE_FILE_SECRET_KEY = "bb085f80e74f5a1ace038586c71590c8";

	/**
	 * 数据库标志文件名
	 */
	static final String DB_IDENTIFY_FILE_NAME = "db" + SysFileStructure.PRO_SUFFIX;

	/**
	 * 预览测试项目的标志文件名
	 */
	public static final  String PREVIEW_TEST_PRO_IDENTIFY_FILE_NAME = "priview_test_pro" + SysFileStructure.PRO_SUFFIX;

	/**
	 * （放置“添加文件” 组件存放的默认文件）的文件夹名称
	 */
	private static final String NEED_FILE_FOLDER_NAME = "needFile";

	/**
	 * （放置“图片文件” 组件存放的图片文件）的文件夹名称
	 */
	private static final String PITURE_FOLDER_NAME = "picture";

	/**
	 * 模块的“附带文件”的文件夹名称
	 */
	private static final String ATTACHED_FILE_FOLD_NAME = "attachedFile";

	/**
	 * 获取数据库文件
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getDatabaseFileRootPath(File parentFolder, String databaseName) {
		return (new File(parentFolder.getAbsolutePath() + File.separator + databaseName));
	}

	/**
	 * 数据库标志文件
	 *
	 * @param parentFile 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @return
	 */
	public static File getDBIdentifyFile(File parentFile) {
		return (new File(parentFile.getAbsolutePath() + File.separator + DB_IDENTIFY_FILE_NAME));
	}

	public static File getPreviewTestProFileIdentifyFile(File parentFile){
		return (new File(parentFile.getAbsolutePath() + File.separator + PREVIEW_TEST_PRO_IDENTIFY_FILE_NAME));
	}

	/**
	 * 获取数据库对应的数据文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getDataFolder(File parentFolder, String databaseName) {
		return (new File(
				getDatabaseFileRootPath(parentFolder, databaseName).getAbsolutePath() + File.separator + "data"));
	}

	/**
	 * 获取对应的sqlite数据库文件
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getSqliteDBRootFile(File parentFolder, String databaseName) {
		return (new File(
				getDatabaseFileRootPath(parentFolder, databaseName).getAbsolutePath() + File.separator + "sqlite"));
	}

	/**
	 * 获取对应的sqlite数据库文件
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param dataSourceFolderName
	 * @param databaseName
	 * @return
	 */
	public static File getSqliteDBFile(File parentFolder,String dataSourceFolderName, String databaseName) {
		return (new File(getSqliteDBRootFile(parentFolder, dataSourceFolderName).getAbsolutePath() + File.separator
				+ databaseName + SysFileStructure.SQLITE_SUFFIX));
	}

	/**
	 * 获取对应的sqlite数据库文件
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getSqliteDBFile(File parentFolder, String databaseName) {
		return getSqliteDBFile(parentFolder,databaseName,databaseName);
	}

	/**
	 * 存放使用模板的放置路径
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getTemplateFolder(File parentFolder, String databaseName) {
		return (new File(getDatabaseFileRootPath(parentFolder, databaseName).getAbsolutePath() + File.separator
				+ "useTemplate"));
	}

	/**
	 * 存放使用文档的放置路径
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getUserDocFolder(File parentFolder, String databaseName) {
		return (new File(
				getDatabaseFileRootPath(parentFolder, databaseName).getAbsolutePath() + File.separator + "userDoc"));
	}

	/**
	 * 获取数据库的格式根文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	protected static File getFormatRootFolder(File parentFolder, String databaseName) {
		return (new File(getDataFolder(parentFolder, databaseName).getAbsolutePath() + File.separator + "format"));
	}

	/**
	 * 获取数据库的模块根文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	protected static File getModuleRootFolder(File parentFolder, String databaseName) {
		return (new File(getDataFolder(parentFolder, databaseName).getAbsolutePath() + File.separator + "module"));
	}



	/**
	 * 获取模块数据存储目录
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @param moduleId
	 * @return
	 */
	public static File getModuleFolder(File parentFolder, String databaseName, String moduleId) {
		return (new File(
				getModuleRootFolder(parentFolder, databaseName).getAbsolutePath() + File.separator + moduleId));
	}

	/**
	 * 获取模块 添加文件 组件的存放文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @param moduleId
	 * @return
	 */
	public static File getModuleNeedFileFolder(File parentFolder, String databaseName, String moduleId) {
		return (new File(getModuleFolder(parentFolder, databaseName, moduleId).getAbsolutePath()
				+ File.separator + NEED_FILE_FOLDER_NAME));
	}

	/**
	 * 获取模块的图片存放文件夹
	 * @param parentFolder
	 * @param databaseName
	 * @param moduleId
	 * @return
	 */
	public static File getModulePictureFolder(File parentFolder, String databaseName, String moduleId) {
		return (new File(getModuleFolder(parentFolder, databaseName, moduleId).getAbsolutePath()
				+ File.separator + PITURE_FOLDER_NAME));
	}


	/**
	 * 获取模块的附带文件存放文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @param moduleId
	 * @return
	 */
	public static File getModuleAttachedFileFolder(File parentFolder, String databaseName, String moduleId) {
		return (new File(getModuleFolder(parentFolder, databaseName, moduleId).getAbsolutePath()
				+ File.separator + ATTACHED_FILE_FOLD_NAME));
	}

	/**
	 * 获取模块的附带文件存放文件夹的第ordinal个文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @param moduleId
	 * @param ordinal
	 * @return
	 */
	public static File getModuleAttachedFileFolder(File parentFolder, String databaseName, String moduleId, int ordinal) {
		return (new File(
				getModuleAttachedFileFolder(parentFolder, databaseName, moduleId).getAbsolutePath()
						+ File.separator + ATTACHED_FILE_FOLD_NAME + "_" + ordinal));
	}

	/**
	 * 获取数据库的模块根文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	private static File getMainRootFolder(File parentFolder, String databaseName) {
		return (new File(getFormatRootFolder(parentFolder, databaseName).getAbsolutePath() + File.separator
				+ NotNamed.main.getClassName()));
	}

	/**
	 * 获取必填模板的所需文件的文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getMainNeedFileFolder(File parentFolder, String databaseName) {
		return (new File(
				getMainRootFolder(parentFolder, databaseName).getAbsolutePath() + File.separator + NEED_FILE_FOLDER_NAME));
	}

	/**
	 * 获取必填模板的图片文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getMainPictureFolder(File parentFolder, String databaseName) {
		return (new File(
				getMainRootFolder(parentFolder, databaseName).getAbsolutePath() + File.separator + PITURE_FOLDER_NAME));
	}

	/**
	 * 获取数据库的可选模板根文件夹
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getAdditionalRootFolder(File parentFolder, String databaseName) {
		return (new File(getFormatRootFolder(parentFolder, databaseName).getAbsolutePath() + File.separator
				+ NotNamed.additional.getClassName()));
	}

	/**
	 * 存放可选模板格式图片的放置路径
	 *
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getAdditionalFolder(File parentFolder, String databaseName, int additionalSerialNumber) {
		return (new File(getAdditionalRootFolder(parentFolder, databaseName).getAbsolutePath() + File.separator + "additional"
				+ additionalSerialNumber));
	}

	/**
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getAdditionalNeedFileFolder(File parentFolder, String databaseName, int additionalSerialNumber) {
		return (new File(getAdditionalFolder(parentFolder, databaseName, additionalSerialNumber).getAbsolutePath()
				+ File.separator + NEED_FILE_FOLDER_NAME));
	}

	/**
	 * @param parentFolder 如果是系统内部的数据源，传参 SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @return
	 */
	public static File getAdditionalPictureFolder(File parentFolder, String databaseName, int additionalSerialNumber) {
		return (new File(getAdditionalFolder(parentFolder, databaseName, additionalSerialNumber).getAbsolutePath()
				+ File.separator + PITURE_FOLDER_NAME));
	}

	/**
	 * 获取模块的附带文件存放文件夹
	 *
	 * @param parentFolder           如果是系统内部的数据源，传参
	 *                               SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @param additionalSerialNumber
	 * @return
	 */
	public static File getAdditionalAttachedFileFolder(File parentFolder, String databaseName, int additionalSerialNumber) {
		return (new File(getAdditionalFolder(parentFolder, databaseName, additionalSerialNumber).getAbsolutePath()
				+ File.separator + ATTACHED_FILE_FOLD_NAME));
	}

	/**
	 * 获取模块的附带文件存放文件夹的第ordinal个文件夹
	 *
	 * @param parentFolder           如果是系统内部的数据源，传参
	 *                               SysFileStructure.getDataFileFolder()
	 * @param databaseName
	 * @param additionalSerialNumber
	 * @param ordinal
	 * @return
	 */
	public static File getAdditionalAttachedFileFolder(File parentFolder, String databaseName, int additionalSerialNumber,
													   int ordinal) {
		return (new File(
				getAdditionalAttachedFileFolder(parentFolder, databaseName, additionalSerialNumber).getAbsolutePath()
						+ File.separator + ATTACHED_FILE_FOLD_NAME + "_" + ordinal));
	}

}
