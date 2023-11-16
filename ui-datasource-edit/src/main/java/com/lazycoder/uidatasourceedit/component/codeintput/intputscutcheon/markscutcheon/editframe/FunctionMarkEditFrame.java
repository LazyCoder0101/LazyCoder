package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.FunctionMarkScutcheon;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
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
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class FunctionMarkEditFrame extends AbstractMarkEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4089580150889343983L;

    private JPanel contentPane;
    private MyButton okButton, cancelButton;
    private FunctionMarkScutcheon functionMarkScutcheon;
    private JLabel matchLabel;
    private CodeLabelCombobox codeLabelCombobox;

    public static void main(String[] args) {
        new FunctionMarkEditFrame().setVisible(true);
    }

    /**
     * Create the frame.
     */
    public FunctionMarkEditFrame() {
        super();
        setTitle("更改\"业务功能\"标记属性");
        setBounds(((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 230), ((int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 180), 460, 230);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel codeCodeLabelPanel = new JPanel();
        codeCodeLabelPanel.setBounds(50, 0, 427, 80);
        contentPane.add(codeCodeLabelPanel);
        codeCodeLabelPanel.setLayout(null);
//		codeCodeLabelPanel.setBorder(new LineBorder(Color.black));

        JLabel codeLabelLabel = new JLabel("代码标签：");
        codeLabelLabel.setBounds(14, 25, 80, 30);
        codeCodeLabelPanel.add(codeLabelLabel);

        codeLabelCombobox = new CodeLabelCombobox();
        codeLabelCombobox.setBounds(90, 25, 150, 30);
        codeLabelCombobox.addPopupMenuListener(popupMenuListener);
        codeCodeLabelPanel.add(codeLabelCombobox);
//		JComboBox comboBox = new JComboBox();
//		comboBox.setBounds(90, 25, 150, 30);
//		codeCodeLabelPanel.add(comboBox);

        okButton = new MyButton("确定");
        okButton.setBounds(170, 140, 80, 30);
        contentPane.add(okButton);

        cancelButton = new MyButton("取消");
        cancelButton.setBounds(290, 140, 80, 30);
        contentPane.add(cancelButton);

        JLabel label1 = new JLabel("匹配值：");
        label1.setBounds(62, 87, 60, 30);
        contentPane.add(label1);

        matchLabel = new JLabel("0");
        matchLabel.setFont(new Font("宋体", Font.PLAIN, 24));
        matchLabel.setBounds(119, 80, 33, 40);
        contentPane.add(matchLabel);

        OperatingTipButton operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "标记组件属性设置界面" + File.separator + "功能")
                .getAbsolutePath());
        operatingTip.setLocation(matchLabel.getX() + matchLabel.getWidth() + 20, matchLabel.getY() + 10);
        contentPane.add(operatingTip);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FunctionMarkEditFrame.this.dispose();
            }
        });
    }

    public FunctionMarkEditFrame(FunctionMarkScutcheon functionMarkScutcheon) {
        this();
        this.functionMarkScutcheon = functionMarkScutcheon;
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
        codeLabelCombobox.setSelectedCodeLabel(this.functionMarkScutcheon.getMarkElement().getCodeLabelId());
    }

    private void setValue() {
        FunctionMarkElement functionMarkElement = new FunctionMarkElement();
        functionMarkElement.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        functionMarkScutcheon.setMarkElement(functionMarkElement);
    }

    public void ok() {
        if (functionMarkScutcheon != null) {
            setValue();
            functionMarkScutcheon.setShowText();
            dispose();
        }
    }

    /**
     * 获取匹配值
     */
    private void setMatchingValue() {
        int matchingValue = 0;
        if (codeLabelCombobox.isNoCodeLabel() == false) {
            matchingValue = matchingValue + functionMarkScutcheon.getMarkElement().getCodeLabelMatchingWeight();
        }
        matchLabel.setText(matchingValue + "");
    }

}
