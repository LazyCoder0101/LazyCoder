package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.BasePane;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.FunctionAddControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.AdditionalSetControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.InitFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.MethodsFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.ModuleSetFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.AdditionalFormatControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.ModuleControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CustomVariableControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.infrequentlyusedsetting.InfrequentlyUsedSettingControlUseControlPane;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.GeneralOptionTextFieldEditPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CustomVariableEditFrame extends AbstractEditFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 5771640258089551106L;

	private JLabel dlabel, tlabel, llabel, slabel, rlabel, prlabel,daLabel;

	private JTextField leftTextField, rightTextField, separatorTextField, previewTextField;

	private MyButton addDFNameButton, delDFNameButton, addLabelTypeButton, delLabelTypeButton, addDataTypeButton, delDataTypeButton;

	private GeneralOptionTextFieldEditPane deafaultNameEditPane, labelTypeEditPane,dataTypeEditPane;

	private JScrollPane variableNameScrollPane, labelTypeScrollPane,dataTypeScrollPane;

	private JCheckBox noUserSelectionIsRequiredCheckBox;

	private VariableUsageRangeCombobox usingRangeComboBox;

	private JCheckBox nOr1checkBox,allowDuplicateNamesCheckBox;

	private OperatingTipButton operatingTip;

	// private PassingComponentParams passingComponentParams;
	private CustomVariableControlLabel controlLabel;

	public static void main(String[] args) {
		new CustomVariableEditFrame().setVisible(true);
	}

	public CustomVariableEditFrame() {
		super();
		getContentPane().setLayout(null);

		dlabel = new JLabel("默认变量名：");
		dlabel.setBounds(390, 25, 100, 30);
		getContentPane().add(dlabel);

		tlabel = new JLabel("标签类型：");
		tlabel.setBounds(400, 210, 94, 30);
		getContentPane().add(tlabel);

		llabel = new JLabel("左包围符：");
		llabel.setBounds(40, 25, 100, 30);
		getContentPane().add(llabel);

		leftTextField = new JTextField();
		leftTextField.getDocument().addDocumentListener(listener);
		leftTextField.setBounds(110, 25, 100, 30);
		getContentPane().add(leftTextField);

		operatingTip = new OperatingTipButton(
				SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "4.png").getAbsolutePath()
		);
		operatingTip.setLocation(leftTextField.getX() + leftTextField.getWidth() + 30, leftTextField.getY() + 3);
		getContentPane().add(operatingTip);

		slabel = new JLabel("间隔符：");
		slabel.setBounds(40, 75, 100, 30);
		getContentPane().add(slabel);

		rightTextField = new JTextField();
		rightTextField.getDocument().addDocumentListener(listener);
		rightTextField.setBounds(110, 125, 100, 30);
		getContentPane().add(rightTextField);

		rlabel = new JLabel("右包围符：");
		rlabel.setBounds(40, 125, 100, 30);
		getContentPane().add(rlabel);

		separatorTextField = new JTextField();
		separatorTextField.getDocument().addDocumentListener(listener);
		separatorTextField.setBounds(110, 75, 100, 30);
		getContentPane().add(separatorTextField);
		separatorTextField.setColumns(10);

		prlabel = new JLabel("预览：");
		prlabel.setBounds(40, 180, 70, 30);
		getContentPane().add(prlabel);

		previewTextField = new JTextField();
		previewTextField.setEditable(false);
		previewTextField.setBounds(85, 180, 160, 30);
		getContentPane().add(previewTextField);

		deafaultNameEditPane = new GeneralOptionTextFieldEditPane(180);
		variableNameScrollPane = new JScrollPane(deafaultNameEditPane);
		variableNameScrollPane.setBounds(500, 20, 210, 140);
		getContentPane().add(variableNameScrollPane);

		addDFNameButton = new MyButton("添加默认变量名");
		addDFNameButton.addActionListener(btlistener);
		addDFNameButton.setBounds(330, 60, 150, 30);
		getContentPane().add(addDFNameButton);

		delDFNameButton = new MyButton("删除默认变量名");
		delDFNameButton.setBounds(330, 110, 150, 30);
		delDFNameButton.addActionListener(btlistener);
		getContentPane().add(delDFNameButton);

		JLabel label4 = new JLabel("可在");
		label4.setBounds(40, 240, 80, 30);
		getContentPane().add(label4);

		usingRangeComboBox = new VariableUsageRangeCombobox();
		usingRangeComboBox.setBounds(75, 240, 180, 30);
		getContentPane().add(usingRangeComboBox);

		nOr1checkBox = new JCheckBox("只能添加一个");
		nOr1checkBox.setFocusPainted(false);
		nOr1checkBox.addChangeListener(changeListener);
		nOr1checkBox.setFocusPainted(false);
		nOr1checkBox.setBounds(360, 155, 120, 30);
		getContentPane().add(nOr1checkBox);

		labelTypeEditPane = new GeneralOptionTextFieldEditPane(180);
		labelTypeScrollPane = new JScrollPane(labelTypeEditPane);
		labelTypeScrollPane.setBounds(500, 210, 210, 120);
		getContentPane().add(labelTypeScrollPane);

		addLabelTypeButton = new MyButton("添加标签类型");
