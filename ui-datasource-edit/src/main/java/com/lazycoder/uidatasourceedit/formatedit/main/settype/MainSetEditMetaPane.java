package com.lazycoder.uidatasourceedit.formatedit.main.settype;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.MainSetMetaModel;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.MainSetEditContainer;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AbstractEditMetaPane;
import java.util.ArrayList;

public class MainSetEditMetaPane extends AbstractEditMetaPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -3559171318174451717L;

	private MainSetEditContainer mainSetEditContainer;

	/**
	 * 新建
	 */
	public MainSetEditMetaPane(int typeSerialNumber, int ordinal) {
		// TODO Auto-generated constructor stub
		mainSetEditContainer = new MainSetEditContainer(typeSerialNumber, ordinal);
		setViewportView(mainSetEditContainer);
	}

	/**
	 * 还原
	 *
	 * @param mainSetMetaModel
	 */
	public MainSetEditMetaPane(MainSetMetaModel mainSetMetaModel) {
		mainSetEditContainer = new MainSetEditContainer(mainSetMetaModel);
		setViewportView(mainSetEditContainer);
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return mainSetEditContainer.check();
	}

	/**
	 * 获取操作层模型
	 *
	 * @return
	 */
	@Override
	public FormatTypeOperatingModel getOperatingModel() {
		return mainSetEditContainer.getOperatingModel();
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<FormatTypeCodeModel> getCodeModelList() {
		return mainSetEditContainer.getCodeModelList();
	}

	public int getOrdinal() {
		return mainSetEditContainer.getOperatingOrdinalNumber();
	}

	/**
	 * 获取代码总数
	 */
	public int getMainSetCodeNum() {
		return mainSetEditContainer.getMainSetCodeNum();
	}

	@Override
	public ContainerModel getContainerModel() {
		return mainSetEditContainer.getContainerModel();
	}

	@Override
	public String getShowText() {
		// TODO Auto-generated method stub
		return mainSetEditContainer.getShowText();
	}

}
