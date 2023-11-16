package com.lazycoder.service.vo.element.lable.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.service.vo.element.lable.MethodChooseElement;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import lombok.Data;

/**
 * 方法选择
 *
 * @author Administrator
 */
@Data
public class MethodChooseControl extends MethodChooseElement {

	/**
	 * 数据类型参数
	 */
	private String labelTypeParam = "[]";

	/**
	 * 变量使用范围
	 */
	private int theAvaliableRange;

	public MethodChooseControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 获取数据类型
	 *
	 * @param control
	 * @return
	 */
	public static ArrayList<String> getLabelTypeList(MethodChooseControl control) {
		ArrayList<String> labelTypeList = JSON.parseObject(control.labelTypeParam,
				new TypeReference<ArrayList<String>>() {
				});
		return labelTypeList;
	}

	/**
	 * 设置变量类型
	 *
	 * @param labelTypeList
	 * @param control
	 */
	public static void setLabelTypeList(ArrayList<String> labelTypeList, MethodChooseControl control) {
		String str = JsonUtil.getJsonStr(labelTypeList);
		control.setLabelTypeParam(str);
	}

	@Override
	public MethodChooseElement controlComponentInformation() {
		MethodChooseElement element = new MethodChooseElement();
		element.setThisName(getThisName());
		return element;
	}

}
