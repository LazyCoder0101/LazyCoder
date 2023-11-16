package com.lazycoder.service.vo.element.lable.code;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.vo.element.lable.ThisFileNameElement;
import lombok.Data;

/**
 * 文件名
 *
 * @author Administrator
 */
@Data
public class ThisFileNameCodeElement extends ThisFileNameElement implements BaseModel {

	private int HaveSuffixOrNot = FALSE_;

	public ThisFileNameCodeElement() {
		super();
	}

}
