package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.BasePane;
import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.FunctionAddControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.AdditionalSetControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.InitFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.MethodsFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.ModuleSetFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.AdditionalFormatControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.ModuleControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.CustomMethodNameControlLabel;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CustomMethodNameEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = 2906951597518327679L;

    private CustomMethodNameControlLabel controlLabel;
    private JLabel label;
    private JTextField methodNameTextField;// returnTypeTextField;
    private VariableUsageRangeCombobox usingRangeComboBox;
    private MyButton addLabelTypeButton, delLabelTypeButton;
    private GeneralOptionTextFieldEditPane labelTypeEditPane;
    private JScrollPane labelTypeScrollPane;
    private OperatingTipButton operatingTip;

    private CustomMethodNameEditFrame() {
        super();
        getContentPane().setLayout(null);
        ok.setBounds(60, 290, 80, 30);
        cancel.setBounds(200, 290, 80, 30);
        getContentPane().add(ok);
        getContentPane().add(cancel);

        label = new JLabel("默认方法名：");
        label.setBounds(50, 35, 94, 30);
        getContentPane().add(label);

        methodNameTextField = new JTextField();
        methodNameTextField.setBounds(145, 35, 150, 30);
        getContentPane().add(methodNameTextField);

        usingRangeComboBox = new VariableUsageRangeCombobox();
        usingRangeComboBox.setBounds(120, 80, 260, 30);
        getContentPane().add(usingRangeComboBox);

        JLabel label2 = new JLabel("使用范围：");
        label2.setBounds(50, 80, 83, 30);
        getContentPane().add(label2);

        JLabel tlabel = new JLabel("标签类型：");
        tlabel.setBounds(86, 137, 94, 30);
        getContentPane().add(tlabel);

        labelTypeEditPane = new GeneralOptionTextFieldEditPane(180);
        labelTypeScrollPane = new JScrollPane(labelTypeEditPane);
        labelTypeScrollPane.setBounds(230, 137, 210, 120);
        getContentPane().add(labelTypeScrollPane);

        addLabelTypeButton = new MyButton("添加标签类型");
        addLabelTypeButton.addActionListener(btlistener);
        addLabelTypeButton.setBounds(50, 180, 150, 30);
        getContentPane().add(addLabelTypeButton);

        delLabelTypeButton = new MyButton("删除标签类型");
        delLabelTypeButton.addActionListener(btlistener);
        delLabelTypeButton.setBounds(50, 223, 150, 30);
        getContentPane().add(delLabelTypeButton);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "11.png").getAbsolutePath()
        );
        operatingTip.setLocation(methodNameTextField.getX() + methodNameTextField.getWidth() + 30, methodNameTextField.getY() + 3);
        getContentPane().add(operatingTip);

        cancel.addActionListener(cancelListener);
        this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 250,
                (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 250,
                500, 407);

    }

    // 更改自定义变量组件内容
    public CustomMethodNameEditFrame(CustomMethodNameControlLabel controlLabel) {
        this();
        this.controlLabel = controlLabel;
        this.setTitle("更改自定义方法组件\"" + this.controlLabel.getControl().getThisName() + "\"属性");

        setComboboxModel();
        this.setText(controlLabel);
        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 自动生成的方法存根
                ok();
            }
        });
        this.setVisible(true);

    }

    public static void main(String[] args) {
        new CustomMethodNameEditFrame().setVisible(true);
    }

    private ActionListener btlistener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addLabelTypeButton) {
                labelTypeEditPane.addOptionTextField();
                labelTypeScrollPane.updateUI();
                labelTypeScrollPane.repaint();

            } else if (e.getSource() == delLabelTypeButton) {
                labelTypeEditPane.delOptionTextField();
                labelTypeScrollPane.updateUI();
                labelTypeScrollPane.repaint();

            }
        }
    };

    ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根
            CustomMethodNameEditFrame.this.dispose();
        }
    };

    private void setComboboxModel() {
        DefaultComboBoxModel<AttributeUsageRange> comboBoxModel = new DefaultComboBoxModel<AttributeUsageRange>();
        comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToItself);
        comboBoxModel.addElement(AttributeUsageRange.ApplyOnlyToAddedTemplates);
        comboBoxModel.addElement(AttributeUsageRange.ApplyToAll);
        BasePane putPane = this.controlLabel.getPassingComponentParams().getThisPane();
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

    private boolean check() {
        boolean flag = true;
        JTextField textField;
        for (int i = 0; i < labelTypeEditPane.getComponentCount(); i++) {
            textField = (JTextField) labelTypeEditPane.getComponent(i);
            if ("".equals(textField.getText().trim())) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(this, "ㄟ( ▔, ▔ )ㄏ  　\"标签类型\"那里 第" + (i + 1) + "个输入框什么都没写呢！", "哎呀，保存不了喔！！",
                        JOptionPane.PLAIN_MESSAGE);

                break;
            }
        }
        return flag;
    }

    @Override
    public void ok() {
        if (check()) {
            setValue();
            CustomMethodNameEditFrame.this.dispose();
        }
    }

    public void setText(CustomMethodNameControlLabel controlLabel) {
        this.methodNameTextField.setText(controlLabel.getMethodName());
        labelTypeEditPane
                .restore(CustomMethodNameControl.getLabelTypeParam(controlLabel.getControl().getLabelTypeParam()));
        if (controlLabel.getTheAvaliableRange() != -1) {
            usingRangeComboBox.setSelectedVariableUsageRange(controlLabel.getTheAvaliableRange());
        }
    }

    public void setValue() {
        controlLabel.setMethodName(methodNameTextField.getText());
        ArrayList<String> labelTypeList = labelTypeEditPane.getTextList();
        CustomMethodNameControl.setLabelTypeParam(labelTypeList, controlLabel.getControl());
        controlLabel.setTheAvaliableRange(usingRangeComboBox.getSelectedDictionaryValue());
    }

//    @Override
//    public void dispose() {
//        // TODO Auto-generated method stub
//        super.dispose();
//    }

}
