package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.AbstractEditFrame;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 选项数据编辑窗口，该类只做UI，不涉及业务处理
 */
public class ContentChooseFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = 6087062434607059608L;
    protected OptionEditPane optionEditPane;
    private JScrollPane optionEditSP;
    protected JTextField optionNameTextField, leftTextField, rightTextField, separatorTextField, previewTextField;
    protected JComboBox<String> nOr1Combobx;
    protected JCheckBox generalCheckBox;
    private Dimension d = SysUtil.SCREEN_SIZE;
    private MyButton newLine, newOption, delLine, delOption;
    protected NoteRowHeaderView noteRowHeaderView = null;
    private Font font = new Font("微软雅黑", Font.PLAIN, 18);
    private OperatingTipButton operatingTips;

    /**
     * Create the frame.
     */
    public ContentChooseFrame() {
        super();
        getContentPane().setLayout(null);

        newLine = new MyButton("新增行");
        newLine.addActionListener(actionListener);
        newLine.setBounds(126, 60, 100, 30);
        getContentPane().add(newLine);

        newOption = new MyButton("新增选项");
        newOption.addActionListener(actionListener);
        newOption.setBounds(233, 136, 100, 30);
        getContentPane().add(newOption);

        delLine = new MyButton("删除行");
        delLine.addActionListener(actionListener);
        delLine.setBounds(126, 215, 100, 30);
        getContentPane().add(delLine);

        delOption = new MyButton("删除选项");
        delOption.addActionListener(actionListener);
        delOption.setBounds(16, 136, 100, 30);
        getContentPane().add(delOption);

        ok.setBounds(200, 570, 80, 30);
        cancel.setBounds(420, 570, 80, 30);
        getContentPane().add(ok);
        getContentPane().add(cancel);

        // optionContentEditPane = new OptionContentEditPane();
        // optionContentEditPane.setBounds(349, 25, 221, 285);
        // getContentPane()add(optionContentEditPane);

        noteRowHeaderView = new NoteRowHeaderView();
        optionEditPane = new OptionEditPane(noteRowHeaderView);
        optionEditSP = new JScrollPane(optionEditPane);
        optionEditSP.setRowHeaderView(noteRowHeaderView);
        optionEditSP.setBounds(20, 320, 710, 234);
        getContentPane().add(optionEditSP);

        generalCheckBox = new JCheckBox("设为通用");
        generalCheckBox.setFocusPainted(false);
        generalCheckBox.setFont(font);
        generalCheckBox.setBounds(290, 265, 120, 30);

        getContentPane().add(generalCheckBox);

        optionNameTextField = new JTextField();
        optionNameTextField.setBounds(317, 20, 300, 30);
        getContentPane().add(optionNameTextField);
        optionNameTextField.setColumns(10);

        JLabel optionNameLabel = new JLabel("选项名：");
        optionNameLabel.setBounds(250, 26, 69, 18);
        getContentPane().add(optionNameLabel);

        JLabel leftLabel = new JLabel("左符号：");
        leftLabel.setBounds(364, 94, 60, 18);
        getContentPane().add(leftLabel);

        leftTextField = new JTextField();
        leftTextField.getDocument().addDocumentListener(previewListener);
        leftTextField.setBounds(424, 88, 122, 30);
        getContentPane().add(leftTextField);
        leftTextField.setColumns(10);

        JLabel rightLabel = new JLabel("右符号：");
        rightLabel.setBounds(570, 94, 60, 18);
        getContentPane().add(rightLabel);

        rightTextField = new JTextField();
        rightTextField.getDocument().addDocumentListener(previewListener);
        rightTextField.setBounds(626, 88, 122, 30);
        getContentPane().add(rightTextField);
        rightTextField.setColumns(10);

        JLabel separatorLabel = new JLabel("间隔符：");
        separatorLabel.setBounds(364, 154, 60, 18);
        getContentPane().add(separatorLabel);

        separatorTextField = new JTextField();
        separatorTextField.getDocument().addDocumentListener(previewListener);
        separatorTextField.setBounds(424, 148, 122, 30);
        getContentPane().add(separatorTextField);
        separatorTextField.setColumns(10);

        JLabel previewLabel = new JLabel("预览：");
        previewLabel.setBounds(570, 148, 60, 18);
        getContentPane().add(previewLabel);

        previewTextField = new JTextField();
        previewTextField.setEditable(false);
        previewTextField.setBounds(626, 148, 122, 30);
        getContentPane().add(previewTextField);
        previewTextField.setColumns(10);

        String[] sStrings = new String[]{"单选", "多选"};
        nOr1Combobx = new JComboBox<String>(sStrings);
        // nOr1Combobx = new JComboBox<String>();
        setPreviewValue();
        // nOr1Combobx = new JComboBox<String>();
        nOr1Combobx.addItemListener(nOr1Listener);
        nOr1Combobx.setSelectedIndex(1);
        nOr1Combobx.setSelectedIndex(0);
        nOr1Combobx.setBounds(441, 265, 80, 30);
        getContentPane().add(nOr1Combobx);

        operatingTips = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "2.png").getAbsolutePath()
        );
        operatingTips.setLocation(30, optionNameLabel.getLocation().y);
        getContentPane().add(operatingTips);

        this.setBounds((int) d.getWidth() / 2 - 400, (int) d.getHeight() / 2 - 330, 800, 660);
    }

    private DocumentListener previewListener = new DocumentListener() {

        @Override
        public void removeUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            setPreviewValue();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            setPreviewValue();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            setPreviewValue();
        }
    };

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == newLine) {
                optionEditPane.addAline();
                SysUtil.scrollToBottom(optionEditSP);
            } else if (e.getSource() == newOption) {
                optionEditPane.addOption();
                SysUtil.scrollToBottom(optionEditSP);
            } else if (e.getSource() == delLine) {
                optionEditPane.delAline();
            } else if (e.getSource() == delOption) {
                optionEditPane.delOption();
            }
            repaint();
            setVisible(true);
        }
    };

    private ItemListener nOr1Listener = new ItemListener() {

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public void itemStateChanged(ItemEvent e) {
            // TODO Auto-generated method stub
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (((JComboBox<String>) e.getSource()).getSelectedItem() == "单选") {
                    leftTextField.setText("");
                    rightTextField.setText("");
                    separatorTextField.setText("");
                    previewTextField.setText("");

                    leftTextField.setEnabled(false);
                    rightTextField.setEnabled(false);
                    separatorTextField.setEnabled(false);
                    previewTextField.setEnabled(false);

                } else if (((JComboBox) e.getSource()).getSelectedItem() == "多选") {
                    leftTextField.setEnabled(true);
                    rightTextField.setEnabled(true);
                    separatorTextField.setEnabled(true);
                    previewTextField.setEnabled(true);

                }
            }
        }
    };


    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    TheUI.flatLookAndFeel();
//                    ContentChooseFrame frame = new ContentChooseFrame();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    private void setPreviewValue() {
        String leftStr = leftTextField.getText(), rigtStr = rightTextField.getText(),
                separatorStr = separatorTextField.getText();
        if ("".equals(leftStr) && "".equals(rigtStr) && "".equals(separatorStr)) {
            previewTextField.setText("");
        } else {
            String value = leftStr + "A" + rigtStr + separatorStr + leftStr + "B" + rigtStr;
            previewTextField.setText(value);
        }
    }

}
