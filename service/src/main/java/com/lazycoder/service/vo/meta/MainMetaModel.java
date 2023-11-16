package com.lazycoder.service.vo.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.format.MainOperating;
import com.lazycoder.database.model.format.deserializer.FormatFilelListDeserializer;
import com.lazycoder.database.model.format.deserializer.MainOperatingDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainMetaModel extends AbstractMetaModel {

	/**
	 * 操作模型
	 */
	@JSONField(deserializeUsing = MainOperatingDeserializer.class)
	private MainOperating operatingModel = new MainOperating();

	/**
	 * 代码模型列表
	 */
	@JSONField(deserializeUsing = FormatFilelListDeserializer.class)
	private ArrayList<GeneralFileFormat> codeModelList = new ArrayList<>();


}
