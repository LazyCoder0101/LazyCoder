package com.lazycoder.uidatasourceedit.previewtest;

import com.lazycoder.lazycodercommon.vo.DataSourceLabel;
import com.lazycoder.service.fileStructure.SourceGenerateFileMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrameModel;
import com.lazycoder.uicodegeneration.generalframe.ProInit;
import com.lazycoder.uicodegeneration.generalframe.SourceGenerateModelGet;
import com.lazycoder.uicodegeneration.moduleselect.ModuleSelectPane;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uiutils.component.AfToaster;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonFrame;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


public class PreviewTestProjectFrame extends LazyCoderCommonFrame {

    private final Font font = new Font("微软雅黑", Font.PLAIN, 16);
    private JPanel contentPane;
    private PreviewTestProjectShowPane showPane;
    private JScrollPane scrollPane;

    private OperatingTipButton operatingTipButton;
    private MyButton backButton;

    private JLabel label, pathLabel;

    private JMenuItem previewTestProjectItem, folderItem;

    /**
     * Create the frame.
     */
    public PreviewTestProjectFrame() {
        DataSourceEditHolder.previewTesting = true;
        setTitle("预览测试");
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

        showPane = new PreviewTestProjectShowPane() {

            @Override
            public void updateListPane() {
                // TODO Auto-generated method stub
                super.updateListPane();
                updateCurrentPath();
            }

            @Override
            protected void clickPreviewTestProLable(PreviewTestProLabel label) {
                // TODO Auto-generated method stub
                DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空

                AfToaster toaster = AfToaster.show(PreviewTestProjectFrame.this, "正在打开界面，请稍后。。。", AfToaster.Level.WARN, 4000);
                super.clickPreviewTestProLable(label);

                toaster.hidePopup();
                PreviewTestProjectFrame.this.dispose();

//                ProInit proInit = new ProInit();
//                proInit.setProjectName(label.getProName());
//                proInit.setProjectParentPath(SysFileStructure.getPreviewTestProFolder().getAbsolutePath());
//                boolean flag = checkCanUseOrNot(proInit);
//                if (flag == true) {
//                    DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空
//
//                    PreviewTestProjectFrame.this.dispose();
//                    new CodeGenerationFrame(proInit, CodeGenerationFrame.PREVIEW_TEST_PROJECT_FRAME);
//
//                    if (CodeGenerationFrameHolder.temporaryErrorList.size() > 0) {//如果在执行过程中出现问题，把问题显示出来
//                        CodeGenerationFrameHolder.showErrorListIfNeed("这预览测试项目有点异常喔   (=ω=；)");
//                    }
//                }
            }
        };
        scrollPane = new JScrollPane(showPane);
        scrollPane.setBounds(80, 60, 330, 290);
        contentPane.add(scrollPane);

        JLabel lblNewLabel = new JLabel("新建");
        lblNewLabel.setBounds(80, 380, 50, 30);
        lblNewLabel.setFont(font);
        contentPane.add(lblNewLabel);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(130, 380, 130, 30);
        contentPane.add(menuBar);

        JMenu menu = new JMenu("项目/文件夹");
        menu.setForeground(Color.LIGHT_GRAY);
        menuBar.add(menu);

        previewTestProjectItem = new JMenuItem("项目");
        previewTestProjectItem.addActionListener(listener);
        menu.add(previewTestProjectItem);

        folderItem = new JMenuItem("文件夹");
        folderItem.addActionListener(listener);
        menu.add(folderItem);

        backButton = new MyButton("返回");
        backButton.addActionListener(listener);
        backButton.setBounds(300, 380, 100, 30);
        contentPane.add(backButton);

        operatingTipButton = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "预览测试")
                        .getAbsolutePath());
        operatingTipButton.setLocation(400, 10);
        contentPane.add(operatingTipButton);

        updateCurrentPath();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(screenSize.width / 2 - 300, screenSize.height / 2 - 300, 550, 570);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PreviewTestProjectFrame.this.dispose();
                DataSourceEditHolder.previewTesting = false;
            }
        });
        setVisible(true);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == previewTestProjectItem) {
                createPreviewTestCodeGenerateProject();
            } else if (e.getSource() == folderItem) {
                buildFolder();
            } else if (e.getSource() == backButton) {
                goBack();
            }
        }
    };

    /**
     * 创建预览测试的代码生成项目
     */
    private void createPreviewTestCodeGenerateProject() {
        String projectName = LazyCoderOptionPane.showInputDialog(this,
                "请输入项目名称：\n", "创建项目", JOptionPane.PLAIN_MESSAGE);
        if (projectName != null) {
            projectName = projectName.trim();
            if (checkCodeGenerateProjectName(projectName)) {//检查项目名字，查看是否符合
                ModuleSelectPane.PreviewTestProModuleSelectFrame(projectName, showPane.getCurrentPath().getAbsolutePath());
                PreviewTestProjectFrame.this.dispose();
            }
        }
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
                File folderFile = new File(showPane.getCurrentPath().getAbsolutePath() + File.separator + fileName);
                if (folderFile.isDirectory() == true) {
                    LazyCoderOptionPane.showMessageDialog(this, "(～￣▽￣)～	看清楚喔，这个文件夹之前早就添加过了");
                } else {
                    if (FileUtil.isValidFileName(fileName) == true || StringUtil.isSpecialChar(fileName) == false) {
                        folderFile.mkdirs();
                        showPane.updateListPane();
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
        if (showPane != null) {
            String path = showPane.getCurrentPath().getAbsolutePath()
                    .replace(SysFileStructure.getPreviewTestShowFolder().getAbsolutePath(), "");
            pathLabel.setText(path);
        }
    }


    /**
     * 返回
     */
    private void goBack() {
        File folder = showPane.getCurrentPath();
        if (!SysFileStructure.getPreviewTestShowFolder().getAbsolutePath().equals(folder.getAbsolutePath())) {
            showPane.setCurrentPath(folder.getParentFile());
            showPane.updateListPane();
        }
    }

    private boolean checkCodeGenerateProjectName(String projectName) {
        boolean flag = true;
        if ("".equals(projectName)) {
            flag = false;
            LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	生成的程序总得有个名字吧，起个项目名呗");
        } else {
            if (FileUtil.isValidFileName(projectName) == true) {
                File file = new File(SysFileStructure.getPreviewTestProFolder().getAbsolutePath() + File.separator + projectName);
                if (file.isDirectory() == true) {// 看看有没有这个文件夹，有的话，不允许创建项目
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(this, "o(´^｀)o  之前创建过这个名字的项目了，换个名字呗");
                }
            } else {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	换个名称吧，这名字。。。我感觉不合适");
            }
        }
        return flag;
    }

    /**
     * 该方法只应用于 CodeGenerationFrame 里的打开项目文件方法使用
     *
     * @param proInit
     * @return
     */
    private static boolean checkCanUseOrNot(ProInit proInit) {
        boolean flag = SourceGenerateFileMethod.checkLannongProFileStrucure(proInit.getProjectParentPath(),
                proInit.getProjectName());
        if (flag == true) {
            CodeGenerationFrameModel codeGenerationFrameModel = SourceGenerateModelGet
                    .getCodeGenerationFrameModel(proInit.getProjectParentPath(), proInit.getProjectName());// 获取根模型

            DataSourceLabel dataSourceLabel = SysService.DB_CHANGE_SERVICE.getUserDataSourceInfoBy(codeGenerationFrameModel.getUseUserDBID());//查一下当前有没有这个文件对应的数据源id
            if (dataSourceLabel == null) {//没这数据源id
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	 这个项目对应的数据源，被删掉了！");
            }
        } else {
            LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	这个预览测试项目不知道怎么回事，发生损坏没法打开了",
                    "系统提示",
                    JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }


}
