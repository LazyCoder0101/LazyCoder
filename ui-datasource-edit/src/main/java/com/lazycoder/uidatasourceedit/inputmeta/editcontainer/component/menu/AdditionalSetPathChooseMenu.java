package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.service.vo.element.mark.AdditionalSetMarkElement;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalCodeFormatPutPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import lombok.Getter;


public class AdditionalSetPathChooseMenu extends AbstractFormatSetPathChooseMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = 2238718116151648661L;

	private AdditionalCodeFormatPutPane additionalCodeFormatPutPane;

	@Getter
	private AdditionalSetMarkElement markElement;

	public AdditionalSetPathChooseMenu(AdditionalCodeFormatPutPane additionalCodeFormatPutPane, AdditionalSetMarkElement markElement) {
		// TODO Auto-generated constructor stub
		super();
		this.additionalCodeFormatPutPane = additionalCodeFormatPutPane;
		this.markElement = markElement;
		init();
		addMouseListener(adapter);
	}

	private MouseAdapter adapter = new MouseAdapter() {

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			navigateToTheCorrespondingMarkOrShowToolTip(additionalCodeFormatPutPane, markElement);
			super.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
			if (NO_STRING.equals(getText()) == false) {// 如果有选写在哪里，定位过去
				if (additionalCodeFormatPutPane != null) {
					additionalCodeFormatPutPane.navigateToTheCorrespondingMark(list, markElement, false);
				}
			}
			super.mouseExited(e);
		}
	};

	@Override
	public boolean checkHaveAddTheMarkScutcheonOrNot() {
		ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
		return additionalCodeFormatPutPane.navigateToTheCorrespondingMark(list, markElement, false);
	}

	@Override
	protected ArrayList<CodeFormatFlagParam> getSelectedCodeFileList() {
		// TODO Auto-generated method stub
		return this.additionalCodeFormatPutPane.getCurrentCodeFormatParam();
	}
}
