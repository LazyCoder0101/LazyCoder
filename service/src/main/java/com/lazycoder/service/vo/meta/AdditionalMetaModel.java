package com.lazycoder.service.vo.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.database.model.format.deserializer.AdditionalOperatingDeserializer;
import com.lazycoder.database.model.format.deserializer.FormatFileDeserializer;
import com.lazycoder.database.model.format.deserializer.FormatFilelListDeserializer;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalMetaModel extends AbstractMetaModel {

	/**
	 * 操作模型
	 */
	@JSONField(deserializeUsing = AdditionalOperatingDeserializer.class)
	private AdditionalOperating operatingModel = new AdditionalOperating();

	/**
	 * 默认代码格式
	 */
	@JSONField(deserializeUsing = FormatFileDeserializer.class)
	private GeneralFileFormat defaultCodeFormat = GeneralFileFormat.createAdditionalFormatFile();

	/**
	 * 代码文件模型列表
	 */
	@JSONField(deserializeUsing = FormatFilelListDeserializer.class)
	private List<GeneralFileFormat> codeModelList = new ArrayList<>();

}
