package com.lazycoder.service.vo.datasourceedit.command;

import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.service.vo.base.BaseCodePane;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import lombok.Data;

/**
 * 基本代码控制模型
 *
 * @author Administrator
 */
@Data
public class ContainerModel extends AbstractEditContainerModel {

	/**
	 * 默认控制
	 */
	private FunctionControlPaneInterface mainFunctionControl;

	/**
	 * 隐藏控制
	 */
	private FunctionControlPaneInterface hiddenFunctionControl;

	/**
	 * 对应所有输入源码
	 */
	private LinkedHashMap<Integer, FunctionCodePaneInterface> functionCodes = new LinkedHashMap<>();

	/**
	 * 新建
	 */
	public ContainerModel() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 还原
	 *
	 * @param commandOperatingModel
	 */
	public ContainerModel(GeneralCommandOperatingModel commandOperatingModel) {
		this();
		reductionContent(commandOperatingModel);
	}

	@Override
	public ArrayList<BaseOperatingPane> getAllControlPaneList() {
		// TODO Auto-generated method stub
		ArrayList<BaseOperatingPane> list = new ArrayList<BaseOperatingPane>();
		list.add(mainFunctionControl);
		list.add(hiddenFunctionControl);
		list.addAll(super.getAllControlPaneList());
		return list;
	}

	@Override
	public ArrayList<BaseCodePane> getCodePaneList() {
		// TODO Auto-generated method stub
		ArrayList<BaseCodePane> codePanelist = new ArrayList<>();
		for (Integer key : functionCodes.keySet()) {
			codePanelist.add(functionCodes.get(key));
		}
		return codePanelist;
	}


}
