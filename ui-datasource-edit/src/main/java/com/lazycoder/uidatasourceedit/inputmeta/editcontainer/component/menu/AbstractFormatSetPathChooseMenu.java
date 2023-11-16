package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu;

import com.lazycoder.database.CodeFormatFlagParam;

import java.util.ArrayList;


public abstract class AbstractFormatSetPathChooseMenu extends BasePathChooseMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = 3052686685951899813L;

	public AbstractFormatSetPathChooseMenu() {
		// TODO Auto-generated constructor stub
		super();

	}

	protected void init() {
		addAllMenuItems();

		ArrayList<CodeFormatFlagParam> list = getSelectedCodeFormatFlagParams();
		showWritePath(list);
	}

	/**
	 * 进行处理
	 *
	 * @param pathChooseMenuItem
	 */
	@Override
	protected void processing(PathChooseMenuItem pathChooseMenuItem) {
		if (pathChooseMenuItem.isSelected() == true) {
			setEnabledFalseExceptFor(pathChooseMenuItem);

		} else {// 取消选中
			setEnabledTrueForAllMenuItem();
		}
	}

	/**
	 * 现在必填模板设置还有可选模板设置的菜单项要做的更改 如果选中某个代码文件后，其他的选项通通失能 如果取消选中某个文件，所有选项都使能
	 */

}
