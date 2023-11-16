package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.TextInputControlLabel;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.RealNumberUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TextInputEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = 864666707537240117L;
    private static final String[]
            INPUT_TYPE = new String[]{"无限制", "仅限整数", "仅限小数"},//这里要和textInputControl的inputLimit字段使用的参数对应
            SHOW_TYPE = new String[]{"文本框", "文本域"},//这里要和textInputControl的presentForm字段使用的参数对应
            SIZE_TYPE = new String[]{"短（小）", "中", "长（大）"};//这里要和textInputControl的textFieldShowSize字段使用的参数对应
    private JPanel contentPane;
    private JTextField minTextField, maxTextField;
    private JComboBox<String> showComboBox, inputTypeComboBox, textfieldSizeCombobox;
    private JTextArea textArea;

    private TextInputControlLabel controlLabel = null;

    private OperatingTipButton operatingTip;

    /**
     * Create the frame.
     */
    private TextInputEditFrame() {
        super();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel showLabel = new JLabel("显示形式：");
        showLabel.setBounds(25, 35, 90, 30);
        contentPane.add(showLabel);

        // showComboBox = new JComboBox<String>();
        showComboBox = new JComboBox<String>(SHOW_TYPE);
        showComboBox.setBounds(117, 38, 90, 30);
        contentPane.add(showComboBox);

        JLabel inputTypeLable = new JLabel("输入类型：");
        inputTypeLable.setBounds(230, 35, 100, 30);
        contentPane.add(inputTypeLable);

        // inputTypeComboBox = new JComboBox<String>();
        inputTypeComboBox = new JComboBox<String>(INPUT_TYPE);
        inputTypeComboBox.setBounds(300, 35, 120, 30);
        contentPane.add(inputTypeComboBox);
        inputTypeComboBox.addItemListener(itemListener);

        JLabel dLabel = new JLabel("默认值：");
        dLabel.setBounds(25, 100, 70, 30);
        contentPane.add(dLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 100, 330, 150);
        contentPane.add(scrollPane);

        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        JLabel slabel = new JLabel("最小值：");
        slabel.setBounds(25, 290, 60, 30);
        contentPane.add(slabel);

        minTextField = new JTextField();
        minTextField.setToolTipText("这个值在选择输入类型为”无限制“时不生效	φ(>ω<*) ");
        minTextField.setBounds(85, 290, 120, 30);
        contentPane.add(minTextField);
        minTextField.setColumns(10);

        JLabel mlabel = new JLabel("最大值：");
        mlabel.setBounds(240, 290, 60, 30);
        contentPane.add(mlabel);

        maxTextField = new JTextField();
        maxTextField.setToolTipText("这个值在选择输入类型为”无限制“时不生效	φ(>ω<*) ");
        maxTextField.setBounds(300, 290, 120, 30);
        contentPane.add(maxTextField);
        maxTextField.setColumns(10);

        ok.setBounds(94, 375, 110, 30);
        contentPane.add(ok);
        ok.addActionListener(listener);

        cancel.setBounds(274, 375, 110, 30);
        contentPane.add(cancel);

        JLabel label = new JLabel("显示尺寸：");
        label.setBounds(23, 335, 120, 30);
        contentPane.add(label);

        textfieldSizeCombobox = new JComboBox<String>(SIZE_TYPE);
        textfieldSizeCombobox.setBounds(110, 335, 100, 30);
        textfieldSizeCombobox.setSelectedIndex(0);
        contentPane.add(textfieldSizeCombobox);
        cancel.addActionListener(listener);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "1.png").getAbsolutePath()
        );
        operatingTip.setLocation(textfieldSizeCombobox.getX() + textfieldSizeCombobox.getWidth() + 30, textfieldSizeCombobox.getY() + 3);
        getContentPane().add(operatingTip);

        this.setBounds(SysUtil.SCREEN_SIZE.width / 2 - 200, SysUtil.SCREEN_SIZE.height / 2 - 250,
                493, 480);
    }


    /**
     * 更改
     *
     * @param controlLabel
     */
    public TextInputEditFrame(TextInputControlLabel controlLabel) {
        this();
        this.controlLabel = controlLabel;
        this.setTitle("更改内容输入组件\"" + this.controlLabel.getControl().getThisName() + "\"属性");


        setText(controlLabel);
        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (check()) {
                    ok();
                }
            }
        });
        setVisible(true);
    }

    public void updateState() {
        if (inputTypeComboBox.getSelectedIndex() == TextInputControl.NOT_LIMIT) {
            minTextField.setEnabled(false);
            maxTextField.setEnabled(false);
        } else {
            minTextField.setEnabled(true);
            maxTextField.setEnabled(true);
        }
    }

    @Override
    public void ok() {
        // TODO Auto-generated method stub
        setValue();
        TextInputEditFrame.this.dispose();
    }

    private boolean check() {
        boolean flag = true;
        if (TextInputControl.ONLY_INTEGER == inputTypeComboBox.getSelectedIndex()) {//仅限整数
            String minValue = minTextField.getText().trim();
            if ("".equals(minValue) == false) {
                if (!RealNumberUtil.isInteger(minValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	现在输入的最小值不是整数");
                    return flag;
                }
            }
            String maxValue = maxTextField.getText().trim();
            if ("".equals(maxValue) == false) {
                if (!RealNumberUtil.isInteger(maxValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	现在输入的最大值不是整数");
                    return flag;
                }
            }
            String defaultValue = textArea.getText().trim();
            if ("".equals(defaultValue) == false) {
                if (!RealNumberUtil.isInteger(defaultValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	现在输入的默认值不是整数");
                    return flag;
                }
            }
            if ("".equals(defaultValue) == false && "".equals(minValue) == false) {
                if (RealNumberUtil.convertedToInteger(defaultValue) < RealNumberUtil.convertedToInteger(minValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	说好的最小是" + minValue + "，你却默认写" + defaultValue);
                    return flag;
                }
            }
            if ("".equals(defaultValue) == false && "".equals(maxValue) == false) {
                if (RealNumberUtil.convertedToInteger(defaultValue) > RealNumberUtil.convertedToInteger(maxValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	说好的最大是" + maxValue + "，你却默认写" + defaultValue);
                    return flag;
                }
            }
        } else if (TextInputControl.ONLY_FLOAT == inputTypeComboBox.getSelectedIndex()) {//仅限浮点数
            String minValue = minTextField.getText().trim();
            if ("".equals(minValue) == false) {
                if (!RealNumberUtil.isDouble(minValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	现在输入的最小值不是小数");
                    return flag;
                }
            }
            String maxValue = maxTextField.getText().trim();
            if ("".equals(maxValue) == false) {
                if (!RealNumberUtil.isDouble(maxValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	现在输入的最大值不是小数");
                    return flag;
                }
            }
            String defaultValue = textArea.getText().trim();
            if ("".equals(defaultValue) == false) {
                if (!RealNumberUtil.isDouble(defaultValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	现在输入的默认值不是小数");
                    return flag;
                }
            }
            if ("".equals(defaultValue) == false && "".equals(minValue) == false) {
                if (RealNumberUtil.convertedToDouble(defaultValue) < RealNumberUtil.convertedToDouble(minValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	说好的最小是" + minValue + "，你却默认写" + defaultValue);
                    return flag;
                }
            }
            if ("".equals(defaultValue) == false && "".equals(maxValue) == false) {
                if (RealNumberUtil.convertedToDouble(defaultValue) > RealNumberUtil.convertedToDouble(maxValue)) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "∑(っ°Д°;)っ	说好的最大是" + maxValue + "，你却默认写" + defaultValue);
                    return flag;
                }
            }
        }
        return flag;
    }

    public void setText(TextInputControlLabel controlLabel) {
        inputTypeComboBox.setSelectedIndex(controlLabel.getInputLimit());

        showComboBox.setSelectedIndex(controlLabel.getPresentForm());
        textArea.setText(controlLabel.getDefaultValue());
        minTextField.setText(controlLabel.getMinValue());
        maxTextField.setText(controlLabel.getMaxValue());
        textfieldSizeCombobox.setSelectedIndex(controlLabel.getTextFieldShowSize());
    }

    public void setValue() {
        // controlElement = new text_input_control();
        controlLabel.setInputLimit(inputTypeComboBox.getSelectedIndex());
        controlLabel.setPresentForm(showComboBox.getSelectedIndex());
        controlLabel.setDefaultValue(textArea.getText());
        controlLabel.setMaxValue(maxTextField.getText());
        controlLabel.setMinValue(minTextField.getText());
        controlLabel.setTextFieldShowSize(textfieldSizeCombobox.getSelectedIndex());
    }


    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
    }

    private ItemListener itemListener = new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
            // TODO Auto-generated method stub
            updateState();
        }
    };

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == cancel) {
                TextInputEditFrame.this.dispose();
            }
        }
    };

}
