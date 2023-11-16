package com.lazycoder.uidatasourceedit;

import com.lazycoder.database.model.SysParam;
import com.lazycoder.lazycoderbaseconfiguration.ClientVersionHandlingManager;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.DatabaseFrameFileMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.DBChangeServiceImpl;
import com.lazycoder.service.vo.UserDataSourceIdentifyFile;
import com.lazycoder.uiutils.component.AfToaster;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonFrame;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.UUIDUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


public class DataSourceClassificationEditFrame extends LazyCoderCommonFrame {

    /**
     *
     */
    private static final long serialVersionUID = -2791423650132808171L;
    private final Font font = new Font("微软雅黑", Font.PLAIN, 16);
    private JPanel contentPane;
    private DataSourceClassificationFolderPane folderPane;
    private JScrollPane scrollPane;

    private OperatingTipButton operatingTipButton;
    private MyButton backButton, importDataSourceButton;

    private JLabel label, pathLabel;

    private JMenuItem dbSourceItem, folderItem;

    /**
     * Create the frame.
     */
    public DataSourceClassificationEditFrame() {
        setTitle("系统数据源管理");
        Dimension screenSize = SysUtil.SCREEN_SIZE;

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        label = new JLabel("当前路径：");
        label.setFont(font);
        label.setBounds(15, 5, 80, 30);
        contentPane.add(label);

        pathLabel = new JLabel("");
        pathLabel.setForeground(Color.DARK_GRAY);
        pathLabel.setFont(font);
        pathLabel.setBounds(90, 5, 300, 30);
        contentPane.add(pathLabel);

        folderPane = new DataSourceClassificationFolderPane() {

            private static final long serialVersionUID = 3261922724513761512L;

            @Override
            public void updateListPane() {
                // TODO Auto-generated method stub
                super.updateListPane();
                updateCurrentPath();
            }

            @Override
            protected void clickDBLable(DBLabel label) {
                // TODO Auto-generated method stub
                DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空

                AfToaster toaster = AfToaster.show(DataSourceClassificationEditFrame.this, "正在打开界面，请稍后。。。", AfToaster.Level.WARN, 4000);

//				DefaultToast toast = SysUtil.showToast(DataSourceClassificationEditFrame.this, ToastStatus.INFO,
//						"正在打开界面，请稍后。。。");
                super.clickDBLable(label);
//				toast.dispose();

                toaster.hidePopup();
                DataSourceClassificationEditFrame.this.dispose();


                if (DataSourceEditHolder.temporaryErrorList.size() > 0) {//如果在执行过程中出现问题，把问题显示出来
                    DataSourceEditHolder.showErrorListIfNeed("这数据源有点异常喔   (=ω=；)");
                    if (DataSourceEditHolder.dataSourceEditFrame != null) {
                        DataSourceEditHolder.dataSourceEditFrame.setSelectedTab(1);//跳到必填模板、可选模板的数据编辑页面让用户自己处理
                    }
                }
            }
        };
        scrollPane = new JScrollPane(folderPane);
        scrollPane.setBounds(80, 60, 330, 290);
        contentPane.add(scrollPane);

        JLabel lblNewLabel = new JLabel("新建");
        lblNewLabel.setBounds(80, 380, 50, 30);
        lblNewLabel.setFont(font);
        contentPane.add(lblNewLabel);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(130, 380, 130, 30);
        contentPane.add(menuBar);

        JMenu menu = new JMenu("数据源/文件夹");
        menu.setForeground(Color.LIGHT_GRAY);
        menuBar.add(menu);

        dbSourceItem = new JMenuItem("数据源");
        dbSourceItem.addActionListener(listener);
        menu.add(dbSourceItem);

        folderItem = new JMenuItem("文件夹");
        folderItem.addActionListener(listener);
        menu.add(folderItem);

        backButton = new MyButton("返回");
        backButton.addActionListener(listener);
        backButton.setBounds(300, 380, 100, 30);
        contentPane.add(backButton);

        importDataSourceButton = new MyButton("在此导入数据源");
        importDataSourceButton.setBounds(80, 435, 150, 30);
        importDataSourceButton.addActionListener(listener);
        contentPane.add(importDataSourceButton);

        operatingTipButton = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源管理操作提示")
                        .getAbsolutePath());
        operatingTipButton.setLocation(400, 10);
        contentPane.add(operatingTipButton);

