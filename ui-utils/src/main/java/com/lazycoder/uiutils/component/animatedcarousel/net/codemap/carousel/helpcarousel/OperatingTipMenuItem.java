package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class OperatingTipMenuItem extends JMenuItem {

    private String path;

    public OperatingTipMenuItem(String path) {
        super("操作提示", OperatingTipButton.questionIcon);
        setRolloverIcon(OperatingTipButton.questionHoverIcon);
        setPressedIcon(OperatingTipButton.questionPressIcon);
        this.path = path;
        addActionListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            OperatingTipAnimatedCarouselPane.creatHelpAnimatedCarouselFrame(path);
        }
    };

}
