package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.mark.AdditionalSetMarkElement;
import com.lazycoder.service.vo.element.mark.MainSetMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.AdditionalSetMarkScutcheon;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.uiutils.utils.TheUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class AdditionalSetMarkEditFrame extends AbstractMarkEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4591743149497821480L;

    private JPanel contentPane;
    private MySpinner classSerialSpinner, commandSpinner, codeSerialSpinner;
    private MyButton okButton, cancelButton;
    private JLabel matchLabel;
    private AdditionalSetMarkScutcheon markScutcheon;
    private CodeLabelCombobox codeLabelCombobox;

    public static void main(String[] args) {
        TheUI.flatLookAndFeel();
        new AdditionalSetMarkEditFrame().setVisible(true);
    }


    /**
     * Create the frame.
     */
    private AdditionalSetMarkEditFrame() {
        super();
        setTitle("更改\"可选模板设置\"标记属性");
        init();
    }

    /**
     * 还原
     *
     * @param markScutcheon
     */
    public AdditionalSetMarkEditFrame(AdditionalSetMarkScutcheon markScutcheon) {
        this();
        this.markScutcheon = markScutcheon;
        reduction();
        setMatchingValue();
        okButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
        setVisible(true);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == okButton) {
                ok();

            } else if (e.getSource() == cancelButton) {
                dispose();
            }
        }
    };

    private ChangeListener spinnerListener = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
        }
    };


    private void init() {
        setBounds(((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 280), ((int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 280), 560, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel classSerialPanel = new JPanel();
        classSerialPanel.setBounds(50, 10, 427, 90);
        contentPane.add(classSerialPanel);
        classSerialPanel.setLayout(null);
        classSerialPanel.setBorder(new LineBorder(Color.black));

        JLabel classSerialLabel = new JLabel("分类序号：");
        classSerialLabel.setBounds(15, 25, 80, 30);
        classSerialPanel.add(classSerialLabel);

        classSerialSpinner = new MySpinner();
        classSerialSpinner.setBounds(90, 25, 80, 30);
        classSerialPanel.add(classSerialSpinner);
        classSerialSpinner.addChangeListener(spinnerListener);

        JPanel commandPanel = new JPanel();
        commandPanel.setBounds(50, 110, 427, 80);
        contentPane.add(commandPanel);
        commandPanel.setLayout(null);
        commandPanel.setBorder(new LineBorder(Color.black));

        JLabel commandLabel = new JLabel("方法序号：");
        commandLabel.setBounds(14, 25, 80, 30);
        commandPanel.add(commandLabel);

        commandSpinner = new MySpinner();
        commandSpinner.setBounds(90, 25, 80, 30);
        commandPanel.add(commandSpinner);
        commandSpinner.addChangeListener(spinnerListener);

        JPanel codeSerialPanel = new JPanel();
        codeSerialPanel.setBounds(50, 200, 427, 80);
        contentPane.add(codeSerialPanel);
        codeSerialPanel.setLayout(null);
        codeSerialPanel.setBorder(new LineBorder(Color.black));

        JLabel codeSerialLabel = new JLabel("代码序号：");
        codeSerialLabel.setBounds(14, 25, 80, 30);
        codeSerialPanel.add(codeSerialLabel);

        codeSerialSpinner = new MySpinner();
        codeSerialSpinner.setBounds(90, 25, 80, 30);
        codeSerialPanel.add(codeSerialSpinner);
        codeSerialSpinner.addChangeListener(spinnerListener);

        JPanel codeCodeLabelPanel = new JPanel();
        codeCodeLabelPanel.setBounds(50, 290, 427, 90);
        contentPane.add(codeCodeLabelPanel);
        codeCodeLabelPanel.setLayout(null);
        codeCodeLabelPanel.setBorder(new LineBorder(Color.black));

        JLabel codeLabelLabel = new JLabel("代码标签：");
        codeLabelLabel.setBounds(14, 25, 80, 30);
        codeCodeLabelPanel.add(codeLabelLabel);

        codeLabelCombobox = new CodeLabelCombobox();
        codeLabelCombobox.setBounds(90, 25, 150, 30);
        codeLabelCombobox.addPopupMenuListener(popupMenuListener);
        codeCodeLabelPanel.add(codeLabelCombobox);
//        JComboBox comboBox = new JComboBox();
//        comboBox.setBounds(90, 25, 150, 30);
//        codeCodeLabelPanel.add(comboBox);

        okButton = new MyButton("确定");
        okButton.addActionListener(listener);
        okButton.setBounds(288, 410, 80, 30);
        contentPane.add(okButton);

        cancelButton = new MyButton("取消");
        cancelButton.addActionListener(listener);
        cancelButton.setBounds(400, 410, 80, 30);
        contentPane.add(cancelButton);

        JLabel label1 = new JLabel("匹配值：");
        label1.setBounds(60, 410, 60, 30);
        contentPane.add(label1);

        matchLabel = new JLabel("0");
        matchLabel.setFont(new Font("宋体", Font.PLAIN, 24));
        matchLabel.setBounds(121, 403, 33, 40);
        contentPane.add(matchLabel);

        OperatingTipButton operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "标记组件属性设置界面" + File.separator + "可选模板设置")
                .getAbsolutePath());
        operatingTip.setLocation(matchLabel.getX() + matchLabel.getWidth() + 20, matchLabel.getY() + 10);
        contentPane.add(operatingTip);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AdditionalSetMarkEditFrame.this.dispose();
            }
        });
    }

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            setMatchingValue();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {}
    };

    private void reduction() {
        if (this.markScutcheon.getMarkElement().getClassificationSerial() == MarkElementName.MARK_NULL) {
            classSerialSpinner.setValue(0);
        } else {
            classSerialSpinner.setValue(this.markScutcheon.getMarkElement().getClassificationSerial());
        }

        if (this.markScutcheon.getMarkElement().getOperatingSerialNumber() == MarkElementName.MARK_NULL) {
            commandSpinner.setValue(0);
        } else {
            commandSpinner.setValue(this.markScutcheon.getMarkElement().getOperatingSerialNumber());
        }
        if (this.markScutcheon.getMarkElement().getCodeNumber() == MarkElementName.MARK_NULL) {
            codeSerialSpinner.setValue(0);
        } else {
            codeSerialSpinner.setValue(this.markScutcheon.getMarkElement().getCodeNumber());
        }
        codeLabelCombobox.setSelectedCodeLabel(this.markScutcheon.getMarkElement().getCodeLabelId());
    }

    private void setValue() {
        AdditionalSetMarkElement markElement = markScutcheon.getMarkElement();

        if ((int) classSerialSpinner.getValue() == 0) {
            markElement.setClassificationSerial(MarkElementName.MARK_NULL);
        } else {
            markElement.setClassificationSerial((int) classSerialSpinner.getValue());
        }

        if ((int) commandSpinner.getValue() == 0) {
            markElement.setOperatingSerialNumber(MarkElementName.MARK_NULL);
        } else {
            markElement.setOperatingSerialNumber((int) commandSpinner.getValue());
        }

        if ((int) codeSerialSpinner.getValue() == 0) {
            markElement.setCodeNumber(MarkElementName.MARK_NULL);
        } else {
            markElement.setCodeNumber((int) codeSerialSpinner.getValue());
        }
        markElement.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        markScutcheon.setMarkElement(markElement);
    }

    /**
     * 获取匹配值
     */
    private void setMatchingValue() {
        int matchingValue = 0;
        if ((int) classSerialSpinner.getValue() != 0) {
            matchingValue = matchingValue + MainSetMarkElement.NUMBER_OF_MODULE_SET_CLASSIFICATION_MATCHING_WEIGHT;
        }
        if ((int) commandSpinner.getValue() != 0) {
            matchingValue = matchingValue + MainSetMarkElement.COMMAND_SERIAL_MATCHING_WEIGHT;
        }
        if ((int) codeSerialSpinner.getValue() != 0) {
            matchingValue = matchingValue + MainSetMarkElement.CODE_SERIAL_MATCHING_WEIGHT;
        }
        if (codeLabelCombobox.isNoCodeLabel() == false) {
            matchingValue = matchingValue + markScutcheon.getMarkElement().getCodeLabelMatchingWeight();
        }
        matchLabel.setText(matchingValue + "");
    }

    public void ok() {
        if (markScutcheon != null) {
            setValue();
            markScutcheon.setShowText();
        }
        dispose();
    }

}
