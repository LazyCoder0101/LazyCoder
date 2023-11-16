package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.GeneralSettingPaneHolder;
import com.lazycoder.uiutils.mycomponent.AbstractTemplateFileSelectDialog;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class RenameFileSelectDialog extends AbstractTemplateFileSelectDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = -980618391810335585L;

	public RenameFileSelectDialog() {
		super(DatabaseFileStructure
				.getTemplateFolder(SysFileStructure.getDataFileFolder(), GeneralHolder.currentUserDataSourceLabel.getDataSourceName())
				.getAbsolutePath(), "源码模板");
		setTitle("请选择需要重命名的文件");
		setVisible(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void ok() {
		// TODO Auto-generated method stub
		String filePath = textField.getText();
		if ("".equals(filePath)) {
			LazyCoderOptionPane.showMessageDialog(this, "o(´^｀)o    亲，你还没告诉我你要设置哪个文件在生成源码时改为项目名", "系统信息",
					JOptionPane.PLAIN_MESSAGE);

		} else {
			if (GeneralSettingPaneHolder.renameFilePane != null) {
				ArrayList<String> list = GeneralSettingPaneHolder.renameFilePane.getRenameFileList();
				boolean flag = checkHaveTheFileOrNot(filePath, list);
				if (flag == true) {// 之前选过这个文件了
					LazyCoderOptionPane.showMessageDialog(this, "o(´^｀)o    亲，注意看一下，你之前添加过这个文件了", "系统信息",
							JOptionPane.PLAIN_MESSAGE);
				} else {
					list.add(filePath);
					SysService.SYS_PARAM_SERVICE.setRenameFileList(list);
					GeneralSettingPaneHolder.renameFilePane.updateList();
					LazyCoderOptionPane.showMessageDialog(this, "添加成功", "系统信息", JOptionPane.PLAIN_MESSAGE);
					dispose();
				}
			}
		}
	}

	/**
	 * 看看有没有添加过这个文件
	 *
	 * @param filePath
	 * @param list
	 * @return
	 */
	private boolean checkHaveTheFileOrNot(String filePath, ArrayList<String> list) {
		boolean flag = false;
		for (String temp : list) {
			if (temp.equals(filePath)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

}
