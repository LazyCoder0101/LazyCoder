package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.BaseControlTextPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CorrespondingAdditionalDefaultFileControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CodeInputControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.ConstantControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CustomMethodNameControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CustomVariableControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FileSelectorControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.MethodChooseControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.NoteControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.PictureControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.TextInputControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableControlLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractInfrequentlyUsedSettingControlInputPane extends BaseControlTextPane
		implements BaseOperatingPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 3570739147690911144L;
	/**
	 * 添加各类组件的对应监听
	 */
	private ActionListener addControlComponentActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addTextInputMenu) {// 文本输入
				addControlComponent(LabelElementName.TEXT_INPUT);

			} else if (e.getSource() == addContentChooseMenu) {// 选择
				clickAddContentChooseMenuItem();

			} else if (e.getSource() == addFunctionAddMenu) {// 功能拓展组件
				addControlComponent(LabelElementName.FUNCTION_ADD);

			} else if (e.getSource() == addCustomVariableMenu) {// 自定义变量
				addControlComponent(LabelElementName.CUSTOM_VARIABLE);

			} else if (e.getSource() == addVariableMenu) {// 变量
				addControlComponent(LabelElementName.VARIABLE);

			} else if (e.getSource() == addConstantMenu) {// 常量
				addControlComponent(LabelElementName.CONSTANT);

			} else if (e.getSource() == addFileSelectorMenu) {// 文件选择
				addControlComponent(LabelElementName.FILE_SELECTOR);

			} else if (e.getSource() == addNoteMenu) {// 备注
				addControlComponent(LabelElementName.NOTE);

			} else if (e.getSource() == addPictureMenu) {// 图片
				addControlComponent(LabelElementName.PICTURE);

			} else if (e.getSource() == addCodeInputMenu) {// 代码输入
				addControlComponent(LabelElementName.CODE_INPUT);

			} else if (e.getSource() == addCustomMethodNameMenu) {// 自定义方法
				addControlComponent(LabelElementName.CUSTOM_METHOD_NAME);

			} else if (e.getSource() == addMethodChooseMenu) {// 方法选择
				addControlComponent(LabelElementName.METHOD_CHOOSE);

			} else if (e.getSource() == addInfrequentlyUsedSettingMenu) {//
				// 不常用设置组件
				addControlComponent(LabelElementName.INFREQUENTLY_USED_SETTING);

			} else if (e.getSource() == addInfrequentlyUsedSettingMenu) {
				addControlComponent(LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE);
			}
		}
	};

	public AbstractInfrequentlyUsedSettingControlInputPane(AbstractEditContainerModel model, String paneName) {
		super();
		setModel(model, paneName);

		ControlCondition condition = new ControlCondition(false, false, false,false);
		menuInit(condition);
	}

	@Override
	public void menuInit(ControlCondition controlCondition) {
		super.menuInit(controlCondition);
		addMenuListener(controlCondition);
//		removeListener();
	}

	private void removeListener() {
		addTextInputMenu.removeActionListener(addControlComponentActionListener);
		addContentChooseMenu.removeActionListener(addControlComponentActionListener);
		addFunctionAddMenu.removeActionListener(addControlComponentActionListener);
		addCustomVariableMenu.removeActionListener(addControlComponentActionListener);
		addVariableMenu.removeActionListener(addControlComponentActionListener);
		addConstantMenu.removeActionListener(addControlComponentActionListener);
		addFileSelectorMenu.removeActionListener(addControlComponentActionListener);
		addNoteMenu.removeActionListener(addControlComponentActionListener);
		addPictureMenu.removeActionListener(addControlComponentActionListener);
		addCodeInputMenu.removeActionListener(addControlComponentActionListener);
		addCustomMethodNameMenu.removeActionListener(addControlComponentActionListener);
		addMethodChooseMenu.removeActionListener(addControlComponentActionListener);
		addInfrequentlyUsedSettingMenu.removeActionListener(addControlComponentActionListener);
	}

	public void addMenuListener(ControlCondition controlCondition) {
		addTextInputMenu.addActionListener(addControlComponentActionListener);
		addContentChooseMenu.addActionListener(addControlComponentActionListener);
		addFunctionAddMenu.addActionListener(addControlComponentActionListener);
		addCustomVariableMenu.addActionListener(addControlComponentActionListener);
		addVariableMenu.addActionListener(addControlComponentActionListener);
		addConstantMenu.addActionListener(addControlComponentActionListener);
		addFileSelectorMenu.addActionListener(addControlComponentActionListener);
		addLabelMenu.addActionListener(addControlComponentActionListener);
		addNoteMenu.addActionListener(addControlComponentActionListener);
		addPictureMenu.addActionListener(addControlComponentActionListener);
		addCodeInputMenu.addActionListener(addControlComponentActionListener);
		addCustomMethodNameMenu.addActionListener(addControlComponentActionListener);
		addMethodChooseMenu.addActionListener(addControlComponentActionListener);

		if (controlCondition.isInfrequentlyUsedSettingState() == true) {
			addInfrequentlyUsedSettingMenu.addActionListener(addControlComponentActionListener);
		}
		if (controlCondition.isCorrespondingAdditionalDefaultFileState() == true){
			addDefaultFileMenu.addActionListener(addControlComponentActionListener);
		}
	}

	/**
	 * 添加标签组件
	 *
	 * @param labelType
	 */
	public void addControlComponent(String labelType) {

		if (LabelElementName.TEXT_INPUT.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.TEXT_INPUT);
			TextInputControlLabel temp = new TextInputControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.TEXT_INPUT, delTextInputMenu);
			addControlLabelAndUpdateCodePaneMenu(temp.getControl());

		} else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
			addFunctionAddOpratingLabel();

		} else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.CUSTOM_VARIABLE);
			CustomVariableControlLabel temp = new CustomVariableControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.CUSTOM_VARIABLE, delCustomVariableMenu);
			addControlLabelAndUpdateCodePaneMenu(temp.getControl());

		} else if (LabelElementName.VARIABLE.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.VARIABLE);
			VariableControlLabel temp = new VariableControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.VARIABLE, delVariableMenu);
			addControlLabelAndUpdateCodePaneMenu(temp.getControl());

		} else if (LabelElementName.CONSTANT.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.CONSTANT);
			ConstantControlLabel temp = new ConstantControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.CONSTANT, delConstantMenu);
			addControlLabelAndUpdateCodePaneMenu(temp.getControl());

		} else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.FILE_SELECTOR);
			FileSelectorControlLabel temp = new FileSelectorControlLabel(name, passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.FILE_SELECTOR, delFileSelectorMenu);
			addControlLabelAndUpdateCodePaneMenu(temp.getControl());

		} else if (LabelElementName.NOTE.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.NOTE);
			NoteControlLabel temp = new NoteControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.NOTE, delNoteMenu);
			addControlLabel(temp.getControl());

		} else if (LabelElementName.PICTURE.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.PICTURE);
			PictureControlLabel temp = new PictureControlLabel(name, passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.PICTURE, delPictureMenu);
			addControlLabel(temp.getControl());

		} else if (LabelElementName.CODE_INPUT.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.CODE_INPUT);
			CodeInputControlLabel temp = new CodeInputControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.CODE_INPUT, delCodeInputMenu);
			addControlLabelAndUpdateCodePaneMenu(temp.getControl());

		} else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.CUSTOM_METHOD_NAME);
			CustomMethodNameControlLabel temp = new CustomMethodNameControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.CUSTOM_METHOD_NAME,
					delCustomMethodNameMenu);
			addControlLabelAndUpdateCodePaneMenu(temp.getControl());

		} else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.METHOD_CHOOSE);
			MethodChooseControlLabel temp = new MethodChooseControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.METHOD_CHOOSE, delMethodChooseMenu);

			addControlLabelAndUpdateCodePaneMenu(temp.getControl());

		} else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
			String name = GeneralControl.generateComponentName(model, LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE);
			CorrespondingAdditionalDefaultFileControlLabel temp = new CorrespondingAdditionalDefaultFileControlLabel(name);
			temp.setPassingComponentParams(passingComponentParams);
			addCorrespondingComponentMethod(temp, name, LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE, delDefaultFileMenu);
			addControlLabelAndUpdateCodePaneMenu(temp.getControl());
		}
	}

	/**
	 * 根据实际需要看看是调用 CommandCodeControl.addControlLabelAndUpdateCodePaneMenu
	 * 方法 还是 lannongFormatControl.addControlLabelAndUpdateCodePaneMenu
	 *
	 * @param addLabelModel
	 */
	protected abstract void addControlLabelAndUpdateCodePaneMenu(BaseLableElement addLabelModel);

	/**
	 * 根据实际需要看看是调用 CommandCodeControl.addControlLabel 方法，还是
	 * lannongFormatControl.addControlLabel
	 *
	 * @param addLabelModel
	 */
	protected abstract void addControlLabel(BaseLableElement addLabelModel);

	/**
	 * 设置模型
	 *
	 * @param model
	 * @param paneName
	 */
	private void setModel(AbstractEditContainerModel model, String paneName) {
		setModel(model);
		model.getInfrequentlyUsedControlList().put(paneName, this);
	}

}
