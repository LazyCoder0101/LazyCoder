package com.lazycoder.database.model.command;

import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 可选模板的功能的源码
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class AdditionalFunctionCodeModel extends GeneralCommandCodeModel {

	private int additionalSerialNumber;

}
