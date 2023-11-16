package com.lazycoder.service.vo.element.lable.control;

import com.lazycoder.service.vo.element.lable.CorrespondingAdditionalDefaultFileElement;
import lombok.Data;

/**
 * 对应添加的可选模板格式的默认文件
 *
 * @author Administrator
 */
@Data
public class CorrespondingAdditionalDefaultFileControl extends CorrespondingAdditionalDefaultFileElement {


	public CorrespondingAdditionalDefaultFileControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public CorrespondingAdditionalDefaultFileElement controlComponentInformation() {
		CorrespondingAdditionalDefaultFileElement element = new CorrespondingAdditionalDefaultFileElement();
		element.setThisName(getThisName());
		return element;
	}

}
