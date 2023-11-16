package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.database.model.command.AdditionalFunctionOperatingModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.AdditionalFunctionMetaModel;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AdditionalFunctionEditContainer;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AbstractEditMetaPane;

import java.util.ArrayList;

public class AdditionalFunctionEditMetaPane extends AbstractEditMetaPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 7876702972323442020L;

	private AdditionalFunctionEditContainer functionContainer;

	/**
	 * 新建
	 */
	public AdditionalFunctionEditMetaPane(int additionalSerialNumber, int ordinal) {
		// TODO Auto-generated constructor stub
		functionContainer = new AdditionalFunctionEditContainer(additionalSerialNumber, ordinal);
		setViewportView(functionContainer);
	}

	/**
	 * 还原
	 */
	public AdditionalFunctionEditMetaPane(AdditionalFunctionMetaModel metaModel) {
		// TODO Auto-generated constructor stub
		functionContainer = new AdditionalFunctionEditContainer(metaModel);
		setViewportView(functionContainer);
	}

	@Override
	public String getShowText() {
		// TODO Auto-generated method stub
		return functionContainer.getShowText();
	}

	/**
	 * 获取操作层模型
	 *
	 * @return
	 */
	@Override
	public AdditionalFunctionOperatingModel getOperatingModel() {
		return functionContainer.getOperatingModel();
	}

	/**
	 * 获取代码模型
	 *
	 * @return
	 */
	public ArrayList<AdditionalFunctionCodeModel> getCodeModelList() {
		return functionContainer.getCodeModelList();
	}

	@Override
	public ContainerModel getContainerModel() {
		// TODO Auto-generated method stub
		return functionContainer.getContainerModel();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return functionContainer.check();
	}
}
