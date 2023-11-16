package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format;

import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.BaseTextPane;
import com.lazycoder.uiutils.component.LineNumberHeaderView;
import java.awt.Font;


/**
 * 代码文件都放在这个滑动面板上
 *
 * @author admin
 */
public class LineNumFormatCodeScrollPane extends FormatCodeScrollPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -3913466999102535019L;

	private LineNumberHeaderView lineNumberHeaderView;

	public LineNumFormatCodeScrollPane(AbstractFormatCodeInputPane component) {
		// TODO Auto-generated constructor stub
		super(component);

//		if (component instanceof BaseTextPane) {
//			component.setShowLineNumber(true);//显示行号
//		}

		lineNumberHeaderView = new LineNumberHeaderView();
		if (component instanceof BaseTextPane) {
			lineNumberHeaderView.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		} else {
			lineNumberHeaderView.setFont(component.getFont());
		}
		setRowHeaderView(lineNumberHeaderView);
	}

}
