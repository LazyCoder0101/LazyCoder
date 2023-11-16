package com.lazycoder.uicodegeneration.proj.stostr.operation.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.uicodegeneration.proj.deserializer.GeneralCodeControlPaneModelDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存放控制功能容器的面板模型
 *
 * @author admin
 */
@Data
@NoArgsConstructor
public abstract class AbstractCodeControlPaneModel extends BaseFormatStructureModel {

	/**
	 * 业务逻辑代码面板
	 */
	public static final String BUSINESS_LOGIC_CODE_CONTROL_PANE = "businessLogicCodeControlPane";

	/**
	 * 初始化代码面板
	 */
	public static final String INIT_CODE_CONTROL_PANE = "initCodeControlPane";

	/**
	 * 功能拓展组件内部面板
	 */
	public static final String FUNCTION_ADD_CODE_CONTROL_PANE = "functionAddCodeControlPane";

	/**
	 * 设置面板
	 */
	public static final String MODULE_TYPE_CODE_CONTROL_PANE = "moduleTypeCodeControlPane";

	/**
	 * 设置按钮里面的内容面板
	 */
	public static final String SETTING_CONTAINER = "settingContainer";

	/**
	 * 必填模板的类型模板
	 */
	public static final String MAIN_TYPE_CODE_CONTROL_PANE = "mainTypeCodeControlPane";

	/**
	 * 可选模板的类型模板
	 */
	public static final String ADDITIONAL_TYPE_CODE_CONTROL_PANE = "additionalTypeCodeControlPane";

	/**
	 * 放置代码容器的面板的类型
	 */
	private String codeControlPaneType;

	/**
	 * 功能容器列表参数
	 */
	@JSONField(deserializeUsing = GeneralCodeControlPaneModelDeserializer.class)
	private ArrayList<AbstractOperatingContainerModel> containerList = new ArrayList<>();

	public AbstractCodeControlPaneModel(String codeControlPaneType) {
		// TODO Auto-generated constructor stub
		this.codeControlPaneType = codeControlPaneType;
	}


}
