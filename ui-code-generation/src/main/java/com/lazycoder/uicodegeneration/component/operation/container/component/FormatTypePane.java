package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import com.lazycoder.uiutils.utils.SysUtil;

public class FormatTypePane extends MyPopupButton {

	public static final int DRAFAULT_BUTTON_WIDTH = 100, DRAFAULT_BUTTON_HEIGHT = 30,
			DRAFAULT_PANE_WIDTH = (int) (0.335 * SysUtil.SCREEN_SIZE.getWidth());
	/**
	 *
	 */
	private static final long serialVersionUID = -163891618724187986L;

	public FormatTypePane() {
		// TODO Auto-generated method stub
		super("设置");
		buttonHeight = DRAFAULT_BUTTON_HEIGHT;
		buttonWidth = DRAFAULT_BUTTON_WIDTH;
		paneWidth = DRAFAULT_PANE_WIDTH;
		paneHeight = 400;
	}


}
