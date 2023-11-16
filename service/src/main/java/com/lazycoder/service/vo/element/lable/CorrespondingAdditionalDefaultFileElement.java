package com.lazycoder.service.vo.element.lable;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.model.BaseModel;
import lombok.Data;

/**
 * 对应添加的可选模板格式的默认文件名
 *
 * @author Administrator
 */
@Data
public class CorrespondingAdditionalDefaultFileElement extends BaseLableElement implements BaseModel {

	public CorrespondingAdditionalDefaultFileElement() {
		// TODO Auto-generated constructor stub
		super();
		this.setLabelType(LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE);
	}

}
