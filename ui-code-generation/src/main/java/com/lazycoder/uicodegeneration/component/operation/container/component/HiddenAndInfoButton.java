package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;

public class HiddenAndInfoButton extends FoldButton {//DefaultButton {

    /**
     *
     */
    private static final long serialVersionUID = 942052105004137816L;

    /**************************************************************************/
    private static ImageIcon
            attributeIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() +
                            File.separator + "CodeGeneration" + File.separator + "more" + File.separator + "more_normal.png"),
            expandTrueIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() +
                            File.separator + "CodeGeneration" + File.separator + "more" + File.separator + "expand_true.png"),
            expandFalseIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() +
                            File.separator + "CodeGeneration" + File.separator + "more" + File.separator + "expand_false.png");
//			more_highlight_icon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "more"
//					+ File.separator + "属性_rollover.png"),
//			more_pressed_icon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "more"
//					+ File.separator + "属性_pressed.png");

    private boolean hiddenState = false;

    public HiddenAndInfoButton(boolean hiddenState, boolean expanded) {
        /**************************************************************************/
        super(expanded);
        this.hiddenState = hiddenState;
        init();
    }

    private void init() {
//		this.setPressedIcon(more_pressed_icon);
//		this.setRolloverIcon(more_highlight_icon);
//		this.setFocusPainted(false);
        Dimension dd ;
        if (hiddenState){
            setIcon(expandTrueIcon);
            dd = new Dimension(expandTrueIcon.getIconWidth(), expandTrueIcon.getIconHeight() + 20);
        }else {
            setIcon(attributeIcon);
            dd = new Dimension(attributeIcon.getIconWidth(), attributeIcon.getIconHeight() + 6);
        }
        this.setMaximumSize(dd);
        this.setMinimumSize(dd);
        this.setPreferredSize(dd);
        this.setSize(dd);
        this.setUI(new FoldButtonUI());
        setContentAreaFilled(false);
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if(this.hiddenState) {
            if (expanded == true) {
                setIcon(expandTrueIcon);
            } else {
                setIcon(expandFalseIcon);
            }
        }
    }

}
