package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.InitMarkScutcheon;
import com.lazycoder.uidatasourceedit.component.component.moduleselectmenu.ModuleSelectMenu;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class InitMarkEditFrame extends AbstractMarkEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4089580150889343983L;

    private JPanel contentPane;
    private MySpinner commandSpinner, codeSerialSpinner;
    private MyButton okButton, cancelButton;
    private InitMarkScutcheon initMarkScutcheon;
    private JLabel matchLabel;
    private CodeLabelCombobox codeLabelCombobox;
    private ModuleSelectMenu moduleSelectMenu;

    public static void main(String[] args) {
        new InitMarkEditFrame().setVisible(true);
    }

    /**
     * Create the frame.
     */
    public InitMarkEditFrame() {
        super();
        setTitle("更改\"模块初始化\"标记属性");
        setBounds(((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 290), ((int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 280), 568, 532);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel modulePanel = new JPanel();
        modulePanel.setBounds(50, 10, 427, 90);
        contentPane.add(modulePanel);
        modulePanel.setLayout(null);
//		modulePanel.setBorder(new LineBorder(Color.black));

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

        JPanel commandPanel = new JPanel();
        commandPanel.setBounds(50, 110, 427, 80);
        contentPane.add(commandPanel);
        commandPanel.setLayout(null);
//		commandPanel.setBorder(new LineBorder(Color.black));

        JLabel commandLabel = new JLabel("方法序号：");
        commandLabel.setBounds(14, 25, 80, 30);
        commandPanel.add(commandLabel);

        commandSpinner = new MySpinner();
        commandSpinner.setBounds(108, 25, 80, 30);
        commandPanel.add(commandSpinner);
        commandSpinner.addChangeListener(spinnerListener);

        JPanel codeSerialPanel = new JPanel();
        codeSerialPanel.setBounds(50, 200, 427, 90);
        contentPane.add(codeSerialPanel);
        codeSerialPanel.setLayout(null);
//		codeSerialPanel.setBorder(new LineBorder(Color.black));

        JLabel codeSerialLabel = new JLabel("代码序号：");
        codeSerialLabel.setBounds(14, 25, 80, 30);
        codeSerialPanel.add(codeSerialLabel);

        codeSerialSpinner = new MySpinner();
        codeSerialSpinner.setBounds(108, 25, 80, 30);
        codeSerialPanel.add(codeSerialSpinner);
        codeSerialSpinner.addChangeListener(spinnerListener);

        JPanel codeCodeLabelPanel = new JPanel();
        codeCodeLabelPanel.setBounds(50, 300, 427, 90);
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
        okButton.setBounds(250, 423, 80, 30);
        contentPane.add(okButton);

        cancelButton = new MyButton("取消");
        cancelButton.setBounds(368, 423, 80, 30);
        contentPane.add(cancelButton);

        JLabel label1 = new JLabel("匹配值：");
        label1.setBounds(62, 409, 60, 30);
        contentPane.add(label1);

        matchLabel = new JLabel("0");
        matchLabel.setFont(new Font("宋体", Font.PLAIN, 24));
        matchLabel.setBounds(119, 402, 33, 40);
        contentPane.add(matchLabel);

        OperatingTipButton operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "标记组件属性设置界面" + File.separator + "初始化")
                .getAbsolutePath());
        operatingTip.setLocation(matchLabel.getX() + matchLabel.getWidth() + 20, matchLabel.getY() + 10);
        contentPane.add(operatingTip);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                InitMarkEditFrame.this.dispose();
            }
        });
    }

    public InitMarkEditFrame(InitMarkScutcheon initMarkScutcheon) {
        this();
        this.initMarkScutcheon = initMarkScutcheon;
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

    private void reduction() {
        String moduleId = this.initMarkScutcheon.getMarkElement().getModuleId();
        if (moduleId != null && "".equals(moduleId) == false) {
            Module theModule = SysService.MODULE_SERVICE.getModuleById(moduleId);
            if (theModule != null) {
                moduleSelectMenu.selectModule(theModule);
            }
        }

        if (this.initMarkScutcheon.getMarkElement().getInitSerialNumber() == MarkElementName.MARK_NULL) {
            commandSpinner.setValue(0);
        } else {
            commandSpinner.setValue(this.initMarkScutcheon.getMarkElement().getInitSerialNumber());
        }

        if (this.initMarkScutcheon.getMarkElement().getCodeNumber() == MarkElementName.MARK_NULL) {
            codeSerialSpinner.setValue(0);
        } else {
            codeSerialSpinner.setValue(this.initMarkScutcheon.getMarkElement().getCodeNumber());
        }
        codeLabelCombobox.setSelectedCodeLabel(this.initMarkScutcheon.getMarkElement().getCodeLabelId());
    }

    private void setValue() {
        InitMarkElement initMarkElement = new InitMarkElement();

        Module module = moduleSelectMenu.getSelectedModule();
        if (module != null) {
            initMarkElement.setModuleId(module.getModuleId());
        }

        if ((int) commandSpinner.getValue() == 0) {
            initMarkElement.setInitSerialNumber(MarkElementName.MARK_NULL);
        } else {
            initMarkElement.setInitSerialNumber((int) commandSpinner.getValue());
        }

        if ((int) codeSerialSpinner.getValue() == 0) {
            initMarkElement.setCodeNumber(MarkElementName.MARK_NULL);
        } else {
            initMarkElement.setCodeNumber((int) codeSerialSpinner.getValue());
        }
        initMarkElement.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        initMarkScutcheon.setMarkElement(initMarkElement);
    }

    public void ok() {
        if (initMarkScutcheon != null) {
            setValue();
            initMarkScutcheon.setShowText();
            dispose();
        }
    }


    /**
     * 获取匹配值
     */
    private void setMatchingValue() {
        int matchingValue = 0;
        if (moduleSelectMenu.getSelectedModule() != null) {
            matchingValue = matchingValue + InitMarkElement.MODULE_MATCHING_WEIGHT;
        }

        if ((int) commandSpinner.getValue() != 0) {
            matchingValue = matchingValue + InitMarkElement.COMMAND_SERIAL_MATCHING_WEIGHT;
        }
        if ((int) codeSerialSpinner.getValue() != 0) {
            matchingValue = matchingValue + InitMarkElement.CODE_SERIAL_MATCHING_WEIGHT;
        }
        if (codeLabelCombobox.isNoCodeLabel() == false) {
            matchingValue = matchingValue + initMarkScutcheon.getMarkElement().getCodeLabelMatchingWeight();
        }
        matchLabel.setText(matchingValue + "");
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