//		addLabelTypeButton.setToolTipText("该自定义变量组件对应的变量是什么数据类型，写在这里。	┗( ▔, ▔ )┛");
		addLabelTypeButton.addActionListener(btlistener);
		addLabelTypeButton.setBounds(330, 250, 150, 30);
		getContentPane().add(addLabelTypeButton);

		delLabelTypeButton = new MyButton("删除标签类型");
		delLabelTypeButton.addActionListener(btlistener);
		delLabelTypeButton.setBounds(330, 300, 150, 30);
		getContentPane().add(delLabelTypeButton);

		JLabel lblNewLabel = new JLabel("使用");
		lblNewLabel.setBounds(260, 240, 80, 30);
		getContentPane().add(lblNewLabel);

		daLabel = new JLabel("数据类型：");
		daLabel.setBounds(400, 360, 94, 30);
		getContentPane().add(daLabel);

		dataTypeEditPane = new GeneralOptionTextFieldEditPane(180);
		dataTypeScrollPane = new JScrollPane(dataTypeEditPane);
		dataTypeScrollPane.setBounds(500, 360, 210, 120);
		getContentPane().add(dataTypeScrollPane);

		addDataTypeButton = new MyButton("添加数据类型");
//		addDataTypeButton.setToolTipText("该自定义变量组件对应的变量是什么数据类型，写在这里。	┗( ▔, ▔ )┛");
		addDataTypeButton.addActionListener(btlistener);
		addDataTypeButton.setBounds(330, 400, 150, 30);
		getContentPane().add(addDataTypeButton);

		delDataTypeButton = new MyButton("删除数据类型");
		delDataTypeButton.addActionListener(btlistener);
		delDataTypeButton.setBounds(330, 440, 150, 30);
		getContentPane().add(delDataTypeButton);

		noUserSelectionIsRequiredCheckBox = new JCheckBox("自动选择");
		noUserSelectionIsRequiredCheckBox.setFocusPainted(false);
		noUserSelectionIsRequiredCheckBox.setBounds(50,300,120,30);
		getContentPane().add(noUserSelectionIsRequiredCheckBox);

		allowDuplicateNamesCheckBox = new JCheckBox("允许变量名重复");
		allowDuplicateNamesCheckBox.setFocusPainted(false);
		allowDuplicateNamesCheckBox.setBounds(50,345,130,30);
		getContentPane().add(allowDuplicateNamesCheckBox);

		ok.setBounds(40, 400, 80, 30);
		cancel.setBounds(150, 400, 80, 30);
		getContentPane().add(ok);
		getContentPane().add(cancel);

		cancel.addActionListener(cancelListener);
		this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 390, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 270, 770, 540);
	}

	// 更改自定义变量组件内容
	public CustomVariableEditFrame(CustomVariableControlLabel controlLabel) {
		this();
		this.controlLabel = controlLabel;
		this.setTitle("更改自定义变量组件\""+this.controlLabel.getControl().getThisName()+"\"属性");

		addCorrespondingRangeItems();

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ok();
			}
		});
		setText(controlLabel);
		this.setVisible(true);
	}

	private void addCorrespondingRangeItems() {
		DefaultComboBoxModel<AttributeUsageRange> comboBoxModel = new DefaultComboBoxModel<AttributeUsageRange>();
		comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToItself);
		comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToAddedTemplates);
		comboBoxModel.addElement(AttributeUsageRange.ApplyToAll);
		BasePane putPane = controlLabel.getPassingComponentParams().getThisPane();
		if (putPane != null) {
			if (putPane instanceof ModuleControlPane || putPane instanceof InitFunctionControlPanel
					|| putPane instanceof ModuleSetFunctionControlPanel
					|| putPane instanceof MethodsFunctionControlPanel) {

				comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisModule);
				comboBoxModel.addElement(AttributeUsageRange.ModulesRequiredForThisModule);
				comboBoxModel.addElement(AttributeUsageRange.ThisModuleAndRequiredModules);

			} else if (putPane instanceof AdditionalSetControlPane
					|| putPane instanceof AdditionalFormatControlPane) {

				comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate);

			} else if (putPane instanceof FunctionAddControlInputPane) {
				String paneType = ((FunctionAddControlInputPane) putPane).getPaneType();
				if (MarkElementName.INIT_MARK.equals(paneType) || MarkElementName.SET_MARK.equals(paneType)
						|| MarkElementName.FUNCTION_MARK.equals(paneType)
						|| MarkElementName.MODULE_CONTROL.equals(paneType)) {

					comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisModule);
					comboBoxModel.addElement(AttributeUsageRange.ModulesRequiredForThisModule);
					comboBoxModel.addElement(AttributeUsageRange.ThisModuleAndRequiredModules);

				} else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(paneType)
						|| MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(paneType)
						) {

					comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate);

				}

			} else if (putPane instanceof InfrequentlyUsedSettingControlUseControlPane) {
				String paneType = ((InfrequentlyUsedSettingControlUseControlPane) putPane).getPaneType();
				if (MarkElementName.INIT_MARK.equals(paneType) || MarkElementName.SET_MARK.equals(paneType)
						|| MarkElementName.FUNCTION_MARK.equals(paneType)
						|| MarkElementName.MODULE_CONTROL.equals(paneType)) {

					comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisModule);
					comboBoxModel.addElement(AttributeUsageRange.ModulesRequiredForThisModule);
					comboBoxModel.addElement(AttributeUsageRange.ThisModuleAndRequiredModules);

				} else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(paneType)
						|| MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(paneType)
						) {
					comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate);
				}
			}
		}
		usingRangeComboBox.setModel(comboBoxModel);

	}

	@Override
	public void ok() {
		if (check() == true) {
			setValue();
			CustomVariableEditFrame.this.dispose();
		}
	}

	private void setText(CustomVariableControlLabel controlLabel) {
		deafaultNameEditPane.restore(CustomVariableControl.getDefaultNameList(controlLabel.getControl()));
		labelTypeEditPane
				.restore(CustomVariableControl.getLabelTypeParam(controlLabel.getControl().getLabelTypeParam()));
		if (BaseModel.TRUE_ == controlLabel.getControl().getOnlyAddOne()) {
			nOr1checkBox.setSelected(true);
		} else if (BaseModel.FALSE_ == controlLabel.getControl().getOnlyAddOne()) {
			nOr1checkBox.setSelected(false);
		}
		leftTextField.setText(controlLabel.getLeftStr());
		separatorTextField.setText(controlLabel.getSeparatorStr());
		rightTextField.setText(controlLabel.getRightStr());
		if (controlLabel.getTheAvaliableRange() != -1) {
			usingRangeComboBox.setSelectedVariableUsageRange(controlLabel.getTheAvaliableRange());
		}
		noUserSelectionIsRequiredCheckBox.setSelected(controlLabel.isNoUserSelectionIsRequired());
		allowDuplicateNamesCheckBox.setSelected(controlLabel.isAllowDuplicateNames());
		dataTypeEditPane.restore(CustomVariableControl.getDataTypeParam(controlLabel.getControl().getDataTypeParam()));
	}

	private void setValue() {
		controlLabel.setLeftStr(leftTextField.getText());
		controlLabel.setSeparatorStr(separatorTextField.getText());
		controlLabel.setRightStr(rightTextField.getText());
		if (true == nOr1checkBox.isSelected()) {
			controlLabel.setOnlyAddOne(CustomVariableControl.TRUE_);

		} else {
			controlLabel.setOnlyAddOne(CustomVariableControl.FALSE_);
		}
		ArrayList<String> defaultNameList = deafaultNameEditPane.getTextList();
		CustomVariableControl.setDefaultNameList(defaultNameList, controlLabel.getControl());

		controlLabel.setTheAvaliableRange(usingRangeComboBox.getSelectedDictionaryValue());

		ArrayList<String> labelTypeList = labelTypeEditPane.getTextList();
		CustomVariableControl.setLabelTypeParam(labelTypeList, controlLabel.getControl());

		ArrayList<String> dataTypeList = dataTypeEditPane.getTextList();
		CustomVariableControl.setDataTypeParam(dataTypeList, controlLabel.getControl());

		controlLabel.setNoUserSelectionIsRequired(noUserSelectionIsRequiredCheckBox.isSelected());
		controlLabel.setAllowDuplicateNames(allowDuplicateNamesCheckBox.isSelected());
	}

	private void showPreviewValue() {
		previewTextField.setText("");
		String leftStr = leftTextField.getText(), rightStr = rightTextField.getText(),
				jiangeStr = separatorTextField.getText();
		previewTextField.setText(leftStr + "A" + rightStr + jiangeStr + leftStr + "B" + rightStr);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	private boolean check() {
		boolean flag = true;
		JTextField textField;
		for (int i = 0; i < deafaultNameEditPane.getComponentCount(); i++) {
			textField = (JTextField) deafaultNameEditPane.getComponent(i);
			if ("".equals(textField.getText().trim())) {
				flag = false;
				LazyCoderOptionPane.showMessageDialog(this, "ㄟ( ▔, ▔ )ㄏ  　\"默认变量名\"那里 第" + (i + 1) + "个输入框什么都没写呢！",
						"哎呀，保存不了喔！！", JOptionPane.PLAIN_MESSAGE);

				break;
			}
		}
		if (flag == true) {
			for (int i = 0; i < labelTypeEditPane.getComponentCount(); i++) {
				textField = (JTextField) labelTypeEditPane.getComponent(i);
				if ("".equals(textField.getText().trim())) {
					flag = false;
					LazyCoderOptionPane.showMessageDialog(this, "ㄟ( ▔, ▔ )ㄏ  　\"标签类型\"那里 第" + (i + 1) + "个输入框什么都没写呢！",
							"哎呀，保存不了喔！！", JOptionPane.PLAIN_MESSAGE);

					break;
				}
			}
		}
		if (flag == true) {
			for (int i = 0; i < dataTypeEditPane.getComponentCount(); i++) {
				textField = (JTextField) dataTypeEditPane.getComponent(i);
				if ("".equals(textField.getText().trim())) {
					flag = false;
					LazyCoderOptionPane.showMessageDialog(this, "ㄟ( ▔, ▔ )ㄏ  　\"数据类型\"那里 第" + (i + 1) + "个输入框什么都没写呢！",
							"哎呀，保存不了喔！！", JOptionPane.PLAIN_MESSAGE);

					break;
				}
			}
		}
		return flag;
	}

	private ChangeListener changeListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == nOr1checkBox) {
				if (nOr1checkBox.isSelected() == true) {
					deafaultNameEditPane.delAndKeepFirstOptionTextField();
					leftTextField.setText("");
					separatorTextField.setText("");
					rightTextField.setText("");
					previewTextField.setText("");
					leftTextField.setEnabled(false);
					separatorTextField.setEnabled(false);
					rightTextField.setEnabled(false);

					variableNameScrollPane.updateUI();
					variableNameScrollPane.repaint();
				} else {
					leftTextField.setEnabled(true);
					separatorTextField.setEnabled(true);
					rightTextField.setEnabled(true);
				}
			}
		}
	};

	private ActionListener btlistener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addDFNameButton) {
				if (nOr1checkBox.isSelected() == true) {// 只能添加一个自定义变量
					if (deafaultNameEditPane.getCurrentTextFieldCount() == 0) {// 当变量名编辑变量一个变量都没有的时候，可以给他添加一个输入框
						deafaultNameEditPane.addOptionTextField();
						variableNameScrollPane.updateUI();
						variableNameScrollPane.repaint();
					}

				} else {// 可以添加多个自定义变量
					deafaultNameEditPane.addOptionTextField();
					variableNameScrollPane.updateUI();
					variableNameScrollPane.repaint();
				}

			} else if (e.getSource() == delDFNameButton) {
				deafaultNameEditPane.delOptionTextField();
				variableNameScrollPane.updateUI();
				variableNameScrollPane.repaint();

			} else if (e.getSource() == addLabelTypeButton) {
				labelTypeEditPane.addOptionTextField();
				labelTypeScrollPane.updateUI();
				labelTypeScrollPane.repaint();

			} else if (e.getSource() == delLabelTypeButton) {
				labelTypeEditPane.delOptionTextField();
				labelTypeScrollPane.updateUI();
				labelTypeScrollPane.repaint();

			} else if (e.getSource() == addDataTypeButton) {
				dataTypeEditPane.addOptionTextField();
				dataTypeScrollPane.updateUI();
				dataTypeScrollPane.repaint();

			} else if (e.getSource() == delDataTypeButton) {
				dataTypeEditPane.delOptionTextField();
				dataTypeScrollPane.updateUI();
				dataTypeScrollPane.repaint();
			}
		}
	};
	private DocumentListener listener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			showPreviewValue();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			showPreviewValue();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			showPreviewValue();
		}
	};

	private ActionListener cancelListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			CustomVariableEditFrame.this.dispose();
		}
	};



}
