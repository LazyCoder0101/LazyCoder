package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;

/**
 * 根据原来相应部分代码稍作修改
 */
public class OperatingTipButton extends MyButton {

    static ImageIcon questionIcon = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator + "help" + File.separator + "question.png"),
            questionHoverIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator + "help" + File.separator + "question_hover.png"),
            questionPressIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator + "help" + File.separator + "question_press.png");

    private String path;

    public OperatingTipButton(String path) {
        super(questionIcon);
        this.path = path;
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setSize(questionIcon.getIconWidth(), questionIcon.getIconHeight());

        setRolloverIcon(questionHoverIcon);
        setPressedIcon(questionPressIcon);
        addActionListener(actionListener);
        setToolTipText("(～￣▽￣)～  可看对应操作提示");
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            OperatingTipAnimatedCarouselPane.creatHelpAnimatedCarouselFrame(path);
        }
    };

}

