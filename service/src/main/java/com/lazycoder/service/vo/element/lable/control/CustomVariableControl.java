package com.lazycoder.service.vo.element.lable.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.element.lable.CustomVariableElement;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import lombok.Data;

/**
 * 自定义变量
 *
 * @author Administrator
 */
@Data
public class CustomVariableControl extends CustomVariableElement implements BaseModel {

	/**
	 * 标签类型参数
	 */
	private String labelTypeParam = "[]";

	/**
	 * 数据类型相关参数
	 */
	private String dataTypeParam = "[]";

	/**
	 * 左符号
	 */
	private String leftStr = "";
	/**
	 * 右符号
	 */
	private String rightStr = "";
	/**
	 * 间隔符
	 */
	private String separatorStr = "";
	/**
	 * 变量使用范围
	 */
	private int theAvaliableRange = AttributeUsageRange.ApplyOnlyToItself.getDictionaryValue();
	/**
	 * 只能添加一个
	 */
	private int onlyAddOne = TRUE_;

	/**
	 * 不需要用户进行选择
	 */
	private boolean noUserSelectionIsRequired = false;

	/**
	 * 允许名字重复
	 */
	private boolean allowDuplicateNames=false;
	/**
	 * 默认名称列表
	 */
	private String defaultNameList = "[]";

	public CustomVariableControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 获取设置好的变量名列表
	 *
	 * @param customVariableControl
	 * @return
	 */
	public static ArrayList<String> getDefaultNameList(CustomVariableControl customVariableControl) {
		ArrayList<String> list = JSON.parseObject(customVariableControl.defaultNameList,
				new TypeReference<ArrayList<String>>() {
				});
		return list;
	}

	/**
	 * 获取选项值列表
	 *
	 * @param defaultNameList
	 * @param control
	 */
	public static void setDefaultNameList(ArrayList<String> defaultNameList, CustomVariableControl control) {
		String str = JsonUtil.getJsonStr(defaultNameList);
		control.setDefaultNameList(str);
	}

	/**
	 * 获取标签类型
	 *
	 * @param labelTypeParam 传custom_variable_control的labelTypeParam参数
	 * @return
	 */
	public static ArrayList<String> getLabelTypeParam(String labelTypeParam) {
		ArrayList<String> list = JSON.parseObject(labelTypeParam, new TypeReference<ArrayList<String>>() {
		});
		return list;
	}

	/**
	 * 设置标签类型
	 *
	 * @param labelTypeList
	 * @param control
	 */
	public static void setLabelTypeParam(ArrayList<String> labelTypeList, CustomVariableControl control) {
		String str = JsonUtil.getJsonStr(labelTypeList);
		control.setLabelTypeParam(str);
	}


	/**
	 * 获取数据类型
	 *
	 * @param dataTypeParam 传custom_variable_control的dataTypeParam参数
	 * @return
	 */
	public static ArrayList<String> getDataTypeParam(String dataTypeParam) {
		ArrayList<String> list = JSON.parseObject(dataTypeParam, new TypeReference<ArrayList<String>>() {
		});
		return list;
	}

	/**
	 * 设置数据类型
	 *
	 * @param dataTypeList
	 * @param control
	 */
	public static void setDataTypeParam(ArrayList<String> dataTypeList, CustomVariableControl control) {
		String str = JsonUtil.getJsonStr(dataTypeList);
		control.setDataTypeParam(str);
	}

	@Override
	public CustomVariableElement controlComponentInformation() {
		CustomVariableElement element = new CustomVariableElement();
		element.setThisName(getThisName());
		return element;
	}

}
