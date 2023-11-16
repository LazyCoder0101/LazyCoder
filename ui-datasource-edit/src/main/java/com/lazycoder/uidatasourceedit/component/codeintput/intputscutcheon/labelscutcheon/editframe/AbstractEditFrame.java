package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public abstract class AbstractEditFrame extends LazyCoderCommonDialog {

	protected final static Dimension SCREEN_SIZE = SysUtil.SCREEN_SIZE;
	/**
	 *
	 */
	private static final long serialVersionUID = 7557606331263008340L;

	protected MyButton ok, cancel;

	public AbstractEditFrame() {
		// TODO Auto-generated constructor stub
		ok = new MyButton("确定");
		cancel = new MyButton("取消");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AbstractEditFrame.this.dispose();
			}
		});
	}

	/**
	 * 点击确定以后的操作
	 */
	public void ok(){};

}
