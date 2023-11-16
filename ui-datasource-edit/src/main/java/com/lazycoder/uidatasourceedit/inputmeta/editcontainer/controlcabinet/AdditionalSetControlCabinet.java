package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.AdditionalSetControlPane;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import javax.swing.JOptionPane;


public class AdditionalSetControlCabinet extends AbstractControlCabinet {

	/**
	 *
	 */
	private static final long serialVersionUID = -5554413177626519868L;

	private int additionalSerialNumber = 0;

	private int typeSerialNumber = 0;

	public AdditionalSetControlCabinet(int additionalSerialNumber, ContainerModel theModel, int typeSerialNumber, int operatingOrdinalNumber,
									   boolean expanded, double proportion) {
		super();
		// TODO Auto-generated constructor stub
		this.additionalSerialNumber = additionalSerialNumber;
		this.typeSerialNumber = typeSerialNumber;
		init(theModel, operatingOrdinalNumber, expanded, proportion);
	}

	@Override
	public void uiInit(boolean expanded, double proportion) {
		derectInputPanel = new AdditionalSetControlPane();
		hiddenInputPane = new AdditionalSetControlPane();
		super.uiInit(expanded, proportion);
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		String typeName = FormatEditPaneHolder.additionalEditPane.getAdditionalTypeName(additionalSerialNumber),
				theSetTypeName = FormatEditPaneHolder.additionalEditPane.getTheAdditionalSetTypeName(additionalSerialNumber,
						typeSerialNumber);

		if (functionNameTextField.check()==false) {
			flag = false;
			LazyCoderOptionPane.showMessageDialog(null,
					"o(´^｀)o   其他\"" + typeName + "\"分类中，\"" +
							theSetTypeName + "\"面板里第" + operatingOrdinalNumber + "个可选模板设置方法方法没写名称",
					"系统信息", JOptionPane.PLAIN_MESSAGE);
		}
		if (flag == true) {
			if (derectInputPanel.getUseState() == false) {
				flag = false;
				LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   其他\"" +
						typeName + "\"分类中，\"" + theSetTypeName
						+ "\"面板里第" + operatingOrdinalNumber + "个默认操作没写任何内容", "系统信息", JOptionPane.PLAIN_MESSAGE);
			}
		}
		return flag;
	}

}
