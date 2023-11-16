package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;

import java.util.ArrayList;

/**
 * 该类目前废弃不用
 */
public class ImportPathChooseMenu extends AbstractCommandPathChooseMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = 4331976093461555395L;

	ImportMarkElement markElement = new ImportMarkElement();

	public ImportPathChooseMenu(int ordinal) {
		// TODO Auto-generated constructor stub
		super();
		this.markElement = new ImportMarkElement();
		addAllMenuItems();
		showWritePath(null);
		markElement.setOrdinal(ordinal);

	}

	@Override
	public boolean checkHaveAddTheMarkScutcheonOrNot() {
		return false;
	}

	@Override
	protected ArrayList<CodeFormatFlagParam> getSelectedCodeFileList() {
		// TODO Auto-generated method stub
		return ModuleEditPaneHolder.useModuleFileEditPane.getCurrentCodeFormatParam();
	}


	@Override
	protected void afterSelectedMenuItem(PathChooseMenuItem pathChooseMenuItem) {}

	@Override
	protected void afterDeselectMenuItem(PathChooseMenuItem pathChooseMenuItem) {}


}
