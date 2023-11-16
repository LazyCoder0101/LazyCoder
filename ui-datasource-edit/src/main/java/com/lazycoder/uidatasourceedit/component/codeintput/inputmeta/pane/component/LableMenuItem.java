package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.component;

import javax.swing.JMenuItem;
import lombok.Getter;
import lombok.Setter;

public class LableMenuItem extends JMenuItem {

	/**
	 *
	 */
	private static final long serialVersionUID = -996602270077140222L;

	@Setter
	@Getter
	private String labelType;

	@Setter
	@Getter
	private String thisName;

	/**
	 * @param text
	 * @param labelType
	 * @param thisName
	 */
	public LableMenuItem(String text, String labelType, String thisName) {
		// TODO Auto-generated constructor stub
		super(text);
		this.labelType = labelType;
		this.thisName = thisName;
	}

}
