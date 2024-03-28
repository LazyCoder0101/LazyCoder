package com.lazycoder.uidatasourceedit.previewtest;

import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFrameFileMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.DBChangeServiceImpl;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrame;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrameModel;
import com.lazycoder.uicodegeneration.generalframe.ProInit;
import com.lazycoder.uicodegeneration.generalframe.SourceGenerateModelGet;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uiutils.mycomponent.MyIconLabel;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class PreviewTestProjectShowPane extends JPanel {

    private final ImageIcon previewTestProIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() +
            File.separator + "DataSourceEdit" + File.separator
            + "PreviewTestProjectShowPane" + File.separator + "previewTestProject.png"),

    gray_previewTestProIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() +
            File.separator + "DataSourceEdit" + File.separator
            + "PreviewTestProjectShowPane" + File.separator + "gray_previewTestProject.png"),

    folderIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
            + "PreviewTestProjectShowPane" + File.separator + "folder.png");

    private JPopupMenu popupMenu;

    private JMenuItem renameMenuItem, delMenuItem;

    private Box vBox;

    @Getter
    @Setter
    private File currentPath = null;

    /**
     * Create the panel.
     */
    public PreviewTestProjectShowPane() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        setLayout(fl);
        vBox = Box.createVerticalBox();
        add(vBox);

        popupMenu = new JPopupMenu();
        renameMenuItem = new JMenuItem("重命名");
        renameMenuItem.addActionListener(menuListener);

        delMenuItem = new JMenuItem("删除");
        delMenuItem.addActionListener(menuListener);

        popupMenu.add(renameMenuItem);
        popupMenu.add(delMenuItem);

        currentPath = SysFileStructure.getPreviewTestShowFolder();
        updateListPane();
    }

    /**
     * 删除某个测试项目
     *
     * @param file
     */
    private static void delPreviewTestPro(File file) {
        String delName = file.getName();

        Boolean flag = false;
        if (DatabaseFrameFileMethod.isProFileInPreviewTestProFile(file) == true) {
            flag = true;
        }
        if (flag) {
            FileUtil.delFolder(file.getAbsolutePath());
            File proFile = SysFileStructure.getPreviewTestProFolder(delName);
            FileUtil.delFolder(proFile.getAbsolutePath());
        }
    }

    private static void delFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File temp : files) {
                if (temp.isDirectory()) {
                    if (DatabaseFrameFileMethod.isProFileInPreviewTestProFile(temp) == true) {
                        delPreviewTestPro(temp);
                    } else {
                        delFolder(temp);
                    }
                }
            }
        }
    }

    public void updateListPane() {
        vBox.removeAll();
        if (currentPath != null) {
            PreviewTestLabel label;
            File previewTestProFolder = SysFileStructure.getPreviewTestProFolder();
//            File proFile;
            CodeGenerationFrameModel codeGenerationFrameModel;
            String userDBId, proNameTemp;

            File[] fileList = currentPath.listFiles();
            if (fileList != null) {
                for (File temp : fileList) {
                    if (DatabaseFrameFileMethod.isProFileInPreviewTestProFile(temp) == true) {
                        proNameTemp = temp.getName();

                        codeGenerationFrameModel = SourceGenerateModelGet
                                .getCodeGenerationFrameModel(previewTestProFolder.getAbsolutePath(), proNameTemp);// 获取根模型
                        if (codeGenerationFrameModel == null) {
                            SysService.SYS_SERVICE_SERVICE.log_error("破损文件（上次应该没保存成功）：" + temp.getAbsolutePath());
                        } else {
                            userDBId = codeGenerationFrameModel.getUseUserDBID();
                            label = new PreviewTestProLabel(proNameTemp, userDBId);
                            vBox.add(label);
                        }

                    } else {
                        label = new FolderLabel(temp.getName());
                        vBox.add(label);
                    }
                }
            }
        }
        updateUI();
        repaint();

//        Window window = SwingUtilities.getWindowAncestor(this);
//        if (window != null) {
//            window.validate();
//        }
    }

    /**
     * 重命名文件夹
     */
    private void renameFolder(FolderLabel label) {
        String newName = LazyCoderOptionPane.showInputDialog(null, "请输入新的文件夹名：（原来名称：" + label.getFolderName() + "）\n", "更改文件夹",
                JOptionPane.PLAIN_MESSAGE);
        if (newName != null) {
            newName = newName.trim();
            if ("".equals((newName)) == true) {
                LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆	什么都不写。。。那我就当没事发生了");
            } else {
                File oldFile = new File(getCurrentPath().getAbsolutePath() + File.separator + label.getFolderName()),
                        newFile = new File(getCurrentPath().getAbsolutePath() + File.separator + newName);
                if (newFile.isDirectory() == true) {
                    LazyCoderOptionPane.showMessageDialog(null, "(～￣▽￣)～	看清楚喔，这个文件夹之前早就添加过了");
                } else {
                    if (FileUtil.isValidFileName(newName) == true || StringUtil.isSpecialChar(newName) == false) {
                        oldFile.renameTo(newFile);
                        updateListPane();
                    } else {
                        LazyCoderOptionPane.showMessageDialog(null, "(￣▽￣)／	换个名称吧，这名字。。。我感觉不合适");
                    }
                }
            }
        }
    }

    /**
     * 重命名预览测试项目
     *
     * @param label
     */
    private void renamePreviewTestProject(PreviewTestProLabel label) {
        String newName = LazyCoderOptionPane.showInputDialog(null, "请输入新的项目名：（原来名称：" + label.getProName() + "）\n", "更改预览测试项目名称",
                JOptionPane.PLAIN_MESSAGE);
        if (newName != null) {//这里要检测是不是符合文件名，有没有对应数据源
            newName = newName.trim();
            if (("".equals(newName)) == true) {
                LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆	什么都不写。。。那我就当没事发生了");
            } else if (newName.equals(label.getProName()) == false) {
                if (DBChangeServiceImpl.checkAddDataSourceName(null, newName)) {
                    String oldName = label.getProName();

                    // 更改显示文件夹对应文件
                    File oldShowFile = new File(getCurrentPath().getAbsolutePath() + File.separator + oldName),
                            newShowFile = new File(getCurrentPath().getAbsolutePath() + File.separator + newName),

                            // 更改预览测试路径，对应项目文件夹
                            oldProFile = SysFileStructure.getPreviewTestProFolder(oldName),
                            newProFile = SysFileStructure.getPreviewTestProFolder(newName);

                    if (newShowFile.isDirectory() == true || newProFile.isDirectory() == true) {
                        LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆	换个名字吧，起过这名字了");
                    } else {//这里才是真正改预览测试项目的操作
                        boolean flag1 = oldShowFile.renameTo(newShowFile), flag2 = false;
                        if (flag1) {
                            flag2 = oldProFile.renameTo(newProFile);
                        }
                        if (!flag1 || !flag2) {
                            LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ 不知道为什么，改不了这数据源的名字了！");
                            newShowFile.renameTo(oldShowFile);
                        }
                        updateListPane();
                    }
                }
            }
        }
    }

    /**
     * 删除某个预览测试项目
     *
     * @param label
     */
    private void delPreviewTestPro(PreviewTestProLabel label) {
        String delName = label.getProName();
        int n = LazyCoderOptionPane.showConfirmDialog(null, "真的要删除\"" + delName + "\"这个项目吗?", "确认一下",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.OK_OPTION) {
            File file = new File(getCurrentPath().getAbsolutePath() + File.separator + delName);
            delPreviewTestPro(file);
            updateListPane();
        }
    }

    private void delFolder(FolderLabel label) {
        String delName = label.getFolderName();
        int n = LazyCoderOptionPane.showConfirmDialog(null, "(￣ェ￣;)  真的要删除\"" + delName + "\"这个文件夹吗，\n删掉的话，文件夹里面的预览测试项目也会一起删掉的喔",
                "确认一下", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.OK_OPTION) {
            File delFolder = new File(getCurrentPath().getAbsolutePath() + File.separator + delName);

            delFolder(delFolder);
            FileUtil.delFolder(delFolder.getAbsolutePath());
            //File delShowFolder = SysFileStructure.getPreviewTestProFolder()
            updateListPane();
        }

//        String path = showPane.getCurrentPath().getAbsolutePath()
//                .replace(SysFileStructure.getPreviewTestShowFolder().getAbsolutePath(), "");
    }

    private ActionListener menuListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == renameMenuItem) {
                if (popupMenu.getInvoker() instanceof FolderLabel) {
                    renameFolder((FolderLabel) popupMenu.getInvoker());
                } else if (popupMenu.getInvoker() instanceof PreviewTestProLabel) {
                    renamePreviewTestProject((PreviewTestProLabel) popupMenu.getInvoker());
                }
            } else if (e.getSource() == delMenuItem) {
                if (popupMenu.getInvoker() instanceof FolderLabel) {
                    delFolder((FolderLabel) popupMenu.getInvoker());
                } else if (popupMenu.getInvoker() instanceof PreviewTestProLabel) {
                    delPreviewTestPro((PreviewTestProLabel) popupMenu.getInvoker());
                }
            }
        }
    };

    private MouseAdapter previewTestProjectAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                PreviewTestProLabel label = (PreviewTestProLabel) e.getSource();
                clickPreviewTestProLable(label);
            }
        }
    };

    private MouseAdapter folderAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                FolderLabel label = (FolderLabel) e.getSource();
                currentPath = new File(currentPath.getAbsolutePath() + File.separator + label.getFolderName());
                updateListPane();
            }
        }
    };

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.isMetaDown()) {// 检测鼠标右键单击
                Component label = e.getComponent();
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    };

    /**
     * 点击对应项目的操作
     */
    protected void clickPreviewTestProLable(PreviewTestProLabel label) {
        if (label.isCurrentDataSourceOrNot()) {//是当前这个数据源的项目，可以打开

            File proFolder = SysFileStructure.getPreviewTestProFolder(label.getProName());
            ProInit proInit = new ProInit();
            proInit.setProjectName(label.getProName());
            proInit.setProjectParentPath(proFolder.getParent());
            DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空
            new CodeGenerationFrame(proInit, CodeGenerationFrame.PREVIEW_TEST_PROJECT_FRAME);
            if (CodeGenerationFrameHolder.temporaryErrorList.size() > 0) {//如果在执行过程中出现问题，把问题显示出来
                CodeGenerationFrameHolder.showErrorListIfNeed("打开这个预览项目时有点异常喔   (=ω=；)");
            }

        } else {//不是当前数据源的项目，给出提示
            DataSourceEditHolder.previewTesting = false;
            LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆	这个项目不是现在这个数据源的！");
        }
    }

    class PreviewTestProLabel extends PreviewTestLabel {

        @Getter
        private boolean currentDataSourceOrNot = true;

        @Getter
        private String userDBId = null;

        public PreviewTestProLabel(String text, String userDBId) {
            super(text,
                    GeneralHolder.currentUserDataSourceLabel != null && userDBId.equals(GeneralHolder.currentUserDataSourceLabel.getDbId()) ?
                            previewTestProIcon : gray_previewTestProIcon);
            this.currentDataSourceOrNot = GeneralHolder.currentUserDataSourceLabel != null &&
                    userDBId.equals(GeneralHolder.currentUserDataSourceLabel.getDbId()) ?
                    true : false;
            this.userDBId = userDBId;
            addMouseListener(previewTestProjectAdapter);
            // TODO Auto-generated constructor stub
        }

        public String getProName() {
            return textLabel.getText();
        }

    }

    class FolderLabel extends PreviewTestLabel {

        public FolderLabel(String text) {
            super(text, folderIcon);
            addMouseListener(folderAdapter);
            // TODO Auto-generated constructor stub
        }

        public String getFolderName() {
            return textLabel.getText();
        }

    }

    class PreviewTestLabel extends MyIconLabel {

        private Dimension dimension = new Dimension(315, 50);

        public PreviewTestLabel(String text, ImageIcon icon) {
            // TODO Auto-generated constructor stub
            init(text, icon);
            setPreferredSize(dimension);
            addMouseListener(mouseAdapter);
        }

    }

}
