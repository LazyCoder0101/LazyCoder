package com.lazycoder.service.vo.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.command.deserializer.FormatTypeCodeModelListDeserializer;
import com.lazycoder.database.model.command.deserializer.FormatTypeOperatingModelDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalSetMetaModel extends AbstractMetaModel {

	/**
	 * 操作模型
	 */
	@JSONField(deserializeUsing = FormatTypeOperatingModelDeserializer.class)
	private FormatTypeOperatingModel operatingModel = FormatTypeOperatingModel.getAdditionalFormatTypeOperatingModel();

	/**
	 * 代码模型列表
	 */
	@JSONField(deserializeUsing = FormatTypeCodeModelListDeserializer.class)
	private ArrayList<FormatTypeCodeModel> codeModelList = new ArrayList<>();


}
