package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import lombok.Data;

/**
 * 格式模型
 *
 * @author admin
 */
@Data
public abstract class AbstractFormatOperatingContainerModel extends AbstractOperatingContainerModel {

	public final static String MAIN_FORMAT_OPERATING_CONTAINER = "mainFormatOperatingContainer";

	public final static String ADDITIONAL_FORMAT_OPERATING_CONTAINER = "additionalFormatOperatingContainer";

	public final static String MODULE_CONTROL_CONTAINER = "moduleControlContainer";

	private String formatOperatingContainerType;

	/**
	 * 对应代码文件名称
	 */
	private String currentCodeFileName = "";

	private boolean formatState = false;//有没有写格式控制部分的内容

	private AbstractFormatOperatingContainerModel() {
		// TODO Auto-generated constructor stub
		super(FORMAT_CONTAINER_TYPE);
	}

	public AbstractFormatOperatingContainerModel(String formatOperatingContainerType) {
		// TODO Auto-generated constructor stub
		this();
		this.formatOperatingContainerType = formatOperatingContainerType;
	}

	/**
	 * 获取格式类型
	 *
	 * @param elementJSONObject
	 * @return
	 */
	public static String getFormatType(JSONObject elementJSONObject) {
		return elementJSONObject.getString("formatOperatingContainerType");
	}

}
