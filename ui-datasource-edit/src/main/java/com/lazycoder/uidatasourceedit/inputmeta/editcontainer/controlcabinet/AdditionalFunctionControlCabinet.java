package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.AdditionalFunctionControlPane;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import javax.swing.JOptionPane;


public class AdditionalFunctionControlCabinet extends AbstractControlCabinet {

	/**
	 *
	 */
	private static final long serialVersionUID = -6705776471433690540L;

	private int additionalSerialNumber = 0;

	public AdditionalFunctionControlCabinet(int additionalSerialNumber, ContainerModel theModel, int ordinal, boolean expanded,
											double proportion) {
		super();
		this.additionalSerialNumber = additionalSerialNumber;
		init(theModel, ordinal, expanded, proportion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void uiInit(boolean expanded, double proportion) {
		derectInputPanel = new AdditionalFunctionControlPane(this.additionalSerialNumber);
		hiddenInputPane = new AdditionalFunctionControlPane(this.additionalSerialNumber);
		super.uiInit(expanded, proportion);
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		String typeName = FormatEditPaneHolder.additionalEditPane.getAdditionalTypeName(additionalSerialNumber);
		if (functionNameTextField.check()==false) {
			flag = false;
			LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   其他\"" + typeName + "\"分类中，第" + operatingOrdinalNumber + "个可选模板业务方法没写名称", "系统信息",
					JOptionPane.PLAIN_MESSAGE);
		}
		if (flag == true) {
			if (derectInputPanel.getUseState() == false) {
				flag = false;
				LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   其他\"" + typeName + "\"分类中，第" + operatingOrdinalNumber + "个默认操作没写任何内容",
						"系统信息", JOptionPane.PLAIN_MESSAGE);
			}
		}
		return flag;
	}

}
