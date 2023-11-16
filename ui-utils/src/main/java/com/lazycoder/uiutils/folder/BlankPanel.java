package com.lazycoder.uiutils.folder;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * 空白面板
 *
 * @author Administrator
 */
public class BlankPanel extends Folder {

	/**
	 *
	 */
	private static final long serialVersionUID = 8207369363892160217L;

	private int width = 300, pHeight = 10;

	public BlankPanel() {
		// TODO Auto-generated constructor stub
		// 设置自己的layout
		setLayout(new UseCodeFolderLayout());

	}

	// 获得该面板目前所需的空间大小：drawer+caption_height
	@Override
	public Dimension getRequiredDimension() {
		// 高度是抽屉的高度加上标题的高度，抽屉的高度是目前抽出的长度
		return new Dimension(width, pHeight);
	}

	public void setPHeight(int height) {
		pHeight = height;
	}

	/**
	 * 负责布局面板组件
	 */
	class UseCodeFolderLayout implements LayoutManager {
		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			// TODO Auto-generated method stub
			return parent.getPreferredSize();
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			// TODO Auto-generated method stub
			return parent.getMinimumSize();
		}

		@Override
		public void layoutContainer(Container parent) {
			// TODO Auto-generated method stub
			int w = parent.getWidth();
			int h = parent.getHeight();
		}
	}

}
