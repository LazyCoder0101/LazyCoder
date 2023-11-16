package com.lazycoder.service.vo.element.lable.control;

import com.lazycoder.service.vo.element.lable.TextInputElement;
import lombok.Data;

/**
 * 内容输入组件
 *
 * @author Administrator
 */
@Data
public class TextInputControl extends TextInputElement {


	/**
	 * 文本框形式（presentForm字段使用参数）
	 */
	public static final int TEXT_FIELD_FORM = 0;

	/**
	 * 输入文本域形式（presentForm字段使用参数）
	 */
	public static final int TEXT_AREA_FORM = 1;

	/**
	 * 内容输入组件的样式
	 */
	private int presentForm = TEXT_FIELD_FORM;
	

	/**
	 * 无限制（input_limit字段使用参数）
	 */
	public static final int NOT_LIMIT = 0;

	/**
	 * 仅限整数（input_limit字段使用参数）
	 */
	public static final int ONLY_INTEGER = 1;

	/**
	 * 仅限小数（input_limit字段使用参数）
	 */
	public static final int ONLY_FLOAT = 2;


	/**
	 * 输入限制类型
	 */
	private int inputLimit = NOT_LIMIT;

	/**
	 * 默认值
	 */
	private String defaultValue = "";

	/**
	 * 最小值
	 */
	private String minValue = "";
	/**
	 * 最大值
	 */
	private String maxValue = "";

	/**
	 * 文本框尺寸（textFieldShowSize字段使用参数）：长
	 */
	public final static int LONG_SIZE = 2;
	/**
	 * 文本框尺寸（textFieldShowSize字段使用参数）：中
	 */
	public final static int MIDDLE_SIZE = 1;

	/**
	 * 文本框尺寸（textFieldShowSize字段使用参数）：短
	 */
	public final static int SHORT_SIZE = 0;
	
	/**
	 * 文本框尺寸
	 */
	private int textFieldShowSize = MIDDLE_SIZE;

	public TextInputControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public TextInputElement controlComponentInformation() {
		TextInputElement element = new TextInputElement();
		element.setThisName(getThisName());
		return element;
	}

}
