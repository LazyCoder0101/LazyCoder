package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MySpinner extends JSpinner {

	/**
	 *
	 */
	private static final long serialVersionUID = -5029860601093075279L;

	public MySpinner() {
		// TODO Auto-generated constructor stub
		super(new SpinnerNumberModel(0, 0, 1000, 1));
	}
}
