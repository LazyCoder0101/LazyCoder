package com.lazycoder.uidatasourceedit.modulemanagement.editframe;

import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
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

public class ModuleEditFrame extends LazyCoderCommonDialog {

    /**
     *
     */
    private static final long serialVersionUID = 8358412199068941740L;

    private final boolean addType = true;

    private final boolean updateType = false;

    private boolean type = addType;

    private JPanel contentPane;
    private JTextField moduleNameTextField, ordinalTextField, noteTextField;//classShowTextField = null;
    private MyButton ok, cancel;
    private ModuleEditFrameClassCombobox combobox = null;
    private JLabel clabel;

    private Module module = null;

    /**
     * 更改时用
     */
    private String oldName;


    /**
     * 添加模块
     */
    public ModuleEditFrame() {
        this.type = addType;
        init();
        setTitle("添加模块");

        combobox = new ModuleEditFrameClassCombobox();
        combobox.updateItems();
        combobox.setBounds(148, 30, 240, 30);
        contentPane.add(combobox);

        ordinalTextField.setText(1 + "");

        ok.addActionListener(addListener);
        cancel.addActionListener(addListener);

        this.setVisible(true);
    }

    /**
     * 更新模块
     *
     * @param module
     */
    public ModuleEditFrame(Module module) {
        this.type = updateType;
        this.module = module;
        init();
        setTitle("修改模块");

//        classShowTextField = new JTextField();
//        classShowTextField.setEditable(false);
//        classShowTextField.setBounds(148, 30, 100, 30);
//        contentPane.add(classShowTextField);
        combobox = new ModuleEditFrameClassCombobox();
        combobox.updateItems();
        combobox.setBounds(148, 30, 240, 30);
        contentPane.add(combobox);

        oldName = module.getModuleName();
        moduleNameTextField.setText(module.getModuleName());
//        classShowTextField.setText(module.getClassName());
        combobox.setSelectedItem(module.getClassName());
        ordinalTextField.setText(module.getIndexParam() + "");
        noteTextField.setText(module.getNote());

        ok.addActionListener(updateListener);
        cancel.addActionListener(updateListener);

        this.setVisible(true);
    }

