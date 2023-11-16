package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.component;

import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import lombok.Getter;

public class ContentChooseCodeMenuItem extends ContentChooseControlMenuItem {

	/**
	 *
	 */
	private static final long serialVersionUID = -448662134263146226L;

	@Getter
	private int groupNum=0;

	/**
	 * 选择组件使用构造函数
	 * @param text
	 * @param contentChooseElement
	 * @param groupNum
	 */
	public ContentChooseCodeMenuItem(String text, ContentChooseElement contentChooseElement,int groupNum) {
		// TODO Auto-generated constructor stub
		super(text, contentChooseElement);
		this.groupNum=groupNum;
	}

}
