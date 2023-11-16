package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.init;

import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.command.InitOperatingModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.InitEditContainer;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AbstractEditMetaPane;

import java.util.ArrayList;

public class InitEditMetaPane extends AbstractEditMetaPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1039299075951893107L;

	private InitEditContainer initEditContainer;

	private int ordinal = 0;

	/**
	 * 测试使用
	 */
	// public InitMetaPane() {
	// // TODO Auto-generated constructor stub
	// initContainer = new InitContainer();
	// setViewportView(initContainer);
	// }

	/**
	 * 新建
	 *
	 * @param ordinal
	 * @param setToDefault
	 */
	public InitEditMetaPane(int ordinal, boolean setToDefault) {
		this.ordinal = ordinal;
		initEditContainer = new InitEditContainer(ordinal);
		setViewportView(initEditContainer);
		if (setToDefault == true) {
			initEditContainer.setToDefault();
		}
	}

	/**
	 * 还原
	 *
	 * @param initMetaModel
	 */
	public InitEditMetaPane(InitMetaModel initMetaModel) {
		initEditContainer = new InitEditContainer(initMetaModel);
		this.ordinal = initMetaModel.getOperatingModel().getOrdinal();
		setViewportView(initEditContainer);
	}

	@Override
	public String getShowText() {
		// TODO Auto-generated method stub
		return initEditContainer.getShowText();
	}

	/**
	 * 获取操作层模型
	 *
	 * @return
	 */
	@Override
	public InitOperatingModel getOperatingModel() {
		return initEditContainer.getOperatingModel();
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<InitCodeModel> getCodeModelList() {
		return initEditContainer.getCodeModelList();
	}

	/**
	 * 获取该方法序号
	 *
	 * @return
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * 获取代码条数
	 *
	 * @return
	 */
	public int getCodeNum() {
		return initEditContainer.getCodeNum();
	}

	@Override
	public ContainerModel getContainerModel() {
		// TODO Auto-generated method stub
		return initEditContainer.getContainerModel();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return initEditContainer.check();
	}

}
