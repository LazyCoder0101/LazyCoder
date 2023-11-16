package com.lazycoder.uiutils.mycomponent;

import javax.swing.Icon;
import javax.swing.JButton;

public class MyButton extends JButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 8262517162910576309L;

	public MyButton() {
		// TODO Auto-generated constructor stub
		setFocusPainted(false);
	}


	public MyButton(String text) {
		// TODO Auto-generated constructor stub
		super(text);
		setFocusPainted(false);
	}

	public MyButton(Icon icon) {
		// TODO Auto-generated constructor stub
		setIcon(icon);
		setFocusPainted(false);
	}


	public MyButton(String text, Icon icon) {
		// TODO Auto-generated constructor stub
		this(text);
		setIcon(icon);
	}
}
