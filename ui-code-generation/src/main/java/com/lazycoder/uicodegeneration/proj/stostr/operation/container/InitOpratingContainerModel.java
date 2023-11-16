package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import com.lazycoder.database.model.featureSelectionModel.deserializer.InitFeatureSelectionListDeserializer;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.service.vo.meta.deserializer.InitMetaModelDeserializer;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.SettingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.deserializer.SettingContainerModelDeserializer;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class InitOpratingContainerModel extends AbstractCommandOperatingContainerModel {

	private boolean initContainerState = true;

	/**
	 * 设置按钮使用状态
	 */
	private boolean setButtonEnableState = false;

	private String moduleId = null;

	/**
	 * 初始化列表信息
	 */
	@JSONField(deserializeUsing = InitFeatureSelectionListDeserializer.class)
	private List<InitFeatureSelectonModel> initList = new ArrayList<>();

	/**
	 * 当前的初始化模型
	 */
	@JSONField(deserializeUsing = InitMetaModelDeserializer.class)
	private InitMetaModel currentInitModel;

	@JSONField(deserializeUsing = SettingContainerModelDeserializer.class)
	private SettingContainerModel settingContainerModel = null;

	public InitOpratingContainerModel() {
		// TODO Auto-generated constructor stub
		super(INIT_OPERATING_CONTAINER);
	}


}