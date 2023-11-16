package com.lazycoder.uidatasourceedit.generalset;

import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.component.TreeUtil;
import com.lazycoder.uiutils.mycomponent.FileSettingTreeCellRenderer;
import com.lazycoder.utils.FileUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


public class TemplatePane extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 4965293042034557941L;

    private JPopupMenu theTreeDelMenu;
    private JMenuItem treeDelMenu;
    private JTree tree;

    public TemplatePane() {
        // TODO Auto-generated constructor stub
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();

        DefaultMutableTreeNode model = TreeUtil.traverseFolder(
                DatabaseFileStructure.getTemplateFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName()));
        model.setUserObject("生成项目模板");
        tree = new JTree(model);
        tree.setRootVisible(false);
        tree.setCellRenderer(new FileSettingTreeCellRenderer());
//        tree.setRootVisible(false);
        setViewportView(tree);
        addTreeDelMenu();
        treeNodeLister();
        TreeUtil.expandTree(tree);
    }

    /**
     * 刷新面板
     */
    public void updatePane() {
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();
        DefaultTreeModel model = new DefaultTreeModel(TreeUtil.traverseFolder(
                DatabaseFileStructure.getTemplateFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName())));
        tree.setModel(model);
        TreeUtil.expandTree(tree);
    }

    private void addTreeDelMenu() {
        theTreeDelMenu = new JPopupMenu();
        treeDelMenu = new JMenuItem("删除");
        treeDelMenu.addActionListener(theMenuListener);
        theTreeDelMenu.add(treeDelMenu);
    }

    private ActionListener theMenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == treeDelMenu) {// 如果是树节点删除菜单项
                // 删除文件
                DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                TreeNode[] pathList = selectednode.getPath();
                String path = TreeUtil.getCorrespondingPath(pathList);
                File sysDataFileFolder = SysFileStructure.getDataFileFolder();
                File file = new File(
                        DatabaseFileStructure.getTemplateFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName())
                                .getAbsolutePath() + File.separator + path);
                if (file.isFile()) {
                    file.delete();
                }else if(file.isDirectory()){
                    FileUtil.delFolder(file.getAbsolutePath());
                }

                updatePane();
            }
        }
    };

    /*
     * 树节点的监听器：右键菜单
     */
    private void treeNodeLister() {
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = tree.getRowForLocation(e.getX(), e.getY());
                TreePath path = tree.getPathForRow(row); // 按了单选框后，从根目录到单选框文件的路径
                if (path == null) {
                    return;
                } else {
                    tree.setSelectionPath(path);
                    if (e.isMetaDown()) {// 右键弹出鼠标，如果是新添加的文件夹节点，可以删
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();// 单选框的文件的文件名
                        if (!node.isRoot()) {
                            theTreeDelMenu.show(tree, e.getX(), e.getY());
                        }
                    }
                }
            }

        });
    }

}
