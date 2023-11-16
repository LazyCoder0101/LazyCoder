package com.lazycoder.service.vo.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import com.lazycoder.database.model.command.deserializer.ModuleSetCodeModelListDeserializer;
import com.lazycoder.database.model.command.deserializer.ModuleSetOperatingModelDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleSetMetaModel extends AbstractMetaModel {

	/**
	 * 操作模型
	 */
	@JSONField(deserializeUsing = ModuleSetOperatingModelDeserializer.class)
	private ModuleSetOperatingModel operatingModel = new ModuleSetOperatingModel();

	/**
	 * 代码模型列表
	 */
	@JSONField(deserializeUsing = ModuleSetCodeModelListDeserializer.class)
	private ArrayList<ModuleSetCodeModel> codeModelList = new ArrayList<>();


}
