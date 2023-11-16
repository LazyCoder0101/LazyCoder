package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.component;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import lombok.Getter;

public class ContentChooseControlMenuItem extends LableMenuItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 7703604757890738281L;

	@Getter
	private ContentChooseElement contentChooseElement = null;

	/**
	 * 选择组件使用构造函数
	 *
	 * @param text
	 * @param contentChooseElement
	 */
	public ContentChooseControlMenuItem(String text, ContentChooseElement contentChooseElement) {
		// TODO Auto-generated constructor stub
		super(text, LabelElementName.CONTENT_CHOOSE, contentChooseElement.getThisName());
		this.contentChooseElement = contentChooseElement;
	}

}
