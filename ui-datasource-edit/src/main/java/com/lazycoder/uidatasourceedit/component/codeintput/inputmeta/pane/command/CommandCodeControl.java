package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command;

import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.datasourceedit.command.FunctionCodePaneInterface;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.utils.MapUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 控制面板和代码的控制
 *
 * @author Administrator
 */
public class CommandCodeControl extends GeneralControl {

	/**
	 * 构建一个新的控制面板
	 *
	 * @param directPane
	 * @param hiddenPane
	 * @param model
	 */
	public static void buildingNewOperatingPane(AbstractFunctionControlInputPane directPane,
                                                AbstractFunctionControlInputPane hiddenPane, ContainerModel model) {
		directPane.setModel(model);
		model.setMainFunctionControl(directPane);

		hiddenPane.setModel(model);
		model.setHiddenFunctionControl(hiddenPane);
	}

	/**
	 * 构建原来的控制面板
	 *
	 * @param directPane
	 * @param hiddenPane
	 * @param model
	 * @param operatingModel
	 */
	public static void buildingOriginalOperatingPane(AbstractFunctionControlInputPane directPane,
                                                     AbstractFunctionControlInputPane hiddenPane, ContainerModel model,
                                                     GeneralCommandOperatingModel operatingModel) {
		directPane.setModel(model);
		model.setMainFunctionControl(directPane);

		hiddenPane.setModel(model);
		model.setHiddenFunctionControl(hiddenPane);

		model.reductionContent(operatingModel);
		ArrayList<BaseElementInterface> defaultElementList = DeserializeElementMethods.getControlPaneElmentList(operatingModel.getDefaultControlStatementFormat());
		directPane.reductionContent(defaultElementList);
		ArrayList<BaseElementInterface> hiddenElementList = DeserializeElementMethods.getControlPaneElmentList(operatingModel.getHiddenControlStatementFormat());
		hiddenPane.reductionContent(hiddenElementList);
	}

	public static void setOperating(GeneralCommandOperatingModel operatingModel, ContainerModel model) {
		operatingModel.setControlComponentCorrespondingInformation(
				AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(model));
		operatingModel.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(model.getUseComponentNum()));
	}

	/**
	 * 删除代码面板（最后一个）
	 *
	 * @param model
	 */
	public static void delLastFunctionCode(AbstractEditContainerModel model) {
		Entry<Integer, FunctionCodePaneInterface> lastElement = MapUtil
				.getLastElement(((ContainerModel) model).getFunctionCodes());
		((ContainerModel) model).getFunctionCodes().remove(lastElement.getKey());
	}

	/**
	 * 通知代码面板更新菜单
	 */
	public static void updateCodePaneMenu(AbstractEditContainerModel model) {
		Map<Integer, FunctionCodePaneInterface> list = ((ContainerModel) model).getFunctionCodes();
		for (Integer key : list.keySet()) {
			list.get(key).updateMenu();
		}
	}

	/**
	 * 删除对应控制模型以及删除
	 *
	 * @param model
	 * @param labelType
	 * @param componentName
	 */
	public static void delControlLabel(AbstractEditContainerModel model, String labelType, String componentName) {
		delCorrespondingLabelFromModel(model, labelType, componentName);
		delCorrespondingLabelFromCodePane(model, labelType, componentName);
		updateCodePaneMenu(model);
	}

	/**
	 * 在控制面板上添加标签组件 通知其他代码模板更新
	 *
	 * @param model
	 * @param addLabelModel 进行操作如下 把对应控制组件的模型添加到总模型 通知总模型的其他的源码面板更新
	 */
	public static void addControlLabelAndUpdateCodePaneMenu(AbstractEditContainerModel model, BaseLableElement addLabelModel) {
		model.getControlComponentCorrespondingInformationList().add(addLabelModel);
		updateCodePaneMenu(model);
	}

	/**
	 * 从代码面板删除对应标签
	 *
	 * @param model
	 * @param labelType
	 * @param componentName
	 */
	private static void delCorrespondingLabelFromCodePane(AbstractEditContainerModel model, String labelType,
														  String componentName) {
		Map<Integer, FunctionCodePaneInterface> functions = ((ContainerModel) model).getFunctionCodes();
		for (Integer key : functions.keySet()) {
			functions.get(key).delLabel(labelType, componentName);
		}
	}

	public static void clear(ContainerModel model) {
		model.clear();
		if (model.getMainFunctionControl() != null) {
			model.getMainFunctionControl().clear();
		}
		if (model.getHiddenFunctionControl() != null) {
			model.getHiddenFunctionControl().clear();
		}
		if (model.getFunctionCodes() != null) {
			for (Integer key : model.getFunctionCodes().keySet()) {
				model.getFunctionCodes().get(key).clear();
			}
		}
	}

	public static void delContentChoose(AbstractEditContainerModel model, String optionId, int useNumbered) {
		ContainerModel containerModel = (ContainerModel) model;
		delContentChooseLableInfomation(containerModel, optionId, useNumbered);
		LinkedHashMap<Integer, FunctionCodePaneInterface> functionPaneList = containerModel.getFunctionCodes();
		for (Integer key : functionPaneList.keySet()) {
			functionPaneList.get(key).delContentChooseLable(optionId, useNumbered);
			functionPaneList.get(key).updateMenu();
		}
	}

	public static void delContentChoose(AbstractEditContainerModel model, String optionId) {
		ContainerModel containerModel = (ContainerModel) model;
		delContentChooseLableInfomation(containerModel, optionId);
		LinkedHashMap<Integer, FunctionCodePaneInterface> functionPaneList = containerModel.getFunctionCodes();
		for (Integer key : functionPaneList.keySet()) {
			functionPaneList.get(key).delContentChooseLable(optionId);
			functionPaneList.get(key).updateMenu();
		}
	}


}
