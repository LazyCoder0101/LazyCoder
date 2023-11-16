package com.lazycoder.uidatasourceedit.component.component;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;

import java.util.ArrayList;

public interface CodeFileEditPaneInterface {

	/**
	 * 定位到对应标记
	 *
	 * @param list
	 * @param thanMarkElement
	 * @param navigateOrNot
	 * @return
	 */
	public boolean navigateToTheCorrespondingMark(ArrayList<CodeFormatFlagParam> list, BaseMarkElement thanMarkElement,
												  boolean navigateOrNot);
}
