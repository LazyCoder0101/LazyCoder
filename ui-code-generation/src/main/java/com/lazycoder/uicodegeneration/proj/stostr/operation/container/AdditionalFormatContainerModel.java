package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.deserializer.AdditionalInfoDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.AdditionalSetTypeFolderModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer.AdditionalSetTypeFolderModelDeserializer;
import lombok.Data;

@Data
public class AdditionalFormatContainerModel extends AbstractFormatOperatingContainerModel {

	private int additionalSerialNumber = 0;

	@JSONField(deserializeUsing = AdditionalInfoDeserializer.class)
	private AdditionalInfo additionalInfo;

	@JSONField(deserializeUsing = AdditionalSetTypeFolderModelDeserializer.class)
	private AdditionalSetTypeFolderModel additionalSetTypeFolderModel = null;

	public AdditionalFormatContainerModel() {
		// TODO Auto-generated constructor stub
		super(ADDITIONAL_FORMAT_OPERATING_CONTAINER);
	}

}
