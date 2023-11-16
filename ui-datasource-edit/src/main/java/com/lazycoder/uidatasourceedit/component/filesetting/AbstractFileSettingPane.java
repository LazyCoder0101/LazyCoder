package com.lazycoder.uidatasourceedit.component.filesetting;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.AbstractGetPathDialog;
import com.lazycoder.uiutils.component.TreeUtil;
import com.lazycoder.uiutils.mycomponent.FileSettingTreeCellRenderer;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


public abstract class AbstractFileSettingPane extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -966818587224882600L;
    protected JTextField pathTextField;
    protected DefaultMutableTreeNode rootNote;
    protected File toFolder;
    private JTree tree;
    private MyButton addButton, delButton, exportButton, selectPathButton;

    private ImageIcon moreIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath()
            + File.separator + "DataSourceEdit" + File.separator+"存放路径.png");

    public AbstractFileSettingPane() {
    }

    /**
     * Create the panel.
     */
    public void init(File toFolder) {
        this.toFolder = toFolder;

        setLayout(null);

        rootNote = TreeUtil.traverseFolder(toFolder);
        tree = new JTree(rootNote);
        tree.setCellRenderer(new FileSettingTreeCellRenderer());
        tree.setRootVisible(false);
//		TreeUtil.expandTree(tree);

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setBounds(15, 15, 280, 180);
        add(scrollPane);

        addButton = new MyButton("添加文件");
        addButton.addActionListener(actionListener);
        addButton.setBounds(305, 25, 130, 30);
        add(addButton);

        delButton = new MyButton("删除全部文件");
        delButton.addActionListener(actionListener);
        delButton.setBounds(470, 25, 130, 30);
        add(delButton);

        exportButton = new MyButton("导出文件查看");
        exportButton.addActionListener(actionListener);
        exportButton.setBounds(305, 80, 130, 30);
        add(exportButton);

        pathTextField = new JTextField();
        pathTextField.setEditable(false);
        pathTextField.setBounds(355, 145, 180, 30);
        add(pathTextField);

        JLabel lblNewLabel = new JLabel("路径：");
        lblNewLabel.setBounds(305, 145, 70, 30);
        add(lblNewLabel);

        selectPathButton = new MyButton(moreIcon);
        selectPathButton.addActionListener(actionListener);
        selectPathButton.setBounds(545, 145, 60, 30);
        add(selectPathButton);
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addButton) {
                addFile();
            } else if (e.getSource() == delButton) {
                delAllFile();
            } else if (e.getSource() == exportButton) {
                exportAllFile();
            } else if (e.getSource() == selectPathButton) {
                selectPath();
            }
        }
    };

    private void addFile() {
        File file = FileUtil.selectFile(FileUtil.FILE_AND_DIRECTORY_MODEL, "请选择需要添加的附带文件");
        if (file != null) {
            File toFile = new File(toFolder.getAbsolutePath() + File.separator + file.getName());
            if (toFile.exists()) {
                LazyCoderOptionPane.showMessageDialog(null, "╮（￣﹏￣）╭⭐        亲，之前已经在这里添加过和这个一样的文件或文件夹了");
            } else {
                if (file.isDirectory() == true) {
                    try {
                        FileUtil.copyDir(file.getAbsolutePath(), toFolder.getAbsolutePath());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (file.isFile() == true) {
                    try {
                        FileUtil.fileCopyNormal(file, toFile);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            updateTree();
        }
    }

    private void delAllFile() {
        int op = LazyCoderOptionPane.showConfirmDialog(null, "真的要删掉这里所有的文件，再重新添加吗？  (〃'▽'〃)", "确认一下", JOptionPane.YES_OPTION);
        if (op == JOptionPane.YES_NO_OPTION) {
            FileUtil.delAllFile(toFolder.getAbsolutePath());
            updateTree();
        }
    }

    private void exportAllFile() {
        File file = FileUtil.selectFile(FileUtil.DIRECTORY_ONLY_MODEL,"请选择要导出的路径");
        if (file != null) {
            try {
                FileUtil.copyDirIn(toFolder.getAbsolutePath(), file.getAbsolutePath());

                File folder = new File(file.getAbsolutePath());
                File[] tempList = folder.listFiles();
                if (tempList != null) {
                    if (tempList.length == 1) {
                        FileUtil.openAndSelect(tempList[0]);
                    } else if (tempList.length > 1) {
                        Desktop.getDesktop().open(folder);
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    protected void selectPath() {
        new FileSettingGetPathDialog();
        ;
    }

    public String getSelectedPath() {
        return pathTextField.getText();
    }

    private void updateTree() {
        if (toFolder != null) {
            DefaultTreeModel treeModel = new DefaultTreeModel(TreeUtil.traverseFolder(toFolder));
            tree.setModel(treeModel);
        }
    }

    class FileSettingGetPathDialog extends AbstractGetPathDialog {

        /**
         *
         */
        private static final long serialVersionUID = -390616276712429127L;

        public FileSettingGetPathDialog() {
            // TODO Auto-generated constructor stub
            super(pathTextField.getText(), "生成源码目录");
            setVisible(true);
        }

        @Override
        protected void ok() {
            // TODO Auto-generated method stub
            pathTextField.setText(getPathStr());
            this.dispose();
        }

    }

}
