package com.lazycoder.database.model.command;

import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;

/**
 * 设置方法控制
 *
 * @author Administrator
 */
@Data
public class MainSetOperatingModel extends FormatTypeOperatingModel {

	private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

	public MainSetOperatingModel() {
		// TODO Auto-generated constructor stub
		super();
		setFormatType(MAIN_TYPE);
	}


}
