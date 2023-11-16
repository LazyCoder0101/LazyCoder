package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.code.ContentChooseCodeElement;
import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;

import java.util.ArrayList;


public class GeneralControl {


	/**
	 * 生成组件名称
	 *
	 * @param codeConctrolModel
	 * @param labelElementName
	 * @return
	 */
	public static String generateComponentName(AbstractEditContainerModel codeConctrolModel, String labelElementName) {
		int tempNum = CommandCodeControl.getCurrentLabelNum(codeConctrolModel, labelElementName);
		tempNum++;
		CommandCodeControl.setCurrentLabelNum(codeConctrolModel, labelElementName, tempNum);
		String temp = "";
		if (LabelElementName.TEXT_INPUT.equals(labelElementName)) {
			temp = "t";
		} else if (LabelElementName.FUNCTION_ADD.equals(labelElementName)) {
			temp = "m";
		} else if (LabelElementName.CUSTOM_VARIABLE.equals(labelElementName)) {
			temp = "x";
		} else if (LabelElementName.VARIABLE.equals(labelElementName)) {
			temp = "v";
		} else if (LabelElementName.CONSTANT.equals(labelElementName)) {
			temp = "s";
		} else if (LabelElementName.FILE_SELECTOR.equals(labelElementName)) {
			temp = "f";
		} else if (LabelElementName.NOTE.equals(labelElementName)) {
			temp = "n";
		} else if (LabelElementName.PICTURE.equals(labelElementName)) {
			temp = "p";
		} else if (LabelElementName.CODE_INPUT.equals(labelElementName)) {
			temp = "i";
		} else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelElementName)) {
			temp = "d";
		} else if (LabelElementName.METHOD_CHOOSE.equals(labelElementName)) {
			temp = "y";
		} else if (LabelElementName.INFREQUENTLY_USED_SETTING.equals(labelElementName)) {
			temp = "u";
		} else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelElementName)) {
			temp = "a";
		}
		String out = temp + tempNum;
		return out;
	}

	/**
	 * 删除对应内容选择组件的信息
	 *
	 * @param optionId
	 * @param useNumbered
	 */
	public static void delContentChooseLableInfomation(AbstractEditContainerModel model, String optionId, int useNumbered) {
		ArrayList<BaseElementInterface> controlComponentCorrespondingInformationList = model.getControlComponentCorrespondingInformationList();
		BaseLableElement lableElement;
		ContentChooseElement chooseElement;
		for (int i = controlComponentCorrespondingInformationList.size() - 1; i >= 0; i--) {
			lableElement = (BaseLableElement) controlComponentCorrespondingInformationList.get(i);
			if (lableElement instanceof ContentChooseElement) {
				chooseElement = (ContentChooseElement) lableElement;
				if (chooseElement.getUseNumbered() == useNumbered) {
					if (chooseElement.getOptionId().equals(optionId)) {
						controlComponentCorrespondingInformationList.remove(i);
						break;
					}
				}
			}
		}
	}

	/**
	 * 删除对应内容选择组件的信息
	 *
	 * @param model
	 * @param optionId
	 */
	public static void delContentChooseLableInfomation(AbstractEditContainerModel model, String optionId) {
		ArrayList<BaseElementInterface> controlComponentCorrespondingInformationList = model.getControlComponentCorrespondingInformationList();
		BaseLableElement lableElement;
		ContentChooseElement chooseElement;
		for (int i = controlComponentCorrespondingInformationList.size() - 1; i >= 0; i--) {
			lableElement = (BaseLableElement) controlComponentCorrespondingInformationList.get(i);
			if (lableElement instanceof ContentChooseElement) {
				chooseElement = (ContentChooseElement) lableElement;
				if (chooseElement.getOptionId().equals(optionId)) {
					controlComponentCorrespondingInformationList.remove(i);
					delContentChooseLableInfomation(model, optionId);
					break;
				}
			}
		}
	}

	/**
	 * 查看当前有没有添加过这种选择组件，并根据结果设置 useNumbered 以区分
	 *
	 * @param model
	 * @param contentChooseElement
	 */
	public static void setContentChooseLabelName(AbstractEditContainerModel model, ContentChooseElement contentChooseElement) {
		BaseLableElement lableElement;
		ContentChooseElement elementTemp;

		ArrayList<BaseElementInterface> controlComponentCorrespondingInformationList = model
				.getControlComponentCorrespondingInformationList();

		boolean flag = false;// 此前有无添加过该选项的标记
		for (int i = controlComponentCorrespondingInformationList.size() - 1; i >= 0; i--) {
			lableElement = (BaseLableElement) controlComponentCorrespondingInformationList.get(i);
			if (lableElement instanceof ContentChooseElement) {
				elementTemp = (ContentChooseElement) lableElement;
				if (elementTemp.getOptionId().equals(contentChooseElement.getOptionId())) {
					int useNumbered = elementTemp.getUseNumbered() + 1;
					contentChooseElement.setUseNumbered(useNumbered);
					flag = true;
					break;
				}
			}
		}
		if (flag == false) {
			contentChooseElement.setUseNumbered(1);
		}
	}

	/**
	 * 在控制面板上添加标签组件
	 *
	 * @param model
	 * @param addLabelModel
	 */
	public static void addControlLabel(AbstractEditContainerModel model, BaseLableElement addLabelModel) {
		model.getControlComponentCorrespondingInformationList().add(addLabelModel);
	}

	/**
	 * 获取当前标签的总数
	 *
	 * @return
	 */
	public static int getCurrentLabelNum(AbstractEditContainerModel model, String labelType) {
		Integer temp = model.getUseComponentNum().get(labelType);
		return temp;
	}

	/**
	 * 设置当前放置的标签数量
	 *
	 * @param model
	 * @param labelType
	 * @param num
	 */
	public static void setCurrentLabelNum(AbstractEditContainerModel model, String labelType, int num) {
		model.getUseComponentNum().put(labelType, num);
	}

	/**
	 * 根据代码模型获取内容选择控制组件对应的模型
	 *
	 * @param contentChooseCodeElement
	 * @return
	 */
	public static ContentChooseControl getContentChooseControlModel(AbstractEditContainerModel model, ContentChooseCodeElement contentChooseCodeElement) {
		ContentChooseControl control = null;
		ArrayList<BaseOperatingPane> controlPaneList = model.getAllControlPaneList();
		if (controlPaneList != null) {
			for (BaseOperatingPane operatingPane : controlPaneList) {
				control = operatingPane.getContentChooseControlModel(contentChooseCodeElement);
				if (control != null) {
					break;
				}
			}
		}
		return control;
	}

	/**
	 * 从模型层删除对应标签
	 *
	 * @param model
	 * @param labelType
	 * @param componentName
	 */
	protected static void delCorrespondingLabelFromModel(AbstractEditContainerModel model, String labelType,
														 String componentName) {
		BaseLableElement element;
		ArrayList<BaseElementInterface> list = model.getControlComponentCorrespondingInformationList();
		for (int i = 0; i < list.size(); i++) {
			element = (BaseLableElement) list.get(i);
			if (componentName.equals(element.getThisName())) {
				if (labelType.equals(element.getLabelType())) {
					list.remove(i);
					break;
				}
			}
		}
	}

}
