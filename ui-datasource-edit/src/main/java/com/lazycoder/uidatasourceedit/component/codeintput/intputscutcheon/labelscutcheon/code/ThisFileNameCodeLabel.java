package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.vo.element.lable.code.ThisFileNameCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ThisFileNameLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.FileNameEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThisFileNameCodeLabel extends ThisFileNameLabel  implements CodeLabelInterface, BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 3868947425156363755L;

	private ThisFileNameCodeElement codeElement = new ThisFileNameCodeElement();


	/**
	 * 新建
	 */
	public ThisFileNameCodeLabel() {
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * 还原
	 *
	 * @param codeElement
	 */
	public ThisFileNameCodeLabel(ThisFileNameCodeElement codeElement) {
		// TODO Auto-generated constructor stub
		this.codeElement = codeElement;
		init();
	}

	private void init() {
		setName("thisName");
		setUI(new ControlLabelButtonUI());
		addActionListener(listener);
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new FileNameEditFrame(ThisFileNameCodeLabel.this);
		}
	};

	public int getHaveSuffixOrNot() {
		return codeElement.getHaveSuffixOrNot();
	}

	public void setHaveSuffixOrNot(int haveSuffixOrNot) {
		codeElement.setHaveSuffixOrNot(haveSuffixOrNot);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		codeElement.setThisName(name);
	}

	@Override
	public ThisFileNameCodeElement property() {
		// TODO Auto-generated method stub
		return codeElement;
	}


	@Override
	public void setNavigate(boolean flag) {}


}
