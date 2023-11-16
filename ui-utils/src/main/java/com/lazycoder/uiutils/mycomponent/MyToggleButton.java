package com.lazycoder.uiutils.mycomponent;

import javax.swing.Icon;
import javax.swing.JToggleButton;

public class MyToggleButton extends JToggleButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -1099741367721712367L;

	public MyToggleButton() {
		// TODO Auto-generated constructor stub
		setFocusPainted(false);
	}

	public MyToggleButton(Icon icon) {
		super(icon);
		setFocusPainted(false);
	}


	public MyToggleButton(Icon icon, boolean selected) {
		super(icon, selected);
		setFocusPainted(false);
	}

	public MyToggleButton(String text) {
		super(text);
		setFocusPainted(false);
	}
}
