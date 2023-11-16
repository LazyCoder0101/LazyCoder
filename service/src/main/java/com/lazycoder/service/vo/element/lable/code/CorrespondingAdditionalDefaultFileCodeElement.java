package com.lazycoder.service.vo.element.lable.code;

import com.lazycoder.service.vo.element.lable.CorrespondingAdditionalDefaultFileElement;
import lombok.Data;

/**
 * 对应添加的可选模板格式的默认文件代码元素模型
 *
 * @author Administrator
 */
@Data
public class CorrespondingAdditionalDefaultFileCodeElement extends CorrespondingAdditionalDefaultFileElement {

	private int haveSuffixOrNot = FALSE_;

	public CorrespondingAdditionalDefaultFileCodeElement() {
		super();
	}

}
