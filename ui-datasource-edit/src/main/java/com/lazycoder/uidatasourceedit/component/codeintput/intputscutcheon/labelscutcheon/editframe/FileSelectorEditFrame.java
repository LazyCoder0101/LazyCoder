package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FileSelectorControlLabel;
import com.lazycoder.uiutils.component.TreeUtil;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyNode;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class FileSelectorEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = -7467176019643413702L;


    private JScrollPane eamShowPaneJSP; // 显示面板滑动面板
    private JTextField eamPathText, // 路径显示
            fileSeparatorField, defaultFileField, codeInputShieldingPathTextField, codeInputPathTextField;
    private MyButton eamPutInModel, defautFileBT, cancelDefautFileBT; // 直接放进面板、确定
    private MyNode eamTreeROOT;
    private JTree eamTree;
    private String message;

    private JCheckBox copyFileToSource;

    private JPopupMenu folderMenu, setPathMenu;
    private JMenuItem delFolder, addFolder, setFilePathMenuItem, setCodeInputShieldingPathMenuItem;

    private FileSelectorControlLabel controlLabel = null;
    private OperatingTipButton operatingTip;

    /**
     * @param tittle  窗口标题
     * @param message 按下确认键后弹出的消息！
     */
    private FileSelectorEditFrame(String tittle, String message) {
        init();
        setTitle(tittle);
        this.message = message;
        this.setVisible(true);
    }

    public FileSelectorEditFrame(FileSelectorControlLabel controlLabel) {
        super();
        this.controlLabel = controlLabel;
        this.setTitle("更改文件选择组件\"" + this.controlLabel.getControl().getThisName() + "\"属性");
        init();
        this.message = "已设置" + this.controlLabel.getControl().getThisName();
        setText(controlLabel);

        this.setVisible(true);
    }

    private void init() {
        getContentPane().setLayout(null);

        JLabel eamShowLabel = new JLabel("路径设置");
        eamShowLabel.setBounds(40, 20, 180, 20);
        getContentPane().add(eamShowLabel);

        File sysDataFileFolder = SysFileStructure.getDataFileFolder();
        eamTreeROOT = traverseFolder(
                DatabaseFileStructure.getTemplateFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName()));
        eamTreeROOT.setUserObject("源码生成目录");

        eamTree = new JTree(eamTreeROOT);
        eamTree.setShowsRootHandles(true); // 显示根句柄
        eamTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        // 单选
        eamTree.putClientProperty("JTree.lineStyle", "Angled");
        eamTree.setCellRenderer(new FileSelectTreeCellRenderer());
        eamShowPaneJSP = new JScrollPane(eamTree);
        // eamShowPaneJSP = new JScrollPane();
        eamShowPaneJSP.setBounds(40, 40, 350, 200);
        getContentPane().add(eamShowPaneJSP);

        JLabel eamLabel = new JLabel("存放路径：");
//        eamLabel.setToolTipText("需要复制文件到源码时，文件在项目文件的相对路径。");
        eamLabel.setBounds(40, 260, 80, 30);
        getContentPane().add(eamLabel);
        eamPathText = new JTextField();
        eamPathText.setEditable(false);
        eamPathText.setBounds(120, 260, 270, 30);
        getContentPane().add(eamPathText);

        JLabel shieldingLabel = new JLabel("屏蔽路径：");
//        shieldingLabel.setToolTipText("把鼠标移到源码路径那里看看	ヽ(ー_ー)ノ");
        shieldingLabel.setBounds(40, 310, 80, 30);
        getContentPane().add(shieldingLabel);
        codeInputShieldingPathTextField = new JTextField();
        codeInputShieldingPathTextField.setEditable(false);
        codeInputShieldingPathTextField.setBounds(120, 310, 270, 30);
        getContentPane().add(codeInputShieldingPathTextField);

        JLabel codePathLabel = new JLabel("源码路径：");
