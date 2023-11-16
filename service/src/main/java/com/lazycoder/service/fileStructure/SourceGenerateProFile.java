package com.lazycoder.service.fileStructure;

import com.lazycoder.database.CodeFormatFlagParam;
import java.io.File;

public class SourceGenerateProFile {

	/**
	 * 获取代码生成项目根文件
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @return
	 */
	public static File getCodeGenerationRootProFile(String projectParentPath, String projectName) {
		File file = new File(
				SourceGenerateFileStructure.getRootTempFilePutPath(projectParentPath, projectName).getAbsolutePath()
						+ File.separator + "pro" + SysFileStructure.PRO_SUFFIX);
		return file;
	}

	/**
	 * 获取代码文件的对应项目文件
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param codeFormatFlagParam
	 * @return
	 */
	public static File getCodeProFile(String projectParentPath, String projectName,
									  CodeFormatFlagParam codeFormatFlagParam) {
		File parentFolder, file = null;
		if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType()) {
			parentFolder = SourceGenerateFileStructure.getModuleSubPath(projectParentPath, projectName,
					codeFormatFlagParam.getModuleId());
			file = new File(parentFolder.getAbsolutePath() + File.separator + codeFormatFlagParam.getFileName()
					+ SysFileStructure.PRO_SUFFIX);

		} else if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParam.getFormatType()) {

			if (CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE == codeFormatFlagParam.getFileType()) {// 必填模板的附带格式
				parentFolder = SourceGenerateFileStructure.getMainSubPath(projectParentPath, projectName);
				file = new File(parentFolder.getAbsolutePath() + File.separator + codeFormatFlagParam.getFileName()
						+ SysFileStructure.PRO_SUFFIX);

			} else if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParam
					.getFileType()) {// 必填模板的默认格式
				parentFolder = SourceGenerateFileStructure.getMainDefaultPath(projectParentPath, projectName);
				file = new File(parentFolder.getAbsolutePath() + File.separator + codeFormatFlagParam.getFileName()
						+ SysFileStructure.PRO_SUFFIX);
			}

		} else if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {

			if (CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE == codeFormatFlagParam.getFileType()) {// 可选模板的附带格式
				parentFolder = SourceGenerateFileStructure.getAdditionalSubPath(projectParentPath, projectName,
						codeFormatFlagParam.getAdditionalSerialNumber());
				file = new File(parentFolder.getAbsolutePath() + File.separator + codeFormatFlagParam.getFileName()
						+ SysFileStructure.PRO_SUFFIX);

			} else if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParam
					.getFileType()) {// 可选模板的默认格式
				parentFolder = SourceGenerateFileStructure.getAdditionalDefaultPath(projectParentPath, projectName,
						codeFormatFlagParam.getAdditionalSerialNumber());
				file = new File(parentFolder.getAbsolutePath() + File.separator + codeFormatFlagParam.getFileName()
						+ SysFileStructure.PRO_SUFFIX);
			}
		}
		return file;
	}

	/**
	 * 获取必填模板操作面板的项目文件
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param fileName
	 * @return
	 */
	public static File getMainOpratingPaneProFile(String projectParentPath, String projectName, String fileName) {
		File parentFile = SourceGenerateFileStructure.getMainOpratingPanePath(projectParentPath, projectName),
				file = new File(
						parentFile.getAbsolutePath() + File.separator + fileName + ".pro" + SysFileStructure.PRO_SUFFIX);
		return file;
	}

	/**
	 * 获取可选模板操作面板的项目文件
	 *
	 * @param projectParentPath
	 * @param projectName
	 * @param fileName
	 * @param additionalSerialNumber
	 * @return
	 */
	public static File getAdditionalOpratingPaneProFile(String projectParentPath, String projectName, String fileName,
														int additionalSerialNumber) {
		File parentFile = SourceGenerateFileStructure.getAdditionalOpratingPanePath(projectParentPath, projectName,
				additionalSerialNumber),
				file = new File(
						parentFile.getAbsolutePath() + File.separator + fileName + ".pro" + SysFileStructure.PRO_SUFFIX);
		return file;
	}

}
