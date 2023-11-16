package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer.AdditionalSetTypeContainerModelListDeserializer;
import java.util.ArrayList;
import lombok.Data;

@Data
public class AdditionalSetTypeFolderModel extends AbstractCodeControlPaneModel {
	/**
	 * 该类的containerList废弃不用
	 */

	@JSONField(deserializeUsing = AdditionalSetTypeContainerModelListDeserializer.class)
	private ArrayList<AdditionalSetTypeContainerModel> additionalSetTypeContainerModelList = new ArrayList<AdditionalSetTypeContainerModel>();

	public AdditionalSetTypeFolderModel() {
		// TODO Auto-generated constructor stub
		setCodeControlPaneType(ADDITIONAL_TYPE_CODE_CONTROL_PANE);
	}

}
