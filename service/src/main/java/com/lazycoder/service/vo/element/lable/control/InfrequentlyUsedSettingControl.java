package com.lazycoder.service.vo.element.lable.control;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.element.lable.InfrequentlyUsedSettingElement;
import com.lazycoder.utils.JsonUtil;
import java.io.File;
import java.util.ArrayList;
import lombok.Data;

/**
 * 不常用设置
 *
 * @author Administrator
 */
@Data
public class InfrequentlyUsedSettingControl extends InfrequentlyUsedSettingElement {

	/**
	 * 控制语句格式
	 */
	private String controlStatementFormat = "";

	public InfrequentlyUsedSettingControl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public InfrequentlyUsedSettingElement controlComponentInformation() {
		InfrequentlyUsedSettingElement element = new InfrequentlyUsedSettingElement();
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

		ArrayList<BaseElementInterface> elementListTemp = DeserializeElementMethods.getControlPaneElmentList(controlStatementFormat);
		for (BaseElementInterface element : elementListTemp) {
			fileTempList = DeserializeElementMethods.getInternalFSCorrespondingFolder(parentFolder, element);
			fileArrayList.addAll(fileTempList);
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

		ArrayList<BaseElementInterface> elementListTemp = DeserializeElementMethods.getControlPaneElmentList(controlStatementFormat);
		for (BaseElementInterface element : elementListTemp) {
			fileTempList = DeserializeElementMethods.getInternalPictureCorrespondingFolder(parentFolder, element);
			fileArrayList.addAll(fileTempList);
		}
		return fileArrayList;
	}

	/**
	 * 更改内部的对应选项
	 */
	public void updateControlStatementInternalCorrespondingContentChooseModel(OptionDataModel option) {

		ArrayList<BaseElementInterface> elementListTemp = DeserializeElementMethods.getControlPaneElmentList(controlStatementFormat);
		for (BaseElementInterface element : elementListTemp) {
			DeserializeElementMethods.updateControlStatementInternalCorrespondingContentChooseModel(option, element);
		}
		setControlStatementFormat(JsonUtil.getJsonStr(elementListTemp));
	}

	/**
	 * 删除内部的对应选项
	 */
	public InfrequentlyUsedSettingControl delControlStatementInternalCorrespondingContentChooseModel(OptionDataModel option) {

		InfrequentlyUsedSettingControl newInfrequentlyUsedSettingControl = this;
		ArrayList<BaseElementInterface> sourceElementListTemp = DeserializeElementMethods.getControlPaneElmentList(controlStatementFormat);
		ArrayList<BaseElementInterface> elementListTemp = DeserializeElementMethods.delControlStatementInternalCorrespondingContentChooseModel(option, sourceElementListTemp);
		newInfrequentlyUsedSettingControl.setControlStatementFormat(JsonUtil.getJsonStr(elementListTemp));
		return newInfrequentlyUsedSettingControl;
	}


}
