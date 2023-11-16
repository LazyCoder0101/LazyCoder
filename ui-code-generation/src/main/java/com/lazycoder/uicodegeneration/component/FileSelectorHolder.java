package com.lazycoder.uicodegeneration.component;

import com.lazycoder.uicodegeneration.generalframe.tool.FileRecord;
import java.util.ArrayList;

/**
 * 添加过的文件添加的文件都放这里
 *
 * @author admin
 */
public class FileSelectorHolder {

	/**
	 * 文件添加组件，添加过的文件都记在这里
	 */
	public static ArrayList<FileRecord> fileRecordList = new ArrayList<>();

	private static boolean checkHaveAddOrNot(String fileName, String path) {
		boolean flag = false;
		for (FileRecord fileTemp : fileRecordList) {
			if (fileTemp.getFileName().equals(fileName)) {
				if (fileTemp.getPath().equals(path)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 添加一个文件记录
	 */
	public static void addFileRecord(String fileName, String path) {
		boolean flag = checkHaveAddOrNot(fileName, path);
		if (flag == true) {
			FileRecord fileRecord = getFileRecord(fileName, path);
			if (fileRecord != null) {
				fileRecord.addTimes();
			}
		} else {
			FileRecord fileRecord = new FileRecord(fileName, path);
			fileRecord.addTimes();
			fileRecordList.add(fileRecord);
		}
	}

	/**
	 * 改变之前的文件记录
	 *
	 * @param newFileName
	 * @param newPath
	 * @param oldFileName
	 * @param oldPath
	 * @return 0：已删除原来的文件记录（就当前这个文件组件有使用这文件） 1：只是在原来的文件记录-1（其他地方也用到这文件）
	 */
	public static int changeFileRecord(String newFileName, String newPath, String oldFileName, String oldPath) {
		int addTimes = delFileRecord(oldFileName, oldPath);
		addFileRecord(newFileName, newPath);
		return addTimes;
	}

	/**
	 * 删除对应文件记录
	 *
	 * @param fileName
	 * @param path
	 * @return 0：已删除原来的文件记录（只是当前这个文件组件有使用这文件） 1：只是在原来的文件记录-1（其他地方也用到这文件）
	 */
	public static int delFileRecord(String fileName, String path) {
		int addTimes = 0;
		FileRecord fileRecord = null;
		for (int i = 0; i < fileRecordList.size(); i++) {
			fileRecord = fileRecordList.get(i);
			if (fileRecord.getFileName().equals(fileName)) {
				if (fileRecord.getPath().equals(path)) {
					fileRecord.reduceTimes();
					addTimes = fileRecord.getAddTimes();
					if (addTimes == 0) {
						fileRecordList.remove(i);
					}
					break;
				}
			}
		}
		return addTimes;
	}

	public static FileRecord getFileRecord(String fileName, String path) {
		FileRecord fileRecord = null;
		for (FileRecord fileTemp : fileRecordList) {
			if (fileTemp.getFileName().equals(fileName)) {
				if (fileTemp.getPath().equals(path)) {
					fileRecord = fileTemp;
					break;
				}
			}
		}
		return fileRecord;
	}

	/**
	 * 文件组件每次新建或者重新选择组件 先根据路径和文件名搜索之前有没有添加过一个一样的 如果有，添加次数那里加1 原来那个，看看是否大于1， 不是删掉
	 *
	 * 如果没有新增
	 */

}
