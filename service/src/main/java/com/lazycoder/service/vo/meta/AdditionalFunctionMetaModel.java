package com.lazycoder.service.vo.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.database.model.command.AdditionalFunctionOperatingModel;
import com.lazycoder.database.model.command.deserializer.AdditionalFunctionCodeModelListDeserializer;
import com.lazycoder.database.model.command.deserializer.AdditionalFunctionOperatingModelDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalFunctionMetaModel {

	/**
	 * 操作模型
	 */
	@JSONField(deserializeUsing = AdditionalFunctionOperatingModelDeserializer.class)
	private AdditionalFunctionOperatingModel operatingModel = new AdditionalFunctionOperatingModel();

	/**
	 * 代码模型列表
	 */
	@JSONField(deserializeUsing = AdditionalFunctionCodeModelListDeserializer.class)
	private ArrayList<AdditionalFunctionCodeModel> codeModelList = new ArrayList<>();


}
