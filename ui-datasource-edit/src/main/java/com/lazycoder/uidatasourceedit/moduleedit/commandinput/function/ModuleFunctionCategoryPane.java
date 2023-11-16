package com.lazycoder.uidatasourceedit.moduleedit.commandinput.function;

import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.command.FunctionOperatingModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.meta.FunctionMetaModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
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
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ModuleFunctionCategoryPane extends JScrollPane implements CheckInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 9003716945576429248L;

	private JPanel panel;
	private JTextField nameField;
	private MyButton addButton, delButton;
	private JScrollPane scrollPane;
	private Box verticalBox;

	/**
	 * 该分类面板的序号
	 */
	private int typeSerialNumber = 0;

	/**
	 * 当前添加功能数量
	 */
	private int currentNum = 0;


	/**
	 *
	 */
	private ModuleFunctionCategoryPane() {
//		setBorder(null);// 无边框，更贴合容器
//		getVerticalScrollBar().setUI(new MyScrollBarUI());
		panel = new JPanel();
		setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		toolbarInit();

		verticalBox = Box.createVerticalBox();
		scrollPane = new JScrollPane(verticalBox);
		Dimension dd = new Dimension((int) (SysUtil.SCREEN_SIZE.getWidth() * 0.295),
				(int) (SysUtil.SCREEN_SIZE.getHeight() * 0.59));
		scrollPane.setPreferredSize(dd);
		scrollPane.setMinimumSize(dd);
		scrollPane.setMaximumSize(dd);
//		scrollPane.setBorder(null);// 无边框，更贴合容器
//		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());

		panel.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * 新建
	 *
	 * @param typeSerialNumber
	 */
	public ModuleFunctionCategoryPane(int typeSerialNumber) {
		this();
		this.typeSerialNumber = typeSerialNumber;
	}

	private void toolbarInit() {
		MyToolBar toolBar = new MyToolBar();
		panel.add(toolBar, BorderLayout.NORTH);

		JLabel label = new JLabel("功能分类名：");
		toolBar.add(label);
		nameField = new JTextField();
		toolBar.add(nameField);
		Dimension dimension = new Dimension(230, 30);
		nameField.setPreferredSize(dimension);
		nameField.setMaximumSize(dimension);
		nameField.setMinimumSize(dimension);

		Component horizontalStrut = Box.createHorizontalStrut(35);
		toolBar.add(horizontalStrut);

		addButton = new MyButton("添加");
		toolBar.add(addButton);
		addButton.addActionListener(listener);

		Component horizontalStrut1 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut1);

		delButton = new MyButton("删除");
		toolBar.add(delButton);
		delButton.addActionListener(listener);
	}

	/**
	 * 显示该模块该分类的内容
	 *
	 * @param typeName
	 */
	public void displayModuleContent(String typeName) {
		nameField.setText(typeName);
		ArrayList<FunctionMetaModel> list = SysService.FUNCTION_SERVICE.getFunctionMetaModelList(DataSourceEditHolder.currentModule.getModuleId(), typeName);
		ModuleFunctionEditMetaPane functionMetaPane;
		currentNum = 0;
		for (FunctionMetaModel model: list) {
			currentNum = currentNum + 1;
			functionMetaPane = new ModuleFunctionEditMetaPane(typeSerialNumber, model);
			verticalBox.add(functionMetaPane);

		}
		this.updateUI();
		this.repaint();
	}

	/**
	 * 获取操作参数列表
	 */
	public ArrayList<FunctionOperatingModel> getOperationParamList() {
		ModuleFunctionEditMetaPane temp;
		FunctionOperatingModel oModel;
		ArrayList<FunctionOperatingModel> list = new ArrayList<>();
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			temp = (ModuleFunctionEditMetaPane) verticalBox.getComponent(i);
			oModel = temp.getOperatingModel();
			oModel.setTypeName(nameField.getText());
			oModel.setTypeSerialNumber(typeSerialNumber);
			list.add(oModel);
		}
		return list;
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addButton) {
				addFunction();
				SysUtil.updateFrameUI(ModuleFunctionCategoryPane.this);
			} else if (e.getSource() == delButton) {
				delFunction();
				SysUtil.updateFrameUI(ModuleFunctionCategoryPane.this);
			}
		}
	};

	/**
	 * 获取代码参数列表
	 */
	public ArrayList<FunctionCodeModel> getCodeParamList() {
		ModuleFunctionEditMetaPane temp;
		FunctionCodeModel tempModel;
		String typeName = nameField.getText();
		ArrayList<FunctionCodeModel> list = new ArrayList<>(), tempList;
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			temp = (ModuleFunctionEditMetaPane) verticalBox.getComponent(i);
			tempList = temp.getCodeModelList();
			for (int a = 0; a < tempList.size(); a++) {
				tempModel = tempList.get(a);
				tempModel.setTypeName(typeName);
				tempModel.setTypeSerialNumber(this.typeSerialNumber);
				list.add(tempModel);
			}
		}
		return list;
	}

	public ArrayList<ContainerModel> getAllEditContainerModel() {
		ArrayList<ContainerModel> list = new ArrayList<ContainerModel>();
		ModuleFunctionEditMetaPane temp;
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			temp = (ModuleFunctionEditMetaPane) verticalBox.getComponent(i);
			list.add(temp.getContainerModel());
		}
		return list;
	}

	private void addFunction() {
		currentNum = currentNum + 1;
		ModuleFunctionEditMetaPane moduleFunctionEditMetaPane = new ModuleFunctionEditMetaPane(typeSerialNumber, currentNum);
		verticalBox.add(moduleFunctionEditMetaPane);

		scrollPane.updateUI();
		scrollPane.repaint();
		this.updateUI();
		this.repaint();
	}

	private void delFunction() {
		if (verticalBox.getComponentCount() > 0) {
			currentNum = currentNum - 1;
			verticalBox.remove(verticalBox.getComponentCount() - 1);
			scrollPane.updateUI();
			scrollPane.repaint();
			this.updateUI();
			this.repaint();
		}
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if ("".equals(nameField.getText().trim())) {
			LazyCoderOptionPane.showMessageDialog(null, "模块功能里第" + typeSerialNumber + "个没写分类名", "系统信息", JOptionPane.PLAIN_MESSAGE);
			flag = false;
		} else {
			if (checkSameNameFromMetaPane() == true) {
				ModuleFunctionEditMetaPane teMetaPane1;
				for (int i = 0; i < verticalBox.getComponentCount(); i++) {
					teMetaPane1 = (ModuleFunctionEditMetaPane) verticalBox.getComponent(i);
					if (teMetaPane1.check() == false) {
						flag = false;
						break;
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
		ModuleFunctionEditMetaPane paneTemp1, paneTemp2;
		if (verticalBox.getComponentCount() > 0) {
			for (int i = 0; i < verticalBox.getComponentCount(); i++) {
				paneTemp1 = (ModuleFunctionEditMetaPane) verticalBox.getComponent(i);
				showText1 = paneTemp1.getShowText();
				for (int a = i + 1; a < verticalBox.getComponentCount(); a++) {
					paneTemp2 = (ModuleFunctionEditMetaPane) verticalBox.getComponent(a);
					showText2 = paneTemp2.getShowText();
					if (showText1.equals(showText2)) {
						flag = false;
						LazyCoderOptionPane.showMessageDialog(null,
								"模块功能中，第" + (i + 1) + "个功能和第" + (a + 1) + "个功能显示名称一样，\n请检查清楚录入数据无误后再保存", "系统信息",
								JOptionPane.PLAIN_MESSAGE);
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

	/**
	 * 获取分类名
	 *
	 * @return
	 */
	public String getTypeName() {
		String typeName = nameField.getText();
		return typeName;
	}

	public int getTypeSerialNumber() {
		return typeSerialNumber;
	}

}
