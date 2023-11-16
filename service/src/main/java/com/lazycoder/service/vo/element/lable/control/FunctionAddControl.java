package com.lazycoder.service.vo.element.lable.control;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.element.lable.FunctionAddElement;
import com.lazycoder.service.vo.element.lable.control.functionadd.FunctionAddControlFunctionListDeserializer;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.utils.JsonUtil;
import java.io.File;
import java.util.ArrayList;
import lombok.Data;

/**
 * 功能拓展
 *
 * @author Administrator
 */
@Data
public class FunctionAddControl extends FunctionAddElement {


	/**
	 * 只允许使用内部方法
	 */
	private boolean onlInternalCodeIsAllowed = false;

	private int otherAttribute = CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE;

	/**
	 * 方法列表
	 */
	@JSONField(deserializeUsing = FunctionAddControlFunctionListDeserializer.class)
	private ArrayList<MethodAddStorageFormat> functionList = new ArrayList<>();


	public FunctionAddControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public FunctionAddElement controlComponentInformation() {
		FunctionAddElement element = new FunctionAddElement();
		element.setThisName(getThisName());
		return element;
	}

	/**
	 * 获取内部文件选择组件对应的文件夹
	 *
	 * @param parentFolder
	 * @return
	 */
	public ArrayList<File> getInternalFSCorrespondingFolder(File parentFolder) {
		ArrayList<File> fileArrayList = new ArrayList<>(), fileTempList;
		ArrayList<BaseElementInterface> elementListTemp;
		for (MethodAddStorageFormat methodAddStorageFormat : functionList) {

			elementListTemp = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getDefaultControlStatementFormat());
			for (BaseElementInterface element : elementListTemp) {
				fileTempList = DeserializeElementMethods.getInternalFSCorrespondingFolder(parentFolder, element);
				fileArrayList.addAll(fileTempList);
			}

			elementListTemp = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getHiddenControlStatementFormat());
			for (BaseElementInterface element : elementListTemp) {
				fileTempList = DeserializeElementMethods.getInternalFSCorrespondingFolder(parentFolder, element);
				fileArrayList.addAll(fileTempList);
			}
		}
		return fileArrayList;
	}

	/**
	 * 获取图片组件对应的文件夹
	 *
	 * @param parentFolder
	 * @return
	 */
	public ArrayList<File> getInternalPictureCorrespondingFolder(File parentFolder) {
		ArrayList<File> fileArrayList = new ArrayList<>(), fileTempList;
		ArrayList<BaseElementInterface> elementListTemp;
		for (MethodAddStorageFormat methodAddStorageFormat : functionList) {

			elementListTemp = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getDefaultControlStatementFormat());
			for (BaseElementInterface element : elementListTemp) {
				fileTempList = DeserializeElementMethods.getInternalPictureCorrespondingFolder(parentFolder, element);
				fileArrayList.addAll(fileTempList);
			}

			elementListTemp = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getHiddenControlStatementFormat());
			for (BaseElementInterface element : elementListTemp) {
				fileTempList = DeserializeElementMethods.getInternalPictureCorrespondingFolder(parentFolder, element);
				fileArrayList.addAll(fileTempList);
			}
		}
		return fileArrayList;
	}

	/**
	 * 更改内部的对应选项
	 */
	public void updateControlStatementInternalCorrespondingContentChooseModel(OptionDataModel option) {
		ArrayList<BaseElementInterface> elementListTemp;
		for (MethodAddStorageFormat methodAddStorageFormat : functionList) {

			elementListTemp = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getDefaultControlStatementFormat());
			for (BaseElementInterface element : elementListTemp) {
				DeserializeElementMethods.updateControlStatementInternalCorrespondingContentChooseModel(option, element);
			}
			methodAddStorageFormat.setDefaultControlStatementFormat(JsonUtil.getJsonStr(elementListTemp));

			elementListTemp = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getHiddenControlStatementFormat());
			for (BaseElementInterface element : elementListTemp) {
				DeserializeElementMethods.updateControlStatementInternalCorrespondingContentChooseModel(option, element);
			}
			methodAddStorageFormat.setHiddenControlStatementFormat(JsonUtil.getJsonStr(elementListTemp));

			elementListTemp = DeserializeElementMethods.getCodePaneElementList(methodAddStorageFormat.getCodeStatementFormat());
			for (BaseElementInterface element : elementListTemp) {
				DeserializeElementMethods.updateCodeStatementInternalCorrespondingContentChooseModel(option, element);
			}
			methodAddStorageFormat.setCodeStatementFormat(JsonUtil.getJsonStr(elementListTemp));
		}
	}

	/**
	 * 删除内部的对应选项
	 */
	public FunctionAddControl delControlStatementInternalCorrespondingContentChooseModel(OptionDataModel option) {
		FunctionAddControl newFunctionAddControl = this;
		ArrayList<BaseElementInterface> elementListTemp, sourceElementListTemp, element = new ArrayList<>();
		for (MethodAddStorageFormat methodAddStorageFormat : newFunctionAddControl.functionList) {

			sourceElementListTemp = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getDefaultControlStatementFormat());//改默认控制面板的生成参数
			elementListTemp = DeserializeElementMethods.delControlStatementInternalCorrespondingContentChooseModel(option, sourceElementListTemp);
			methodAddStorageFormat.setDefaultControlStatementFormat(JsonUtil.getJsonStr(elementListTemp));

			sourceElementListTemp = DeserializeElementMethods.getControlPaneElmentList(methodAddStorageFormat.getHiddenControlStatementFormat());//改隐藏控制面板的生成参数
			elementListTemp = DeserializeElementMethods.delControlStatementInternalCorrespondingContentChooseModel(option, sourceElementListTemp);
			methodAddStorageFormat.setHiddenControlStatementFormat(JsonUtil.getJsonStr(elementListTemp));

			sourceElementListTemp = DeserializeElementMethods.getControlComponentCorrespondingInformationList(methodAddStorageFormat.getControlComponentCorrespondingInformation());//改代码面板的添加组件信息参数
			elementListTemp = DeserializeElementMethods.delContentChooseControlComponentCorrespondingInformation(option.getOptionId(), sourceElementListTemp);
			methodAddStorageFormat.setControlComponentCorrespondingInformation(JsonUtil.getJsonStr(elementListTemp));

			sourceElementListTemp = DeserializeElementMethods.getCodePaneElementList(methodAddStorageFormat.getCodeStatementFormat());//改代码面板的生成参数
			elementListTemp = DeserializeElementMethods.delCodeStatementInternalCorrespondingContentChooseModel(option, sourceElementListTemp);
			methodAddStorageFormat.setCodeStatementFormat(JsonUtil.getJsonStr(elementListTemp));

		}
		return newFunctionAddControl;
	}

}
