package com.lazycoder.uidatasourceedit.formatedit.additional.settype;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.AdditionalSetMetaModel;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalCodeFormatPutPane;
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


public class AdditionalSetCategoryEditPane extends JScrollPane implements CheckInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 2061190693648288695L;

	private JPanel panel;

	private JTextField textField;

	private MyButton addButton, delButton;

	private JScrollPane scrollPane;

	private Box verticalBox;

	private int additionalSerialNumber = 0;

	/**
	 * 该分类面板的序号
	 */
	private int typeSerialNumber = 0;

	private AdditionalCodeFormatPutPane additionalCodeFormatPutPane;

	/**
	 * 当前添加方法数量
	 */
	private int currentNum = 0;

	private AdditionalSetCategoryEditPane() {
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
		panel.add(scrollPane, BorderLayout.CENTER);

	}

	/**
	 * 新建
	 *
	 * @param additionalSerialNumber
	 * @param additionalCodeFormatPutPane
	 * @param typeSerialNumber
	 */
	public AdditionalSetCategoryEditPane(int additionalSerialNumber, AdditionalCodeFormatPutPane additionalCodeFormatPutPane,
										 int typeSerialNumber) {
		this();
		this.additionalSerialNumber = additionalSerialNumber;
		this.typeSerialNumber = typeSerialNumber;
		this.additionalCodeFormatPutPane = additionalCodeFormatPutPane;
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

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addButton) {
				addSetFunction();
				SysUtil.scrollToBottom(scrollPane);
			} else if (e.getSource() == delButton) {
				delSetFunction();
				SysUtil.scrollToBottom(scrollPane);
			}
		}
	};

	/**
	 * 显示该模块该分类的内容
	 *
	 * @param typeName
	 */
	public void displayAdditionalSetContents(String typeName) {
		currentNum = 0;
		textField.setText(typeName);
		ArrayList<AdditionalSetMetaModel> list = SysService.ADDITIONAL_SET_SERVICE.getAdditionalSetMetaModelList(additionalSerialNumber,
				typeName);
		if (list != null) {
			AdditionalSetEditMetaPane metaPane;
			for (int i = 0; i < list.size(); i++) {
				currentNum++;
				metaPane = new AdditionalSetEditMetaPane(list.get(i), additionalCodeFormatPutPane);
				verticalBox.add(metaPane);
			}
		}
		this.updateUI();
		this.repaint();
	}

	public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
		ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
		AdditionalSetEditMetaPane temp;
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			temp = (AdditionalSetEditMetaPane) verticalBox.getComponent(i);
			list.add(temp.getContainerModel());
		}
		return list;
	}

	/**
	 * 获取操作参数列表
	 */
	public ArrayList<FormatTypeOperatingModel> getOperationParamList() {
		AdditionalSetEditMetaPane temp;
		FormatTypeOperatingModel tempModel;
		String typeName = textField.getText().trim();
		ArrayList<FormatTypeOperatingModel> list = new ArrayList<>();
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			temp = (AdditionalSetEditMetaPane) verticalBox.getComponent(i);
			tempModel = temp.getOperatingModel();
			tempModel.setTypeName(typeName);
			list.add(tempModel);
		}
		return list;
	}

	/**
	 * 获取代码参数列表
	 */
	public ArrayList<FormatTypeCodeModel> getCodeParamList() {
		AdditionalSetEditMetaPane temp;
		String typeName = textField.getText().trim();
		ArrayList<FormatTypeCodeModel> list = new ArrayList<>(), tempList;
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			temp = (AdditionalSetEditMetaPane) verticalBox.getComponent(i);
			tempList = temp.getCodeModelList();
			for (FormatTypeCodeModel tempModel : tempList) {
				tempModel.setTypeName(typeName);
				list.add(tempModel);
			}
		}
		return list;
	}

	private void addSetFunction() {
		currentNum++;
		AdditionalSetEditMetaPane metaPane = new AdditionalSetEditMetaPane(this.additionalSerialNumber, additionalCodeFormatPutPane,
				typeSerialNumber, currentNum);
		verticalBox.add(metaPane);
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
	public int getAdditionalSetOpreatingNum() {
		return verticalBox.getComponentCount();
	}

	/**
	 * 获取该分类的某个方法的代码数
	 */
	public int getAdditionalSetCodeNum(int opreatingSerial) {
		int codeNum = MarkElementName.MARK_NULL;
		AdditionalSetEditMetaPane teMetaPane;
		for (int i = 0; i < verticalBox.getComponentCount(); i++) {
			teMetaPane = (AdditionalSetEditMetaPane) verticalBox.getComponent(i);
			if (teMetaPane.getOrdinal() == opreatingSerial) {
				codeNum = teMetaPane.getAdditionalSetCodeNum();
				break;
			}
		}
		return codeNum;
	}

	/**
	 * 获取模块类型名
	 *
	 * @return
	 */
	public String getTheAdditionalSetTypeName() {
		return textField.getText().trim();
	}

	private boolean checkSameNameFromMetaPane() {
		boolean flag = true;
		String typeName = FormatEditPaneHolder.additionalEditPane.getAdditionalTypeName(additionalSerialNumber),
				theAdditionalSetTypeName = FormatEditPaneHolder.additionalEditPane.getTheAdditionalSetTypeName(additionalSerialNumber,
						typeSerialNumber);
		String showText1, showText2;
		AdditionalSetEditMetaPane paneTemp1, paneTemp2;
		if (verticalBox.getComponentCount() > 0) {
			for (int i = 0; i < verticalBox.getComponentCount(); i++) {
				paneTemp1 = (AdditionalSetEditMetaPane) verticalBox.getComponent(i);
				showText1 = paneTemp1.getShowText();
				for (int a = i + 1; a < verticalBox.getComponentCount(); a++) {
					paneTemp2 = (AdditionalSetEditMetaPane) verticalBox.getComponent(a);
					showText2 = paneTemp2.getShowText();
					if (showText1.equals(showText2)) {
						flag = false;
						LazyCoderOptionPane.showMessageDialog(
								null, "\"" + typeName + "\"类型里,\"" + theAdditionalSetTypeName + "\"分类中，第" + (i + 1)
										+ "个可选模板业务方法和第" + (a + 1) + "个可选模板业务方法显示名称一样，\n请检查清楚录入数据无误后再保存",
								"系统信息", JOptionPane.PLAIN_MESSAGE);
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

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if ("".equals(textField.getText().trim())) {
			LazyCoderOptionPane.showMessageDialog(null, "其他" + additionalSerialNumber + "设置里的\"设置" + typeSerialNumber + "\"没写分类名",
					"系统信息", JOptionPane.PLAIN_MESSAGE);
			flag = false;
		} else {
			if (checkSameNameFromMetaPane() == true) {
				AdditionalSetEditMetaPane teMetaPane1;
				for (int i = 0; i < verticalBox.getComponentCount(); i++) {
					teMetaPane1 = (AdditionalSetEditMetaPane) verticalBox.getComponent(i);
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

	public int getTypeSerialNumber() {
		return typeSerialNumber;
	}

}
