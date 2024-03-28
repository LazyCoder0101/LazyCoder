package com.lazycoder.uicodegeneration.moduleselect;

import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.fileStructure.SysFileStructure;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.Border;
import lombok.Getter;


/**
 * 改自 https://www.jiweichengzhu.com/article/5ff1be1e8abe463794aa1011b4bcff96
 *
 * @author admin
 */
public class ClassNameNode extends AbstractModuleSelectBaseNode {

    /**
     *
     */
    private static final long serialVersionUID = 16184360989604506L;

    private final static ImageIcon ARROW_LEFT_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
            + "ModuleSelect" + File.separator + "arrow_left.png"),
            ARROW_DOWN_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator + "ModuleSelect"
                    + File.separator + "arrow_down.png");

    private final static Border BORDER = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY);

    private JLabel iconLabel;

    @Getter
    private JCheckBox classNameCheckBox = null;

    @Getter
    private TheClassification classification = null;

    public ClassNameNode(TheClassification classification) {
        // TODO Auto-generated constructor stub
        this.classification = classification;
        initNodeGUI();
    }

    /**
     * 自定义分组UI
     */
    @Override
    protected void initNodeGUI() {
        super.initNodeGUI();
//		cateContent.setOpaque(false);
        // 这里大家注意，当我们写好UI之后可能会发现他的颜色不太对，
        // 这时候千万不要用上面那句，不然当我们想再次改变其颜色的时候，就生效不了
        // 红绿蓝分别为255的这个颜色趋近于透明，我们可以用它来代替setOpaque
//		cateContent.setBackground(new Color(255,255,255));
        // 突然发现置成null也可以
        nodePane.setPreferredSize(new Dimension(320, 30));
//		nodePane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        iconLabel = new JLabel(ARROW_LEFT_ICON);
        iconLabel.setBounds(10, 10, ARROW_LEFT_ICON.getIconWidth(), ARROW_LEFT_ICON.getIconHeight());
        nodePane.add(iconLabel);

        classNameCheckBox = new JCheckBox(classification.getClassName());
        classNameCheckBox.setBounds(23, 0, 132, 30);
        classNameCheckBox.setEnabled(false);
        nodePane.add(classNameCheckBox);

        nodePane.setBorder(BORDER);
    }

    /**
     * 展开
     */
    public void setExpanded() {
        iconLabel.setIcon(ARROW_DOWN_ICON);
    }

    /**
     * 折叠
     */
    public void setContract() {
        iconLabel.setIcon(ARROW_LEFT_ICON);
    }

    @Override
    public JCheckBox getCurrentCheckBox() {
        return classNameCheckBox;
    }
}
