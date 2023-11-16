package com.lazycoder.service.vo.element.lable.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.service.vo.element.lable.ConstantElement;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import lombok.Data;

/**
 * 常量
 *
 * @author Administrator
 */
@Data
public class ConstantControl extends ConstantElement {

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
	 * 默认名称列表
	 */
	private String defaultList = "[]";

	public ConstantControl() {
		// TODO Auto-generated constructor stub
		super();
	}


	public static String getValue(ConstantControl controlElement, ArrayList<String> valueList) {
		StringBuilder out = new StringBuilder("");
		if (valueList != null) {
			if (valueList.size() == 1) {
				out.append(valueList.get(0));

			} else if (valueList.size() > 1) {
				out.append(controlElement.getLeftStr() + valueList.get(0) + controlElement.getRightStr());
				for (int i = 1; i < valueList.size(); i++) {
					out.append(controlElement.getSeparatorStr() + controlElement.getLeftStr() + valueList.get(i)
							+ controlElement.getRightStr());
				}
			}
		}
		return out.toString();
	}

	/**
	 * 获取设置好的变量名列表
	 *
	 * @param constantControl
	 * @return
	 */
	public static ArrayList<String> getDefaultList(ConstantControl constantControl) {
		ArrayList<String> optionNameList = JSON.parseObject(constantControl.defaultList,
				new TypeReference<ArrayList<String>>() {
				});
		return optionNameList;
	}

	/**
	 * 获取选项值列表
	 *
	 * @param defaultNameList
	 * @param constantControl
	 */
	public static void setDefaultList(ArrayList<String> defaultNameList, ConstantControl constantControl) {
		String str = JsonUtil.getJsonStr(defaultNameList);
		constantControl.setDefaultList(str);
	}

	@Override
	public ConstantElement controlComponentInformation() {
		ConstantElement element = new ConstantElement();
		element.setThisName(getThisName());
		return element;
	}

}
