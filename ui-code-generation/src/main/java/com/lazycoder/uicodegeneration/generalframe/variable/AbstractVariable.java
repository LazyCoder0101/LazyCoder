package com.lazycoder.uicodegeneration.generalframe.variable;

import com.lazycoder.service.vo.AttributeUsageRange;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractVariable {

	public final static int CUSTOM_VARIABLE_TYPE = 0;

	public final static int FORMAT_VARIABLE_TYPE = 1;

	private int type = FORMAT_VARIABLE_TYPE;

	/**
	 * 变量ID，由变量生成器赋值
	 */
	private int variableId = 0;

	/**
	 * 变量值
	 */
	private String variableValue = "";

	/**
	 * 有值的时候弹出的提示
	 */
	private String toolTipTextOfHaveValue = "";

	/**
	 * 没有值的时候弹出的提示
	 */
	private String toolTipTextOfHaveNotValue = null;


	/**
	 * 变量数据类型
	 */
	private ArrayList<String> dataTypeList = new ArrayList<>();

	/**
	 * 变量标签类型
	 */
	private ArrayList<String> labelTypeList = new ArrayList<>();

	private int variableRange = AttributeUsageRange.ApplyToAll.getDictionaryValue();

	/**
	 * 不需要用户进行选择（自动选择）
	 */
	private boolean noUserSelectionIsRequired = false;


	/**
	 * 获取type这个属性的名称
	 *
	 * @return
	 */
	public static String getTypeFieldName() {
		return "type";
	}

	/**
	 * 比较
	 *
	 * @return
	 */
	public boolean compare(int variableId) {
		boolean flag = false;
		if (variableId == this.variableId) {
			flag = true;
		}
		return flag;
	}


}
