package com.lazycoder.service.vo.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.command.InitOperatingModel;
import com.lazycoder.database.model.command.deserializer.InitCodeModelListDeserializer;
import com.lazycoder.database.model.command.deserializer.InitOperatingModelDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitMetaModel extends AbstractMetaModel {

	/**
	 * 操作模型
	 */
	@JSONField(deserializeUsing = InitOperatingModelDeserializer.class)
	private InitOperatingModel operatingModel = new InitOperatingModel();

	/**
	 * 代码模型列表
	 */
	@JSONField(deserializeUsing = InitCodeModelListDeserializer.class)
	private ArrayList<InitCodeModel> codeModelList = new ArrayList<>();

	public static InitMetaModel restoreByJSONObject(JSONObject initMetaModelJSONObject) {
		InitMetaModel theMetaModel = JSON.parseObject(initMetaModelJSONObject.toJSONString(),
				new TypeReference<InitMetaModel>() {
				});
		return theMetaModel;
	}

}
