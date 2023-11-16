package com.lazycoder.uidatasourceedit.moduleedit.codepane;

import com.lazycoder.uidatasourceedit.component.component.BaseUseCodeFileEditPane;
import com.lazycoder.uiutils.mycomponent.CodeTabbedPane;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class showDelOptionPaneCodeTabbedPane extends CodeTabbedPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 2150112491292810160L;

	private BaseUseCodeFileEditPane codeFileEditPane;

	public showDelOptionPaneCodeTabbedPane(BaseUseCodeFileEditPane codeFileEditPane) {
		// TODO Auto-generated constructor stub
		this.codeFileEditPane = codeFileEditPane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (pressTheCloseButton(e) == true) {
			int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
			String fileName = this.getTitleAt(tabNumber);
			int n = LazyCoderOptionPane.showConfirmDialog(null, "真的要删除\"" + fileName + "\"这个源码文件吗?", "确认一下",
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				codeFileEditPane.deletePane();
				super.mouseClicked(e);
			}
		}
	}

}
