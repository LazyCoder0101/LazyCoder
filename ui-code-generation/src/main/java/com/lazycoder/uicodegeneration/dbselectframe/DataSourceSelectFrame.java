package com.lazycoder.uicodegeneration.dbselectframe;

import com.lazycoder.lazycodercommon.vo.DataSourceLabel;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.moduleselect.ModuleSelectPane;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonFrame;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class DataSourceSelectFrame extends LazyCoderCommonFrame {

    /**
     *
     */
    private static final long serialVersionUID = 5735924802205937966L;

    private final Font font = new Font("微软雅黑", Font.PLAIN, 16);

    private JPanel contentPane;

    private DataSourceSelectPane dataSourceSelectPane;

    private JTextField proNameTextField, selectedDBTextField, putPathTextField;

    private MyButton setPathBt, okbt, cancelBt;

    private OperatingTipButton operatingTip;

    private final ImageIcon proPathIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration"
            + File.separator + "项目路径.png");

    /**
     * Create the frame.
     */
    public DataSourceSelectFrame() {
        setTitle("懒农");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = SysUtil.SCREEN_SIZE;
        setBounds(dimension.width / 2 - 400, dimension.height / 2 - 380, 810, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("数据源选择");
        label.setBounds(130, 15, 120, 30);
        contentPane.add(label);

        JLabel dbNameLabel = new JLabel("数据源名称");
        dbNameLabel.setFont(font);
        dbNameLabel.setBounds(140, 50, 180, 30);
        contentPane.add(dbNameLabel);

        JLabel dbLabel = new JLabel("数据源位置");
        dbLabel.setFont(font);
        dbLabel.setBounds(130 + 180 + 20, 50, 190, 30);
        contentPane.add(dbLabel);

        dataSourceSelectPane = new DataSourceSelectPane();
        dataSourceSelectPane.setCellRenderer(new DataSourceSelectListCellRenderer(dataSourceSelectPane, this));
        JScrollPane scrollPane = new JScrollPane(dataSourceSelectPane);
        scrollPane.setBounds(130, 80, 520, 300);
        contentPane.add(scrollPane);

        JLabel selectedDBLabel = new JLabel("当前选中数据源：");
        selectedDBLabel.setBounds(130, 420, 140, 30);
        selectedDBLabel.setFont(font);
        contentPane.add(selectedDBLabel);

        selectedDBTextField = new JTextField();
        selectedDBTextField.setFont(font);
        selectedDBTextField.setEditable(false);
        selectedDBTextField.setBounds(280, 420, 300, 30);
        contentPane.add(selectedDBTextField);

        JLabel proNameLabel = new JLabel("项目名：");
        proNameLabel.setFont(font);
        proNameLabel.setBounds(130, 470, 70, 30);
        contentPane.add(proNameLabel);

        proNameTextField = new JTextField();
        proNameTextField.setFont(font);
        proNameTextField.setBounds(210, 470, 200, 30);
        contentPane.add(proNameTextField);

        JLabel pathLabel = new JLabel("存放位置：");
        pathLabel.setFont(font);
        pathLabel.setBounds(130, 520, 100, 30);
        contentPane.add(pathLabel);

        putPathTextField = new JTextField();
        putPathTextField.setEditable(false);
        putPathTextField.setFont(font);
        putPathTextField.setBounds(210, 520, 300, 30);
        contentPane.add(putPathTextField);
        String lastSetProPath = SysService.SYS_PARAM_SERVICE.getLastSetProPath();
        if (lastSetProPath != null) {
            putPathTextField.setText(lastSetProPath);
        }

        setPathBt = new MyButton(proPathIcon);
        setPathBt.setToolTipText("设置项目生成路径");
        setPathBt.addActionListener(listener);
        setPathBt.setBounds(550, 520, 80, 30);
        contentPane.add(setPathBt);

        okbt = new MyButton("确定");
        okbt.addActionListener(listener);
        okbt.setBounds(180, 590, 100, 30);
        contentPane.add(okbt);

        cancelBt = new MyButton("取消");
        cancelBt.addActionListener(listener);
        cancelBt.setBounds(450, 590, 100, 30);
        contentPane.add(cancelBt);

        operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "生成程序" + File.separator + "数据源选择界面")
                .getAbsolutePath());
        operatingTip.setLocation(proNameTextField.getX() + proNameTextField.getWidth() + 30, proNameTextField.getY() + 3);
        contentPane.add(operatingTip);

        closingWindow();
        setVisible(true);
    }

    public DataSourceSelectFrame(String proName, String putPath) {
        this();
        proNameTextField.setText(proName);
        putPathTextField.setText(putPath);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == setPathBt) {
                setPutPath();
            } else if (e.getSource() == okbt) {
                ok();
            } else if (e.getSource() == cancelBt) {
                cancel();
            }
        }
    };

    public String getSelectedDataSourceName() {
        return selectedDBTextField.getText();
    }

    public void setSelectedDataSourceName(String selectedDataSourceName) {
        if (selectedDataSourceName == null) {
            selectedDBTextField.setText("");
        } else {
            selectedDBTextField.setText(selectedDataSourceName);
        }
    }

    private void setPutPath() {
        File folder = FileUtil.selectFile(FileUtil.DIRECTORY_ONLY_MODEL, "请选择项目存放路径");
        if (folder != null) {
            putPathTextField.setText(folder.getAbsolutePath());
            SysService.SYS_PARAM_SERVICE.setLastProPath(folder.getAbsolutePath());
        }
    }

    private void ok() {
        if (check() == true) {
            DataSourceLabel sourceLabel = new DataSourceLabel();
            sourceLabel.setDataSourceName(dataSourceSelectPane.getSelectedDataSourceInfo().getDataSourceName());
            sourceLabel.setDbId(dataSourceSelectPane.getSelectedDataSourceInfo().getDbId());

            GeneralHolder.changeUserDB(sourceLabel);
//            if (SysService.MODULE_SERVICE.checkCanUseOrNot()) {
            GeneralHolder.currentUserDataSourceLabel = sourceLabel;
            String proName = proNameTextField.getText().trim(), putPath = putPathTextField.getText().trim();

            this.dispose();
            ModuleSelectPane.ModuleSelectFrame(proName, putPath);
//            } else {
//                GeneralHolder.changeSysDB();
//                GeneralHolder.currentUserDataSourceLabel = null;
//            }
        }
    }

    private boolean check() {
        boolean flag = true;
        String projectParentPath = putPathTextField.getText().trim(), projectName = proNameTextField.getText().trim();

        if (dataSourceSelectPane.getSelectedDataSourceInfo() == null) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	你还没选要用哪个数据源生成程序喔");
        } else {
            if ("".equals(projectName)) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	生成的程序总得有个名字吧，起个项目名呗");
            } else {
                if (FileUtil.isValidFileName(projectName) == true) {
                    if (projectParentPath == null || projectParentPath.equals("")) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	生成的程序要放在哪个文件夹啊，选个存放位置呗");
                    } else {
                        File file = SourceGenerateFileStructure.getTheProjectPath(projectParentPath, projectName);
                        if (file.isDirectory() == true) {// 看看有没有这个文件夹，有的话，不允许创建项目
                            flag = false;

                            File proFlagFile = SourceGenerateFileStructure.getLannongProFlagFile(projectParentPath,
                                    projectName);// 做个简单的判断，根据有没有懒农的标记文件来判断是不是懒农生成的项目
                            if (proFlagFile.exists() == true) {
                                LazyCoderOptionPane.showMessageDialog(this, "o(´^｀)o  之前已经在\"" + projectParentPath + "\"创建过\""
                                        + projectName + "\"这个项目了，换个名字呗");
                            } else {
                                LazyCoderOptionPane.showMessageDialog(this, "o(´^｀)o  在\"" + projectParentPath
                                        + "\"里面，有个文件夹也叫\"" + projectName + "\"，换个名字呗");
                            }
                        }
                    }
                } else {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	换个名称吧，这名字。。。我感觉不合适");
                }
            }
        }
        return flag;
    }

    private void cancel() {
        System.exit(0);
    }

    /**
     * 关闭窗口
     */
    private void closingWindow() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int show = LazyCoderOptionPane.showConfirmDialog(null, "(oﾟ▽ﾟ)o  亲，确定要退出系统吗？", "系统提示", JOptionPane.YES_NO_OPTION);
                if (show == JOptionPane.YES_OPTION) {
                    System.exit(0);// 正常退出
                }
            }

        });
    }

}
