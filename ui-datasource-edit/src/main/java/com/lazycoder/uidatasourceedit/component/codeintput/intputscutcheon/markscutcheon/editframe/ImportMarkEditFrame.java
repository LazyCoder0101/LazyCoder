package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.ImportMarkScutcheon;
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
import lombok.Setter;

public class ImportMarkEditFrame extends AbstractMarkEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -6609396961300494593L;

    private JPanel contentPane;
    private MySpinner importSpinner;
    private MyButton okButton, cancelButton;
    private JLabel matchLabel;

    private CodeLabelCombobox codeLabelCombobox;

    private ModuleSelectMenu moduleSelectMenu;

    @Setter
    private ImportMarkScutcheon importMarkScutcheon;

    /**
     * Create the frame.
     */
    private ImportMarkEditFrame() {
        super();
        setTitle("更改\"模块引入\"标记属性");
        setBounds(((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 290), ((int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 230), 568, 416);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel modulePanel = new JPanel();
        modulePanel.setBounds(50, 0, 427, 80);
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

        JPanel importPanel = new JPanel();
        importPanel.setBounds(50, 90, 427, 80);
        contentPane.add(importPanel);
        importPanel.setBorder(new LineBorder(Color.black));
        importPanel.setLayout(null);

        JLabel importLabel = new JLabel("引入代码序号：");
        importLabel.setBounds(14, 25, 110, 30);
        importPanel.add(importLabel);

        importSpinner = new MySpinner();
        importSpinner.setBounds(138, 25, 80, 30);
        importPanel.add(importSpinner);
        importSpinner.addChangeListener(spinnerListener);

        JPanel codeCodeLabelPanel = new JPanel();
        codeCodeLabelPanel.setBounds(50, 180, 427, 90);
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
        okButton.setBounds(238, 282, 80, 30);
        contentPane.add(okButton);

        cancelButton = new MyButton("取消");
        cancelButton.setBounds(358, 282, 80, 30);
        contentPane.add(cancelButton);

        JLabel label1 = new JLabel("匹配值：");
        label1.setBounds(64, 282, 60, 30);
        contentPane.add(label1);

        matchLabel = new JLabel("0");
        matchLabel.setFont(new Font("宋体", Font.PLAIN, 24));
        matchLabel.setBounds(123, 275, 33, 40);
        contentPane.add(matchLabel);

        OperatingTipButton operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "标记组件属性设置界面" + File.separator + "引入")
                .getAbsolutePath());
        operatingTip.setLocation(matchLabel.getX() + matchLabel.getWidth() + 20, matchLabel.getY() + 10);
        contentPane.add(operatingTip);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                ImportMarkEditFrame.this.dispose();
//            }
//        });
    }

    public ImportMarkEditFrame(ImportMarkScutcheon importMarkScutcheon) {
        this();
        this.importMarkScutcheon = importMarkScutcheon;
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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ImportMarkEditFrame frame = new ImportMarkEditFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void reduction() {
        String moduleId = this.importMarkScutcheon.getMarkElement().getModuleId();
        if (moduleId != null && "".equals(moduleId) == false) {
            Module theModule = SysService.MODULE_SERVICE.getModuleById(moduleId);
            if (theModule != null) {
                moduleSelectMenu.selectModule(theModule);
            }
        }

        if (this.importMarkScutcheon.getMarkElement().getOrdinal() == MarkElementName.MARK_NULL) {
            importSpinner.setValue(0);
        } else {
            importSpinner.setValue(this.importMarkScutcheon.getMarkElement().getOrdinal());
        }
        codeLabelCombobox.setSelectedCodeLabel(this.importMarkScutcheon.getMarkElement().getCodeLabelId());
    }

    private void setValue() {
        ImportMarkElement importMarkElement = new ImportMarkElement();

        Module module = moduleSelectMenu.getSelectedModule();
        if (module != null) {
            importMarkElement.setModuleId(module.getModuleId());
        }
        if ((int) importSpinner.getValue() == 0) {
            importMarkElement.setOrdinal(MarkElementName.MARK_NULL);
        } else {
            importMarkElement.setOrdinal((int) importSpinner.getValue());
        }
        importMarkElement.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        importMarkScutcheon.setMarkElement(importMarkElement);
    }

    private void ok() {
        if (importMarkScutcheon != null) {
            setValue();
            importMarkScutcheon.setShowText();
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
        if ((int) importSpinner.getValue() != 0) {
            matchingValue = matchingValue + ImportMarkElement.IMPPRT_SERIAL_MATCHING_WEIGHT;
        }
        if (codeLabelCombobox.isNoCodeLabel() == false) {
            matchingValue = matchingValue + importMarkScutcheon.getMarkElement().getCodeLabelMatchingWeight();
        }
        matchLabel.setText(matchingValue + "");
    }


}
