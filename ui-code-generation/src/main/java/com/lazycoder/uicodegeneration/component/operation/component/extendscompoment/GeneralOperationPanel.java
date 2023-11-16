package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment;

import com.lazycoder.uiutils.mycomponent.MyToolBar;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class GeneralOperationPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -7448806009002676375L;

	protected JScrollPane scrollPane;
	protected MyToolBar toolBar;
	private int xOld = 0, yOld = 0;

	/**
	 * Create the frame.
	 */
	public GeneralOperationPanel() {
		setLayout(new BorderLayout());

		toolBar = new MyToolBar();
		toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
		add(toolBar, BorderLayout.NORTH);

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

	}


}