    private ActionListener addListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == ok) {
                addModule();
                // DatabaseFrameFileMethod.generateOrCheckModuleFileStrucure(databaseName,
                // className, moduleName);
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
                updateModule();
            } else if (e.getSource() == cancel) {
                dispose();
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
//                    ModuleEditFrame frame = new ModuleEditFrame();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the frame.
     */
    public void init() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("请输入模块名：");
        label.setBounds(35, 85, 115, 30);
        contentPane.add(label);

        moduleNameTextField = new JTextField();
        moduleNameTextField.setBounds(148, 85, 240, 30);
        contentPane.add(moduleNameTextField);
        moduleNameTextField.setColumns(10);

        JLabel plabel = new JLabel("排序：");
        plabel.setBounds(35, 128, 72, 30);
        contentPane.add(plabel);

        ordinalTextField = new JTextField();
        ordinalTextField.setBounds(148, 139, 100, 30);
        contentPane.add(ordinalTextField);
        ordinalTextField.setColumns(10);

        ok = new MyButton("确定");
        ok.setBounds(70, 243, 100, 30);
        contentPane.add(ok);

        cancel = new MyButton("取消");
        cancel.setBounds(260, 243, 100, 30);
        contentPane.add(cancel);

        JLabel nlabel = new JLabel("备注：");
        nlabel.setBounds(35, 195, 60, 18);
        contentPane.add(nlabel);

        noteTextField = new JTextField();
        noteTextField.setBounds(100, 189, 300, 30);
        contentPane.add(noteTextField);

        clabel = new JLabel("分类名：");
        clabel.setBounds(35, 30, 72, 30);
        contentPane.add(clabel);

        Dimension dimension = SysUtil.SCREEN_SIZE;
        setBounds(dimension.width / 2 - 200, dimension.height / 2 - 200, 500, 360);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ModuleEditFrame.this.dispose();
            }
        });

    }

    private void addModule() {
        boolean flag = check();
        if (flag == true) {
            String className = (String) combobox.getSelectedItem(), moduleName = moduleNameTextField.getText(), note = noteTextField.getText();
            int oridinal = RealNumberUtil.convertedToInteger(ordinalTextField.getText());

            SysService.DATABASE_NAME_SERVICE.addModule(className, moduleName, oridinal, note);

            LazyCoderOptionPane.showMessageDialog(null, "已添加\"" + moduleName + "\"模块", "系统信息", JOptionPane.PLAIN_MESSAGE);
            dispose();
            updateModuleData();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void updateModule() {
        boolean flag = check();
        if (flag == true) {
            Module theModule = this.module;
            String newClassName = (String) combobox.getSelectedItem(),
                    oldClassName = theModule.getClassName(),
                    moduleName = moduleNameTextField.getText(),
                    oldModuleName = oldName;
            if (theModule != null) {
                theModule.setClassName(newClassName);
                theModule.setModuleName(moduleName);
                theModule.setIndexParam(RealNumberUtil.convertedToInteger(ordinalTextField.getText()));
                theModule.setNote(noteTextField.getText());

                try {
                    SysService.DATABASE_NAME_SERVICE.updateModule(theModule, oldModuleName, oldClassName);
                } catch (Exception e) {
                    SysService.SYS_SERVICE_SERVICE.log_error(
                            getClass()+" 更改模块出错：" + e.getMessage());
                }

                LazyCoderOptionPane.showMessageDialog(null, "已更改\"" + moduleName + "\"模块", "系统信息", JOptionPane.PLAIN_MESSAGE);
                dispose();
                updateModuleData();

            } else {
                SysService.SYS_SERVICE_SERVICE.log_error(
                        getClass()+" 更改模块信息出错-无法从数据库中获取原来的数据");
            }
        }
    }

    private void updateModuleData(){
        if (DataSourceEditHolder.moduleManagementPane != null) {
            DataSourceEditHolder.moduleManagementPane.queryModule();
        }
        if (ModuleEditPaneHolder.currentModuleSelectMenu != null) {
            ModuleEditPaneHolder.currentModuleSelectMenu.uploadModuleItems();
        }
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
                ModuleEditPaneHolder.relatedModuleInfoMenu.updateMenuItems();
            }
        }
    }

    public boolean check() {
        String className = null, moduleName = moduleNameTextField.getText().trim(), ordinalText = ordinalTextField.getText().trim();
        boolean checkResult = true;
        if ("".equals(ordinalText) == true) {//检查看看有没有写排序
            LazyCoderOptionPane.showMessageDialog(this, "请在\"排序\"填上序号（数字），所填数字越小，排序越靠前", "系统信息", JOptionPane.INFORMATION_MESSAGE);
            checkResult = false;
        } else {
            boolean flag = RealNumberUtil.isInteger(ordinalText);
            if (flag == false) {//看看填写的排序是不是整数
                checkResult = false;
                LazyCoderOptionPane.showMessageDialog(this, "请在排序输入框输入大于0的整数!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int ordinalTemp = RealNumberUtil.convertedToInteger(ordinalText);
                if (ordinalTemp < 0) {//看看填写的排序是不是大于0
                    checkResult = false;
                    LazyCoderOptionPane.showMessageDialog(this, "请在排序输入框输入大于0的整数!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if ("".equals(moduleName) == true) {//检查看看模块名有没有写
                        checkResult = false;
                        LazyCoderOptionPane.showMessageDialog(this, "请输入模块名!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (type == addType) {
                            if (combobox != null) {
                                className = (String) combobox.getSelectedItem();
                                boolean exitFlag = SysService.MODULE_SERVICE.selectExist(className, moduleName);
                                if (exitFlag == true) {
                                    LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆  这个模块名添加过了，换一个吧");
                                    checkResult = false;
                                }
                            }
                        } else if (type == updateType) {
                            if (combobox != null) {
                                if (combobox.getSelectedIndex() < 0 || combobox.getSelectedItem() == null) {
                                    LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆  还没选分类名呢，先选一个");
                                    checkResult = false;
                                } else {
                                    String newClassName = (String) combobox.getSelectedItem(),
                                            oldClassName = module.getClassName(),
                                            oldModuleName = oldName;
                                    if (newClassName.equals(oldClassName) == false || moduleName.equals(oldModuleName) == false) {//如果分类名和模块名被改了，检查一下有没有添加过这个新名字的模块

                                        className = ((String) combobox.getSelectedItem()).trim();
                                        boolean exitFlag = SysService.MODULE_SERVICE.selectExist(className, moduleName);
                                        if (exitFlag == true) {
                                            LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆  这个模块名添加过了，换一个名字吧");
                                            checkResult = false;
//                                    }else {
//                                        if (oldName.equals(moduleName) == true) {
//                                            MyOptionPane.showMessageDialog(this, "(^_−)☆  这个新起的模块名怎么和原来的一样的？");
//                                            checkResult = false;
//                                        }
                                        }
                                    }
                                }
                            }
                        }
                        if (checkResult == true) {
                            for (NotNamed temp : NotNamed.values()) {
                                if (moduleName.equals(temp.getModuleName())) {
                                    checkResult = false;
                                    LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆  不能起模块名叫\"" + moduleName + "\"，换一个吧");
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
