package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment;

import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.uiutils.popup.AfPopup;
import com.lazycoder.uiutils.popup.AfPopupMouseGrabber;
import com.lazycoder.uiutils.popup.AfPopupPanel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import org.apache.commons.lang3.StringUtils;

public class MultipleInputForCodeGeneration extends MyToolBar {

	protected final static int TEXT_FIELD_WIDTH = 120, THIS_HEIGHT = 30;
	/**
	 *
	 */
	private static final long serialVersionUID = 5139995696835714814L;
	private final static Dimension DIMENSION = new Dimension(TEXT_FIELD_WIDTH, THIS_HEIGHT);
	protected JTextField textField;
	protected MyButton addButton, delButton;
	protected MyPopupButton expandButton;
	protected MultipleInputPaneForCodeGeneration multipleInputPane = null;
	protected JScrollPane scrollPane;


	/**
	 * Create the panel.
	 */
	public MultipleInputForCodeGeneration() {

		textField = new JTextField();
		textField.setEditable(false);
		textField.setPreferredSize(DIMENSION);
		textField.setMaximumSize(DIMENSION);
		textField.setMinimumSize(DIMENSION);
		add(textField);

		expandButton = new MyPopupButton(){

			@Override
			protected void showPopupPane() {
				showPanel();
			}

			@Override
			public void hidePopupPanel() {
				MultipleInputForCodeGeneration.this.hidePopupPanel();
			}
		};
//		expandButton.setSelected(false);
//		expandButton.setFocusPainted(false);
		add(expandButton);

		addButton = new MyButton();
		addButton.addActionListener(listener);
		add(addButton);

		delButton = new MyButton();
		delButton.addActionListener(listener);
		add(delButton);

		scrollPane = new JScrollPane();
		Dimension dimension = new Dimension(160, MultipleInputPaneForCodeGeneration.PANE_HEIGHT);
		scrollPane.setPreferredSize(dimension);
		scrollPane.setMinimumSize(dimension);
		scrollPane.setMaximumSize(dimension);
		scrollPane.setSize(dimension);
	}

	private void showPanel() {
		if (expandButton.isSelected() == true) {
//			if (multipleInputPane == null) {
			multipleInputPane = new MultipleInputPaneForCodeGeneration(scrollPane);
			Point miPoint = this.getLocationOnScreen();

			if (miPoint.y + THIS_HEIGHT
					+ MultipleInputPaneForCodeGeneration.PANE_HEIGHT < SysUtil.SCREEN_SIZE.height -  SysUtil.taskbarInsets.bottom) {
				multipleInputPane.showPopup(expandButton, 0 - TEXT_FIELD_WIDTH,
						expandButton.getIcon().getIconHeight());

			} else {
				multipleInputPane.showPopup(expandButton, 0 - TEXT_FIELD_WIDTH,
						0 - MultipleInputPaneForCodeGeneration.PANE_HEIGHT);
			}

//			}
		} else {
			if (multipleInputPane != null) {
				multipleInputPane.hidePopup();
				multipleInputPane = null;
			}
		}
	}



	/**
	 * 获取显示字符
	 *
	 * @param list
	 * @return
	 */
	protected String getShowStr(ArrayList<String> list) {
		return StringUtils.join(list,"、");
	}

	/**
	 * 设置图标
	 *
	 * @param addIcon
	 * @param minusIcon
	 * @param expandIcon
	 */
	protected void setIcon(Icon addIcon, Icon minusIcon, Icon expandIcon, Icon collapseIcon) {
		// TODO Auto-generated constructor stub
		addButton.setIcon(addIcon);
		Dimension d1 = new Dimension(addIcon.getIconWidth(), addIcon.getIconHeight());
		addButton.setPreferredSize(d1);
		addButton.setMaximumSize(d1);
		addButton.setMinimumSize(d1);

		delButton.setIcon(minusIcon);
		Dimension d2 = new Dimension(minusIcon.getIconWidth(), minusIcon.getIconHeight());
		delButton.setPreferredSize(d2);
		delButton.setMaximumSize(d2);
		delButton.setMinimumSize(d2);

		expandButton.setIcon(expandIcon);
		expandButton.setSelectedIcon(collapseIcon);
		Dimension d3 = new Dimension(expandIcon.getIconWidth(), expandIcon.getIconHeight());
		expandButton.setPreferredSize(d3);
		expandButton.setMaximumSize(d3);
		expandButton.setMinimumSize(d3);
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addButton) {
				expandButton.expandPanel();

			} else if (e.getSource() == delButton) {
				expandButton.expandPanel();

			}
			scrollPane.updateUI();
			scrollPane.repaint();
		}
	};

//	private ChangeListener changeListener = new ChangeListener() {
//
//		@Override
//		public void stateChanged(ChangeEvent e) {
//			// TODO Auto-generated method stub
//			showPanel();
//		}
//	};



	protected void hidePopupPanel() {
		// TODO Auto-generated method stub
		if (multipleInputPane != null) {
			multipleInputPane.hidePopup();
			multipleInputPane=null;
		}
	}

	class MultipleInputPaneForCodeGeneration extends AfPopupPanel {

		/**
		 *
		 */
		private static final long serialVersionUID = -6774506799238823087L;

		private static final int PANE_HEIGHT = 200;

		public MultipleInputPaneForCodeGeneration(JComponent component) {
			setLayout(new BorderLayout());
			add(component, BorderLayout.CENTER);
		}

		@Override
		protected AfPopupMouseGrabber getMouseGrabber(Component owner, JWindow popup) {
			// TODO Auto-generated method stub
			return new AfPopupMouseGrabber(owner, popup) {
				@Override
				public void cancelPopup() {
					// TODO Auto-generated method stub
					super.cancelPopup();
					if (expandButton != null) {
						if (expandButton.isSelected() == true) {
							expandButton.setSelected(false);
						}
					}
				}
			};
		}

		@Override
		public void showPopup(Component owner, int x, int y) {
			AfPopup.ClosingPolicy closingPolicy = new AfPopup.ClosingPolicy();
			closingPolicy.setAutoClose(true);
			showPopup(owner, x, y, closingPolicy); // 使用默认关闭策略
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(TEXT_FIELD_WIDTH + 30, PANE_HEIGHT);
		}

	}

}
