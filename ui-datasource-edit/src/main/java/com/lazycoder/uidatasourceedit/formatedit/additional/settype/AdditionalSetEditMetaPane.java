package com.lazycoder.uidatasourceedit.formatedit.additional.settype;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.AdditionalSetMetaModel;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalCodeFormatPutPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AbstractEditMetaPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.AdditionalSetEditContainer;
import java.util.ArrayList;

public class AdditionalSetEditMetaPane extends AbstractEditMetaPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -3559171318174451717L;

	private AdditionalSetEditContainer additionalSetEditContainer;

	/**
	 * 新建
	 */
	public AdditionalSetEditMetaPane(int additionalSerialNumber, AdditionalCodeFormatPutPane additionalCodeFormatPutPane, int typeSerialNumber,
									 int operatingOrdinalNumber) {
		// TODO Auto-generated constructor stub
		additionalSetEditContainer = new AdditionalSetEditContainer(additionalSerialNumber, additionalCodeFormatPutPane, typeSerialNumber,
				operatingOrdinalNumber);
		setViewportView(additionalSetEditContainer);
	}

	/**
	 * 还原
	 * @param additionalSetMetaModel
	 * @param additionalCodeFormatPutPane
	 */
	public AdditionalSetEditMetaPane(AdditionalSetMetaModel additionalSetMetaModel, AdditionalCodeFormatPutPane additionalCodeFormatPutPane) {
		additionalSetEditContainer = new AdditionalSetEditContainer(additionalSetMetaModel, additionalCodeFormatPutPane);
		setViewportView(additionalSetEditContainer);
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return additionalSetEditContainer.check();
	}

	@Override
	public String getShowText() {
		// TODO Auto-generated method stub
		return additionalSetEditContainer.getShowText();
	}

	/**
	 * 获取操作层模型
	 *
	 * @return
	 */
	@Override
	public FormatTypeOperatingModel getOperatingModel() {
		return additionalSetEditContainer.getOperatingModel();
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<FormatTypeCodeModel> getCodeModelList() {
		return additionalSetEditContainer.getCodeModelList();
	}

	public int getOrdinal() {
		return additionalSetEditContainer.getOperatingOrdinalNumber();
	}

	/**
	 * 获取代码总数
	 */
	public int getAdditionalSetCodeNum() {
		return additionalSetEditContainer.getAdditionalSetCodeNum();
	}

	@Override
	public ContainerModel getContainerModel() {
		// TODO Auto-generated method stub
		return additionalSetEditContainer.getContainerModel();
	}

}
