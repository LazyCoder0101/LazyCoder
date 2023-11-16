package com.lazycoder.service.vo.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.command.FunctionOperatingModel;
import com.lazycoder.database.model.command.deserializer.FunctionCodeModelListDeserializer;
import com.lazycoder.database.model.command.deserializer.FunctionOperatingModelDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FunctionMetaModel {

	/**
	 * 操作模型
	 */
	@JSONField(deserializeUsing = FunctionOperatingModelDeserializer.class)
	private FunctionOperatingModel operatingModel = new FunctionOperatingModel();

	/**
	 * 代码模型列表
	 */
	@JSONField(deserializeUsing = FunctionCodeModelListDeserializer.class)
	private ArrayList<FunctionCodeModel> codeModelList = new ArrayList<>();

}
