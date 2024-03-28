package com.lazycoder.service;


import com.lazycoder.lazycodercommon.vo.DataSourceLabel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import java.util.ArrayList;

public class GeneralHolder {

	/**
	 * 是否正在执行预览测试操作
	 */
	public static boolean previewTesting = false;

	//临时错误信息记录
	public static ArrayList<String> temporaryErrorList = new ArrayList<>();

	/**
	 * 数据库名
	 */
	public static DataSourceLabel currentUserDataSourceLabel = null;

	public static void changeUserDB(DataSourceLabel dataSourceLabel){
		SysService.DB_CHANGE_SERVICE.changeUserDB(dataSourceLabel.getDataSourceName(),dataSourceLabel.getDbId());
	}

	public static void changeSysDB(){
		SysService.DB_CHANGE_SERVICE.changeSYSDB();
	}

	/**
	 * 操作异常记录
	 */
	public static void errorLogging(String text, String logText) {
		temporaryErrorList.add(text);
		SysService.SYS_SERVICE_SERVICE.log_error("操作异常记录 "+logText);
	}

	/**
	 * 如果在执行过程中出现问题，把问题显示出来
	 *
	 * @param title
	 */
	public static void showErrorListIfNeed(String title) {
		if (temporaryErrorList.size() > 0) {
			String str = getTemporaryErrorListContent();
			LazyCoderOptionPane.showMessageDialog(null, str, title, JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**
	 * 获取临时错误信息记录的内容
	 *
	 * @return
	 */
	private static String getTemporaryErrorListContent() {
		StringBuilder sb = new StringBuilder();
		for (String str : temporaryErrorList) {
			sb.append(str + "\n\n");
		}
		return sb.toString();
	}


}
