package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import lombok.Getter;


/**
 * 代码文件都放在这个滑动面板上
 *
 * @author admin
 */
public class FormatCodeScrollPane extends JScrollPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -3913466999102535019L;

	@Getter
	private CodeFormatFlagParam fileParam = null;

	private AbstractFormatCodeInputPane formatCodeInputPane = null;

	@Getter
	private JTextPane textPane = null;

	public FormatCodeScrollPane(AbstractFormatCodeInputPane component) {
		// TODO Auto-generated constructor stub
		super(component);
		this.formatCodeInputPane = component;
		this.fileParam = component.getFileParam();
	}

	public FormatCodeScrollPane(JTextPane component) {
		super(component);
		this.textPane = component;
	}

	/**
	 * 重新加载格式代码面板
	 */
	public void reloadFormatCodeInputPane() {
		formatCodeInputPane.setUpdateScrollpane(this);
		setViewportView(formatCodeInputPane);
	}


	/**
	 * 返回对应的代码面板
	 *
	 * @return
	 */
	public AbstractFormatCodeInputPane getTheFormatCodeInputPane() {
		return formatCodeInputPane;
	}


}
