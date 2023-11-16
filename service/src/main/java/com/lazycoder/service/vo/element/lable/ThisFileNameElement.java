package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import lombok.Data;

/**
 * 本文件名
 *
 * @author admin
 */
@Data
public class ThisFileNameElement extends BaseLableElement {

	public ThisFileNameElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.THIS_FILE_NAME);
	}

}