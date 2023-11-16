package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 点击该按钮，隐藏或展开隐藏 隐藏的控制输入面板
 *
 * @author Administrator
 */
public class CodeHiddenControlButton extends FoldButton {

    /**
     *
     */
    private static final long serialVersionUID = 8422142395539031623L;

    private static ImageIcon
            expandTrueIcon = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() +
                    File.separator + "CodeGeneration" + File.separator + "more" + File.separator + "expand_true_big.png"),
            expandFalseIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() +
                            File.separator + "CodeGeneration" + File.separator + "more" + File.separator + "expand_false_big.png");

    public CodeHiddenControlButton(boolean expanded) {
        super(expanded);
        // TODO Auto-generated constructor stub
        setHorizontalAlignment(JButton.RIGHT);
        setIconTextGap(6);
        setIcon(expandTrueIcon);

        setUI(new FoldButtonUI());
//		setBackground(Color.cyan);
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (expanded == true) {
            setIcon(expandTrueIcon);
        } else {
            setIcon(expandFalseIcon);
        }
    }

}
