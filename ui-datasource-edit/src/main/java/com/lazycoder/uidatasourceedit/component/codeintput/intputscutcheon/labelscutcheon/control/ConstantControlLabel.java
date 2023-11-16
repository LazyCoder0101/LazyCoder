package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ConstantLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.ConstantEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.Setter;

public class ConstantControlLabel extends ConstantLabel implements ControlLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 8753527309628906919L;

	@Setter
	private ConstantControl controlElement = new ConstantControl();


	/**
	 * 新建
	 *
	 * @param name
	 */
	public ConstantControlLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param controlElement
	 */
	public ConstantControlLabel(ConstantControl controlElement) {
		// TODO Auto-generated constructor stub
		this.controlElement = controlElement;
		init(controlElement.getThisName());
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new ConstantEditFrame(ConstantControlLabel.this);
		}
	};

	public void init(String name) {
		setName(name);
		setText(name);
		setUI(new ControlLabelButtonUI());
		addActionListener(listener);
	}

	public String getLeftStr() {
		return controlElement.getLeftStr();
	}

	public void setLeftStr(String leftStr) {
		this.controlElement.setLeftStr(leftStr);
	}

	public String getRightStr() {
		return controlElement.getRightStr();
	}

	public void setRightStr(String rightStr) {
		this.controlElement.setRightStr(rightStr);
	}

	public String getSeparatorStr() {
		return controlElement.getSeparatorStr();
	}

	public void setSeparatorStr(String separatorStr) {
		this.controlElement.setSeparatorStr(separatorStr);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		controlElement.setThisName(name);
	}

	@Override
	public ConstantControl property() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	@Override
	public ConstantControl getControl() {
		return controlElement;
	}


	@Override
	public void deleteFromPanel() {

	}

}
