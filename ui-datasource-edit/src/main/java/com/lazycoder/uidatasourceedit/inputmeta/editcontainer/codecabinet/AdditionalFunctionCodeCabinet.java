package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier.AdditionalFunctionCodeTier;
import com.lazycoder.uiutils.utils.SysUtil;

import java.util.ArrayList;

public class AdditionalFunctionCodeCabinet extends AbstractCodeCabinet {

	/**
	 *
	 */
	private static final long serialVersionUID = -4813759324576315110L;

	private int additionalSerialNumber = 0;

	public AdditionalFunctionCodeCabinet(int additionalSerialNumber, ContainerModel theModel, int operatingOrdinal,
										 boolean expanded, double proportion) {
		super(expanded, proportion);
		this.additionalSerialNumber = additionalSerialNumber;
		this.operatingOrdinalNumber = operatingOrdinal;
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<AdditionalFunctionCodeModel> getCodeModelList() {
		ArrayList<AdditionalFunctionCodeModel> list = new ArrayList<>();
		AdditionalFunctionCodeTier temp;
		AdditionalFunctionCodeModel model;
		for (int i = 0; i < getVBox().getComponentCount(); i++) {
			temp = (AdditionalFunctionCodeTier) getVBox().getComponent(i);
			model = temp.getCodeModel();
			model.setOrdinal(operatingOrdinalNumber);
			model.setCodeOrdinal(i + 1);
			list.add(model);
		}
		return list;
	}

	/**
	 * 保存所有代码
	 */
//	public void saveAllCode() {
//
//	}
	@Override
	public void addCodePane(ContainerModel theModel) {
		int codeNum = getCodeNum() + 1;
		//setCodeNum(codeNum);

		AdditionalFunctionCodeTier additionalFunctionCodeTier = new AdditionalFunctionCodeTier(additionalSerialNumber, theModel,
				operatingOrdinalNumber, codeNum);
		getVBox().add(additionalFunctionCodeTier);

		CommandCodeControl.updateCodePaneMenu(theModel);
		SysUtil.scrollToBottom(scrollPane);
		super.addCodePane(theModel);
	}

	/**
	 * 还原内容
	 *
	 * @param codeModelList
	 * @param theModel
	 */
	public void reductionContent(ArrayList<AdditionalFunctionCodeModel> codeModelList, ContainerModel theModel) {
		// TODO Auto-generated method stub
		AdditionalFunctionCodeModel temp;
		AdditionalFunctionCodeTier initCodeInputPane;
		if (codeModelList != null) {
			for (int i = 0; i < codeModelList.size(); i++) {
				temp = codeModelList.get(i);
				//setCodeNum(temp.getCodeOrdinal());
				initCodeInputPane = new AdditionalFunctionCodeTier(additionalSerialNumber, theModel, this.operatingOrdinalNumber,
						temp.getCodeOrdinal());
				initCodeInputPane.reductionContent(temp);
				getVBox().add(initCodeInputPane);
			}
		}
		this.updateUI();
		this.repaint();
	}

}
