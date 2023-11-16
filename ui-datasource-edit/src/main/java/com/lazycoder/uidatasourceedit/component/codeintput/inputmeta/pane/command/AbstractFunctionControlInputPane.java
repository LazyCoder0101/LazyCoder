package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.datasourceedit.command.FunctionControlPaneInterface;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.BaseControlTextPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CodeInputControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.ConstantControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CorrespondingAdditionalDefaultFileControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CustomMethodNameControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CustomVariableControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FileSelectorControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.MethodChooseControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.NoteControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.PictureControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.TextInputControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableControlLabel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyleConstants;

/**
 * 通用方法控制输入面板
 *
 * @author Administrator
 */
public abstract class AbstractFunctionControlInputPane extends BaseControlTextPane
        implements FunctionControlPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = 7043849804092537147L;

    public AbstractFunctionControlInputPane() {
        super();
    }

    /**
     * 设置显示的内容颜色和字体
     *
     * @param textColor    输入显示的字体颜色
     * @param textFontSize 输入显示的字体大小
     */
    public void setTheStyle(Color textColor, int textFontSize) {
        StyleConstants.setFontSize(attr, textFontSize);
        StyleConstants.setForeground(attr, textColor);
        setParagraphAttributes(attr, false);
    }


    /**
     * 添加各类组件的对应监听
     */
    protected ActionListener addControlComponentActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addTextInputMenu) {// 文本输入
                addControlComponent(LabelElementName.TEXT_INPUT);

            } else if (e.getSource() == addContentChooseMenu) {// 选择
                clickAddContentChooseMenuItem();

            } else if (e.getSource() == addFunctionAddMenu) {// 功能拓展
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

            } else if (e.getSource() == addDefaultFileMenu) {
                addControlComponent(LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE);
            }
        }
    };

    private DocumentListener theDocumentListener = new DocumentListener() {

        @Override
        public void removeUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
//			updateUI();
//			repaint();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
        }
    };


    @Override
    protected void menuInit(ControlCondition controlCondition) {
        super.menuInit(controlCondition);
//		removeListener();
        addMenuListener(controlCondition);
        getDocument().addDocumentListener(theDocumentListener);
    }

//    private void removeListener() {
//        addTextInputMenu.removeActionListener(addControlComponentActionListener);
//        addContentChooseMenu.removeActionListener(addControlComponentActionListener);
//        addFunctionAddMenu.removeActionListener(addControlComponentActionListener);
//        addCustomVariableMenu.removeActionListener(addControlComponentActionListener);
//        addVariableMenu.removeActionListener(addControlComponentActionListener);
//        addConstantMenu.removeActionListener(addControlComponentActionListener);
//        addFileSelectorMenu.removeActionListener(addControlComponentActionListener);
//        addNoteMenu.removeActionListener(addControlComponentActionListener);
//        addPictureMenu.removeActionListener(addControlComponentActionListener);
//        addCodeInputMenu.removeActionListener(addControlComponentActionListener);
//        addCustomMethodNameMenu.removeActionListener(addControlComponentActionListener);
//        addMethodChooseMenu.removeActionListener(addControlComponentActionListener);
//        addInfrequentlyUsedSettingMenu.removeActionListener(addControlComponentActionListener);
//    }

    private void addMenuListener(ControlCondition controlCondition) {
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
        if (controlCondition.isCorrespondingAdditionalDefaultFileState() == true) {
            addDefaultFileMenu.addActionListener(addControlComponentActionListener);
        }
    }

    /**
     * 添加标签组件
     *
     * @param labelType
     */
    protected void addControlComponent(String labelType) {
        if (LabelElementName.TEXT_INPUT.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.TEXT_INPUT);
            TextInputControlLabel temp = new TextInputControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.TEXT_INPUT, delTextInputMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
            addFunctionAddOpratingLabel();

        } else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.CUSTOM_VARIABLE);
            CustomVariableControlLabel temp = new CustomVariableControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.CUSTOM_VARIABLE, delCustomVariableMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.VARIABLE.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.VARIABLE);
            VariableControlLabel temp = new VariableControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.VARIABLE, delVariableMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.CONSTANT.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.CONSTANT);
            ConstantControlLabel temp = new ConstantControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.CONSTANT, delConstantMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.FILE_SELECTOR);
            FileSelectorControlLabel temp = new FileSelectorControlLabel(name, passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.FILE_SELECTOR, delFileSelectorMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.NOTE.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.NOTE);
            NoteControlLabel temp = new NoteControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.NOTE, delNoteMenu);
            CommandCodeControl.addControlLabel(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.PICTURE.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.PICTURE);
            PictureControlLabel temp = new PictureControlLabel(name, passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.PICTURE, delPictureMenu);
            CommandCodeControl.addControlLabel(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.CODE_INPUT.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.CODE_INPUT);
            CodeInputControlLabel temp = new CodeInputControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.CODE_INPUT, delCodeInputMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.INFREQUENTLY_USED_SETTING.equals(labelType)) {
            addInfrequentlyUsedSettingOpratingLabel();

        } else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.CUSTOM_METHOD_NAME);
            CustomMethodNameControlLabel temp = new CustomMethodNameControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.CUSTOM_METHOD_NAME,
                    delCustomMethodNameMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.METHOD_CHOOSE);
            MethodChooseControlLabel temp = new MethodChooseControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.METHOD_CHOOSE, delMethodChooseMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());

        } else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE);
            CorrespondingAdditionalDefaultFileControlLabel temp = new CorrespondingAdditionalDefaultFileControlLabel(name);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE, delDefaultFileMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());
        }
    }

    /**
     * 添加不常用组件（在该方法内部调用addInfrequentlyUsedSettingOpratingLabel方法）
     */
    protected abstract void addInfrequentlyUsedSettingOpratingLabel();

    @Override
    protected void addInfrequentlyUsedSettingOpratingLabel(InfrequentlyUsedSettingControlLabel controlLabel) {
        controlLabel.setPassingComponentParams(passingComponentParams);
        addCorrespondingComponentMethod(controlLabel, controlLabel.getName(),
                LabelElementName.INFREQUENTLY_USED_SETTING, delInfrequentlyUsedSettingMenu);
        CommandCodeControl.addControlLabel(model, controlLabel.getControl());
    }

    @Override
    protected void delContentChoose(String optionId, int useNumbered) {
        super.delContentChoose(optionId, useNumbered);
        /**
         * 让模块通知其他代码面板删除组件
         */
        CommandCodeControl.delContentChoose(model, optionId, useNumbered);
    }

    @Override
    public void delContentChoose(String optionId) {
        super.delContentChoose(optionId);
        /**
         * 让模块通知其他代码面板删除组件
         */
        CommandCodeControl.delContentChoose(model, optionId);
    }

    @Override
    protected void delControlLabel(AbstractEditContainerModel model, String delLabelType, String delName) {
        // TODO Auto-generated method stub
        CommandCodeControl.delControlLabel(model, delLabelType, delName);
    }


    @Override
    protected void performServiceOperationsAfterClickAddContentChooseComponent(ContentChooseControl chooseControl) {
        super.performServiceOperationsAfterClickAddContentChooseComponent(chooseControl);
        CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, chooseControl.controlComponentInformation());
    }

}
