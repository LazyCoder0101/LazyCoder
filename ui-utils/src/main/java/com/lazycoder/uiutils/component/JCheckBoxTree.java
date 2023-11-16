package com.lazycoder.uiutils.component;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;

/**
 * 该部分代码摘自网上
 */
public class JCheckBoxTree extends JTree {


    private static final long serialVersionUID = 1L;

    public JCheckBoxTree(CheckNode checkNode) {
        super(checkNode);
        this.setCellRenderer(new CheckRenderer());
        this.setShowsRootHandles(true); // 显示根句柄
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); // 单选
        this.putClientProperty("JTree.lineStyle", "Angled");
        this.addLister(this);
    }

    public static CheckNode traverseFolder(String path) {
        CheckNode fujiedian = null;
        CheckNode temp;
        File file = new File(path);

        if (file.exists()) {
            File[] files = file.listFiles();
            fujiedian = new CheckNode(new File(path).getName());
            if (files.length == 0) {
                if (file.isDirectory()) {// 如果是空文件夹
                    fujiedian = new CheckNode(file.getName(), true);
                    return fujiedian;
                }
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        // 是目录的话，生成节点，并添加里面的节点
                        fujiedian.add(traverseFolder(file2.getAbsolutePath()));
                    } else {
                        // 是文件的话直接生成节点，并把该节点加到对应父节点上
                        temp = new CheckNode(file2.getName());
                        fujiedian.add(temp);
                    }
                }
            }
        } else {// 文件不存在
            return null;
        }
        return fujiedian;
    }

    /***
     * 添加点击事件使其选中父节点时 子节点也选中
     *
     * @param tree
     */
    private void addLister(final JTree tree) {
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = tree.getRowForLocation(e.getX(), e.getY());
                TreePath path = tree.getPathForRow(row); // 按了单选框后，从根目录到单选框文件的路径
                if (path != null) {
                    CheckNode node = (CheckNode) path.getLastPathComponent();// 单选框的文件的文件名
                    node.setSelected(!node.isSelected);
                    if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION) {
                        if (node.isSelected) // 勾上后
                        {
                            tree.expandPath(path);
                        } else {
                            if (!node.isRoot()) // 把勾去掉后
                            {
                                tree.collapsePath(path);
                            }
                        }
                    }
                    // 响应事件更新树
                    ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
                    tree.revalidate();
                    tree.repaint();
                }
            }

        });
    }

    public static class CheckNode extends DefaultMutableTreeNode {

        public final static int SINGLE_SELECTION = 0;
        public final static int DIG_IN_SELECTION = 4;
        private static final long serialVersionUID = 1L;
        public boolean isSelected;
        protected int selectionMode;

        public CheckNode() {
            this(null);
        }

        public CheckNode(Object userObject, boolean allowsChildren) {
            super(userObject, allowsChildren);
        }

        public CheckNode(Object userObject) {
            this(userObject, true, false);
        }

        public CheckNode(Object userObject, boolean allowsChildren, boolean isSelected) {
            super(userObject, allowsChildren);
            this.isSelected = isSelected;
            setSelectionMode(DIG_IN_SELECTION);
        }

        public int getSelectionMode() {
            return selectionMode;
        }

        public void setSelectionMode(int mode) {
            selectionMode = mode;
        }

        public boolean isSelected() {
            return isSelected;
        }

        /**
         * 选中父节点时也级联选中子节点
         *
         * @param isSelected
         */
        @SuppressWarnings("unchecked")
        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
            if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
                Enumeration e = children.elements();
                while (e.hasMoreElements()) {
                    CheckNode node = (CheckNode) e.nextElement();
                    node.setSelected(isSelected);
                }
            }
        }
    }

    private class CheckRenderer extends JPanel implements TreeCellRenderer {

        /**
         *
         */
        private static final long serialVersionUID = 3280226926738272543L;

        protected JCheckBox check;

        protected TreeLabel label;

        public CheckRenderer() {
            setLayout(null);
            add(check = new JCheckBox());
            add(label = new TreeLabel());
            check.setBackground(UIManager.getColor("Tree.textBackground"));
            label.setForeground(UIManager.getColor("Tree.textForeground"));
        }

        /**
         * 改变的节点的为JLabel和JChekBox的组合
         */
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, hasFocus);
            setEnabled(tree.isEnabled());
            check.setSelected(((CheckNode) value).isSelected());
            label.setFont(tree.getFont());
            label.setText(stringValue);
            label.setSelected(isSelected);
            label.setFocus(hasFocus);
            if (leaf) {
//                label.setIcon(UIManager.getIcon("Tree.leafIcon"));
//                label.setIcon(null);//把leaf前的图片去掉
                label.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
            } else if (expanded) {
                label.setIcon(UIManager.getIcon("Tree.openIcon"));
            } else {
                label.setIcon(UIManager.getIcon("Tree.closedIcon"));
            }
            return this;
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension dCheck = check.getPreferredSize();
            Dimension dLabel = label.getPreferredSize();
            return new Dimension(dCheck.width + dLabel.width,
                    (dCheck.height < dLabel.height ? dLabel.height : dCheck.height));
        }

        @Override
        public void doLayout() {
            Dimension dCheck = check.getPreferredSize();
            Dimension dLabel = label.getPreferredSize();
            int yCheck = 0;
            int yLabel = 0;
            if (dCheck.height < dLabel.height) {
                yCheck = (dLabel.height - dCheck.height) / 2;
            } else {
                yLabel = (dCheck.height - dLabel.height) / 2;
            }
            check.setLocation(0, yCheck);
            check.setBounds(0, yCheck, dCheck.width, dCheck.height);
            label.setLocation(dCheck.width, yLabel);
            label.setBounds(dCheck.width, yLabel, dLabel.width, dLabel.height);
        }

        @Override
        public void setBackground(Color color) {
            if (color instanceof ColorUIResource) {
                color = null;
            }
            super.setBackground(color);
        }

        private class TreeLabel extends JLabel {
            private static final long serialVersionUID = 1L;

            private boolean isSelected;

            private boolean hasFocus;

            public TreeLabel() {
            }

            @Override
            public void setBackground(Color color) {
                if (color instanceof ColorUIResource) {
                    color = null;
                }
                super.setBackground(color);
            }

            @Override
            public void paint(Graphics g) {
                String str;
                if ((str = getText()) != null) {
                    if (0 < str.length()) {
                        if (isSelected) {
                            g.setColor(UIManager.getColor("Tree.selectionBackground"));
                        } else {
                            g.setColor(UIManager.getColor("Tree.textBackground"));
                        }
                        Dimension d = getPreferredSize();
                        int imageOffset = 0;
                        Icon currentI = getIcon();
                        if (currentI != null) {
                            imageOffset = currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
                        }
                        g.fillRect(imageOffset, 0, d.width - 1 - imageOffset, d.height);
                        if (hasFocus) {
                            g.setColor(UIManager.getColor("Tree.selectionBorderColor"));
                            g.drawRect(imageOffset, 0, d.width - 1 - imageOffset, d.height - 1);
                        }
                    }
                }
                super.paint(g);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension retDimension = super.getPreferredSize();
                if (retDimension != null) {
                    retDimension = new Dimension(retDimension.width + 3, retDimension.height);
                }
                return retDimension;
            }

            public void setSelected(boolean isSelected) {
                this.isSelected = isSelected;
            }

            public void setFocus(boolean hasFocus) {
                this.hasFocus = hasFocus;
            }
        }
    }
}