package com.lazycoder.uicodegeneration.moduleselect;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ModuleSelectCellRenderer extends DefaultTreeCellRenderer {

    /**
     *
     */
    private static final long serialVersionUID = 7986609487268103433L;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {
        // TODO Auto-generated method stub
        if (value instanceof AbstractModuleSelectBaseNode) {
            AbstractModuleSelectBaseNode node = (AbstractModuleSelectBaseNode) value;
            if (node.getLevel() == 1) {
                if (value instanceof ClassNameNode) {
                    ClassNameNode classNameNode = (ClassNameNode) value;
                    // 是否展开
                    if (expanded) {
                        classNameNode.setExpanded();
                    } else {
                        classNameNode.setContract();
                    }
                }
            }
            return node.getNodeView();
        }
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }

}
