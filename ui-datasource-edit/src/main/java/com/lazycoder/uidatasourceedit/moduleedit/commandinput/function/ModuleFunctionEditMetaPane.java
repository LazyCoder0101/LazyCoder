package com.lazycoder.uidatasourceedit.moduleedit.commandinput.function;

import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.command.FunctionOperatingModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.FunctionMetaModel;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.FunctionEditContainer;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AbstractEditMetaPane;

import java.util.ArrayList;

public class ModuleFunctionEditMetaPane extends AbstractEditMetaPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 7876702972323442020L;

	private FunctionEditContainer functionEditContainer;


	/**
	 * 新建
	 */
	public ModuleFunctionEditMetaPane(int typeSerialNumber, int ordinal) {
		// TODO Auto-generated constructor stub
		functionEditContainer = new FunctionEditContainer(typeSerialNumber, ordinal);
		setViewportView(functionEditContainer);
	}

	/**
	 * 还原
	 */
	public ModuleFunctionEditMetaPane(int typeSerialNumber, FunctionMetaModel functionMetaModel) {
		// TODO Auto-generated constructor stub
		functionEditContainer = new FunctionEditContainer(typeSerialNumber, functionMetaModel);
		setViewportView(functionEditContainer);
	}

	@Override
	public String getShowText() {
		// TODO Auto-generated method stub
		return functionEditContainer.getShowText();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return functionEditContainer.check();
	}

	/**
	 * 获取操作层模型
	 *
	 * @return
	 */
	@Override
	public FunctionOperatingModel getOperatingModel() {
		return functionEditContainer.getOperatingModel();
	}

	/**
	 * 获取代码模型
	 *
	 * @return
	 */
	public ArrayList<FunctionCodeModel> getCodeModelList() {
		return functionEditContainer.getCodeModelList();
	}

	@Override
	public ContainerModel getContainerModel() {
		// TODO Auto-generated method stub
		return functionEditContainer.getContainerModel();
	}
}
