package com.lazycoder.uicodegeneration.moduleselect;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTreeUI;

/**
 * 重写UI，主要为了左对齐
 *
 * @author sf_dream
 * @date 2015年5月25日
 * 源码摘自https://www.jiweichengzhu.com/article/5ff1be1e8abe463794aa1011b4bcff96
 */
public class ModuleSelectPaneUI extends BasicTreeUI {

	// 去除JTree的垂直线
	@Override
	protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
	}

	// 去除JTree的水平线
	@Override
	protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
	}

	// 实现父节点与子节点左对齐
	@Override
	public void setLeftChildIndent(int newAmount) {

	}

	// 实现父节点与子节点右对齐
	@Override
	public void setRightChildIndent(int newAmount) {

	}

}
