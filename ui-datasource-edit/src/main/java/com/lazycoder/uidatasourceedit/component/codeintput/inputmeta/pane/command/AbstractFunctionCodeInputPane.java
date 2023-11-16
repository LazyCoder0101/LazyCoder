package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command;

import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.datasourceedit.command.FunctionCodePaneInterface;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.BaseFunctionTextPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.CodeCondition;
import javax.swing.text.BadLocationException;

public abstract class AbstractFunctionCodeInputPane extends BaseFunctionTextPane implements FunctionCodePaneInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 7034862463836588737L;


	public AbstractFunctionCodeInputPane(int serialNumber, ContainerModel model) {
		super(serialNumber);
		// TODO Auto-generated constructor stub
		menuInit();
		setModel(model);
//        getDocument().addDocumentListener(theDocumentListener);
	}

	protected void menuInit() {
		CodeCondition codeCondition = new CodeCondition(false, false);
		menuInit(codeCondition);
	}

	/**
	 * 获取面板编号
	 *
	 * @return
	 */
	@Override
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * 获取代码参数
	 */
	@Override
	public String getCodeParam() {
		String out = "";
		try {
			out = getCodeStatementFormat();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

}