//        codePathLabel.setToolTipText("最终输出到源码的路径参数，”存放路径“去掉”屏蔽路径“即为源码路径");
        codePathLabel.setBounds(40, 360, 80, 30);
        getContentPane().add(codePathLabel);
        codeInputPathTextField = new JTextField();
        codeInputPathTextField.setEditable(false);
        codeInputPathTextField.setBounds(120, 360, 270, 30);
        getContentPane().add(codeInputPathTextField);

        copyFileToSource = new JCheckBox("复制文件到源码生成目录");
        copyFileToSource.setBounds(40, 305 + 100, 160, 27);
        getContentPane().add(copyFileToSource);

        operatingTip = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "7.png").getAbsolutePath()
        );
        operatingTip.setLocation(copyFileToSource.getX() + copyFileToSource.getWidth() + 30, copyFileToSource.getY() + 3);
        getContentPane().add(operatingTip);

        JLabel label = new JLabel("文件路径分隔符：");
        label.setBounds(40, 340 + 100, 120, 30);
        getContentPane().add(label);
        fileSeparatorField = new JTextField();
        fileSeparatorField.setBounds(170, 340 + 100, 80, 30);
        getContentPane().add(fileSeparatorField);

        cancelDefautFileBT = new MyButton("取消默认文件");
        cancelDefautFileBT.setBounds(260, 340 + 100, 140, 30);
        getContentPane().add(cancelDefautFileBT);
        cancelDefautFileBT.addActionListener(listener);

        defaultFileField = new JTextField();
        defaultFileField.setEditable(false);
        defaultFileField.setBounds(40, 380 + 100, 230, 30);
        getContentPane().add(defaultFileField);
        defaultFileField.setColumns(10);

        defautFileBT = new MyButton("默认文件");
        defautFileBT.addActionListener(listener);
        defautFileBT.setBounds(288, 380 + 100, 100, 30);
        getContentPane().add(defautFileBT);

        eamPutInModel = new MyButton("不使用该窗口的路径设置");
        eamPutInModel.setFocusPainted(false);
        eamPutInModel.setBounds(35, 431 + 100, 230, 30);
        eamPutInModel.addActionListener(eamButtonListener);
        getContentPane().add(eamPutInModel);

        ok.setBounds(290, 431 + 100, 80, 30);
        ok.addActionListener(eamButtonListener);
        getContentPane().add(ok);

        treeNodeLister();
        folderMenu();

        this.setBounds(((int) SysUtil.SCREEN_SIZE.getWidth() / 2) - 260, ((int) SysUtil.SCREEN_SIZE.getHeight() / 2) - 320, 450, 650);

    }

    ActionListener theMenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MyNode selectednode = (MyNode) eamTree.getLastSelectedPathComponent();
            if (e.getSource() == addFolder) {// 如果是新建文件夹菜单项
                newANode(selectednode);
            } else if (e.getSource() == delFolder) {// 如果是删除菜单项
                delCurrentSelectedNodeForSelectedTree();
            } else if (e.getSource() == setFilePathMenuItem) {
                String filePath = TreeUtil.getCorrespondingPath(eamTree.getSelectionPath()),
                        shieldPath = codeInputShieldingPathTextField.getText();
                if (filePath.contains(shieldPath)) {
                    showFilePath(eamTree.getSelectionPath());
                    showCodePath();
//				}else if(shieldPath.contains(filePath)){
//					MyOptionPane.showMessageDialog(null, "请在\""+shieldPath+"\"的子目录里面选需要的目录作为存放路径");
                } else {
//					MyOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	别乱点好吗，\"存放路径\"和\"屏蔽路径\"都不在同一个目录下，我没法屏蔽");
                    codeInputShieldingPathTextField.setText("");
                    showFilePath(eamTree.getSelectionPath());
                    showCodePath();
                }
            } else if (e.getSource() == setCodeInputShieldingPathMenuItem) {
                String filePath = eamPathText.getText(),
                        shieldPath = TreeUtil.getCorrespondingPath(eamTree.getSelectionPath());
                if (filePath.contains(shieldPath)) {
                    showShieldingPath(eamTree.getSelectionPath());
                    showCodePath();
                } else if (shieldPath.contains(filePath)) {
                    if ("".equals(filePath)) {
                        LazyCoderOptionPane.showMessageDialog(FileSelectorEditFrame.this, "∑(っ°Д°;)っ	先选\"存放路径\"吧");
                    } else {
                        LazyCoderOptionPane.showMessageDialog(FileSelectorEditFrame.this, "请在\"" + filePath + "\"的父目录里面选需要的目录作为屏蔽路径");
                    }
                } else {
                    LazyCoderOptionPane.showMessageDialog(FileSelectorEditFrame.this, "∑(っ°Д°;)っ	别乱点好吗，\"存放路径\"和\"屏蔽路径\"都不在同一个目录下，我没法屏蔽");
                }
            }
        }
    };

    ActionListener eamButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == eamPutInModel) {
                eamPathText.setText("");
                codeInputShieldingPathTextField.setText("");
                codeInputPathTextField.setText("");

            } else if (e.getSource() == ok) {
                ok();
            }
        }
    };

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == defautFileBT) {
                copyDefautFile();

            } else if (e.getSource() == cancelDefautFileBT) {
                cancelDefaultFile();
            }
        }
    };


    /**
     * 把文件里的内容（路径）转成数组
     *
     * @param content
     * @return
     */
    public static String[] getPathForFile(String content) {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] temp = content.split(pattern), pathForFile = new String[temp.length];
        for (int i = 0; i < pathForFile.length; i++) {
            pathForFile[i] = temp[i];
        }
        return pathForFile;
    }

    /**
     * 根据TheTemplatePath 路径里面的文件和文件夹生成文件夹树结构
     *
     * @param TheTemplatePath 模板路径
     * @return
     */
    public static MyNode traverseFolder(File TheTemplatePath) {
        MyNode fujiedian = null;
        File file = TheTemplatePath;
        if (file.exists()) {
            File[] files = file.listFiles();
            fujiedian = new MyNode(file.getName(), true, MyNode.CAN_NOT_BE_DEL);
            if (files.length == 0) {
                if (file.isDirectory()) {// 如果是空文件夹
                    fujiedian = new MyNode(file.getName(), true, MyNode.CAN_NOT_BE_DEL);
                    return fujiedian;
                }
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        // 是目录的话，生成节点，并添加里面的节点
                        fujiedian.add(traverseFolder(file2));
                    }
                }
            }
        } else {// 文件不存在
            return null;
        }
        return fujiedian;
    }

    private void copyDefautFile() {
        if (controlLabel != null) {
            File file = FileUtil.selectFile(FileUtil.FILE_ONLY_MODEL, "请选择需要在这里添加的默认文件");
            if (file != null) {
                defaultFileField.setText(file.getName());

                File fileFolder = controlLabel.getFileFolder();
                String path = fileFolder.getAbsolutePath();
                new File(path).mkdirs();
                FileUtil.delAllFile(path);
                try {
                    FileUtil.fileCopyNormal(file,
                            new File(controlLabel.getFileFolder().getAbsolutePath() + File.separator + file.getName()));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cancelDefaultFile() {
        defaultFileField.setText("");
        if (controlLabel != null) {
            File fileFolder = controlLabel.getFileFolder();
            FileUtil.delAllFile(fileFolder.getAbsolutePath());
        }
    }


    public void setText(FileSelectorControlLabel controlLabel) {
        this.eamPathText.setText(controlLabel.getControl().getFilePath());//把模型里面的路径写进eamPathText这个框	codeInputShieldingPathTextField
        fileSeparatorField.setText(controlLabel.getFileSeparator());//写入文件分隔符
        if (controlLabel.getCopyFileToSourceOrNot() == FileSelectorControlLabel.TRUE_) {
            copyFileToSource.setSelected(true);
        } else {
            copyFileToSource.setSelected(false);
        }
        if (controlLabel != null) {
            File fileFolder = controlLabel.getFileFolder();
            File[] list = fileFolder.listFiles();
            if (list.length > 0) {
                for (File tempFile : list) {
                    defaultFileField.setText(tempFile.getName());
                }
            }
        }
        codeInputShieldingPathTextField.setText(controlLabel.getControl().getCodeInputShieldingPath());
        showCodePath();
        addOriginalData(eamPathText.getText(), eamTreeROOT, eamPathText);
    }

    public void setValue() {
        if (controlLabel != null) {
            if (copyFileToSource.isSelected() == true) {
                controlLabel.setCopyFileToSourceOrNot(FileSelectorControlLabel.TRUE_);
            } else {
                controlLabel.setCopyFileToSourceOrNot(FileSelectorControlLabel.FALSE_);
            }
            controlLabel.setFilePath(eamPathText.getText());
            controlLabel.setFileSeparator(fileSeparatorField.getText());
            controlLabel.getControl().setCodeInputShieldingPath(codeInputShieldingPathTextField.getText());
        }

    }


    @Override
    public void ok() {
        setValue();
        LazyCoderOptionPane.showMessageDialog(this, message);
        FileSelectorEditFrame.this.dispose();
    }

    private void folderMenu() {
        folderMenu = new JPopupMenu();
        addFolder = new JMenuItem("添加文件夹");
        addFolder.addActionListener(theMenuListener);
        folderMenu.add(addFolder);
        delFolder = new JMenuItem("删除");
        delFolder.addActionListener(theMenuListener);
        folderMenu.add(delFolder);

        setPathMenu = new JPopupMenu();
        setFilePathMenuItem = new JMenuItem("设置为文件存储路径");
        setFilePathMenuItem.addActionListener(theMenuListener);
        setPathMenu.add(setFilePathMenuItem);
        setCodeInputShieldingPathMenuItem = new JMenuItem("设置为屏蔽路径");
        setCodeInputShieldingPathMenuItem.addActionListener(theMenuListener);
        setPathMenu.add(setCodeInputShieldingPathMenuItem);
    }

    /**
     * 删除当前选择树的选择节点
     */
    private void delCurrentSelectedNodeForSelectedTree() {
        MyNode node = (MyNode) eamTree.getLastSelectedPathComponent();
        if (node.isRoot()) {
            return;
        }
        ((DefaultTreeModel) eamTree.getModel()).removeNodeFromParent(node);
    }

    /**
     * 在选中的节点下新建一个节点
     *
     * @param selectednode
     */
    private void newANode(MyNode selectednode) {
        String getNewName = LazyCoderOptionPane.showInputDialog(this, "请输入新建文件夹名", "", JOptionPane.PLAIN_MESSAGE);
        if (getNewName == null || "".equals(getNewName)) {
            LazyCoderOptionPane.showMessageDialog(this, "新建文件夹名不能为空！");
        } else {
            if (FileUtil.isValidFileName(getNewName.trim()) == false
                    || StringUtil.isSpecialChar(getNewName.trim())) {//判断文件名是否合法，有没有特殊字符
                LazyCoderOptionPane.showMessageDialog(this, "┗( ▔, ▔ )┛	换个名称吧，这名字。。。我感觉不合适");
            } else {
                boolean newTheNode = true;
                for (int i = 0; i < selectednode.getChildCount(); i++) {
                    TreeNode childNode = selectednode.getChildAt(i);
                    if (childNode.toString().equals(getNewName)) {
                        newTheNode = false;
                        LazyCoderOptionPane.showMessageDialog(this, "已存在该子节点");
                        break;
                    }
                }
                if (newTheNode == true) {
                    MyNode newNode = new MyNode(getNewName, true, MyNode.CAN_BE_DEL);
                    treeNodeLister();
                    ((DefaultTreeModel) eamTree.getModel()).insertNodeInto(newNode, selectednode,
                            selectednode.getChildCount());
                    eamTree.expandPath(eamTree.getSelectionPath());
                }
            }
        }
    }

    private void addOriginalData(String content, DefaultMutableTreeNode theNode, JTextField pathTextField) {
        if ("".equals(content.trim()) == false) {
            String[] pathList = getPathForFile(content);
            addNewNode(theNode, pathList);
        }
        DefaultTreeModel model = new DefaultTreeModel(theNode);
        eamTree.setModel(model);
        pathTextField.setText(content);
    }

    /**
     * 添加一个能删的新节点
     *
     * @param theNode
     * @param pathList
     */
    private void addNewNode(DefaultMutableTreeNode theNode, String[] pathList) {
        DefaultMutableTreeNode node = theNode;
        for (int i = 0; i < pathList.length; i++) {
            boolean haveOrNot = false;
            for (int j = 0; j < node.getChildCount(); j++) {
                if (node.getChildAt(j).toString().equals(pathList[i])) {
                    node = (DefaultMutableTreeNode) node.getChildAt(j);
                    haveOrNot = true;
                    break;
                }
            }
            if (haveOrNot == false) {
                MyNode newNode = new MyNode(pathList[i], true, MyNode.CAN_BE_DEL);
                node.add(newNode);
                node = newNode;
            }
        }
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
    }

    /*
     * 树节点的监听器：右键菜单，左键实时把选择的地址传给文本框
     */
    private void treeNodeLister() {
        eamTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = eamTree.getRowForLocation(e.getX(), e.getY());
                TreePath path = eamTree.getPathForRow(row); // 按了单选框后，从根目录到单选框文件的路径
                if (path == null) {
                    return;
                } else {
                    eamTree.setSelectionPath(path);
//					showCurrentTreePath(path);
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        setPathMenu.show(eamTree, e.getX(), e.getY());
                        //showFilePath(path);
                    } else if (e.isMetaDown()) {// 右键弹出鼠标，如果是新添加的文件夹节点，可以删
                        MyNode node = (MyNode) path.getLastPathComponent();// 单选框的文件的文件名
                        if (node.isCanDel() == MyNode.CAN_NOT_BE_DEL) {
                            delFolder.setEnabled(false);
                        } else {
                            delFolder.setEnabled(true);
                        }
                        folderMenu.show(eamTree, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    private void showCodePath() {
        String str = controlLabel.getControl().getCodeInputPath(eamPathText.getText(), codeInputShieldingPathTextField.getText(), fileSeparatorField.getText());
        codeInputPathTextField.setText(str);
    }

    /**
     * 把当前选择的路作为文件存放路径
     *
     * @param path
     */
    private void showFilePath(TreePath path) {
        String selectedPath = TreeUtil.getCorrespondingPath(path);
        eamPathText.setText(selectedPath);
        // 响应事件更新树
//		if (path != null) {
//			MyNode node = (MyNode) path.getLastPathComponent();// 单选框的文件的文件名
//			// 响应事件更新树
//			((DefaultTreeModel) eamTree.getModel()).nodeChanged(node);
//			eamTree.revalidate();
//			eamTree.repaint();
//		}
    }

    /**
     * 把当前选择的路作为屏蔽路径
     *
     * @param path
     */
    private void showShieldingPath(TreePath path) {
        String selectedPath = TreeUtil.getCorrespondingPath(path);
        codeInputShieldingPathTextField.setText(selectedPath);
    }

    class FileSelectTreeCellRenderer extends DefaultTreeCellRenderer {

        /**
         *
         */
        private static final long serialVersionUID = -6512622396905517842L;

        /**
         * 重写父类DefaultTreeCellRenderer的方法
         */
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {

            // 执行父类原型操作
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if (leaf) {
                this.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
            }
            return this;
        }

    }
}
