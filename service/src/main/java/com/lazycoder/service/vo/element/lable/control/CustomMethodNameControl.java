package com.lazycoder.service.vo.element.lable.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.element.lable.CustomMethodNameElement;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import lombok.Data;

/**
 * 自定义方法名
 *
 * @author Administrator
 */
@Data
public class CustomMethodNameControl extends CustomMethodNameElement {

	/**
	 * 方法名
	 */
	private String methodName = "";

	/**
	 * 标签类型参数
	 */
	private String labelTypeParam = "[]";

	/**
	 * 变量使用范围
	 */
	private int theAvaliableRange = AttributeUsageRange.ApplyOnlyToItself.getDictionaryValue();

	public CustomMethodNameControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 获取数据类型
	 *
	 * @param labelTypeParam 传custom_variable_control的dataTypeParam参数
	 * @return
	 */
	public static ArrayList<String> getLabelTypeParam(String labelTypeParam) {
		ArrayList<String> optionNameList = JSON.parseObject(labelTypeParam, new TypeReference<ArrayList<String>>() {
		});
		return optionNameList;
	}

	/**
	 * 设置数据类型
	 *
	 * @param labelTypeList
	 * @param control
	 */
	public static void setLabelTypeParam(ArrayList<String> labelTypeList, CustomMethodNameControl control) {
		String str = JsonUtil.getJsonStr(labelTypeList);
		control.setLabelTypeParam(str);
	}

	@Override
	public CustomMethodNameElement controlComponentInformation() {
		CustomMethodNameElement element = new CustomMethodNameElement();
		element.setThisName(getThisName());
		return element;
	}

}
