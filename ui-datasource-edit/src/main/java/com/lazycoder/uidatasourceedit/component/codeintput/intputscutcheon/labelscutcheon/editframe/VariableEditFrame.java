package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.BasePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.FunctionAddControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.AdditionalFunctionControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.AdditionalSetControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.InitFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.MethodsFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.ModuleSetFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.AdditionalFormatControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.ModuleControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableControlLabel;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class VariableEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4792935038152723575L;

    public VariableControlLabel controlLabel;

    private MyButton addDataTypeBt, delDataTypeBt, addLabelTypeBt, delLabelTypeBt;

    private JScrollPane dataTypeScrollPane, labelTypeScrollPane;

    private VariableUsageRangeCombobox usingRangeComboBox;

    private JLabel dLabel, lLabel;

    private JCheckBox noUserSelectionIsRequiredCheckBox;

    private GeneralOptionTextFieldEditPane dataTypePane, labelTypePane;

    private OperatingTipButton operatingTip;

    private VariableEditFrame() {
        super();
        getContentPane().setLayout(null);

        dLabel = new JLabel("数据类型：");
        dLabel.setBounds(80, 25, 94, 30);
        getContentPane().add(dLabel);

        dataTypePane = new GeneralOptionTextFieldEditPane();
        dataTypeScrollPane = new JScrollPane(dataTypePane);
        dataTypeScrollPane.setBounds(205, 25, 140, 150);
        getContentPane().add(dataTypeScrollPane);

        addDataTypeBt = new MyButton("添加数据类型");
//        addDataTypeBt.setToolTipText("该变量选择组件对应的可以选择什么数据类型数据类型，写在这里。（不写显示所有数据类型的变量）	┗( ▔, ▔ )┛");
        addDataTypeBt.addActionListener(listener);
        addDataTypeBt.setBounds(45, 75, 140, 30);
        getContentPane().add(addDataTypeBt);

        delDataTypeBt = new MyButton("删除数据类型");
        delDataTypeBt.addActionListener(listener);
        delDataTypeBt.setBounds(45, 135, 140, 30);
        getContentPane().add(delDataTypeBt);

        usingRangeComboBox = new VariableUsageRangeCombobox();
        usingRangeComboBox.setBounds(85, 210, 180, 30);
        getContentPane().add(usingRangeComboBox);

        JLabel label1 = new JLabel("获取");
        label1.setBounds(45, 210, 50, 30);
        getContentPane().add(label1);

        JLabel label2 = new JLabel("的变量");
        label2.setBounds(275, 210, 72, 30);
        getContentPane().add(label2);

        noUserSelectionIsRequiredCheckBox = new JCheckBox("自动选择");
        noUserSelectionIsRequiredCheckBox.setFocusPainted(false);
        noUserSelectionIsRequiredCheckBox.setBounds(380, 210, 120, 30);
        getContentPane().add(noUserSelectionIsRequiredCheckBox);

        lLabel = new JLabel("标签类型：");
        lLabel.setBounds(420, 25, 94, 30);
        getContentPane().add(lLabel);

        labelTypePane = new GeneralOptionTextFieldEditPane(150);
        labelTypeScrollPane = new JScrollPane(labelTypePane);
        labelTypeScrollPane.setBounds(545, 25, 170, 150);
        getContentPane().add(labelTypeScrollPane);

        addLabelTypeBt = new MyButton("添加标签类型");
//        addLabelTypeBt.setToolTipText("该变量选择组件对应的可以选择什么数据类型数据类型，写在这里。（不写显示所有数据类型的变量）	┗( ▔, ▔ )┛");
        addLabelTypeBt.addActionListener(listener);
        addLabelTypeBt.setBounds(385, 75, 140, 30);
        getContentPane().add(addLabelTypeBt);

        delLabelTypeBt = new MyButton("删除标签类型");
        delLabelTypeBt.addActionListener(listener);
        delLabelTypeBt.setBounds(385, 135, 140, 30);
        getContentPane().add(delLabelTypeBt);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "5.png").getAbsolutePath()
        );
        operatingTip.setLocation(noUserSelectionIsRequiredCheckBox.getX() + noUserSelectionIsRequiredCheckBox.getWidth() + 30, noUserSelectionIsRequiredCheckBox.getY() + 3);
        getContentPane().add(operatingTip);

        ok.setFocusPainted(false);
        ok.setBounds(160, 270, 80, 30);
        cancel.setFocusPainted(false);
        cancel.setBounds(410, 270, 80, 30);
        getContentPane().add(ok);
        getContentPane().add(cancel);

        cancel.addActionListener(cancelListener);
        this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 400, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 230, 780, 380);

    }

    // 更改自定义变量组件内容
    public VariableEditFrame(VariableControlLabel controlLabel) {
        this();
        this.controlLabel = controlLabel;
        this.setTitle("更改变量组件\"" + this.controlLabel.getControl().getThisName() + "\"属性");

        addCorrespondingRangeItems();

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
        new VariableEditFrame().setVisible(true);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addDataTypeBt) {
                dataTypePane.addOptionTextField();
                dataTypeScrollPane.updateUI();
                dataTypeScrollPane.repaint();

            } else if (e.getSource() == delDataTypeBt) {
                dataTypePane.delOptionTextField();
                dataTypeScrollPane.updateUI();
                dataTypeScrollPane.repaint();

            } else if (e.getSource() == addLabelTypeBt) {
                labelTypePane.addOptionTextField();
                labelTypeScrollPane.updateUI();
                labelTypeScrollPane.repaint();

            } else if (e.getSource() == delLabelTypeBt) {
                labelTypePane.delOptionTextField();
                labelTypeScrollPane.updateUI();
                labelTypeScrollPane.repaint();
            }
        }
    };
    private ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根
            VariableEditFrame.this.dispose();
        }
    };

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

            if (putPane instanceof MethodsFunctionControlPanel || putPane instanceof AdditionalFunctionControlPane){
                comboBoxModel.addElement(AttributeUsageRange.VariableThatIncludesTheEntireOuterFunction);
                comboBoxModel.addElement(AttributeUsageRange.VariableThatFirstLayerAndAllTheFunctionsItContains);

            } else if (putPane instanceof FunctionAddControlInputPane) {
                comboBoxModel.addElement(AttributeUsageRange.VariableThatIncludesTheEntireOuterFunction);
                comboBoxModel.addElement(AttributeUsageRange.VariableThatFirstLayerAndAllTheFunctionsItContains);

            } else if (putPane instanceof InfrequentlyUsedSettingControlUseControlPane) {
                String paneType = ((InfrequentlyUsedSettingControlUseControlPane) putPane).getPaneType();
                if (MarkElementName.FUNCTION_MARK.equals(paneType) || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(paneType)) {
                    comboBoxModel.addElement(AttributeUsageRange.VariableThatIncludesTheEntireOuterFunction);
                    comboBoxModel.addElement(AttributeUsageRange.VariableThatFirstLayerAndAllTheFunctionsItContains);
                }
            }

        }
        usingRangeComboBox.setModel(comboBoxModel);

    }

    @Override
    public void ok() {
        // TODO Auto-generated method stub
        if (check() == true) {
            setValue();
            VariableEditFrame.this.dispose();
        }
    }

    private void setText(VariableControlLabel controlLabel) {
        dataTypePane.restore(controlLabel.getDataTypeList());
        if (controlLabel.getTheAvaliableRange() != -1) {
            usingRangeComboBox.setSelectedVariableUsageRange(controlLabel.getTheAvaliableRange());
        }
        labelTypePane.restore(controlLabel.getLabelTypeList());
        noUserSelectionIsRequiredCheckBox.setSelected(controlLabel.isNoUserSelectionIsRequired());
    }

    private void setValue() {
        controlLabel.setDataTypeList(dataTypePane.getTextList());
        controlLabel.setTheAvaliableRange(usingRangeComboBox.getSelectedDictionaryValue());
        controlLabel.setLabelTypeList(labelTypePane.getTextList());
        controlLabel.setNoUserSelectionIsRequired(noUserSelectionIsRequiredCheckBox.isSelected());
    }

    private boolean check() {
        boolean flag = true;
        JTextField textField;
        for (int i = 0; i < dataTypePane.getComponentCount(); i++) {
            textField = (JTextField) dataTypePane.getComponent(i);
            if ("".equals(textField.getText().trim())) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(this, "ㄟ( ▔, ▔ )ㄏ  　\"数据类型\"那里 第" + (i + 1) + "个输入框什么都没写呢！", "哎呀，保存不了喔！！",
                        JOptionPane.PLAIN_MESSAGE);

                break;
            }
        }
        if (flag == true) {
            for (int i = 0; i < labelTypePane.getComponentCount(); i++) {
                textField = (JTextField) labelTypePane.getComponent(i);
                if ("".equals(textField.getText().trim())) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "ㄟ( ▔, ▔ )ㄏ  　\"标签类型\"那里 第" + (i + 1) + "个输入框什么都没写呢！",
                            "哎呀，保存不了喔！！", JOptionPane.PLAIN_MESSAGE);
                    break;
                }
            }
        }
        return flag;
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
    }
}
