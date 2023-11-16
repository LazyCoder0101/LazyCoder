package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleset;

import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.ModuleSetMetaModel;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AbstractEditMetaPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.ModuleSetEditContainer;

import java.util.ArrayList;


public class ModuleSetEditMetaPane extends AbstractEditMetaPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -3559171318174451717L;

	private ModuleSetEditContainer moduleSetEditContainer;

	/**
	 * 测试
	 */
	public ModuleSetEditMetaPane() {
		// TODO Auto-generated constructor stub
		moduleSetEditContainer = new ModuleSetEditContainer();
		setViewportView(moduleSetEditContainer);
	}

	/**
	 * 新建
	 */
	public ModuleSetEditMetaPane(int moduleSetSerial, int ordinal) {
		// TODO Auto-generated constructor stub
		moduleSetEditContainer = new ModuleSetEditContainer(moduleSetSerial, ordinal);
		setViewportView(moduleSetEditContainer);
	}

	/**
	 * 还原
	 *
	 * @param moduleSetMetaModel
	 */
	public ModuleSetEditMetaPane(ModuleSetMetaModel moduleSetMetaModel) {
		moduleSetEditContainer = new ModuleSetEditContainer(moduleSetMetaModel);
		setViewportView(moduleSetEditContainer);
	}

	@Override
	public String getShowText() {
		// TODO Auto-generated method stub
		return moduleSetEditContainer.getShowText();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return moduleSetEditContainer.check();
	}

	/**
	 * 获取操作层模型
	 *
	 * @return
	 */
	@Override
	public ModuleSetOperatingModel getOperatingModel() {
		return moduleSetEditContainer.getOperatingModel();
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<ModuleSetCodeModel> getCodeModelList() {
		return moduleSetEditContainer.getCodeModelList();
	}

	public int getOrdinal() {
		return moduleSetEditContainer.getOperatingOrdinalNumber();
	}

	/**
	 * 获取代码总数
	 */
	public int getModuleSetCodeNum() {
		return moduleSetEditContainer.getModuleSetCodeNum();
	}

	@Override
	public ContainerModel getContainerModel() {
		// TODO Auto-generated method stub
		return moduleSetEditContainer.getContainerModel();
	}

}
