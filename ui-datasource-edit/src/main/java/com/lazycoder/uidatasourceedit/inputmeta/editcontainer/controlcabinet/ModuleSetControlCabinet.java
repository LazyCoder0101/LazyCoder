package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.ModuleSetFunctionControlPanel;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import javax.swing.JOptionPane;

public class ModuleSetControlCabinet extends AbstractControlCabinet {

	/**
	 *
	 */
	private static final long serialVersionUID = 8743130138636656439L;

	private int typeSerialNumber  = 0;

	public ModuleSetControlCabinet(int typeSerialNumber, ContainerModel theModel, int ordinal, boolean expanded, double proportion) {
		super();
		this.typeSerialNumber = typeSerialNumber;
		init(theModel, ordinal, expanded, proportion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void uiInit(boolean expanded, double proportion) {
		derectInputPanel = new ModuleSetFunctionControlPanel();
		hiddenInputPane = new ModuleSetFunctionControlPanel();
		super.uiInit(expanded, proportion);
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (functionNameTextField.check()==false) {
			flag = false;
			LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   \"设置"+ typeSerialNumber +"\"里的第" +"第" + operatingOrdinalNumber + "个模块设置方法没写名称", "系统信息",
					JOptionPane.PLAIN_MESSAGE);
		}
		if (flag == true) {
			if (derectInputPanel.getUseState() == false) {
				flag = false;
				LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   \"设置"+ typeSerialNumber +"\"里的第" +"第" + operatingOrdinalNumber + "个默认操作没写任何内容", "系统信息",
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return flag;
	}

}
