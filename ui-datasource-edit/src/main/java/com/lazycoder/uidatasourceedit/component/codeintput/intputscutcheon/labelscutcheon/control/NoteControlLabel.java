package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.NoteControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.NoteLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.NoteEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteControlLabel extends NoteLabel implements ControlLabelInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 3991263616480037392L;

	private NoteControl controlElement = new NoteControl();


	/**
	 * 新建
	 *
	 * @param name
	 */
	public NoteControlLabel(String name) {
		// TODO Auto-generated constructor stub
		super();
		init(name);
	}

	/**
	 * 还原
	 *
	 * @param controlElement
	 */
	public NoteControlLabel(NoteControl controlElement) {
		// TODO Auto-generated constructor stub
		super();
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
			new NoteEditFrame(NoteControlLabel.this);
		}
	};

	public String getNote() {
		return controlElement.getNote();
	}

	/**
	 * 设置备注
	 *
	 * @param note
	 */
	public void setNote(String note) {
		controlElement.setNote(note);
	}

	public boolean isAsAUsageReminder() {
		return controlElement.isAsAUsageReminder();
	}

	/**
	 * 设置是否设为使用注释
	 *
	 * @param asAUsageReminder
	 */
	public void setAsAUsageReminder(boolean asAUsageReminder) {
		controlElement.setAsAUsageReminder(asAUsageReminder);
	}


	@Override
	public void deleteFromPanel() {}

	@Override
	public void setName(String name) {
		super.setName(name);
		controlElement.setThisName(name);
	}

	@Override
	public NoteControl property() {
		// TODO Auto-generated method stub
		return controlElement;
	}

	@Override
	public NoteControl getControl() {
		// TODO Auto-generated method stub
		return controlElement;
	}

}