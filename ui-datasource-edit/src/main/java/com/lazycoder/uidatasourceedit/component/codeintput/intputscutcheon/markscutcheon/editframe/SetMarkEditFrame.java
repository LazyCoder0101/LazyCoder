package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.service.vo.element.mark.SetMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.SetMarkScutcheon;
import com.lazycoder.uidatasourceedit.component.component.moduleselectmenu.ModuleSelectMenu;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

public class SetMarkEditFrame extends AbstractMarkEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -7371840830926913260L;

    private JPanel contentPane;
    private MySpinner classSerialSpinner, commandSpinner, codeSerialSpinner;
    private MyButton okButton, cancelButton;
    private JLabel matchLabel;
    private SetMarkScutcheon setMarkScutcheon;

    private CodeLabelCombobox codeLabelCombobox;

    private ModuleSelectMenu moduleSelectMenu;

    /**
     * Create the frame.
     */
    private SetMarkEditFrame() {
        super();
        setTitle("更改\"模块设置\"标记属性");
        init();
    }

    /**
     * 还原
     *
     * @param setMarkScutcheon
     */
    public SetMarkEditFrame(SetMarkScutcheon setMarkScutcheon) {
        this();
        this.setMarkScutcheon = setMarkScutcheon;
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
            // TODO Auto-generated method stub
            setMatchingValue();
        }
    };

    private FocusListener textFieldListener = new FocusListener() {

        @Override
        public void focusLost(FocusEvent e) {
            // TODO Auto-generated method stub
            setMatchingValue();
        }

        @Override
        public void focusGained(FocusEvent e) {
            // TODO Auto-generated method stub
        }
    };

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    SetMarkEditFrame frame = new SetMarkEditFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init() {
        setBounds(((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 290), ((int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 320), 568, 628);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel modulePanel = new JPanel();
        modulePanel.setBounds(50, 10, 427, 90);
        contentPane.add(modulePanel);
        modulePanel.setLayout(null);
        modulePanel.setBorder(new LineBorder(Color.black));

        JLabel moduleLabel = new JLabel("模块：");
        moduleLabel.setBounds(14, 25, 70, 30);
        modulePanel.add(moduleLabel);

        moduleSelectMenu = new ModuleSelectMenu(){

            @Override
            public void setNoModule() {
                super.setNoModule();
                setMatchingValue();
            }

            @Override
            public void selectModule(Module module) {
                super.selectModule(module);
                setMatchingValue();
            }
        };
        moduleSelectMenu.setBounds(78, 25, 200, 30);
        modulePanel.add(moduleSelectMenu);

        JPanel classSerialPanel = new JPanel();
        classSerialPanel.setBounds(50, 110, 427, 90);
        contentPane.add(classSerialPanel);
        classSerialPanel.setLayout(null);
        classSerialPanel.setBorder(new LineBorder(Color.black));

        JLabel classSerialLabel = new JLabel("分类序号：");
        classSerialLabel.setBounds(15, 25, 80, 30);
        classSerialPanel.add(classSerialLabel);

        classSerialSpinner = new MySpinner();
        classSerialSpinner.setBounds(109, 25, 80, 30);
        classSerialPanel.add(classSerialSpinner);
        classSerialSpinner.addChangeListener(spinnerListener);

        JPanel commandPanel = new JPanel();
        commandPanel.setBounds(50, 210, 427, 80);
        contentPane.add(commandPanel);
        commandPanel.setLayout(null);
        commandPanel.setBorder(new LineBorder(Color.black));

        JLabel commandLabel = new JLabel("方法序号：");
        commandLabel.setBounds(14, 25, 80, 30);
        commandPanel.add(commandLabel);

        commandSpinner = new MySpinner();
        commandSpinner.setBounds(108, 25, 80, 30);
        commandPanel.add(commandSpinner);
        commandSpinner.addChangeListener(spinnerListener);

        JPanel codeSerialPanel = new JPanel();
        codeSerialPanel.setBounds(50, 300, 427, 90);
        contentPane.add(codeSerialPanel);
        codeSerialPanel.setLayout(null);
        codeSerialPanel.setBorder(new LineBorder(Color.black));

        JLabel codeSerialLabel = new JLabel("代码序号：");
        codeSerialLabel.setBounds(14, 25, 80, 30);
        codeSerialPanel.add(codeSerialLabel);

        codeSerialSpinner = new MySpinner();
        codeSerialSpinner.setBounds(108, 25, 80, 30);
        codeSerialPanel.add(codeSerialSpinner);
        codeSerialSpinner.addChangeListener(spinnerListener);

        JPanel codeCodeLabelPanel = new JPanel();
        codeCodeLabelPanel.setBounds(50, 400, 427, 90);
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
        okButton.setBounds(288, 518, 80, 30);
        contentPane.add(okButton);

        cancelButton = new MyButton("取消");
        cancelButton.addActionListener(listener);
        cancelButton.setBounds(400, 518, 80, 30);
        contentPane.add(cancelButton);

        JLabel label1 = new JLabel("匹配值：");
        label1.setBounds(60, 515, 60, 30);
        contentPane.add(label1);

        matchLabel = new JLabel("0");
        matchLabel.setFont(new Font("宋体", Font.PLAIN, 24));
        matchLabel.setBounds(121, 509, 33, 40);
        contentPane.add(matchLabel);

        OperatingTipButton operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "标记组件属性设置界面" + File.separator + "设置")
                .getAbsolutePath());
        operatingTip.setLocation(matchLabel.getX() + matchLabel.getWidth() + 20, matchLabel.getY() + 10);
        contentPane.add(operatingTip);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SetMarkEditFrame.this.dispose();
            }
        });
    }

    private void reduction() {
        String moduleId = this.setMarkScutcheon.getMarkElement().getModuleId();
        if (moduleId != null && "".equals(moduleId) == false) {
            Module theModule = SysService.MODULE_SERVICE.getModuleById(moduleId);
            if (theModule != null) {
                moduleSelectMenu.selectModule(theModule);
            }
        }
        if (this.setMarkScutcheon.getMarkElement().getClassificationSerial() == MarkElementName.MARK_NULL) {
            classSerialSpinner.setValue(0);
        } else {
            classSerialSpinner.setValue(this.setMarkScutcheon.getMarkElement().getClassificationSerial());
        }

        if (this.setMarkScutcheon.getMarkElement().getOperatingSerialNumber() == MarkElementName.MARK_NULL) {
            commandSpinner.setValue(0);

        } else {
            commandSpinner.setValue(this.setMarkScutcheon.getMarkElement().getOperatingSerialNumber());

        }
        if (this.setMarkScutcheon.getMarkElement().getCodeNumber() == MarkElementName.MARK_NULL) {
            codeSerialSpinner.setValue(0);
        } else {
            codeSerialSpinner.setValue(this.setMarkScutcheon.getMarkElement().getCodeNumber());
        }
        codeLabelCombobox.setSelectedCodeLabel(this.setMarkScutcheon.getMarkElement().getCodeLabelId());
    }

    private void setValue() {
        SetMarkElement setMarkElement = new SetMarkElement();

        Module module = moduleSelectMenu.getSelectedModule();
        if (module != null) {
            setMarkElement.setModuleId(module.getModuleId());
        }

        if ((int) classSerialSpinner.getValue() == 0) {
            setMarkElement.setClassificationSerial(MarkElementName.MARK_NULL);
        } else {
            setMarkElement.setClassificationSerial((int) classSerialSpinner.getValue());
        }

        if ((int) commandSpinner.getValue() == 0) {
            setMarkElement.setOperatingSerialNumber(MarkElementName.MARK_NULL);
        } else {
            setMarkElement.setOperatingSerialNumber((int) commandSpinner.getValue());
        }

        if ((int) codeSerialSpinner.getValue() == 0) {
            setMarkElement.setCodeNumber(MarkElementName.MARK_NULL);
        } else {
            setMarkElement.setCodeNumber((int) codeSerialSpinner.getValue());
        }
        setMarkElement.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        setMarkScutcheon.setMarkElement(setMarkElement);
    }

    /**
     * 获取匹配值
     */
    private void setMatchingValue() {
        int matchingValue = 0;
        if (moduleSelectMenu.getSelectedModule() != null) {
            matchingValue = matchingValue + InitMarkElement.MODULE_MATCHING_WEIGHT;
        }

        if ((int) classSerialSpinner.getValue() != 0) {
            matchingValue = matchingValue + SetMarkElement.NUMBER_OF_MODULE_SET_CLASSIFICATION_MATCHING_WEIGHT;
        }
        if ((int) commandSpinner.getValue() != 0) {
            matchingValue = matchingValue + SetMarkElement.COMMAND_SERIAL_MATCHING_WEIGHT;
        }
        if ((int) codeSerialSpinner.getValue() != 0) {
            matchingValue = matchingValue + SetMarkElement.CODE_SERIAL_MATCHING_WEIGHT;
        }
        if (codeLabelCombobox.isNoCodeLabel() == false) {
            matchingValue = matchingValue + setMarkScutcheon.getMarkElement().getCodeLabelMatchingWeight();
        }
        matchLabel.setText(matchingValue + "");
    }

    public void ok() {
        if (setMarkScutcheon != null) {
            setValue();
            setMarkScutcheon.setShowText();
            dispose();
        }
    }

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            setMatchingValue();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    };

}
