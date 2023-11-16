package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalCodeFormatPutPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier.AdditionalSetCodeTier;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;

public class AdditionalSetCodeCabinet extends AbstractCodeCabinet {

	/**
	 *
	 */
	private static final long serialVersionUID = 7306877878749024048L;

	private int additionalSerialNumber;

	private AdditionalCodeFormatPutPane additionalCodeFormatPutPane;

	private int typeSerialNumber;

	// private int operatingOrdinalNumber;

	/**
	 * @param additionalSerialNumber
	 * @param additionalCodeFormatPutPane
	 * @param typeSerialNumber
	 * @param operatingOrdinalNumber
	 * @param expanded
	 * @param proportion
	 */
	public AdditionalSetCodeCabinet(int additionalSerialNumber, AdditionalCodeFormatPutPane additionalCodeFormatPutPane,
									int typeSerialNumber, int operatingOrdinalNumber, boolean expanded, double proportion) {
		super(expanded, proportion);
		this.additionalSerialNumber = additionalSerialNumber;
		this.additionalCodeFormatPutPane = additionalCodeFormatPutPane;
		this.typeSerialNumber = typeSerialNumber;
		this.operatingOrdinalNumber = operatingOrdinalNumber;
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<FormatTypeCodeModel> getCodeModelList() {
		ArrayList<FormatTypeCodeModel> list = new ArrayList<>();
		AdditionalSetCodeTier tempPane;
		FormatTypeCodeModel temp;
		for (int i = 0; i < getVBox().getComponentCount(); i++) {
			tempPane = (AdditionalSetCodeTier) getVBox().getComponent(i);
			temp = tempPane.getCodeModel();
			temp.setAdditionalSerialNumber(additionalSerialNumber);
			temp.setTypeSerialNumber(typeSerialNumber);
			temp.setOrdinal(this.operatingOrdinalNumber);
			temp.setCodeOrdinal(i + 1);
			list.add(temp);
		}
		return list;
	}

	@Override
	public void addCodePane(ContainerModel theModel) {
		int currenNum = getCodeNum() + 1;
		//setCodeNum(currenNum);

		AdditionalSetCodeTier additionalSetCodeTier = new AdditionalSetCodeTier(additionalSerialNumber, additionalCodeFormatPutPane,
				typeSerialNumber, operatingOrdinalNumber, currenNum, theModel);
		getVBox().add(additionalSetCodeTier);

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
	public void reductionContent(ArrayList<FormatTypeCodeModel> codeModelList, ContainerModel theModel,
								 int operatingOrdinal) {
		// TODO Auto-generated method stub
		this.operatingOrdinalNumber = operatingOrdinal;
		FormatTypeCodeModel temp;
		AdditionalSetCodeTier additionalSetCodeTier;
		if (codeModelList != null) {
			for (int i = 0; i < codeModelList.size(); i++) {
				temp = codeModelList.get(i);
				//setCodeNum(temp.getCodeOrdinal());
				additionalSetCodeTier = new AdditionalSetCodeTier(additionalSerialNumber, additionalCodeFormatPutPane,
						typeSerialNumber, operatingOrdinal, temp.getCodeOrdinal(), theModel);
				additionalSetCodeTier.reductionContent(temp);
				getVBox().add(additionalSetCodeTier);
			}
		}
		this.updateUI();
		this.repaint();
	}

	/**
	 * 获取代码总数
	 *
	 * @return
	 */
	public int getAdditionalSetCodeNum() {
		return getVBox().getComponentCount();
	}

}