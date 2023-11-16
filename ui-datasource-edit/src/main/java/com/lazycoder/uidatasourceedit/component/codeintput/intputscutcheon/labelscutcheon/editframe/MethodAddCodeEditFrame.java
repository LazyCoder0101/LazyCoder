package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.formdev.flatlaf.FlatLightLaf;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.FunctionAddCodeLabel;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MethodAddCodeEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -8108312765531408563L;

    private FunctionAddCodeLabel functionAddCodeLabel = null;

    private CodeLabelCombobox codeLabelCombobox = null;

    private JTextField indentTextField = null;

    private OperatingTipButton operatingTip;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new MethodAddCodeEditFrame().setVisible(true);
    }

    private MethodAddCodeEditFrame() {
        super();
        getContentPane().setLayout(null);

        JLabel label1 = new JLabel("代码标签：");
        label1.setBounds(30, 10, 80, 30);
        getContentPane().add(label1);
//		JComboBox comboBox = new JComboBox();
//		comboBox.setBounds(90,10,150,30);
//		getContentPane().add(comboBox);

        codeLabelCombobox = new CodeLabelCombobox();
        codeLabelCombobox.setBounds(90, 10, 150, 30);
        getContentPane().add(codeLabelCombobox);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "功能拓展代码组件").getAbsolutePath()
        );
        operatingTip.setLocation(codeLabelCombobox.getX() + codeLabelCombobox.getWidth() + 15, codeLabelCombobox.getY() + 10);
        getContentPane().add(operatingTip);

        JLabel label2 = new JLabel("缩进符：");
        label2.setBounds(30, 50, 80, 30);
        getContentPane().add(label2);
        indentTextField = new JTextField();
        indentTextField.setBounds(90,50,150,30);
        indentTextField.setToolTipText("如有需要在每次插入代码的时候写入缩进符，在此设置");
        getContentPane().add(indentTextField);

        ok.setBounds(30, 100, 80, 30);
        cancel.setBounds(150, 100, 80, 30);
        getContentPane().add(ok);
        getContentPane().add(cancel);
        cancel.addActionListener(cancelListener);
        this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 150, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 100, 300, 200);

    }

    // 更改代码组件内容
    public MethodAddCodeEditFrame(FunctionAddCodeLabel functionAddCodeLabel) {
        this();
        this.functionAddCodeLabel = functionAddCodeLabel;
        this.setTitle("更改功能拓展组件(代码)\"" + functionAddCodeLabel.getCodeElement().getThisName() + "\"属性");
        codeLabelCombobox.setSelectedCodeLabel(functionAddCodeLabel.getCodeLabelId());
        indentTextField.setText(functionAddCodeLabel.getIndent());
        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 自动生成的方法存根
                ok();
            }
        });
        this.setVisible(true);

    }

    ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根
            MethodAddCodeEditFrame.this.dispose();
        }
    };


    @Override
    public void ok() {
        functionAddCodeLabel.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        functionAddCodeLabel.setIndent(indentTextField.getText());
        dispose();
    }

}
