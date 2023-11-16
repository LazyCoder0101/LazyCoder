package com.lazycoder.uicodegeneration.proj.stostr.operation;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.database.model.format.deserializer.AdditionalOperatingDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.AdditionalCustomFunctionNameListDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AdditionalCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.deserializer.AdditionalCustomVariableHolderDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AdditionalCustomVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractFormatControlPaneModel;
import lombok.Data;

/**
 * 可选模板
 *
 * @author admin
 */
@Data
public class AdditionalFormatControlPaneModel extends AbstractFormatControlPaneModel {

	/**
	 * 可选模板序号
	 */
	private int additionalSerialNumber = 0;

	@JSONField(deserializeUsing = AdditionalOperatingDeserializer.class)
	private AdditionalOperating operating=null;

	@JSONField(deserializeUsing = AdditionalCustomVariableHolderDeserializer.class)
	private AdditionalCustomVariableHolder customVariableHolder = null;

	@JSONField(deserializeUsing = AdditionalCustomFunctionNameListDeserializer.class)
	private AdditionalCustomFunctionNameHolder customFunctionNameHolder = null;


	public AdditionalFormatControlPaneModel() {
		// TODO Auto-generated constructor stub
		super(ADDITIONAL_FORMAT_CONTROL_PANE);
	}

}
