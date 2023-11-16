package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier.MainSetCodeTier;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;

public class MainSetCodeCabinet extends AbstractCodeCabinet {

	/**
	 *
	 */
	private static final long serialVersionUID = 7306877878749024048L;

	private int mainSetOrdinal;

	/**
	 * @param theModel
	 * @param operatingOrdinal
	 * @param expanded
	 * @param proportion
	 */
	public MainSetCodeCabinet(ContainerModel theModel, int mainSetOrdinal, int operatingOrdinal, boolean expanded,
							  double proportion) {
		super(expanded, proportion);
		this.operatingOrdinalNumber = operatingOrdinal;
		this.mainSetOrdinal = mainSetOrdinal;
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<FormatTypeCodeModel> getCodeModelList() {
		ArrayList<FormatTypeCodeModel> list = new ArrayList<>();
		MainSetCodeTier tempPane;
		FormatTypeCodeModel temp;
		for (int i = 0; i < getVBox().getComponentCount(); i++) {
			tempPane = (MainSetCodeTier) getVBox().getComponent(i);
			temp = tempPane.getCodeModel();
			temp.setOrdinal(this.operatingOrdinalNumber);
			temp.setCodeOrdinal(i + 1);
			list.add(temp);
		}
		return list;
	}

	@Override
	public void addCodePane(ContainerModel theModel) {
		int codeNum = getCodeNum() + 1;
		//setCodeNum(codeNum);

		MainSetCodeTier mainSetCodeInputPane = new MainSetCodeTier(mainSetOrdinal, operatingOrdinalNumber, codeNum, theModel);
		getVBox().add(mainSetCodeInputPane);

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
		MainSetCodeTier mainSetCodeInputPane;
		if (codeModelList != null) {
			for (int i = 0; i < codeModelList.size(); i++) {
				temp = codeModelList.get(i);
				//setCodeNum(temp.getCodeOrdinal());
				mainSetCodeInputPane = new MainSetCodeTier(mainSetOrdinal, operatingOrdinal, temp.getCodeOrdinal(),
						theModel);
				mainSetCodeInputPane.reductionContent(temp);
				getVBox().add(mainSetCodeInputPane);
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
	public int getMainSetCodeNum() {
		return getVBox().getComponentCount();
	}

}