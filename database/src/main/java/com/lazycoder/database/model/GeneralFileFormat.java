package com.lazycoder.database.model;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用文件格式
 *
 * @author admin
 */
@NoArgsConstructor
@Data
public class GeneralFileFormat extends CodeFormatFlagParam {

	/**
	 * 格式内容
	 */
	private String formatContent = "";

	/**
	 * 路径
	 */
	private String path = "";

	/**
	 * 是否写入了默认文件名(默认文件名才用)
	 */
	private int defaultFilenameSetting = FALSE_;


	/**
	 * 是否代码格式化
	 */
	private int codeFormatOrNot = TRUE_;

	/**
	 * 创建一个模块格式文件的模型
	 * @return
	 */
	public static GeneralFileFormat createModuleFormatFile(){
		GeneralFileFormat formatFile = new GeneralFileFormat();
		formatFile.setFormatType(MODULE_TYPE);
		formatFile.setFileType(TYPE_ADDITIONAL_FORMAT_FILE);
		return formatFile;
	}

	/**
	 * 创建一个模块格式文件的模型
	 * @return
	 */
	public static GeneralFileFormat createAdditionalFormatFile(){
		GeneralFileFormat formatFile = new GeneralFileFormat();
		formatFile.setFormatType(ADDITIONAL_TYPE);
		return formatFile;
	}

	/**
	 * 创建一个模块格式文件的模型
	 * @return
	 */
	public static GeneralFileFormat createMainFormatFile(){
		GeneralFileFormat formatFile = new GeneralFileFormat();
		formatFile.setAdditionalSerialNumber(0);// 加上这句，删除语句根据此删除
		formatFile.setFormatType(MAIN_TYPE);
		return formatFile;
	}

	/**
	 * 根据 JSONObject 还原
	 *
	 * @param jsonObject
	 * @return
	 */
	public static GeneralFileFormat restoreByJSONObject(JSONObject jsonObject) {
		GeneralFileFormat fileFormat = JsonUtil.restoreByJSONObject(jsonObject, GeneralFileFormat.class);
		return fileFormat;
	}



}