        updateCurrentPath();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(screenSize.width / 2 - 300, screenSize.height / 2 - 300, 550, 570);
        setVisible(true);
        folderPane.checkAndDelPossibleFile();

    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == dbSourceItem) {
                buildDataSource();
            } else if (e.getSource() == importDataSourceButton) {
                importDataSource();
            } else if (e.getSource() == folderItem) {
                buildFolder();
            } else if (e.getSource() == backButton) {
                goBack();
            }
        }
    };

    /**
     * 新建数据源
     */
    private void buildDataSource() {
        folderPane.checkAndDelPossibleFile();
        String dataSourceName = LazyCoderOptionPane.showInputDialog(this,
                "请输入数据源名称：\n", "添加数据源", JOptionPane.PLAIN_MESSAGE);
        if (DBChangeServiceImpl.checkAddDataSourceName(folderPane.getCurrentPath().getAbsolutePath(),
                dataSourceName)) {//检查数据源名字，查看是否符合
            String dbId = UUIDUtil.getUUID();
            String cliVer = System.getProperty("clientVersion");
            DatabaseFrameFileMethod.generateDBFileInFileListPath(folderPane.getCurrentPath().getAbsolutePath(),
                    dataSourceName);
            DatabaseFrameFileMethod.generateOrCheckDBFileStrucure(dataSourceName, dbId, cliVer);
            DatabaseFrameFileMethod.generateDBFile(dataSourceName);
            folderPane.updateListPane();

            SysService.DB_CHANGE_SERVICE.setUserDBID(dataSourceName, dbId);//创建数据库后，对该数据库重新分配一个id作为标志
            SysService.SYS_PARAM_SERVICE.updateSysParam(SysParam.DS_CLI_VER, cliVer);//这里不采用 updateDataSourceClientVersion() 方法，要用事务管理避免出错
        }
        SysService.DB_CHANGE_SERVICE.changeSYSDB();
    }

    /**
     * 新建文件夹
     */
    private void buildFolder() {
        String fileName = LazyCoderOptionPane.showInputDialog(this, "请输入文件夹名称：\n", "新建文件夹", JOptionPane.PLAIN_MESSAGE);
        if (fileName != null) {
            if ("".equals(fileName.trim()) == true) {
                LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆	什么都不写。。。那我不在这创建文件夹了");
            } else {
                File folderFile = new File(folderPane.getCurrentPath().getAbsolutePath() + File.separator + fileName);
                if (folderFile.isDirectory() == true) {
                    LazyCoderOptionPane.showMessageDialog(this, "(～￣▽￣)～	看清楚喔，这个文件夹之前早就添加过了");
                } else {
                    if (FileUtil.isValidFileName(fileName) == true || StringUtil.isSpecialChar(fileName) == false) {
                        folderFile.mkdirs();
                        folderPane.updateListPane();
                    } else {
                        LazyCoderOptionPane.showMessageDialog(this, "(￣▽￣)／	换个名称吧，这名字。。。我感觉不合适");
                    }
                }
            }
        }
    }

    /**
     * 更新当前路径
     */
    private void updateCurrentPath() {
        if (folderPane != null) {
            String path = folderPane.getCurrentPath().getAbsolutePath()
                    .replace(SysFileStructure.getFileListFolder().getAbsolutePath(), "");
            pathLabel.setText(path);
        }
    }

    /**
     * 导入外部的数据源文件
     */
    private void importDataSource() {
        File file = FileUtil.selectFile(FileUtil.DIRECTORY_ONLY_MODEL, "请选择需要导入的懒农数据源文件");
        if (file != null) {
            boolean flag = DatabaseFrameFileMethod.checkDBFileStrucure(file);
            if (flag == true) {
                File identifyFileFile = DatabaseFileStructure.getDBIdentifyFile(file);//从标志文件那里再获取数据源名称
//                String content = FileUtil.getFileContent(identifyFileFile.getAbsolutePath());
//                UserDataSourceIdentifyFile userDataSourceIdentifyFile = JsonUtil.toBean(content, UserDataSourceIdentifyFile.class);
                UserDataSourceIdentifyFile userDataSourceIdentifyFile = JsonUtil.fileToBean(
                        identifyFileFile.getAbsolutePath(), UserDataSourceIdentifyFile.class
                );
                String originalDSName = userDataSourceIdentifyFile.getDbName();
                String cliVer = userDataSourceIdentifyFile.getCliVer();
                if (ClientVersionHandlingManager.dataSourceVersionDetection(cliVer)) {

                    String newDSName = (String) LazyCoderOptionPane.showInputDialog(this, "(*^▽^*)  导入数据源的名字要改不，要的话改一下",
                            "系统提示", JOptionPane.PLAIN_MESSAGE, null, null, originalDSName);
                    if (newDSName != null) {
                        if ("".equals(newDSName.trim())) {
                            LazyCoderOptionPane.showMessageDialog(this, "(^_−)☆	什么都不写吗。。。那等你想好名字再导入吧");
                        } else {
                            if (DBChangeServiceImpl.checkAddDataSourceName(
                                    folderPane.getCurrentPath().getAbsolutePath(), newDSName)) {// 检查能不能用这个文件名作为数据源名
                                flag = SysService.DB_CHANGE_SERVICE.importUserDataSource(file, newDSName, originalDSName, folderPane.getCurrentPath().getAbsolutePath());
                                if (flag == true) {
                                    folderPane.updateListPane();
                                    LazyCoderOptionPane.showMessageDialog(this, "(*^▽^*) 已经把数据源导入到系统了");
                                }
                            }
                        }
                    }
                }else {//在这里写不通过版本检测的处理

                }
            }
        }
    }

    /**
     * 返回
     */
    private void goBack() {
        File folder = folderPane.getCurrentPath();
        if (!SysFileStructure.getFileListFolder().getAbsolutePath().equals(folder.getAbsolutePath())) {
            folderPane.setCurrentPath(folder.getParentFile());
            folderPane.updateListPane();
        }
    }


}
