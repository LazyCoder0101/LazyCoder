package com.lazycoder.uidatasourceedit.component;

import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.AbstractMyFileTree;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 路径设置窗口，通过该类获取用户设置的路径
 *
 * @author admin
 */
public abstract class AbstractGetPathDialog extends LazyCoderCommonDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = -1110961525686235033L;

	private final JPanel contentPanel = new JPanel();

	private JTextField textField;

	private MyButton okButton, cancelButton;

	private PathTree tree;

	private Dimension screenSize = SysUtil.SCREEN_SIZE;

	/**
	 * Create the dialog.
	 */
	protected AbstractGetPathDialog(String pathStr, String rootShowText) {
		setBounds(screenSize.width / 2 - 260, screenSize.height / 2 - 300, 450, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		tree = new PathTree(
				pathStr, rootShowText);

//		tree.setRootVisible(false);
		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setBounds(44, 25, 336, 300);
		contentPanel.add(scrollPane);

		textField = new JTextField(pathStr);
		textField.setEditable(false);
		textField.setBounds(128, 350, 200, 30);
		contentPanel.add(textField);

		JLabel label = new JLabel("选择路径：");
		label.setBounds(45, 356, 103, 18);
		contentPanel.add(label);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new MyButton("确定");
				okButton.setActionCommand("OK");
				okButton.addActionListener(actionListener);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new MyButton("取消");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(actionListener);
				buttonPane.add(cancelButton);
			}
		}

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
//		TreeUtil.expandTree(tree);
	}

	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == okButton) {
				ok();
			} else if (e.getSource() == cancelButton) {
				dispose();
			}
		}
	};

	protected abstract void ok();

	/**
	 * 获取设置路径
	 *
	 * @return
	 */
	public String getPathStr() {
		return textField.getText();
	}

	class PathTree extends AbstractMyFileTree {

		/**
		 *
		 */
		private static final long serialVersionUID = 6455587320026230568L;

		public PathTree(String pathStr, String rootShowText) {
			super(DatabaseFileStructure.getTemplateFolder(SysFileStructure.getDataFileFolder(),
					GeneralHolder.currentUserDataSourceLabel.getDataSourceName()), pathStr, rootShowText);
			// TODO Auto-generated constructor stub

		}

		@Override
		protected void doSomethingWhenClickedAbout(String correspondingPath) {
			// TODO Auto-generated method stub
			textField.setText(correspondingPath);
		}

	}
}
