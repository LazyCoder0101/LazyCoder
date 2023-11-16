package com.lazycoder.uidatasourceedit.modulemanagement.editframe;

import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.ModuleManagementPaneHolder;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.RealNumberUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ClassEditFrame extends LazyCoderCommonDialog {

    /**
     *
     */
    private static final long serialVersionUID = 7641960324240116480L;

    private final boolean addType = true;

    private final boolean updateType = false;

    private boolean type = addType;

    private JPanel contentPane;
    //	private ModuleManagementPane managementPane;
    private JTextField classNameTextField, ordinalTextField;
    private MyButton ok, cancel;

    /**
     * 更改时用
     */
    private String oldName;

    /**
     * 添加分类
     */
    public ClassEditFrame() {
        this.type = addType;
        init();
        setTitle("添加分类");
        ordinalTextField.setText(1 + "");
        ok.addActionListener(addListener);
        cancel.addActionListener(addListener);
        this.setVisible(true);
    }

    /**
     * 更新分类
     *
     * @param classification
     */
    public ClassEditFrame(TheClassification classification) {
        this.type = updateType;
        init();
        setTitle("修改分类");
        oldName = classification.getClassName();
        classNameTextField.setText(classification.getClassName());
        ordinalTextField.setText(classification.getIndexParam() + "");
        ok.addActionListener(updateListener);
        cancel.addActionListener(updateListener);
        this.setVisible(true);
    }


    /**
     * Create the frame.
     */
    public void init() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel clabel = new JLabel("请输入分类名：");
        clabel.setBounds(34, 45, 115, 18);
        contentPane.add(clabel);

        classNameTextField = new JTextField();
        classNameTextField.setBounds(148, 42, 220, 30);
        contentPane.add(classNameTextField);
        classNameTextField.setColumns(10);

        JLabel plabel = new JLabel("排序：");
        plabel.setBounds(34, 94, 72, 18);
        contentPane.add(plabel);

        ordinalTextField = new JTextField();
        ordinalTextField.setBounds(148, 91, 100, 30);
        contentPane.add(ordinalTextField);
        ordinalTextField.setColumns(10);

        ok = new MyButton("确定");
        ok.setBounds(90, 150, 100, 30);
        contentPane.add(ok);

        cancel = new MyButton("取消");
        cancel.setBounds(300, 150, 100, 30);
        contentPane.add(cancel);

        Dimension dimension = SysUtil.SCREEN_SIZE;
        setBounds(dimension.width / 2 - 200, dimension.height / 2 - 200, 480, 240);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ClassEditFrame.this.dispose();
            }
        });
    }

    private ActionListener addListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == ok) {
                boolean flag = check();
                if (flag == true) {
                    addClassification();
                    if (ModuleManagementPaneHolder.classTable != null) {
                        ModuleManagementPaneHolder.classTable.showClassList();
                    }
                    if (DataSourceEditHolder.moduleManagementPane != null) {
                        DataSourceEditHolder.moduleManagementPane.queryModule();
                    }
                    if (ModuleManagementPaneHolder.classNameComboBox != null) {
                        ModuleManagementPaneHolder.classNameComboBox.updateItems();
                    }
                    if (ModuleEditPaneHolder.currentModuleSelectMenu != null) {
                        ModuleEditPaneHolder.currentModuleSelectMenu.uploadModuleItems();
                    }
                    boolean theFlag = DataSourceEditHolder.moduleEditPane.checkModuleState();
                    if (theFlag == true) {
                        if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
                            ModuleEditPaneHolder.relatedModuleInfoMenu.updateMenuItems();
                        }
                    }
                    dispose();
                }
            } else if (e.getSource() == cancel) {
                dispose();
            }
        }
    };

    private ActionListener updateListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == ok) {
                boolean flag = check();
                if (flag == true) {
                    updateClassification();

                    if (ModuleManagementPaneHolder.classTable != null) {
                        ModuleManagementPaneHolder.classTable.showClassList();
                    }
                    if (DataSourceEditHolder.moduleManagementPane != null) {
                        DataSourceEditHolder.moduleManagementPane.queryModule();
                    }
                    if (ModuleManagementPaneHolder.classNameComboBox != null) {
                        ModuleManagementPaneHolder.classNameComboBox.updateItems();
                    }
                    if (ModuleEditPaneHolder.currentModuleSelectMenu != null) {
                        ModuleEditPaneHolder.currentModuleSelectMenu.uploadModuleItems();
                    }
                    boolean theFlag = DataSourceEditHolder.moduleEditPane.checkModuleState();
                    if (theFlag == true) {
                        if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
                            ModuleEditPaneHolder.relatedModuleInfoMenu.updateMenuItems();
                        }
                    }
                    dispose();
                }
            } else if (e.getSource() == cancel) {
                dispose();
            }
        }
    };

    private void addClassification() {
        TheClassification classification = new TheClassification();
        classification.setClassName(classNameTextField.getText());
        classification.setIndexParam(RealNumberUtil.convertedToInteger(ordinalTextField.getText()));
        SysService.CLASSIFICATION_SERVICE.addClassification(classification);
    }

    public void updateClassification() {
        TheClassification classification = new TheClassification();
        classification.setClassName(classNameTextField.getText());
        classification.setIndexParam(RealNumberUtil.convertedToInteger(ordinalTextField.getText()));
        SysService.DATABASE_NAME_SERVICE.updateClassification(classification, oldName);
    }

    public boolean check() {
        boolean checkResult = true;
        String newClassName = classNameTextField.getText().trim(), ordinalText = ordinalTextField.getText().trim();

        if ("".equals(ordinalText)) {
            LazyCoderOptionPane.showMessageDialog(this, "请在\"排序\"填上序号（数字），所填数字越小，排序越靠前", "系统信息", JOptionPane.INFORMATION_MESSAGE);
            checkResult = false;
        } else {
            if (RealNumberUtil.isInteger(ordinalText) == false) {
                checkResult = false;
                LazyCoderOptionPane.showMessageDialog(this, "请在排序输入框输入大于0的整数!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int ordinalTemp = RealNumberUtil.convertedToInteger(ordinalTextField.getText());
                if (ordinalTemp < 0) {
                    checkResult = false;
                    LazyCoderOptionPane.showMessageDialog(this, "请在排序输入框输入大于0的整数!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if ("".equals(newClassName) == true) {
                        checkResult = false;
                        LazyCoderOptionPane.showMessageDialog(this, "请输入分类名!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (type == updateType) {
                            if (oldName.equals(newClassName) == false) {
                                boolean exitFlag = SysService.CLASSIFICATION_SERVICE.selectExist(newClassName);
                                if (exitFlag == true) {
                                    LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆  这个分类名添加过了，换一个吧");
                                    checkResult = false;
                                }
                            }
                        } else if (type == addType) {
                            boolean exitFlag = SysService.CLASSIFICATION_SERVICE.selectExist(newClassName);
                            if (exitFlag == true) {
                                LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆  这个分类名添加过了，换一个吧");
                                checkResult = false;
                            }
                        }
                        if (checkResult == true) {
                            for (NotNamed temp : NotNamed.values()) {
                                if (temp.getClassName().equals(newClassName)) {
                                    checkResult = false;
                                    LazyCoderOptionPane.showMessageDialog(this,
                                            "(^_−)☆  不能起分类名叫\"" + classNameTextField.getText().trim() + "\"，换一个吧");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return checkResult;
    }

}
