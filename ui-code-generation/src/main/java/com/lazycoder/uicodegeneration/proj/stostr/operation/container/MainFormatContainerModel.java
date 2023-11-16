package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.deserializer.MainInfoDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.MainSetTypeFolderModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer.MainSetTypeFolderModelDeserializer;
import lombok.Data;

@Data
public class MainFormatContainerModel extends AbstractFormatOperatingContainerModel {

	@JSONField(deserializeUsing = MainInfoDeserializer.class)
	private MainInfo mainInfo = null;

	@JSONField(deserializeUsing = MainSetTypeFolderModelDeserializer.class)
	private MainSetTypeFolderModel mainSetTypeFolderModel = null;

	public MainFormatContainerModel() {
		// TODO Auto-generated constructor stub
		super(MAIN_FORMAT_OPERATING_CONTAINER);
	}

}
