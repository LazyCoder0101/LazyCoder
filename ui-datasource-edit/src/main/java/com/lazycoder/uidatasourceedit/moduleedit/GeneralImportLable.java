package com.lazycoder.uidatasourceedit.moduleedit;

import lombok.Getter;

import javax.swing.*;


public class GeneralImportLable extends JLabel {

	/**
	 *
	 */
	private static final long serialVersionUID = -510902441421283806L;

	@Getter
	private int ordinal;

	public GeneralImportLable() {
		// TODO Auto-generated constructor stub
		super();
	}

	public void setLabelText(int ordinal) {
		setText("引入" + ordinal + "：");
		this.ordinal = ordinal;
	}

}
