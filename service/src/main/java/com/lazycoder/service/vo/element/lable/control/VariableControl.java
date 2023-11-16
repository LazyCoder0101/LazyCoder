package com.lazycoder.service.vo.element.lable.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.service.vo.element.lable.VariableElement;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import lombok.Data;

/**
 * 变量
 *
 * @author Administrator
 */
@Data
public class VariableControl extends VariableElement {

	/**
	 * 标签类型参数
	 */
	private String labelTypeParam = "[]";

	/**
	 * 数据类型相关参数
	 */
	private String dataTypeParam = "[]";

	/**
	 * 变量使用范围
	 */
	private int theAvaliableRange;

	/**
	 * 不需要用户进行选择
	 */
	private boolean noUserSelectionIsRequired = false;

	public VariableControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 获取数据类型
	 *
	 * @param variableControl
	 * @return
	 */
	public static ArrayList<String> getDataTypeList(VariableControl variableControl) {
		ArrayList<String> dataTypeList = JSON.parseObject(variableControl.dataTypeParam,
				new TypeReference<ArrayList<String>>() {
				});
		return dataTypeList;
	}

	/**
	 * 设置变量类型
	 *
	 * @param dataTypeList
	 * @param variableControl
	 */
	public static void setDataTypeList(ArrayList<String> dataTypeList, VariableControl variableControl) {
		String str = JsonUtil.getJsonStr(dataTypeList);
		variableControl.setDataTypeParam(str);
	}

	/**
	 * 获取标签类型
	 *
	 * @param variableControl
	 * @return
	 */
	public static ArrayList<String> getLabelTypeList(VariableControl variableControl) {
		ArrayList<String> list = JSON.parseObject(variableControl.labelTypeParam,
				new TypeReference<ArrayList<String>>() {
				});
		return list;
	}

	/**
	 * 设置变量标签类型
	 *
	 * @param labelTypeList
	 * @param variableControl
	 */
	public static void setLabelTypeList(ArrayList<String> labelTypeList, VariableControl variableControl) {
		String str = JsonUtil.getJsonStr(labelTypeList);
		variableControl.setLabelTypeParam(str);
	}

	@Override
	public VariableElement controlComponentInformation() {
		VariableElement element = new VariableElement();
		element.setThisName(getThisName());
		return element;
	}


}
