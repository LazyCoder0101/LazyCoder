package com.lazycoder.uicodegeneration.proj.stostr.operation.containerput;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer.MainSetTypeContainerModelListDeserializer;
import java.util.ArrayList;
import lombok.Data;

@Data
public class MainSetTypeFolderModel extends AbstractCodeControlPaneModel {
	/**
	 * 该类的containerList废弃不用
	 */
	@JSONField(deserializeUsing = MainSetTypeContainerModelListDeserializer.class)
	private ArrayList<MainSetTypeContainerModel> mainSetTypeContainerModelList = new ArrayList<MainSetTypeContainerModel>();

	public MainSetTypeFolderModel() {
		// TODO Auto-generated constructor stub
		setCodeControlPaneType(MAIN_TYPE_CODE_CONTROL_PANE);
	}


}
