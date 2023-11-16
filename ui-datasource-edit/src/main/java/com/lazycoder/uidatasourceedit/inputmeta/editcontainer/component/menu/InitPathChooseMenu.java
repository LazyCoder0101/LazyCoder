package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import lombok.Getter;


public class InitPathChooseMenu extends AbstractCommandPathChooseMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = 889016120743294913L;

	@Getter
	private InitMarkElement markElement;

	public InitPathChooseMenu(int initSerialNumber, int codeNumber) {
		// TODO Auto-generated constructor stub
		super();
		markElement = new InitMarkElement();
		markElement.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
		markElement.setInitSerialNumber(initSerialNumber);
		markElement.setCodeNumber(codeNumber);
		addAllMenuItems();
		showWritePath(null);
		addMouseListener(adapter);
	}

	@Override
	public boolean checkHaveAddTheMarkScutcheonOrNot() {
		ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
		return ModuleEditPaneHolder.useModuleFileEditPane.navigateToTheCorrespondingMark(list, markElement, false);
	}

	@Override
	protected ArrayList<CodeFormatFlagParam> getSelectedCodeFileList() {
		// TODO Auto-generated method stub
		return ModuleEditPaneHolder.useModuleFileEditPane.getCurrentCodeFormatParam();
	}

	private MouseAdapter adapter = new MouseAdapter() {

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			navigateToTheCorrespondingMarkOrShowToolTip(ModuleEditPaneHolder.useModuleFileEditPane, markElement);
			super.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
			if (getText().equals(NO_STRING) == false) {// 如果有选写在哪里，定位过去
				if (ModuleEditPaneHolder.useModuleFileEditPane != null) {
					ModuleEditPaneHolder.useModuleFileEditPane.navigateToTheCorrespondingMark(list, markElement, false);
				}
			}
			super.mouseExited(e);
		}
	};

	@Override
	protected void afterSelectedMenuItem(PathChooseMenuItem pathChooseMenuItem) {}

	@Override
	protected void afterDeselectMenuItem(PathChooseMenuItem pathChooseMenuItem) {}

}
