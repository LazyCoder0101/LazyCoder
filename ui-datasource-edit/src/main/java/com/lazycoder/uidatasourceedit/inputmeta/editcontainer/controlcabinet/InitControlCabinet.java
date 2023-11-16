package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.InitFunctionControlPanel;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import javax.swing.JOptionPane;

public class InitControlCabinet extends AbstractControlCabinet {

	/**
	 *
	 */
	private static final long serialVersionUID = 865957072827950890L;

	public InitControlCabinet(ContainerModel theModel, int operatingOrdinalNumber, boolean expanded, double proportion) {
		super();
		init(theModel, operatingOrdinalNumber, expanded, proportion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (functionNameTextField.check()==false) {
			flag = false;
			LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   第" +
							operatingOrdinalNumber + "个初始化方法没写名称", "系统信息",
					JOptionPane.PLAIN_MESSAGE);
		}
		if (flag == true) {
			if (derectInputPanel.getUseState() == false) {
				flag = false;
				LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   第" +
								operatingOrdinalNumber + "个默认操作没写任何内容", "系统信息",
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return flag;
	}

	@Override
	public void uiInit(boolean expanded, double proportion) {
		derectInputPanel = new InitFunctionControlPanel();
		hiddenInputPane = new InitFunctionControlPanel();
		super.uiInit(expanded, proportion);
	}

}
