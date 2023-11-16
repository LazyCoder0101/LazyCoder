package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


/**
 * 使用文档面板
 *
 * @author admin
 */
public class UsingDocumentPane extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -7493424396918691904L;

	private JPopupMenu menu;
	private JMenuItem del;

	/**
	 * Create the panel.
	 */
	public UsingDocumentPane() {
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		this.setLayout(fl);
		updateList();
		addMenu();
		this.setBackground(Color.white);
		this.setSize(200, 500);
		this.setVisible(true);
	}

	public void updateList() {
		this.removeAll();
		this.repaint();
		Box vBox = Box.createVerticalBox();
		this.add(vBox);
		File sysDataFileFolder = SysFileStructure.getDataFileFolder();
		File[] fileList = DatabaseFileStructure.getUserDocFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName()).listFiles();
		if (fileList != null) {
			if (fileList.length > 0) {

				UsingDocumentLabel usingDocumentLabel;
				for (int i = 0; i < fileList.length; i++) {
					usingDocumentLabel = new UsingDocumentLabel(fileList[i].getName());
					usingDocumentLabel.addMouseListener(thisMouseAdapter);
					vBox.add(usingDocumentLabel);
				}
			}
		}
		this.setSize(200, 500);
	}

	private void addMenu() {
		menu = new JPopupMenu();
		del = new JMenuItem("删除");
		del.addActionListener(menuLisener);
		menu.add(del);
	}

	private MouseAdapter thisMouseAdapter = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.isMetaDown()) {// 检测鼠标右键单击
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

	};

	private ActionListener menuLisener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == del) {
				String getName = ((UsingDocumentLabel) menu.getInvoker()).getUsingDocFileName();

				int n = LazyCoderOptionPane.showConfirmDialog(null,
						"(～￣▽￣)～  确定要删除\"" + getName + "\"这个文件吗?", "系统信息",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.OK_OPTION) {

					File sysDataFileFolder = SysFileStructure.getDataFileFolder();
					File file = new File(
							DatabaseFileStructure.getUserDocFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName()).getAbsolutePath()
									+ File.separator + getName);
					if (file.isFile()) {
						file.delete();
					}
					updateList();
				}
			}

		}
	};


}
