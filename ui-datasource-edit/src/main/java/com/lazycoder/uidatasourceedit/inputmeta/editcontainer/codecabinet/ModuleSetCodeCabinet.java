package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier.ModuleSetCodeTier;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;

public class ModuleSetCodeCabinet extends AbstractCodeCabinet {

	/**
	 *
	 */
	private static final long serialVersionUID = 7306877878749024048L;

	private int typeSerialNumber;

	/**
	 * @param theModel
	 * @param operatingOrdinal
	 * @param expanded
	 * @param proportion
	 */
	public ModuleSetCodeCabinet(ContainerModel theModel, int typeSerialNumber, int operatingOrdinal, boolean expanded,
								double proportion) {
		super(expanded, proportion);
		this.operatingOrdinalNumber = operatingOrdinal;
		this.typeSerialNumber = typeSerialNumber;
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<ModuleSetCodeModel> getCodeModelList() {
		ArrayList<ModuleSetCodeModel> list = new ArrayList<>();
		ModuleSetCodeTier tempPane;
		ModuleSetCodeModel temp;
		for (int i = 0; i < getVBox().getComponentCount(); i++) {
			tempPane = (ModuleSetCodeTier) getVBox().getComponent(i);
			temp = tempPane.getCodeModel();
			temp.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
			temp.setOrdinal(this.operatingOrdinalNumber);
			temp.setTypeSerialNumber(this.typeSerialNumber);
			temp.setCodeOrdinal(i + 1);
			list.add(temp);
		}
		return list;
	}

	@Override
	public void addCodePane(ContainerModel theModel) {
		int codeNum = getCodeNum() + 1;
		//setCodeNum(codeNum);

		ModuleSetCodeTier moduleSetCodeInputPane = new ModuleSetCodeTier(typeSerialNumber, operatingOrdinalNumber, codeNum,
				theModel);
		getVBox().add(moduleSetCodeInputPane);
		CommandCodeControl.updateCodePaneMenu(theModel);
		SysUtil.scrollToBottom(scrollPane);
//		super.addCodePane(theModel);
	}

	/**
	 * 还原内容
	 *
	 * @param codeModelList
	 * @param theModel
	 */
	public void reductionContent(ArrayList<ModuleSetCodeModel> codeModelList, ContainerModel theModel,
								 int operatingOrdinalNumber) {
		// TODO Auto-generated method stub
		this.operatingOrdinalNumber = operatingOrdinalNumber;
		ModuleSetCodeTier moduleSetCodeInputPane;
		if (codeModelList != null) {
			for (ModuleSetCodeModel temp: codeModelList) {
				//setCodeNum(temp.getCodeOrdinal());
				moduleSetCodeInputPane = new ModuleSetCodeTier(typeSerialNumber, operatingOrdinalNumber,
						temp.getCodeOrdinal(), theModel);
				moduleSetCodeInputPane.reductionContent(temp);
				getVBox().add(moduleSetCodeInputPane);
			}
		}
//		this.updateUI();
//		this.repaint();
	}

	/**
	 * 获取代码总数
	 *
	 * @return
	 */
	public int getModuleSetCodeNum() {
		return getVBox().getComponentCount();
	}

}