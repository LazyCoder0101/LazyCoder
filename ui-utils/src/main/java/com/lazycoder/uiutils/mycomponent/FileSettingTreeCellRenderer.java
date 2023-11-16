package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.service.fileStructure.SysFileStructure;
import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class FileSettingTreeCellRenderer extends DefaultTreeCellRenderer {

	/**
	 *
	 */
	private static final long serialVersionUID = -2062523012123151335L;

	public final ImageIcon treeClosedIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit"
			+ File.separator + "base_code_content_edit" + File.separator + "tree" + File.separator + "TreeClosed.png"),
			treeOpenIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator
					+ "base_code_content_edit" + File.separator + "tree" + File.separator + "TreeOpen.png"),
			fileIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator
					+ "base_code_content_edit" + File.separator + "tree" + File.separator + "file.png");

	/**
	 * 重写父类DefaultTreeCellRenderer的方法
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
												  int row, boolean hasFocus) {

		// 执行父类原型操作
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		if (((DefaultMutableTreeNode) value).getAllowsChildren() == true) {// 用户添加的文件夹
			if (expanded == true) {// 如果文件夹展开
				this.setIcon(treeOpenIcon);
			} else {// 如果文件夹折叠
				this.setIcon(treeClosedIcon);
			}
		} else {// 文件
			this.setIcon(fileIcon);
		}
		return this;
	}

}
