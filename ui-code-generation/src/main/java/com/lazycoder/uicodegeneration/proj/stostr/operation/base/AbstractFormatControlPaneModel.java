package com.lazycoder.uicodegeneration.proj.stostr.operation.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.common.deserializer.ModuleRelatedParamListDeserializer;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.deserializer.ModuleInfoListDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.BusinessLogicCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.InitCodeControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.deserializer.BusinessLogicCodeControlPaneModelDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.deserializer.InitCodeControlPaneModelDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 格式控制面板（必填模板面板、可选模板面板继承此类）
 *
 * @author admin
 */
@Data
@NoArgsConstructor
public abstract class AbstractFormatControlPaneModel extends BaseFormatStructureModel {

	/**
	 * 必填模板格式面板
	 */
	public static final String MAIN_FORMAT_CONTROL_PANE = "mainFormatControlPane";
	/**
	 * 可选模板格式面板
	 */
	public static final String ADDITIONAL_FORMAT_CONTROL_PANE = "additionalFormatControlPane";

	@JSONField(deserializeUsing = InitCodeControlPaneModelDeserializer.class)
	private InitCodeControlPaneModel initCodeControlPaneModel;

	@JSONField(deserializeUsing = BusinessLogicCodeControlPaneModelDeserializer.class)
	private BusinessLogicCodeControlPaneModel businessLogicCodeControlPaneModel;

	private String fileName = "";

	@JSONField(deserializeUsing = ModuleInfoListDeserializer.class)
	private ArrayList<ModuleInfo> useModuleList = new ArrayList<>();

	@JSONField(deserializeUsing = ModuleRelatedParamListDeserializer.class)
	private ArrayList<ModuleRelatedParam> useModuleRelatedParamList = new ArrayList<>();

	private String formatControlPaneType;

	private boolean haveBusinessLogicMarkFlag = true;

	public AbstractFormatControlPaneModel(String formatControlPaneType) {
		// TODO Auto-generated constructor stub
		this.formatControlPaneType = formatControlPaneType;
	}

}
