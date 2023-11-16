package com.lazycoder.uiutils.mycomponent;

import javax.swing.tree.DefaultMutableTreeNode;

public class MyNode extends DefaultMutableTreeNode {

	public static final boolean CAN_BE_DEL = true;
	public static final boolean CAN_NOT_BE_DEL = false;
	/**
	 *
	 */
	private static final long serialVersionUID = -8963959361970991353L;
	private boolean canDelOrNot = false;

	public MyNode(Object userObject, boolean allowsChildren, boolean canDel) {
		super(userObject, allowsChildren);
		this.canDelOrNot = canDel;
	}

	public boolean isCanDel() {
		return canDelOrNot;
	}

}
