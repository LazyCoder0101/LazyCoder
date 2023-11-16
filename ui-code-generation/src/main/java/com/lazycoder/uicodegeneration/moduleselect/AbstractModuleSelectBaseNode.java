package com.lazycoder.uicodegeneration.moduleselect;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class AbstractModuleSelectBaseNode extends DefaultMutableTreeNode {

    /**
     *
     */
    private static final long serialVersionUID = -2336538929886223549L;

    public AbstractModuleSelectBaseNode() {
        super();
    }

    protected JPanel nodePane;

    public JPanel getNodeView() {
        return nodePane;
    }

    protected void initNodeGUI() {
        nodePane = new JPanel();
        nodePane.setLayout(null);
//		nodePane.setOpaque(false);
        nodePane.setBackground(null);
//		nodePane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    /**
     * 获取这个树形结构对应的单选框
     *
     * @return
     */
    public abstract JCheckBox getCurrentCheckBox();

}
