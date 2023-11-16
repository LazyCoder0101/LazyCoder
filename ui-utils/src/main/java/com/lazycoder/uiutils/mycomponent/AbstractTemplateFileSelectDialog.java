package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.uiutils.component.TreeUtil;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * 文件选择窗口，通过该窗口获取用户设置的文件，继承该类重写ok方法即可
 *
 * @author admin
 */
public abstract class AbstractTemplateFileSelectDialog extends LazyCoderCommonDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = -3955727442669494658L;

	private final JPanel contentPanel = new JPanel();

	protected JTextField textField;

	private MyButton okButton, cancelButton;

	private JTree tree;

	private Dimension screenSize = SysUtil.SCREEN_SIZE;

	/**
	 * @param parentPath   要生成的文件树的文件夹路径
	 * @param rootShowText 根目录显示字样
	 */
	public AbstractTemplateFileSelectDialog(String parentPath, String rootShowText) {
		setBounds(screenSize.width / 2 - 260, screenSize.height / 2 - 300, 450, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		DefaultMutableTreeNode node = TreeUtil.traverseFolder(new File(parentPath));
		node.setUserObject("源码模板");
		tree = new JTree(node);
		tree.setCellRenderer(new FileSettingTreeCellRenderer());
		treeNodeLister();

		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setBounds(44, 24, 336, 300);
		contentPanel.add(scrollPane);

		textField = new JTextField();
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
		TreeUtil.expandTree(tree);
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
	 * 树节点的监听器：右键菜单
	 */
	private void treeNodeLister() {
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int row = tree.getRowForLocation(e.getX(), e.getY());
				TreePath path = tree.getPathForRow(row); // 按了单选框后，从根目录到单选框文件的路径
				if (path == null) {
					return;
				} else {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();// 单选框的文件的文件名
					if (node != null) {
						if (node.getAllowsChildren() == false) {
							tree.setSelectionPath(path);
							String correspondingPath = TreeUtil.getCorrespondingPath(path);
							textField.setText(correspondingPath);
						}
					}
				}
			}
		});
	}

	/**
	 * 获取设置路径
	 *
	 * @return
	 */
	public String getPathStr() {
		return textField.getText();
	}

}
