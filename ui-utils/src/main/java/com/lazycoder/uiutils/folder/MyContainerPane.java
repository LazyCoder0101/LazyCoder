package com.lazycoder.uiutils.folder;

import java.awt.Dimension;

public class MyContainerPane extends Folder {

	/**
	 *
	 */
	private static final long serialVersionUID = 2167134504514228097L;

	private int width = 500, pHeight = 35;

	public MyContainerPane() {
		// TODO Auto-generated constructor stub
		// 设置自己的layout
		setLayout(null);
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

}
