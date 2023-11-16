package com.lazycoder.service.vo.element.lable.code;

import com.lazycoder.service.vo.element.lable.FileSelectorElement;
import lombok.Data;

/**
 * 文件选择器
 *
 * @author Administrator
 */
@Data
public class FileSelectorCodeElement extends FileSelectorElement {

	/**
	 * 输入的时候是否填写路径
	 */
	private boolean writtenWithPath = true;

	public FileSelectorCodeElement() {
		// TODO Auto-generated constructor stub
		super();
	}

}
