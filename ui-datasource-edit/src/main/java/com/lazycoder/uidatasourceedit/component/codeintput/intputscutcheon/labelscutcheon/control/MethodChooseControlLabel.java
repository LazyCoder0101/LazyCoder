package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.MethodChooseLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.MethodChooseControlEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MethodChooseControlLabel extends MethodChooseLabel implements ControlLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -7139826355934242406L;

	private MethodChooseControl controlElement = new MethodChooseControl();


	/**
	 * 新建
	 *
	 * @param name
	 */
	public MethodChooseControlLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param controlElement
	 */
	public MethodChooseControlLabel(MethodChooseControl controlElement) {
		// TODO Auto-generated constructor stub
		this.controlElement = controlElement;
		init(controlElement.getThisName());
	}

	public void init(String name) {
		setName(name);
		setUI(new ControlLabelButtonUI());
		setText(name);
		addActionListener(listener);
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MethodChooseControlEditFrame(MethodChooseControlLabel.this);
		}
	};

	@Override
	public void deleteFromPanel() {

	}

	/**
	 * 获取数据类型列表
	 *
	 * @return
	 */
	public ArrayList<String> getDataTypeList() {
		return MethodChooseControl.getLabelTypeList(controlElement);
	}

	/**
	 * 设置数据类型列表
	 *
	 * @param dataTypeList
	 */
	public void setDataTypeList(ArrayList<String> dataTypeList) {
		MethodChooseControl.setLabelTypeList(dataTypeList, controlElement);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		controlElement.setThisName(name);
	}

	public int getTheAvaliableRange() {
		return controlElement.getTheAvaliableRange();
	}

	public void setTheAvaliableRange(int theAvaliableRange) {
		this.controlElement.setTheAvaliableRange(theAvaliableRange);
	}

	@Override
	public MethodChooseControl property() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	@Override
	public MethodChooseControl getControl() {
		// TODO Auto-generated method stub
		return controlElement;
	}

}
