package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;

import java.util.List;


public abstract class AbstractCommandPathChooseMenu extends BasePathChooseMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = -2669011067813699181L;

	public AbstractCommandPathChooseMenu() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 进行处理
	 *
	 * @param pathChooseMenuItem
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void processing(PathChooseMenuItem pathChooseMenuItem) {
		CodeFormatFlagParam codeFormatFlagParam = pathChooseMenuItem.getCodeFormatFlagParam();

		if (pathChooseMenuItem.isSelected() == true) {
			PathChooseMenuItem pathChooseMenuItemTemp;

			if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MODULE_TYPE) {// 如果选中的是某个模块的代码文件，除了该选项菜单以外，其他都失能
				setEnabledFalseExceptFor(pathChooseMenuItem);

			} else if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MAIN_TYPE) {// 如果选中的是必填模板的某个文件
				setEnabledFalseExceptFor(pathChooseMenuItem); // 先把除了该文件以外的菜单项都失能

				List<UsingObject> usingAdditionalList = ModuleEditPaneHolder.usingRange
						.getAdditionalUsingRangeThatHaveBeenSelected();// 再看看有没有选中可选模板
				if (usingAdditionalList.size() > 0) {// 如果有选中可选模板
					for (UsingObject temp : usingAdditionalList) {
						pathChooseMenuItemTemp = checkAdditionalCodeFileAreSelected(temp.getSerial());// 检查选中的可选模板现在有没有哪个文件的菜单项被选中了
						// 看看这个可选模板有没有文件被选中
						if (pathChooseMenuItemTemp == null) {// 没有文件被选中
							setEnabledTrueForAdditionalCodeFile(temp.getSerial());// 把这个可选模板所有的代码文件都使能，以让用户继续选择
						} else {// 有文件被选中
							pathChooseMenuItemTemp.setEnabled(true);// 只是把选中的文件的菜单项使能，以让用户需要的时候取消选择
						}
					}
				}

			} else if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.ADDITIONAL_TYPE) {// 如果选中的是可选模板
				setEnabledFalseExceptFor(pathChooseMenuItem);// 先把除了该文件以外的菜单项都失能
				boolean flag = ModuleEditPaneHolder.usingRange.chekMainUsingRangeThatHaveBeenSelected();// 查看使用范围那里当前有没有选中必填模板
				if (flag == true) {// 如果用户选择该模块可以在必填模板中使用
					pathChooseMenuItemTemp = checkMainCodeFileAreSelected();// 查一下用户有没有选择可以使用现在必填模板的某个代码文件
					if (pathChooseMenuItemTemp == null) {// 如果必填模板目前还没哪个被选中
						setEnabledTrueForMainCodeFile();// 把必填模板的所有代码文件的菜单项都使能

					} else {// 如果必填模板有代码文件被选中了
						pathChooseMenuItemTemp.setEnabled(true);// 把选中的那个代码文件的菜单项使能
					}

				}
				List<UsingObject> additionalList = ModuleEditPaneHolder.usingRange
						.getAdditionalUsingRangeThatHaveBeenSelectedExceptFor(codeFormatFlagParam.getAdditionalSerialNumber());// 查看使用范围那里当前有没有选中除了当前选中的可选模板，以外的另外的可选模板
				// 查看除此之外的可选模板
				if (additionalList != null) {// 如果真的选了
					if (additionalList.size() > 0) {
						for (UsingObject temp : additionalList) {
							pathChooseMenuItemTemp = checkAdditionalCodeFileAreSelected(temp.getSerial());// 拿到另外选的可选模板，有没有哪个代码文件被选中
							// 看看这个可选模板有没有文件被选中
							if (pathChooseMenuItemTemp == null) {// 没有文件被选中
								setEnabledTrueForAdditionalCodeFile(temp.getSerial());
							} else {// 有文件被选中
								pathChooseMenuItemTemp.setEnabled(true);
							}
						}
					}
				}
			}
			afterSelectedMenuItem(pathChooseMenuItem);
		} else {// 取消选中
			if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MODULE_TYPE) {
				setEnabledTrueForAllMenuItem();

			} else if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MAIN_TYPE) {
				List<UsingObject> usingAdditionalList = ModuleEditPaneHolder.usingRange
						.getAdditionalUsingRangeThatHaveBeenSelected();// 再看看有没有选中可选模板
				if (usingAdditionalList.size() > 0) {// 如果有选中可选模板
					setEnabledTrueForMainCodeFile();// 把必填模板的所有代码文件的菜单项都使能

				} else {// 没有选中可选模板
					setEnabledTrueForAllMenuItem();
//					setEnabledTrueForMainCodeFile();
				}

			} else if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.ADDITIONAL_TYPE) {
				int additionalSerialNumber = codeFormatFlagParam.getAdditionalSerialNumber();
				List<UsingObject> additionalList = ModuleEditPaneHolder.usingRange
						.getAdditionalUsingRangeThatHaveBeenSelectedExceptFor(additionalSerialNumber);// 查看使用范围那里当前有没有选中除了当前选中的可选模板，以外的另外的可选模板
				boolean flag = ModuleEditPaneHolder.usingRange.chekMainUsingRangeThatHaveBeenSelected();// 查看使用范围那里当前有没有选中必填模板
				if ((additionalList != null && additionalList.size() == 0) && flag == false) {// 如果只选中这个可选模板格式、或者还有模块的代码文件上
					setEnabledTrueForAllMenuItem();

				} else if ((additionalList != null && additionalList.size() > 0)) {// 如果还选了别的可选模板格式、可能还有必填模板格式
					if (getSelectedCodeFormatFlagParams().size() == 0) {// 菜单项取消选中后，没有别的菜单项被选中，就使能所有菜单项
						setEnabledTrueForAllMenuItem();

					} else if (getSelectedCodeFormatFlagParams().size() > 0) {// 菜单项取消选中后，如果还有其他菜单项有被选中的，必定是别的可选模板格式或者是必填模板有菜单项被选中
						setEnabledTrueForAdditionalCodeFile(additionalSerialNumber);// 把这个可选模板所有的代码文件都使能，以让用户继续选择
					}

				} else if ((additionalList != null && additionalList.size() == 0) && flag == true) {// 如果只选了这个可选模板格式、还有必填模板格式
					PathChooseMenuItem pathChooseMenuItemTemp = checkMainCodeFileAreSelected();// 查一下用户有没有选择可以使用现在必填模板的某个代码文件
					if (pathChooseMenuItemTemp == null) {// 如果必填模板目前还没哪个被选中，使能所有的菜单项
						setEnabledTrueForAllMenuItem();

					} else {// 如果必填模板有代码文件被选中了
						setEnabledTrueForAdditionalCodeFile(additionalSerialNumber);// 把这个可选模板所有的代码文件都使能，以让用户继续选择
					}
				}
			}
			afterDeselectMenuItem(pathChooseMenuItem);
		}
	}

	/**
	 * 把所有其他文件选项进行使能
	 */
	@SuppressWarnings({"unchecked", "unused"})
	private void setEnabledTrueAllAdditionalCodeFile() {
		List<UsingObject> list = ModuleEditPaneHolder.usingRange.getUsingRange();
		if (list != null) {
			PathChooseMenuItem item;
			CodeFormatFlagParam codeFormatFlagParam;
			UsingObject temp;
			for (int i = 0; i < list.size(); i++) {
				temp = list.get(i);
				if (temp.getType() == UsingObject.ADDITIONAL_TYPE) {
					int ordinal = temp.getSerial();
					for (int a = 0; a < getMenuComponentCount(); a++) {
						item = (PathChooseMenuItem) getItem(i);
						codeFormatFlagParam = item.getCodeFormatFlagParam();
						if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.ADDITIONAL_TYPE) {
							if (codeFormatFlagParam.getAdditionalSerialNumber() == ordinal) {
								item.setEnabled(true);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 把必填模板所有选项进行使能
	 */
	private void setEnabledTrueForMainCodeFile() {
		PathChooseMenuItem item;
		CodeFormatFlagParam codeFormatFlagParam;
		for (int a = 0; a < getMenuComponentCount(); a++) {
			if (getItem(a) instanceof PathChooseMenuItem) {
				item = (PathChooseMenuItem) getItem(a);
				codeFormatFlagParam = item.getCodeFormatFlagParam();
				if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MAIN_TYPE) {
					item.setEnabled(true);
				}
			}
		}
	}

	/**
	 * 查看该可选模板有没有文件被选中
	 *
	 * @return
	 */
	private PathChooseMenuItem checkAdditionalCodeFileAreSelected(int ordinal) {
		PathChooseMenuItem item = null, returnItem = null;
		CodeFormatFlagParam codeFormatFlagParam;
		for (int i = 0; i < getMenuComponentCount(); i++) {
			if (getItem(i) instanceof PathChooseMenuItem) {
				item = (PathChooseMenuItem) getItem(i);
				codeFormatFlagParam = item.getCodeFormatFlagParam();
				if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.ADDITIONAL_TYPE) {
					if (codeFormatFlagParam.getAdditionalSerialNumber() == ordinal) {
						if (item.isSelected() == true) {
							returnItem = item;
							break;
						}
					}
				}
			}
		}
		return returnItem;
	}

	/**
	 * 查看该可选模板有没有文件被选中
	 *
	 * @return
	 */
	private void setEnabledTrueForAdditionalCodeFile(int additionalSerialNumber) {
		PathChooseMenuItem item = null;
		CodeFormatFlagParam codeFormatFlagParam;
		for (int i = 0; i < getMenuComponentCount(); i++) {
			if (getItem(i) instanceof PathChooseMenuItem) {
				item = (PathChooseMenuItem) getItem(i);
				codeFormatFlagParam = item.getCodeFormatFlagParam();
				if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.ADDITIONAL_TYPE) {
					if (codeFormatFlagParam.getAdditionalSerialNumber() == additionalSerialNumber) {
						item.setEnabled(true);
					}
				}
			}
		}
	}

	/**
	 * 查看必填模板有没有文件被选中
	 *
	 * @return
	 */
	private PathChooseMenuItem checkMainCodeFileAreSelected() {
		PathChooseMenuItem item = null, returnItem = null;
		CodeFormatFlagParam codeFormatFlagParam;
		for (int i = 0; i < getMenuComponentCount(); i++) {
			if (getItem(i) instanceof PathChooseMenuItem) {
				item = (PathChooseMenuItem) getItem(i);
				codeFormatFlagParam = item.getCodeFormatFlagParam();
				if (codeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MAIN_TYPE) {
					if (item.isSelected() == true) {
						returnItem = item;
						break;
					}
				}
			}
		}
		return returnItem;
	}

	/**
	 * 选中某个路径以后的操作
	 * @param pathChooseMenuItem
	 */
	protected abstract void afterSelectedMenuItem(PathChooseMenuItem pathChooseMenuItem);

	/**
	 * 取消选择某个路径文件后的操作
	 * @param pathChooseMenuItem
	 */
	protected abstract void afterDeselectMenuItem(PathChooseMenuItem pathChooseMenuItem);

}
