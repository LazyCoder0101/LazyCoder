package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.ConstantControlLabel;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.GeneralOptionTextFieldEditPane;

import com.lazycoder.uiutils.utils.SysUtil;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConstantEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -5203078780190674847L;

    // private PassingComponentParams passingComponentParams;
    private ConstantControlLabel controlLabel;
    private JLabel label;
    private JTextField leftTextField, rightTextField, separatorTextField, previewTextField;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private MyButton addDFNameButton, delDFNameButton;
    private GeneralOptionTextFieldEditPane optionTextFieldEditPane;
    private JScrollPane scrollPane;
    private OperatingTipButton operatingTip;

    private ConstantEditFrame() {
        super();
        getContentPane().setLayout(null);

        label = new JLabel("左包围符：");
        label.setBounds(30, 35, 94, 18);
        getContentPane().add(label);

        leftTextField = new JTextField();
        leftTextField.getDocument().addDocumentListener(listener);
        leftTextField.setBounds(150, 30, 100, 30);
        getContentPane().add(leftTextField);

        label1 = new JLabel("间隔符：");
        label1.setBounds(31, 80, 94, 18);
        getContentPane().add(label1);

        separatorTextField = new JTextField();
        separatorTextField.getDocument().addDocumentListener(listener);
        separatorTextField.setBounds(150, 75, 100, 30);
        getContentPane().add(separatorTextField);

        label2 = new JLabel("右包围符：");
        label2.setBounds(30, 125, 94, 18);
        getContentPane().add(label2);

        rightTextField = new JTextField();
        rightTextField.getDocument().addDocumentListener(listener);
        rightTextField.setBounds(150, 125, 100, 30);
        getContentPane().add(rightTextField);

        label3 = new JLabel("预览：");
        label3.setBounds(31, 178, 72, 18);
        getContentPane().add(label3);

        previewTextField = new JTextField();
        previewTextField.setBounds(105, 175, 160, 30);
        getContentPane().add(previewTextField);
        previewTextField.setColumns(10);

        optionTextFieldEditPane = new GeneralOptionTextFieldEditPane();
        scrollPane = new JScrollPane(optionTextFieldEditPane);
        // scrollPane.setUI(new MultiScrollPaneUI());
        scrollPane.setBounds(420, 25, 140, 200);
        getContentPane().add(scrollPane);

        JLabel label4 = new JLabel("默认常量：");
        label4.setBounds(330, 30, 80, 30);
        getContentPane().add(label4);

        addDFNameButton = new MyButton("添加常量");
        addDFNameButton.addActionListener(btlistener);
        addDFNameButton.setBounds(300, 75, 100, 30);
        getContentPane().add(addDFNameButton);

        delDFNameButton = new MyButton("删除常量");
        delDFNameButton.setBounds(300, 130, 100, 30);
        delDFNameButton.addActionListener(btlistener);
        getContentPane().add(delDFNameButton);

        ok.setBounds(62, 228, 80, 30);
        cancel.setBounds(254, 228, 80, 30);
        getContentPane().add(ok);
        getContentPane().add(cancel);

        cancel.addActionListener(cancelListener);
        this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 170, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 250, 630, 320);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "6.png").getAbsolutePath()
        );
        operatingTip.setLocation(previewTextField.getX() + previewTextField.getWidth() + 30, previewTextField.getY() + 3);
        getContentPane().add(operatingTip);
    }

    /**
     * 更改自定义变量组件内容
     *
     * @param controlLabel
     */
    public ConstantEditFrame(ConstantControlLabel controlLabel) {
        this();
        this.controlLabel = controlLabel;
        this.setTitle("更改常量数组组件\"" + this.controlLabel.getControl().getThisName() + "\"属性");

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

    @Override
    public void ok() {
        setValue();
        ConstantEditFrame.this.dispose();
    }

    private ActionListener btlistener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addDFNameButton) {
                optionTextFieldEditPane.addOptionTextField();
                scrollPane.updateUI();
                scrollPane.repaint();

            } else if (e.getSource() == delDFNameButton) {
                optionTextFieldEditPane.delOptionTextField();
                scrollPane.updateUI();
                scrollPane.repaint();

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

    ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根
            ConstantEditFrame.this.dispose();
        }
    };

    public void setText(ConstantControlLabel controlLabel) {
        this.leftTextField.setText(controlLabel.getLeftStr());
        this.rightTextField.setText(controlLabel.getRightStr());
        this.separatorTextField.setText(controlLabel.getSeparatorStr());
        ArrayList<String> list = ConstantControl.getDefaultList(controlLabel.getControl());
        optionTextFieldEditPane.restore(list);
    }

    public void setValue() {
        controlLabel.setLeftStr(leftTextField.getText());
        controlLabel.setRightStr(rightTextField.getText());
        controlLabel.setSeparatorStr(separatorTextField.getText());
        ArrayList<String> defaultList = optionTextFieldEditPane.getTextList();
        ConstantControl.setDefaultList(defaultList, controlLabel.getControl());
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


}
