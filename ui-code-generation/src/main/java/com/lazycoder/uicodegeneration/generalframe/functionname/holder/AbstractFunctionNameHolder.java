package com.lazycoder.uicodegeneration.generalframe.functionname.holder;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.GeneralFunctionNameHolderDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.deserializer.GeneralFunctionNameHolderSerializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractFunctionNameHolder {

	@JSONField(deserializeUsing = GeneralFunctionNameHolderDeserializer.class, serializeUsing = GeneralFunctionNameHolderSerializer.class)
	protected ArrayList<AbstractMethodName> functionNameList = new ArrayList<>();

	public void addFunctionName(AbstractMethodName functionName) {
		functionNameList.add(functionName);
	}

}
