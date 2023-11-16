package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.MultipleInputForCodeGeneration;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.ConstantMeta;
import com.lazycoder.uiutils.mycomponent.GeneralOptionTextFieldEditPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 常量组件
 *
 * @author admin
 */
public class ConstantMutipleInoutBox extends MultipleInputForCodeGeneration implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -3676043976006540915L;

	private static final ImageIcon ADD_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
			+ "FunctionOperationComponent" + File.separator + "Constant" + File.separator + "add.png"),
			MINUS_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
					+ "FunctionOperationComponent" + File.separator + "Constant" + File.separator + "minus.png"),
			EXPAND_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
					+ "FunctionOperationComponent" + File.separator + "Constant" + File.separator + "expand.png"),
			COLLAPSE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
					+ "FunctionOperationComponent" + File.separator + "Constant" + File.separator + "collapse.png");

	private ConstantControl controlElement = new ConstantControl();

	private PathFind pathFind;

	private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

	private GeneralOptionTextFieldEditPane optionTextFieldEditPane;

	public ConstantMutipleInoutBox() {
		// TODO Auto-generated constructor stub
		super();
		setIcon(ADD_ICON, MINUS_ICON, EXPAND_ICON, COLLAPSE_ICON);

		Border border = BorderFactory.createEtchedBorder();
		textField.setBorder(border);
		addButton.setBorder(border);
		delButton.setBorder(border);
		expandButton.setBorder(border);

		optionTextFieldEditPane = new GeneralOptionTextFieldEditPane() {

			/**
			 *
			 */
			private static final long serialVersionUID = -23957221350915242L;

			@Override
			public void restore(ArrayList<String> textList) {
				JTextField textField;
				for (int i = 0; i < textList.size(); i++) {
					textField = new JTextField();
					textField.setText(textList.get(i));
					add(textField);
					textField.getDocument().addDocumentListener(documentListener);
				}
			}
		};
		scrollPane.setViewportView(optionTextFieldEditPane);
	}

	/**
	 * 新建
	 *
	 * @param codeGenerationalOpratingContainerParam
	 * @param controlElement
	 */
	public ConstantMutipleInoutBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
								   ConstantControl controlElement) {
		this();
		this.controlElement = controlElement;
		this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

		addButton.addActionListener(actionListener);
		delButton.addActionListener(actionListener);

		// this.codeSerialNumber = codeSerialNumber;
		PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
				controlElement, codeGenerationalOpratingContainerParam.getPaneType());
		PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
		pathFindTemp.getPathList().add(pathFindCell);
		this.pathFind = pathFindTemp;

		ArrayList<String> defaultList = ConstantControl.getDefaultList(controlElement);
		optionTextFieldEditPane.restore(defaultList);

		updateValue();
	}

	/**
	 * 还原
	 *
	 * @param codeGenerationalOpratingContainerParam
	 * @param constantMeta
	 */
	public ConstantMutipleInoutBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
								   ConstantMeta constantMeta) {
		this();
		this.controlElement = constantMeta.getControlElement();
		this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

		addButton.addActionListener(actionListener);
		delButton.addActionListener(actionListener);

		this.pathFind = constantMeta.getPathFind();

		optionTextFieldEditPane.restore(constantMeta.getInputConstantParam());
		textField.setText(getShowStr(optionTextFieldEditPane.getTextList()));
//		expandButton.doClick();
		// updateValue();

	}

	private DocumentListener documentListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateValue();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateValue();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			updateValue();
		}
	};

	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addButton) {
				JTextField textField = optionTextFieldEditPane.addOptionTextField();
				textField.getDocument().addDocumentListener(documentListener);
				updateValue();

			} else if (e.getSource() == delButton) {
				optionTextFieldEditPane.delOptionTextField();
				updateValue();

			}
		}
	};

	@Override
	public OpratingContainerInterface getThisOpratingContainer() {
		return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
	}

	@Override
	public void updateValue() {
		// TODO Auto-generated method stub
		String value = ConstantControl.getValue(controlElement, optionTextFieldEditPane.getTextList());
		CodeGenerationStaticFunction.setValue(this,codeGenerationalOpratingContainerParam.getPaneType(), value);
		textField.setText(getShowStr(optionTextFieldEditPane.getTextList()));
	}

	@Override
	public void delThis() {
		// TODO Auto-generated method stub
		super.hidePopupPanel();
	}

	@Override
	public ConstantControl getControlElement() {
		return controlElement;
	}

	@Override
	public PathFind getPathFind() {
		return this.pathFind;
	}

	@Override
	public int getCodeSerialNumber() {
		return codeGenerationalOpratingContainerParam.getCodeSerialNumber();
	}

	@Override
	public AbstractOperatingPane getOperatingComponentPlacePane() {
		// TODO Auto-generated method stub
		return codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
	}

	@Override
	public void setParam(FormatStructureModelInterface model) {
		// TODO Auto-generated method stub
		ConstantMeta theModel = (ConstantMeta) model;
		theModel.setControlElement(controlElement);
		theModel.setPathFind(this.pathFind);
		theModel.setInputConstantParam(optionTextFieldEditPane.getTextList());
	}

	@Override
	public ConstantMeta getFormatStructureModel() {
		// TODO Auto-generated method stub
		ConstantMeta model = new ConstantMeta();
		setParam(model);
		return model;
	}

	@Override
	public int getComponentWidth() {
		// TODO Auto-generated method stub
		return TEXT_FIELD_WIDTH + ADD_ICON.getIconWidth() + MINUS_ICON.getIconWidth() + EXPAND_ICON.getIconWidth() + 10;
	}

	@Override
	public void collapseThis() {
		expandButton.packUpPanel();
	}

}
