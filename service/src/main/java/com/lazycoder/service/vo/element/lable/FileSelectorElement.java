package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 文件选择器
 *
 * @author Administrator
 */
@Data
public class FileSelectorElement extends BaseLableElement {

	public FileSelectorElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.FILE_SELECTOR);
	}
}
