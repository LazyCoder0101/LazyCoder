package com.lazycoder.uiutils.component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.Enumeration;

/**
 * 该部分源码多源自网上查找所得，已找不到出处，略有改动
 */
public class TreeUtil {

	/*
	 * 根据path内的文件生成树
	 */
	public static DefaultMutableTreeNode traverseFolder(File folder) {
		DefaultMutableTreeNode fujiedian = null;
		DefaultMutableTreeNode temp;

		if (folder.exists()) {
			File[] files = folder.listFiles();
			fujiedian = new DefaultMutableTreeNode(folder.getName(), true);
			if (files.length == 0) {
				if (folder.isDirectory()) {// 如果是空文件夹
					fujiedian = new DefaultMutableTreeNode(folder.getName(), true);
					return fujiedian;
				}
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						// 是目录的话，生成节点，并添加里面的节点
						fujiedian.add(traverseFolder(file2));
					} else {
						// 是文件的话直接生成节点，并把该节点加到对应父节点上
						temp = new DefaultMutableTreeNode(file2.getName(), false);
						fujiedian.add(temp);
					}
				}
			}
		} else {// 文件不存在
			return null;
		}
		return fujiedian;
	}

	// 展开树的所有节点的方法
	@SuppressWarnings("rawtypes")
	private static void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements(); ) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	public static void expandTree(JTree tree) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root), true);
	}

	/**
	 * 根据传过来的树节点返回对应路径
	 *
	 * @param path
	 * @return
	 */
	public static String getCorrespondingPath(TreePath path) {
		Object[] pathList = path.getPath();
		StringBuilder out = new StringBuilder("");
		for (int i = 1; i < pathList.length; i++) {
			if (i == 1) {
				out.append(pathList[i]);
			} else {
				out.append(System.getProperty("file.separator") + pathList[i]);
			}
		}
		return out.toString();
	}

	public static String getCorrespondingPath(TreeNode[] pathList) {
		StringBuilder temp = new StringBuilder();
		for (int i = 1; i < pathList.length; i++) {
			if (i == 1) {
				temp.append(pathList[i]);
			} else {
				temp.append(System.getProperty("file.separator") + pathList[i]);
			}
		}
		return temp.toString();
	}

}
