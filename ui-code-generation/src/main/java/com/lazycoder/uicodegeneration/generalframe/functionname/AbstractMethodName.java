package com.lazycoder.uicodegeneration.generalframe.functionname;

import com.lazycoder.service.vo.AttributeUsageRange;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class AbstractMethodName implements FunctionNameForCodeGenerationInterface {

	public final static int CUSTOM_FUNCTION_NAME_TYPE = 0;

	public final static int FORMAT_FUNCTION_NAME_TYPE = 1;

	private int type = FORMAT_FUNCTION_NAME_TYPE;

	/**
	 * 方法名ID，由方法名生成器赋值
	 */
	private int functionNameId = 0;

	/**
	 * 方法名值
	 */
	private String functionNameValue = "";

	/**
	 * 有值的时候弹出的提示
	 */
	private String toolTipTextOfHaveValue = "";

	/**
	 * 没有值的时候弹出的提示
	 */
	private String toolTipTextOfHaveNotValue = null;

	/**
	 * 方法名类型
	 */
	private ArrayList<String> functionNameType = new ArrayList<>();

	private int functionNameRange = AttributeUsageRange.ApplyToAll.getDictionaryValue();

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
	public boolean compare(int functionNameId) {
		boolean flag = false;
		if (functionNameId == this.functionNameId) {
			flag = true;
		}
		return flag;
	}


}
