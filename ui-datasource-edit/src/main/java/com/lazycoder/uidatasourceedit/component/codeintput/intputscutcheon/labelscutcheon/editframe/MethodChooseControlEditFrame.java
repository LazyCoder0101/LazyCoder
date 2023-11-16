package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.BasePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.FunctionAddControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.AdditionalSetControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.InitFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.MethodsFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation.ModuleSetFunctionControlPanel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.AdditionalFormatControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.ModuleControlPane;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.MethodChooseControlLabel;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class MethodChooseControlEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -8365761458716394861L;

    public MethodChooseControlLabel controlLabel;

    private MyButton addDataTypeBt, delDataTypeBt;

    private JScrollPane scrollPane;

    private VariableUsageRangeCombobox usingRangeComboBox;

    private JLabel dLabel;

    private GeneralOptionTextFieldEditPane dataTypePane;

    private OperatingTipButton operatingTip;

    private MethodChooseControlEditFrame() {
        super();
        getContentPane().setLayout(null);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "12.png").getAbsolutePath()
        );
        operatingTip.setLocation(10, 10);
        getContentPane().add(operatingTip);

        dLabel = new JLabel("数据类型：");
        dLabel.setBounds(50, 30, 94, 30);
        getContentPane().add(dLabel);

        dataTypePane = new GeneralOptionTextFieldEditPane(180);
        scrollPane = new JScrollPane(dataTypePane);
        scrollPane.setBounds(200, 30, 210, 150);
        getContentPane().add(scrollPane);

        addDataTypeBt = new MyButton("添加标签类型");
//		addDataTypeBt.setToolTipText("该方法选择组件对应的可以选择什么数据类型数据类型，写在这里。（不写显示所有类型的方法）	┗( ▔, ▔ )┛");
        addDataTypeBt.addActionListener(listener);
        addDataTypeBt.setBounds(40, 80, 140, 30);
        getContentPane().add(addDataTypeBt);

        delDataTypeBt = new MyButton("删除标签类型");
        delDataTypeBt.addActionListener(listener);
        delDataTypeBt.setBounds(40, 140, 140, 30);
        getContentPane().add(delDataTypeBt);

        ok.setFocusPainted(false);
        ok.setBounds(70, 270, 80, 30);
        cancel.setFocusPainted(false);
        cancel.setBounds(230, 270, 80, 30);
        getContentPane().add(ok);
        getContentPane().add(cancel);

        usingRangeComboBox = new VariableUsageRangeCombobox();
        usingRangeComboBox.setBounds(80, 210, 180, 30);
        getContentPane().add(usingRangeComboBox);

        JLabel label1 = new JLabel("获取");
        label1.setBounds(40, 210, 50, 30);
        getContentPane().add(label1);

        JLabel label2 = new JLabel("的方法");
        label2.setBounds(270, 210, 72, 30);
        getContentPane().add(label2);
        cancel.addActionListener(cancelListener);
        this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 230, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 250, 460, 370);
    }

    // 更改自定义方法组件内容
    public MethodChooseControlEditFrame(MethodChooseControlLabel controlLabel) {
        this();
        this.controlLabel = controlLabel;
        this.setTitle("更改方法选择组件\"" + this.controlLabel.getControl().getThisName() + "\"属性");

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
        new MethodChooseControlEditFrame().setVisible(true);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addDataTypeBt) {
                dataTypePane.addOptionTextField();
                scrollPane.updateUI();
                scrollPane.repaint();

            } else if (e.getSource() == delDataTypeBt) {
                dataTypePane.delOptionTextField();
                scrollPane.updateUI();
                scrollPane.repaint();
            }
        }
    };
    private ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根
            MethodChooseControlEditFrame.this.dispose();
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
        }
        usingRangeComboBox.setModel(comboBoxModel);

    }

    @Override
    public void ok() {
        // TODO Auto-generated method stub
        if (check() == true) {
            setValue();
            MethodChooseControlEditFrame.this.dispose();
        }
    }

    private void setText(MethodChooseControlLabel controlLabel) {
        dataTypePane.restore(controlLabel.getDataTypeList());
        if (controlLabel.getTheAvaliableRange() != -1) {
            usingRangeComboBox.setSelectedVariableUsageRange(controlLabel.getTheAvaliableRange());
        }
    }

    private void setValue() {
        controlLabel.setDataTypeList(dataTypePane.getTextList());
        controlLabel.setTheAvaliableRange(usingRangeComboBox.getSelectedDictionaryValue());
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
        return flag;
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
    }
}
