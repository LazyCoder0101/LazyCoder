package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.VariableControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.VariableLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.VariableEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VariableControlLabel extends VariableLabel implements ControlLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -7931536247569510127L;

	private VariableControl controlElement = new VariableControl();


	/**
	 * 新建
	 *
	 * @param name
	 */
	public VariableControlLabel(String name) {
		// TODO Auto-generated constructor stub
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param controlElement
	 */
	public VariableControlLabel(VariableControl controlElement) {
		// TODO Auto-generated constructor stub
		this.controlElement = controlElement;
		init(controlElement.getThisName());
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new VariableEditFrame(VariableControlLabel.this);
		}
	};

	private void init(String name) {
		setName(name);
		setUI(new ControlLabelButtonUI());
		setText(name);
		addActionListener(listener);
	}

	/**
	 * 获取数据类型列表
	 *
	 * @return
	 */
	public ArrayList<String> getDataTypeList() {
		return VariableControl.getDataTypeList(controlElement);
	}

	/**
	 * 设置数据类型列表
	 *
	 * @param dataTypeList
	 */
	public void setDataTypeList(ArrayList<String> dataTypeList) {
		VariableControl.setDataTypeList(dataTypeList, controlElement);
	}


	/**
	 * 获取标签类型列表
	 *
	 * @return
	 */
	public ArrayList<String> getLabelTypeList() {
		return VariableControl.getLabelTypeList(controlElement);
	}

	/**
	 * 设置标签类型列表
	 *
	 * @param labelTypeList
	 */
	public void setLabelTypeList(ArrayList<String> labelTypeList) {
		VariableControl.setLabelTypeList(labelTypeList, controlElement);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		controlElement.setThisName(name);
	}

	@Override
	public VariableControl property() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	@Override
	public VariableControl getControl() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	public int getTheAvaliableRange() {
		return controlElement.getTheAvaliableRange();
	}

	public void setTheAvaliableRange(int theAvaliableRange) {
		this.controlElement.setTheAvaliableRange(theAvaliableRange);
	}

	public String getLabelTypeParam() {
		return controlElement.getLabelTypeParam();
	}

	public void setLabelTypeParam(String labelTypeParam) {
		this.controlElement.setLabelTypeParam(labelTypeParam);
	}

	public boolean isNoUserSelectionIsRequired() {
		return controlElement.isNoUserSelectionIsRequired();
	}

	public void setNoUserSelectionIsRequired(boolean noUserSelectionIsRequired) {
		this.controlElement.setNoUserSelectionIsRequired(noUserSelectionIsRequired);
	}

	@Override
	public void deleteFromPanel() {}

}