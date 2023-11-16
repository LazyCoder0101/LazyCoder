package com.lazycoder.uidatasourceedit.formatedit.main.settype;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.MainSetMetaModel;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainSetCategoryEditPane extends JScrollPane implements CheckInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 2061190693648288695L;

	private JPanel panel;

	private JTextField textField;

	private MyButton addButton, delButton;

	private JScrollPane scrollPane;

	private Box verticalBox;

	/**
	 * 该分类面板的序号
	 */
	private int typeSerialNumber = 0;

	/**
	 * 当前添加方法数量
	 */
	private int currentNum = 0;


	private MainSetCategoryEditPane() {
		panel = new JPanel();
		setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		toolbarInit();

		verticalBox = Box.createVerticalBox();
		scrollPane = new JScrollPane(verticalBox);// 0.59
		Dimension dd = new Dimension((int) (SysUtil.SCREEN_SIZE.getWidth() * 0.297),
				(int) (SysUtil.SCREEN_SIZE.getHeight() * 0.59));
		scrollPane.setPreferredSize(dd);
		scrollPane.setMinimumSize(dd);
		scrollPane.setMaximumSize(dd);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		panel.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * 新建
	 *
	 * @param serial
	 */
	public MainSetCategoryEditPane(int serial) {
		this();
		this.typeSerialNumber = serial;
	}

	private void toolbarInit() {
		MyToolBar toolBar = new MyToolBar();
		panel.add(toolBar, BorderLayout.NORTH);

		JLabel label = new JLabel("设置分类名：");
		toolBar.add(label);
		textField = new JTextField();
		toolBar.add(textField);
		Dimension dimension = new Dimension(230, 30);
		textField.setPreferredSize(dimension);
		textField.setMaximumSize(dimension);
		textField.setMinimumSize(dimension);

		Component horizontalStrut1 = Box.createHorizontalStrut(35);
		toolBar.add(horizontalStrut1);

		addButton = new MyButton("添加");
		toolBar.add(addButton);
		addButton.addActionListener(listener);

		Component horizontalStrut2 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut2);

		delButton = new MyButton("删除");
		toolBar.add(delButton);
		delButton.addActionListener(listener);
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addButton) {
				addSetFunction();
				SysUtil.updateFrameUI(MainSetCategoryEditPane.this);
			} else if (e.getSource() == delButton) {
				delSetFunction();
				SysUtil.updateFrameUI(MainSetCategoryEditPane.this);
			}
		}
	};

	/**
	 * 显示该模块该分类的内容
	 *
	 * @param typeName
	 */
	public void displayMainSetContent(String typeName) {
		textField.setText(typeName);
		currentNum = 0;
		ArrayList<MainSetMetaModel> list = SysService.MAIN_SET_SERVICE.getMainSetMetaModelList(typeName);
		if (list != null) {
			MainSetEditMetaPane moduleSetMetaPane;
			for (int i = 0; i < list.size(); i++) {
				currentNum++;
				moduleSetMetaPane = new MainSetEditMetaPane(list.get(i));
				verticalBox.add(moduleSetMetaPane);
			}
		}
		this.updateUI();
		this.repaint();
	}

	/**
	 * 获取操作参数列表
	 */
	public ArrayList<FormatTypeOperatingModel> getOperationParamList() {
		MainSetEditMetaPane temp;
		FormatTypeOperatingModel tempModel;
		ArrayList<FormatTypeOperatingModel> list = new ArrayList<>();
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			temp = (MainSetEditMetaPane) verticalBox.getComponent(i);
			tempModel = temp.getOperatingModel();
			tempModel.setTypeName(textField.getText());
			tempModel.setTypeSerialNumber(this.typeSerialNumber);
			list.add(tempModel);
		}
		return list;
	}

	/**
	 * 获取代码参数列表
	 */
	public ArrayList<FormatTypeCodeModel> getCodeParamList() {
		MainSetEditMetaPane temp;
		FormatTypeCodeModel tempModel;
		String typeName = textField.getText();
		ArrayList<FormatTypeCodeModel> list = new ArrayList<>(), tempList;
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			temp = (MainSetEditMetaPane) verticalBox.getComponent(i);
			tempList = temp.getCodeModelList();
			for (int a = 0; a < tempList.size(); a++) {
				tempModel = tempList.get(a);
				tempModel.setTypeSerialNumber(this.typeSerialNumber);
				tempModel.setTypeName(typeName);
				list.add(tempModel);
			}
		}
		return list;
	}

	private void addSetFunction() {
		currentNum++;
		MainSetEditMetaPane moduleSetMetaPane = new MainSetEditMetaPane(typeSerialNumber, currentNum);
		verticalBox.add(moduleSetMetaPane);

		this.updateUI();
		this.repaint();
	}

	private void delSetFunction() {
		if (verticalBox.getComponentCount() > 0) {
			currentNum--;
			verticalBox.remove(verticalBox.getComponentCount() - 1);
			this.updateUI();
			this.repaint();
		}
	}

	/**
	 * 获取该分类的方法数
	 *
	 * @return
	 */
	public int getMainSetOpreatingNum() {
		return verticalBox.getComponentCount();
	}

	/**
	 * 获取该分类的某个方法的代码数
	 */
	public int getMainSetCodeNum(int opreatingSerial) {
		int moduleSetCodeNum = MarkElementName.MARK_NULL;
		MainSetEditMetaPane teMetaPane;
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			teMetaPane = (MainSetEditMetaPane) verticalBox.getComponent(i);
			if (teMetaPane.getOrdinal() == opreatingSerial) {
				moduleSetCodeNum = teMetaPane.getMainSetCodeNum();
				break;
			}
		}
		return moduleSetCodeNum;
	}

	public ArrayList<ContainerModel> getAllEditContainerModel() {
		ArrayList<ContainerModel> list = new ArrayList<ContainerModel>();
		MainSetEditMetaPane teMetaPane;
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			teMetaPane = (MainSetEditMetaPane) verticalBox.getComponent(i);
			list.add(teMetaPane.getContainerModel());
		}
		return list;
	}

	/**
	 * 获取模块类型名
	 *
	 * @return
	 */
	public String getMainSetTypeName() {
		return textField.getText().trim();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if ("".equals(textField.getText().trim())) {
			flag = false;
			LazyCoderOptionPane.showMessageDialog(null, "必填模板设置里的\"设置" + typeSerialNumber + "\"还没写分类名，请确认数据无误后再保存", "系统信息",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			if (checkSameNameFromMetaPane() == true) {
				if (verticalBox.getComponentCount() > 0) {
					MainSetEditMetaPane teMetaPane1, teMetaPane2;
					for (int i = 0; i < verticalBox.getComponentCount(); i++) {
						teMetaPane1 = (MainSetEditMetaPane) verticalBox.getComponent(i);
						if (teMetaPane1.check() == false) {
							flag = false;
							break;
						}
					}
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

	private boolean checkSameNameFromMetaPane() {
		boolean flag = true;
		String showText1, showText2;
		MainSetEditMetaPane paneTemp1, paneTemp2;
		if (verticalBox.getComponentCount() > 0) {
			for (int i = 0; i < verticalBox.getComponentCount(); i++) {
				paneTemp1 = (MainSetEditMetaPane) verticalBox.getComponent(i);
				showText1 = paneTemp1.getShowText();
				for (int a = i + 1; a < verticalBox.getComponentCount(); a++) {
					paneTemp2 = (MainSetEditMetaPane) verticalBox.getComponent(a);
					showText2 = paneTemp2.getShowText();
					if (showText1.equals(showText2)) {
						flag = false;
						LazyCoderOptionPane.showMessageDialog(null, "必填模板设置里\"" + getMainSetTypeName() + "\"分类中，第" + (i + 1)
								+ "个方法和第" + (a + 1) + "个方法显示名称一样，\n请检查清楚录入数据无误后再保存", "系统信息", JOptionPane.PLAIN_MESSAGE);
						break;
					}
				}
				if (flag == false) {
					break;
				}
			}
		}
		return flag;
	}

	public int getTypeSerialNumber() {
		return typeSerialNumber;
	}

}
