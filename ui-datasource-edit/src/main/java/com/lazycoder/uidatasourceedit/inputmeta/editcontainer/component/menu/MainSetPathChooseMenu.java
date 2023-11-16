package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.service.vo.element.mark.MainSetMarkElement;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import lombok.Getter;


public class MainSetPathChooseMenu extends AbstractFormatSetPathChooseMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = -732061558865343969L;

	@Getter
	private MainSetMarkElement markElement;

	public MainSetPathChooseMenu(int mainSetOrdinal, int operatingOrdinal, int codeOrdinal) {
		// TODO Auto-generated constructor stub
		super();
		this.markElement = new MainSetMarkElement();
		init();
		markElement.setClassificationSerial(mainSetOrdinal);
		markElement.setOperatingSerialNumber(operatingOrdinal);
		markElement.setCodeNumber(codeOrdinal);

		addMouseListener(adapter);
	}

	@Override
	public boolean checkHaveAddTheMarkScutcheonOrNot() {
		ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
		return FormatEditPaneHolder.mainCodePane.navigateToTheCorrespondingMark(list, markElement, false);
	}

	private MouseAdapter adapter = new MouseAdapter() {

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			navigateToTheCorrespondingMarkOrShowToolTip(FormatEditPaneHolder.mainCodePane, markElement);
			super.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
			if (NO_STRING.equals(getText()) == false) {// 如果有选写在哪里，定位过去
				if (FormatEditPaneHolder.mainCodePane != null) {
					FormatEditPaneHolder.mainCodePane.navigateToTheCorrespondingMark(list, markElement, false);
				}
			}
			super.mouseExited(e);
		}
	};

	@Override
	protected ArrayList<CodeFormatFlagParam> getSelectedCodeFileList() {
		// TODO Auto-generated method stub
		return FormatEditPaneHolder.mainCodePane.getCurrentCodeFormatParam();
	}
}
