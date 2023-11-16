package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.component.TreeUtil;

import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.regex.Pattern;

public abstract class AbstractMyFileTree extends JTree {

    /**
     *
     */
    private static final long serialVersionUID = 443078278735601602L;

    private JPopupMenu folderMenu;
    private JMenuItem delFolder, addFolder;

    private File pathFile;

    private MyNode rootNode;

    /**
     * @param pathFile     生成树的文件路径
     * @param pathStr      再加上的路径参数
     * @param rootShowText 根目录显示内容，不需要写null
     */
    public AbstractMyFileTree(File pathFile, String pathStr, String rootShowText) {
        // TODO Auto-generated constructor stub
        super();

        rootNode = createFolder(pathFile, MyNode.CAN_NOT_BE_DEL);
        rostoreNodeBy(pathStr);
        if (rootShowText != null) {
            rootNode.setUserObject(rootShowText);
        }

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        setModel(treeModel);
        this.pathFile = pathFile;
        folderMenuInit();
        treeNodeLister();
        this.setCellRenderer(new MyTreeCellRenderer());

    }

    /*
     * 把文件里的内容（路径）转成数组
     */
    private static String[] getPathForFile(String content) {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] temp = content.split(pattern);
        return temp;
    }

    /**
     * 根据path内的文件生成树
     *
     * @param pathFile
     * @return
     */
    private static MyNode createFolder(File pathFile, boolean canDel) {
        MyNode fujiedian = null;
        File file = pathFile;

        if (file.exists()) {
            File[] files = file.listFiles();
            fujiedian = new MyNode(pathFile.getName(), true, canDel);
            if (files.length == 0) {
                if (file.isDirectory()) {// 如果是空文件夹
                    fujiedian = new MyNode(file.getName(), true, canDel);
                    return fujiedian;
                }
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        // 是目录的话，生成节点，并添加里面的节点
                        fujiedian.add(createFolder(file2, canDel));
                    }
                }
            }
        } else {// 文件不存在
            return null;
        }
        return fujiedian;
    }

    private ActionListener theMenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MyNode selectednode = (MyNode) getLastSelectedPathComponent();
            if (e.getSource() == addFolder) {// 如果是新建文件夹菜单项
                newANode(selectednode);
            } else if (e.getSource() == delFolder) {// 如果是删除菜单项
                delCurrentSelectedNodeForSelectedTree();
            }
        }
    };


    private void folderMenuInit() {
        folderMenu = new JPopupMenu();
        addFolder = new JMenuItem("添加文件夹");
        addFolder.addActionListener(theMenuListener);
        folderMenu.add(addFolder);
        delFolder = new JMenuItem("删除");
        delFolder.addActionListener(theMenuListener);
        folderMenu.add(delFolder);
    }

    /**
     * 在选中的节点下新建一个节点
     *
     * @param selectednode
     */
    private void newANode(MyNode selectednode) {
        String getNewName = LazyCoderOptionPane.showInputDialog(null, "请输入新建文件夹名", "", JOptionPane.PLAIN_MESSAGE);
        if (getNewName == null || "".equals(getNewName)) {
            LazyCoderOptionPane.showMessageDialog(null, "新建文件夹名不能为空！");
        } else {
            if (FileUtil.isValidFileName(getNewName.trim()) == false
                    || StringUtil.isSpecialChar(getNewName.trim())) {//判断文件名是否合法，有没有特殊字符
                LazyCoderOptionPane.showMessageDialog(null, "┗( ▔, ▔ )┛	换个名称吧，这名字。。。我感觉不合适");
            } else {
                boolean newTheNode = true;
                for (int i = 0; i < selectednode.getChildCount(); i++) {
                    TreeNode childNode = selectednode.getChildAt(i);
                    if (childNode.toString().equals(getNewName)) {
                        newTheNode = false;
                        LazyCoderOptionPane.showMessageDialog(null, "已存在该子节点");
                        break;
                    }
                }
                if (newTheNode == true) {
                    MyNode newNode = new MyNode(getNewName, true, MyNode.CAN_BE_DEL);
                    treeNodeLister();
                    ((DefaultTreeModel) getModel()).insertNodeInto(newNode, selectednode, selectednode.getChildCount());
                    expandPath(getSelectionPath());
                }
                TreeUtil.expandTree(this);
            }
        }
    }

    private void rostoreNodeBy(String content) {
        if (content != null) {
            if ("".equals(content) == false) {
                String[] pathList = getPathForFile(content);
                MyNode findNode = this.rootNode;
                TreeNode treeNodeTemp;
                for (int i = 0; i < pathList.length; i++) {
                    boolean have_or_not = false;

                    for (int a = 0; a < findNode.getChildCount(); a++) {// 在对应的叶节点一个个搜
                        treeNodeTemp = findNode.getChildAt(a);

                        if (treeNodeTemp.toString().equals(pathList[i])) {// 如果有个叶节点，就有该路径对应的文件夹
                            findNode = (MyNode) treeNodeTemp;
                            have_or_not = true;
                            break;
                        }
                    }

                    if (have_or_not == false) {
                        MyNode newNode = new MyNode(pathList[i], true, MyNode.CAN_BE_DEL);
                        findNode.add(newNode);
                        findNode = newNode;
                    }

                }
            }
        }
    }

    /**
     * 删除当前选择树的选择节点
     */
    private void delCurrentSelectedNodeForSelectedTree() {
        MyNode node = (MyNode) getLastSelectedPathComponent();
        if (node.isRoot()) {
            return;
        }
        ((DefaultTreeModel) getModel()).removeNodeFromParent(node);
    }

    /**
     * 点击以后对应路径要怎么处理
     */
    protected abstract void doSomethingWhenClickedAbout(String correspondingPath);

    /**
     * 树节点的监听器：右键菜单
     */
    private void treeNodeLister() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = AbstractMyFileTree.this.getRowForLocation(e.getX(), e.getY());
                TreePath path = AbstractMyFileTree.this.getPathForRow(row); // 按了单选框后，从根目录到单选框文件的路径
                if (path == null) {
                    return;
                } else {
                    String correspondingPath = TreeUtil.getCorrespondingPath(path);
                    doSomethingWhenClickedAbout(correspondingPath);

                    AbstractMyFileTree.this.setSelectionPath(path);
                    if (e.isMetaDown()) {// 右键弹出鼠标，如果是新添加的文件夹节点，可以删
                        MyNode node = (MyNode) path.getLastPathComponent();// 单选框的文件的文件名
                        // if (!node.isRoot()) {
                        if (node.isCanDel() == MyNode.CAN_NOT_BE_DEL) {
                            delFolder.setEnabled(false);
                        }
                        if (node.getAllowsChildren() == true) {// 一般为文件夹的叶节点
                            addFolder.setEnabled(true);
                        } else {// 一般为文件的叶节点
                            addFolder.setEnabled(false);
                        }
                        folderMenu.show(AbstractMyFileTree.this, e.getX(), e.getY());
                        // }
                    }
                }
            }
        });
    }

    public void updateTree() {
        if (pathFile != null) {
            DefaultTreeModel treeModel = new DefaultTreeModel(createFolder(pathFile, MyNode.CAN_NOT_BE_DEL));
            setModel(treeModel);
            TreeUtil.expandTree(this);
        }
    }

    private class MyTreeCellRenderer extends DefaultTreeCellRenderer {

        /**
         *
         */
        private static final long serialVersionUID = -5402160759883984731L;

        public final ImageIcon treeClosedIcon = new ImageIcon(
                SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator + "base_code_content_edit"
                        + File.separator + "tree" + File.separator + "TreeClosed.png"),
                treeOpenIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator
                        + "base_code_content_edit" + File.separator + "tree" + File.separator + "TreeOpen.png");

        /**
         * 重写父类DefaultTreeCellRenderer的方法
         */
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {

            // 执行父类原型操作
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if (((MyNode) value).isCanDel() == true) {// 用户添加的文件夹
                this.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
            } else {// 原来的文件夹
                if (expanded == true) {// 如果原来是展开的
                    this.setIcon(treeOpenIcon);
                } else {// 收起的
                    this.setIcon(treeClosedIcon);
                }
            }
            return this;
        }

    }

}
